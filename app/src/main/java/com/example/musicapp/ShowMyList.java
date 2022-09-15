package com.example.musicapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.adapter.MusicListAdapter1;
import com.example.musicapp.object.MusicList;
import com.example.musicapp.tool.CallAble;
import com.example.musicapp.tool.JsonTool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class ShowMyList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView toptext;
    private String user_id;
    private String musiclistid;
    private String Mid;//音乐id
    private JsonTool jsontool;
    private List<MusicList> musicList =new ArrayList<MusicList>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            ShowMyList.this.setResult(1);
            ShowMyList.this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        UserApplication application1 = (UserApplication) ShowMyList.this.getApplication();//获取application
        jsontool=new JsonTool();
        user_id = application1.getValue();//获得用户ID
        showlist(user_id);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MusicListAdapter1 adapter=new MusicListAdapter1( musicList,this,1,user_id);
        recyclerView.setAdapter(adapter);
        ImageView img =(ImageView)findViewById(R.id.addnewlist);
        img.setImageAlpha(100);
        toptext = (TextView)findViewById(R.id.toptext) ;
        toptext.setText("我的歌单");
        adapter.setOnItemClickListener(new MusicListAdapter1.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MusicList music = musicList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list",music);//将singer存入bundle通过Intent传递
                Intent intent=new Intent(ShowMyList.this, MusicListActivity.class);//进入改变歌手信息界面
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
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
        musicList=jsontool.jsonToList_musiclist(ans);//将json格式转换为歌单形式
    }
}