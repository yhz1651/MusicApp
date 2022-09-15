package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.object.User;
import com.example.musicapp.service.UserService;
import com.example.musicapp.tool.DownloadTool;
import com.google.gson.Gson;

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

public class ChangeUserInfoActivity extends AppCompatActivity {
    private String ans;
    private User person;
    private String searchid;
    private User user;
    TextView sex;
    EditText username;
    EditText password;
    EditText age;
    EditText phone;
    EditText xingquregister;
    Button change_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_userinfo);
        Intent intent =  getIntent();
        searchid = intent.getStringExtra("id");
        ask_user();
        findViews();
        username.setText(person.getU_username());
        password.setText(person.getU_password());
        sex.setText(person.getU_sex());
        age.setText(person.getU_age()+"");
        phone.setText(person.getU_phone());
        xingquregister.setText(person.getU_hobby());
        change_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name=username.getText().toString().trim();
                String pass=password.getText().toString().trim();
                int ag= Integer.parseInt(age.getText().toString().trim());
                String ph=phone.getText().toString().trim();
                String xingqustr=xingquregister.getText().toString().trim();
                Log.i("TAG",name+"_"+pass+"_"+xingqustr+"_");
                UserService uService=new UserService(ChangeUserInfoActivity.this);
                User user=new User();
                user.setU_username(name);
                user.setU_password(pass);
                user.setU_age(ag);
                user.setU_phone(ph);
                user.setU_hobby(xingqustr);
                register(user);
                Toast.makeText(ChangeUserInfoActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    public void  ask_user(){
        ChangeUserInfoActivity.askUser callable = new ChangeUserInfoActivity.askUser();
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
        System.out.println("answer");
        Gson gson = new Gson();
        person = gson.fromJson(ans, User.class);
        System.out.println(person);
    }
    class askUser implements Callable<String>
    {   String ans;
        @Override
        public String call() throws Exception {//创建带回调方法的线程进行网络请求
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()//创建requestbody
                    .setType(MultipartBody.FORM)//传参
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"u_id\""),
                            RequestBody.create(null, searchid))
                    .build();
            String url = DownloadTool.url +"/askUser";
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

    private void findViews() {
        username = (EditText) findViewById(R.id.usernameRegister);
        password = (EditText) findViewById(R.id.passwordRegister);
        age = (EditText) findViewById(R.id.ageRegister);
        sex = (TextView)findViewById(R.id.sex);
        phone = (EditText) findViewById(R.id.phoneRegister);
        xingquregister = (EditText) findViewById(R.id.xingquRegister);
        change_info = (Button) findViewById(R.id.change_info);
    }
    public String  register(User user){
        this.user=user;
        ChangeUserInfoActivity.rejicall callable = new ChangeUserInfoActivity.rejicall();
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
                            "form-data; name=\"u_id\""),
                            RequestBody.create(null, searchid))
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
            String url = DownloadTool.url +"/changeUser";
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