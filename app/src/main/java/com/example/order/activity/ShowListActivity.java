package com.example.order.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.order.R;
import com.example.order.UserApplication;
import com.example.order.adapter.MusicListAdapter1;
import com.example.order.object.MusicList;
import com.example.order.tool.CallAble;
import com.example.order.tool.JsonTool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class ShowListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String user_id;
    private String musiclistid;
    private String Mid;//音乐id
    private JsonTool jsontool;
    private List<MusicList> musicList =new ArrayList<MusicList>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        UserApplication application1 = (UserApplication) ShowListActivity.this.getApplication();//获取application
        Intent _intent = getIntent();
        //从Intent当中根据key取得value
        if (_intent != null) {
            Mid = _intent.getStringExtra("Mid");
        }
        jsontool=new JsonTool();
        user_id = application1.getValue();//获得用户ID
        showlist(user_id);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MusicListAdapter1 adapter=new MusicListAdapter1( musicList,this,1,user_id);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MusicListAdapter1.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MusicList music = musicList.get(position);
                musiclistid=music.getMl_id();
                addtolist(Mid,musiclistid);
                Toast.makeText(ShowListActivity.this,"添加成功",Toast.LENGTH_SHORT).show();// 扫描到的歌曲数量
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
    public String  addtolist(String ml_userid, String ml_name){//添加到歌单
        String ans = null;
        CallAble.add_to_list callable = new CallAble.add_to_list (ml_userid,ml_name);
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
    public void  showlist(String ml_userid){//展示歌单
        String ans = null;
        CallAble.askList callable = new CallAble.askList (ml_userid);
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
        musicList=jsontool.jsonToList_musiclist(ans);//获取音乐播放列表
    }
}