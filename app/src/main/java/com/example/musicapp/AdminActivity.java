package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.musicapp.adapter.MusicAdapter;
import com.example.musicapp.adapter.SingerAdapter;
import com.example.musicapp.adapter.UserAdapter;
import com.example.musicapp.object.Music;
import com.example.musicapp.object.MusicList;
import com.example.musicapp.object.Singer;
import com.example.musicapp.object.User;
import com.example.musicapp.service.DatabaseHelper;
import com.example.musicapp.tool.DownloadTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

/**
 * 管理员界面
 * 负责进行用户管理，歌曲管理，歌单管理，歌手管理等功能
 *
 */

public class AdminActivity extends AppCompatActivity {
    private String[] MusicMames;
    private List<Music> MusicList=new ArrayList<Music>();
    private List<Singer> SingerList = new ArrayList<Singer>();
    private List<User> UserList= new ArrayList<User>();
    private RecyclerView recyclerView;
    private String ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_fragment);
        MusicList.clear();
        ask_music();//获取歌曲列表
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MusicAdapter(MusicList,this) );
        ImageButton search_btn = (ImageButton)findViewById(R.id.search);
        search_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView tex = (TextView)findViewById(R.id.s_key);
                Intent intent=new Intent(AdminActivity.this, SearchActivity.class);
                intent.putExtra("key",tex.getText().toString());
                startActivity(intent);
            }
        });
        ImageButton up_btn = (ImageButton)findViewById(R.id.upload) ;
        up_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });
        ImageView songa = (ImageView) findViewById(R.id.change_user) ;
        songa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        ImageView singermanager = (ImageView) findViewById(R.id.singer_manage);
        singermanager.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ask_singer();
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
                recyclerView.setAdapter(new SingerAdapter(SingerList,AdminActivity.this) );
            }
        });
        ImageView music_mangager = (ImageView) findViewById(R.id.music_manager);
        music_mangager.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ask_music();//获取歌曲列表
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
                recyclerView.setAdapter(new MusicAdapter(MusicList,AdminActivity.this) );
            }
        });
        ImageView user_mangager = (ImageView) findViewById(R.id.user_manager);
        user_mangager.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserList.clear();
                ask_user();//获取用户列表
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
                recyclerView.setAdapter(new UserAdapter(UserList,AdminActivity.this) );
            }
        });
    }

    public void  ask_singer(){//调用接口
        AdminActivity.askSinger callable = new AdminActivity.askSinger();//将实现Callable接口的对象作为参数创建一个FutureTask对象
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
        jsonToList(ans);
    }
    class askSinger implements Callable<String>
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
            Thread.sleep(1000);
            return ans;
        }
    }



    public void  ask_music(){
        AdminActivity.askMusic callable = new AdminActivity.askMusic();
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
        jsonToList_music(ans);//获取音乐播放列表
    }
    class askMusic implements Callable<String>
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

    public void  ask_user(){
        AdminActivity.askUser callable = new AdminActivity.askUser();
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
        jsonToList_user(ans);//获取用户列表
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



    public  void jsonToList(String json) {//将JSON转化为对象数组
        Gson gson = new Gson();
        SingerList = gson.fromJson(json, new TypeToken<List<Singer>>() {}.getType());//对于不是类的情况，用这个参数给出
        for (Singer singer1 : SingerList) {
            System.out.println(singer1.getS_name());
        }
    }
    public  void jsonToList_music(String json) {//将JSON转化为对象数组
        Gson gson = new Gson();
        MusicList = gson.fromJson(json, new TypeToken<List<Music>>() {}.getType());//对于不是类的情况，用这个参数给出
        for (Music Music1 : MusicList) {
            System.out.println(Music1.getM_name());
        }
    }
    public  void jsonToList_user(String json) {//将JSON转化为对象数组
        Gson gson = new Gson();
        UserList = gson.fromJson(json, new TypeToken<List<User>>() {}.getType());//对于不是类的情况，用这个参数给出
        for (User User1 : UserList) {
            System.out.println(User1.getU_username());
        }
    }
}


