package com.example.musicapp.tool;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CallAble {
    public static class askSinger implements Callable<String> //调用获得歌手列表回调函数
    {   String ans;
        @Override
        public String call() throws Exception {//创建带回调方法的线程进行网络请求
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()//创建requestbody
                    .setType(MultipartBody.FORM)//传参
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"u_username\""),
                            RequestBody.create(null, "ss"))
                    .build();
            String url = DownloadTool.url +"/askSinger";
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
                    Log.i("text", "run ask singer !");
                    ans = response.body().string();
                    Log.i("text", ans);
                }
            });
            Thread.sleep(1000);//等待返回
            return ans;
        }
    }
    public static class askMusic implements Callable<String> //获取歌曲列表回调函数
    {   String ans;
        @Override
        public String call() throws Exception {//创建带回调方法的线程进行网络请求
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()//创建requestbody
                    .setType(MultipartBody.FORM)//传参
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"u_username\""),
                            RequestBody.create(null, "ss"))
                    .build();
            String url = DownloadTool.url +"/askMusic";
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
    public static class askUser implements Callable<String>//获取用户列表回调函数
    {   String ans;
        @Override
        public String call() throws Exception {//创建带回调方法的线程进行网络请求
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()//创建requestbody
                    .setType(MultipartBody.FORM)//传参
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"u_username\""),
                            RequestBody.create(null, "ss"))
                    .build();
            String url = DownloadTool.url +"/askUserList";
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
