package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.musicapp.adapter.MusicAdapter;
import com.example.musicapp.object.Music;
import com.example.musicapp.service.DatabaseHelper;
import com.example.musicapp.tool.DownloadTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

public class SearchActivity extends AppCompatActivity {
    String ans;
    String key;
    private List<Music> musicList =new ArrayList<Music>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent=getIntent();
        key =intent.getStringExtra("key");
        ask();
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

    public void  ask(){
        SearchActivity.askMusic callable = new SearchActivity.askMusic();
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

        jsonToList(ans);
    }
    class askMusic implements Callable<String>
    {   String ans;
        @Override
        public String call() throws Exception {//创建带回调方法的线程进行网络请求
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()//创建requestbody
                    .setType(MultipartBody.FORM)
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"m_name\""),

                            RequestBody.create(null, key))
                    .build();
            String url = DownloadTool.url +"/searchMusic";
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
    public  void jsonToList(String json) {
        Gson gson = new Gson();
        musicList = gson.fromJson(json, new TypeToken<List<Music>>() {}.getType());//对于不是类的情况，用这个参数给出
        for (Music music1 : musicList) {
            System.out.println(music1.getM_name()+" "+music1.getM_singer());
        }
    }
}
