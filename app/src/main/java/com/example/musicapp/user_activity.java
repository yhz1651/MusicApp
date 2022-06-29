package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.musicapp.object.Music;
import com.example.musicapp.service.DatabaseHelper;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class user_activity extends AppCompatActivity {
    private String[] MusicMames;
    private List<Music> MusicList=new ArrayList<Music>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_fragment);
        Cursor cursor;
        Intent intent=getIntent();
        String key =intent.getStringExtra("key");
        DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(user_activity.this);
        SQLiteDatabase sdb = dbsqLiteOpenHelper.getWritableDatabase();
        UserApplication application1 = (UserApplication) user_activity.this.getApplication();
        String id = application1.getValue();;
        String sql="SELECT m_id,m_name,m_singer FROM Music WHERE m_userid = ?";
        cursor=sdb.rawQuery(sql, new String[]{id+""});
        if(cursor.moveToFirst()){
            do{
                String song_name = cursor.getString(1);
                String singer_name = cursor.getString(2);
                Music Music1=new Music(cursor.getString(0), song_name,singer_name,null,null,0);
                MusicList.add(Music1);
            }while(cursor.moveToNext());
            cursor.close();
        }
        sql="SELECT u_id,u_username FROM User WHERE u_id = ?";
        cursor=sdb.rawQuery(sql, new String[]{id+""});
        if(cursor.moveToFirst()){
            TextView tx = (TextView)findViewById(R.id.username);
            tx.setText( cursor.getString(1));
            cursor.close();
        }
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MusicAdapter(MusicList,this) );
        ImageButton search_btn = (ImageButton)findViewById(R.id.search) ;
        search_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView tex = (TextView)findViewById(R.id.s_key);
                Intent intent=new Intent(user_activity.this, SearchActivity.class);
                intent.putExtra("key",tex.getText().toString());
                startActivity(intent);
            }
        });
        ImageButton up_btn = (ImageButton)findViewById(R.id.upload) ;//上传歌曲
        up_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(user_activity.this, UploadActivity.class);
                startActivity(intent);
            }
        });
        ImageView songa = (ImageView) findViewById(R.id.change_user) ;//重新登录
        songa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(user_activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        ImageView uspage = (ImageView) findViewById(R.id.userpage);//切换到用户界面
        songa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(user_activity.this, user_activity.class);
                startActivity(intent);
            }
        });
    }
}


