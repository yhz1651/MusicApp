package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.musicapp.object.Singer;
import com.example.musicapp.service.DatabaseHelper;
import com.example.musicapp.tool.DownloadTool;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeSinger extends AppCompatActivity {
    private EditText singername;
    private EditText singerregion;
    private EditText singerintro;
    private Button submit;
    private Singer singer;
    private String s_id;
    private String s_name;
    private String s_intro;
    private String s_region;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_singer);
        Intent intent = getIntent();
        singer = (Singer) intent.getSerializableExtra("singer");
        singername = (EditText) findViewById(R.id.singername);
        singerregion = (EditText) findViewById(R.id.reogin);
        singerintro = (EditText) findViewById(R.id.singerintro);
        submit = (Button) findViewById(R.id.submit);
        singername.setText(singer.getS_name());
        singerregion.setText(singer.getS_region());
        singerintro.setText(singer.getS_intro());
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s_id=singer.getS_id();
                s_name=singername.getText().toString().trim();
                s_intro=singerintro.getText().toString().trim();
                s_region=singerregion.getText().toString().trim();//获取修改值

                startActivity(intent);
            }
        });

    }
    private void change(){
        new Thread(new Runnable() {//使用多线程，防止主线程堵塞
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new MultipartBody.Builder()//创建requestbody
                        .setType(MultipartBody.FORM)
                        .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"s_id\""),
                                RequestBody.create(null, singer.getS_id()))
                        .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"s_name\""),
                                RequestBody.create(null, s_name))
                        .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"s_region\""),
                                RequestBody.create(null, s_region))
                        .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"s_intro\""),
                                RequestBody.create(null, s_intro))
                        .build();
                String url = DownloadTool.url+"/changesinger";
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
                        String json = response.body().string();
                        Log.i("success........", "成功" + json);//上传成功后插入到本地歌曲表

                    }
                });
            }
        }).start();
    }
}