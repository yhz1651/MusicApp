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
import com.example.musicapp.tool.CallAble;
import com.example.musicapp.tool.DownloadTool;
import com.example.musicapp.tool.JsonTool;
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
    private JsonTool jsontool;
    private String ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_fragment);
        jsontool = new JsonTool();
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

    public void  ask_singer(){//调用接口获取歌手信息
        CallAble.askSinger callable = new CallAble.askSinger();//将实现Callable接口的对象作为参数创建一个FutureTask对象
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
        SingerList=jsontool.jsonToList_singer(ans);
    }




    public void  ask_music(){//调用接口获取歌曲信息
        CallAble.askMusic callable = new CallAble.askMusic();
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
        MusicList=jsontool.jsonToList_music(ans);//获取音乐播放列表
    }


    public void  ask_user(){//调用接口获取用户信息
        CallAble.askUser callable = new CallAble.askUser();
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
        UserList=jsontool.jsonToList_user(ans);//获取用户列表
    }


}


