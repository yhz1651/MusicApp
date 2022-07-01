package com.example.musicapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.musicapp.adapter.MusicAdapter;
import com.example.musicapp.object.Music;
import com.example.musicapp.object.User;
import com.example.musicapp.service.DatabaseHelper;
import com.example.musicapp.service.UserService;
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

/**
 * 主页面
* */
public class MainFragment extends Fragment {
    private String ans;
    private MainViewModel mViewModel;
    private List<Music> musicList =new ArrayList<Music>();
    private RecyclerView recyclerView;
    private ImageButton search_btn;
    private ImageButton up_btn;
    private TextView tex;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        up_btn = view.findViewById(R.id.upload) ;
        search_btn = view.findViewById(R.id.search);
        tex = view.findViewById(R.id.s_key);
//        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
        musicList.clear();
        ask();//调用网络请求，请求歌曲列表
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MusicAdapter( musicList,getContext()));
        search_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("key",tex.getText().toString());
                startActivity(intent);
            }
        });
        up_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), UploadActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    public void  ask(){
        askMusic callable = new askMusic();
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
            while(ans==null){}//等待赋值
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