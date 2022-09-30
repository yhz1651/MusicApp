package com.example.order.activity;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order.fragment.PlayViewModel;
import com.example.order.R;
import com.example.order.adapter.MusicAdapter;
import com.example.order.object.Music;
import com.example.order.object.MusicList;
import com.example.order.tool.CallAble;
import com.example.order.tool.JsonTool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class MusicListActivity extends AppCompatActivity {
    private JsonTool jsontool;
    private PlayViewModel viewModel;
    private MusicList ml;
    private TextView title;
    private TextView creator;
    private RecyclerView recyclerView;
    private List<Music> musicList =new ArrayList<Music>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        title = (TextView) findViewById(R.id.list_title);
        creator=(TextView) findViewById(R.id.creater);
        Intent intent=getIntent();
        ml = (MusicList) intent.getSerializableExtra("list");
        title.setText(ml.getMl_name());
        creator.setText(ml.getMl_userid());
        viewModel = new ViewModelProvider(this).get(PlayViewModel.class);
        musicList.clear();
        jsontool = new JsonTool();
        ask(ml.getMl_id());//调用网络请求，请求歌曲列表
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MusicAdapter adapter=new MusicAdapter( musicList,this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Music music = musicList.get(position);
                if(music!=null){//如果本地有，则播放本地
                    List<Music> mlist =new ArrayList<Music>();
                    mlist.add(music);
                    viewModel.setSelectMusic(mlist);
//                        finish();
                    MusicListActivity.this.setResult(1);
                    MusicListActivity.this.finish();
//                    Intent intent = new Intent(MusicListActivity.this,MainActivity.class);//上传成功后，回到主页面
//                    intent.putExtra("idd",1);
//                    startActivity(intent);
//                    NavController controller= Navigation.findNavController(view);
//                    controller.popBackStack();//出栈，非常重要，否则会出现点击导航栏时，该fragment无法跳转的情况
//                    controller.navigate(R.id.play);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        Button love = (Button) findViewById(R.id.love);
        love.setOnClickListener(new View.OnClickListener() {//收藏
            public void onClick(View v) {
                TextView tex = (TextView)findViewById(R.id.s_key);
                Intent intent=new Intent(MusicListActivity.this, SearchActivity.class);
                intent.putExtra("key",tex.getText().toString());
                startActivity(intent);
            }
        });
        Button playall = (Button) findViewById(R.id.play_all) ;
        playall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(MusicListActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });
    }
    public void  ask(String lid){
        String ans = null;
        CallAble.music_of_list callable = new CallAble.music_of_list(lid);
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
        String str;
        musicList=jsontool.jsonToList_music(ans);//服务器现有的歌曲
        for (Music music1 : musicList){//遍历list 如果不在本地就标记
            str = check(music1.getM_name());
            if(!str.equals("")){
                music1.setLocal(true);//如果不为空 就说明有
                music1.setM_url(str);
            }else{
                music1.setLocal(false);
            }
        }
    }

    String check(String tit){//判断是否在本地
        boolean flag=false;
        MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()}, null, null);
        Cursor cursor = this.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DATA},
                MediaStore.Audio.Media.TITLE + "=?",
                new String[]{tit}, null);
        String filePath="";
        if (cursor.moveToFirst()) {
            do{
                if (cursor.getString(7) != null) filePath = cursor.getString(7);
                flag=true;
            }while (cursor.moveToNext());
            // 释放资源
            cursor.close();
        }
        return filePath;
    }
}