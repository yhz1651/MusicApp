package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.musicapp.adapter.CommentAdapter;
import com.example.musicapp.adapter.MusicAdapter;
import com.example.musicapp.adapter.PlayListAdapter;
import com.example.musicapp.object.Comment;
import com.example.musicapp.object.Music;
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

public class CommentActivity extends AppCompatActivity {

    private String music_name;
    private String music_singer;
    private String userid;
    private RecyclerView recyclerView;
    private List<Comment> commentList =new ArrayList<Comment>();
    private TextView musicname;
    private TextView singname;
    private EditText comment;
    private Button submit;
    private String ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        music_name = intent.getStringExtra("music_name");
        music_singer = intent.getStringExtra("music_singer");
        musicname = (TextView) findViewById(R.id.music_name);
        singname = (TextView) findViewById(R.id.singer_name);
        comment = (EditText) findViewById(R.id.comment_content);
        submit = (Button) findViewById(R.id.comment_submit);
        musicname.setText(music_name);
        singname.setText("-"+music_singer);

        UserApplication application1 = (UserApplication) CommentActivity.this.getApplication();//获取application
        userid = application1.getValue(); // 获得用户ID

        ask(); // 调用网络请求，请求评论列表

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommentAdapter adapter = new CommentAdapter(commentList,this);
        recyclerView.setAdapter(adapter);

        // 添加新评论
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(CommentActivity.this, AddComment.class);
//                intent.putExtra("content",comment.getText().toString());
//                startActivity(intent);
                addComment();
//                finish();
            }
        });

    }


    // 添加一条新评论
    private void addComment(){
        new Thread(new Runnable() { // 使用多线程，防止主线程堵塞
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new MultipartBody.Builder()// 创建 requestbody
                        .setType(MultipartBody.FORM)
                        .addPart(Headers.of(
                                        "Content-Disposition",
                                        "form-data; name=\"m_name\""),
                                RequestBody.create(null, music_name))
                        .addPart(Headers.of(
                                        "Content-Disposition",
                                        "form-data; name=\"c_userid\""),
                                RequestBody.create(null, userid))
                        .addPart(Headers.of(
                                        "Content-Disposition",
                                        "form-data; name=\"c_content\""),
                                RequestBody.create(null, comment.getText().toString()))
                        .build();
                String url = DownloadTool.url+"/addComment";
                System.out.println("----------------------------------------------------");
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() { // 回调方法
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("text", "failure upload!" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        Log.i("success", "成功" + json);//上传成功后插入到本地歌曲表

                    }
                });
            }
        }).start();
    }



    // 查找所有评论
    public void ask(){
        CommentActivity.askComment callable = new CommentActivity.askComment();
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
    class askComment implements Callable<String>
    {   String ans;
        @Override
        public String call() throws Exception { // 创建带回调方法的线程进行网络请求
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder() // 创建requestbody
                    .setType(MultipartBody.FORM) // 传参
                    .addPart(Headers.of(
                                    "Content-Disposition",
                                    "form-data; name=\"m_name\""), // 通过歌曲名来查找
                            RequestBody.create(null, music_name))
                    .build();
            String url = DownloadTool.url +"/askComment";
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
//            while(ans==null){} // 等待赋值
            Thread.sleep(1000);
            return ans;
        }
    }
    public void jsonToList(String json) {
        Gson gson = new Gson();
        if(!json.isEmpty())
        {
            commentList = gson.fromJson(json, new TypeToken<List<Comment>>() {}.getType());//对于不是类的情况，用这个参数给出
            for (Comment comment1 : commentList) {
                System.out.println(comment1.getC_content()+" "+comment1.getC_good()+" "+comment1.getC_username()+" "+comment1.getC_userid());
            }
        }
        else System.out.println("Empty!");
    }
}