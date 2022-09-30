package com.example.order.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.order.R;
import com.example.order.adapter.MusicAdapter;
import com.example.order.adapter.SingerAdapter;
import com.example.order.adapter.UserAdapter;
import com.example.order.object.Music;
import com.example.order.object.Singer;
import com.example.order.object.User;
import com.example.order.tool.CallAble;
import com.example.order.tool.JsonTool;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

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


