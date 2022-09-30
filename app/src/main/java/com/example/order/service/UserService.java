package com.example.order.service;

import android.content.Context;
import android.util.Log;

import com.example.order.object.User;
import com.example.order.tool.DownloadTool;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UserService {
    private static DatabaseHelper dbHelper;
    public static DatabaseHelper getDbHelper() {
        return dbHelper;
    }
    public  String ans;
    private String u_name;
    private String u_password;
    public UserService(Context context){
        dbHelper=new DatabaseHelper(context);
    }
    private User user;
    public String login(String username,String password){
        u_name=username;
        u_password=password;
        logincall callable = new logincall();
        //将实现Callable接口的对象作为参数创建一个FutureTask对象
        FutureTask<String> task = new FutureTask<>(callable);
        //创建线程处理当前callable任务
        Thread thread = new Thread(task);
        //开启线程
        thread.start();
        //获取到call方法的返回值
        try {
            String result = task.get();
            ans=result;
            Log.e("text", "result" +ans );
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ans;
    }
    public String  register(User user){
        this.user=user;
        rejicall callable = new rejicall();
        //将实现Callable接口的对象作为参数创建一个FutureTask对象
        FutureTask<String> task = new FutureTask<>(callable);
        //创建线程处理当前callable任务
        Thread thread = new Thread(task);
        //开启线程
        thread.start();
        //获取到call方法的返回值
        try {
            String result = task.get();
            ans=result;
            Log.e("text", "result" +ans );
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
            return ans;
    }
    class logincall implements Callable<String>
    {
        @Override
        public String call() throws Exception {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()//创建requestbody
                    .setType(MultipartBody.FORM)//传参
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"u_username\""),
                            RequestBody.create(null, u_name))
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"u_password\""),
                            RequestBody.create(null, u_password))
                    .build();
            String url = DownloadTool.url+"/login";
            System.out.println("----------------------------------------------------");
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {//回调方法
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("text", "failure upload!" + e.getMessage());
                }


                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.i("text", "success upload!");
                    ans = response.body().string();
                }
            });
            Thread.sleep(1000);
            return ans;
        }
    }
    class rejicall implements Callable<String>
    {
        @Override
        public String call() throws Exception {//创建带回调方法的线程进行网络请求
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()//创建requestbody
                    .setType(MultipartBody.FORM)//传参
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"u_username\""),
                            RequestBody.create(null, user.getU_username()))
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"u_password\""),
                            RequestBody.create(null, user.getU_password()))
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"u_sex\""),
                            RequestBody.create(null, user.getU_sex()))
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"u_age\""),
                            RequestBody.create(null, user.getU_age()+""))
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"u_phone\""),
                            RequestBody.create(null, user.getU_phone()+""))
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"u_hobby\""),
                            RequestBody.create(null, user.getU_hobby()+""))
                    .build();
            String url = DownloadTool.url +"/register";
            System.out.println("----------------------------------------------------");
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {//回调方法
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("text", "failure upload!" + e.getMessage());
                }


                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.i("text", "success upload!");
                    ans = response.body().string();
                }
            });
            Thread.sleep(1000);
            return ans;
        }
    }
}
