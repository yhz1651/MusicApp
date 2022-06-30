package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.musicapp.adapter.MusicAdapter;
import com.example.musicapp.object.Music;
import com.example.musicapp.service.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private List<Music> musicList =new ArrayList<Music>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Cursor cursor;
        Intent intent=getIntent();
        String key =intent.getStringExtra("key");
        DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(SearchActivity.this);
        SQLiteDatabase sdb = dbsqLiteOpenHelper.getWritableDatabase();
        UserApplication application1 = (UserApplication) SearchActivity.this.getApplication();
        String id = application1.getValue();;
        if(key != null && key.length()!=0){
            String sql="SELECT m_userid,m_name,m_singer FROM Music WHERE m_name like ?";
            cursor=sdb.rawQuery(sql, new String[]{"%"+key+"%"});
        }else{
            cursor=sdb.query("Music",new String[]{"m_id,m_name,m_singer"},null,null,null,null,null);
        }
        if(cursor.moveToFirst()){
            do{
                Music music1 =new Music(cursor.getString(0), cursor.getString(1),cursor.getString(2),null,null,0);
                musicList.add(music1);
            }while(cursor.moveToNext());
            cursor.close();
        }
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MusicAdapter(musicList,this) );
        ImageButton search_btn = (ImageButton)findViewById(R.id.search);
        search_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView tex = (TextView)findViewById(R.id.s_key);
                Intent intent=new Intent(SearchActivity.this, SearchActivity.class);
                intent.putExtra("key",tex.getText().toString());
                startActivity(intent);
            }
        });
        ImageButton up_btn = (ImageButton)findViewById(R.id.upload) ;
        up_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(SearchActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });


    }
}
