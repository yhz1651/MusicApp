package com.example.order.fragment;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order.R;
import com.example.order.activity.CommentActivity;
import com.example.order.adapter.PlayListAdapter;
import com.example.order.object.Music;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class playFragment extends Fragment {

    private TextView music_name;
    private TextView music_singer;
    private TextView music_type;
    private TextView start_time;
    private TextView end_time;
    private SeekBar seekBar;
    private Button btn_music_mode;
    private Button btn_music_pre;
    private Button btn_music_play;
    private Button btn_music_next;
    private Button btn_music_comment;
    private RecyclerView recyclerView;
    private int playmode=0; // 0为列表循环，1为单曲循环，2为随机播放
    private int music_id=1;
    private List<Music> musicList =new ArrayList<Music>();
    public MediaPlayer mediaplayer = null;
    private boolean isplay = false;//是否在播放
    private boolean seekbarchange=false;

    private View view;
    private PlayViewModel PlayViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.play_fragment, container, false);
        PlayViewModel = new ViewModelProvider(
                requireActivity(),
                new ViewModelProvider.NewInstanceFactory()).get(PlayViewModel.class);
        musicList = PlayViewModel.getSelectMusic().getValue();
        if(musicList==null){
            musicList=new ArrayList<Music>();
            initMusicList();
        }
        initview();
        Music music = musicList.get(0);
        initMediaPlayer(music);
        return view;
    }

    void initview()
    {
        music_name=view.findViewById(R.id.music_name);
        music_singer=view.findViewById(R.id.music_singer);
        start_time=view.findViewById(R.id.start_time);
        end_time=view.findViewById(R.id.end_time);
        seekBar=view.findViewById(R.id.seekbar);
        btn_music_mode=view.findViewById(R.id.btn_music_mode);
        btn_music_pre=view.findViewById(R.id.btn_music_pre);
        btn_music_play=view.findViewById(R.id.btn_music_play);
        btn_music_next=view.findViewById(R.id.btn_music_next);
        btn_music_comment=view.findViewById(R.id.btn_music_comment);
        recyclerView =view.findViewById(R.id.recyclerView);


        //切换歌曲播放模式
        btn_music_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playmode=(++playmode) % 3;
                if(playmode==0){// 0为列表循环
                    btn_music_mode.setBackgroundResource(R.drawable.music_mode_list);
                }else if(playmode==1){// 1为单曲循环
                    btn_music_mode.setBackgroundResource(R.drawable.music_mode_single);
                }else{// 2为随机播放
                    btn_music_mode.setBackgroundResource(R.drawable.music_mode_random);
                }
            }
        });

        //暂停，播放
        btn_music_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaplayer==null) return;
                if(isplay==true){
                    isplay=false;
                    mediaplayer.pause();
                    btn_music_play.setBackgroundResource(R.drawable.music_pause);
                }else{
                    isplay=true;
                    mediaplayer.start();
                    btn_music_play.setBackgroundResource(R.drawable.music_play);
                }
            }
        });

        //上一首下一首
        btn_music_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaplayer==null) return;
                if(playmode==0){// 0为列表循环
                    music_id=(--music_id) % musicList.size();
                    initMediaPlayer(musicList.get(music_id));
                }else if(playmode==1){// 1为单曲循环

                }else{// 2为随机播放
                    music_id = new Random().nextInt(musicList.size());
                    initMediaPlayer(musicList.get(music_id));
                }
            }
        });
        //播放方式
        btn_music_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaplayer==null) return;
                if(playmode==0){
                    music_id=(++music_id) % musicList.size();
                    initMediaPlayer(musicList.get(music_id));
                }else if(playmode==1){// 1为单曲循环

                }else{// 2为随机播放
                    music_id = new Random().nextInt(musicList.size());
                    initMediaPlayer(musicList.get(music_id));
                }
            }
        });


        //进度条 开始时间 结束时间
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekbarchange = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekbarchange = false;
                mediaplayer.seekTo(seekBar.getProgress());
                int m = mediaplayer.getCurrentPosition() / 60000;
                int s = (mediaplayer.getCurrentPosition() - m * 60000) / 1000;
                start_time.setText(m+":"+s);
            }
        });

        //绑定adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PlayListAdapter adapter=new PlayListAdapter( musicList,getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new PlayListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Music music = musicList.get(position);
                initMediaPlayer(music);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        // 评论
        btn_music_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String musicname = music_name.getText().toString();
//                String singer = music_singer.getText().toString();
                if(music_name.getText().toString().equals("歌曲名") || music_name.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(),"请先选择歌曲！",Toast.LENGTH_SHORT).show(); // 提示
                }
                else // 已选择歌曲
                {
                    Toast.makeText(getContext(),music_name.getText().toString(),Toast.LENGTH_SHORT).show(); // 提示
                    Intent intent=new Intent(getContext(), CommentActivity.class);
                    intent.putExtra("music_name",music_name.getText().toString());
                    intent.putExtra("music_singer",music_singer.getText().toString());
                    startActivity(intent);
                }

            }
        });


    }


    private void initMusicList()
    {
        musicList.clear();
        MediaScannerConnection.scanFile(getContext(), new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()}, null, null);
        Cursor cursor = getContext().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DATA},
                MediaStore.Audio.Media.MIME_TYPE + "=?",
                new String[]{"audio/mpeg"}, null);
        String fileName, title, singer,size, filePath = "";
        int duration, m, s;
        Music song;

        if (cursor.moveToFirst()) {
            do{//获取本地歌曲列表 存入数组
                fileName = cursor.getString(1);
                title = cursor.getString(2);
                duration = cursor.getInt(3);
                singer = cursor.getString(4);
                size = (cursor.getString(6) == null) ? "未知" : cursor.getInt(6) / 1024 / 1024 + "MB";
                if (cursor.getString(7) != null) filePath = cursor.getString(7);
                System.out.println(filePath);
                song = new Music(null,title,singer,filePath,null,duration);
                musicList.add(song);

            }while (cursor.moveToNext());
            // 释放资源
            cursor.close();
        }
        int len=musicList.size();
        Toast.makeText(getContext(),"歌曲数量为"+len,Toast.LENGTH_SHORT).show();// 扫描到的歌曲数量
//        for(int i=0;i<musicList.size();i++){//用于测试读取的歌曲
//            Music music = (Music) musicList.get(i);
//            System.out.println(music.getName()+" "+music.getSinger()+" "+music.getDuration()+"\n");
//
//        }
    }



    private void initMediaPlayer(Music music) {

        try {
            if (mediaplayer == null)
                mediaplayer = new MediaPlayer();
            mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(playmode==0){
                        music_id=(++music_id) % musicList.size();
                        initMediaPlayer(musicList.get(music_id));
                    }else if(playmode==1){

                    }else{
                        music_id = new Random().nextInt(musicList.size());
                        initMediaPlayer(musicList.get(music_id));
                    }
                }
            });
            mediaplayer.reset();
            mediaplayer.setDataSource(music.getM_url());
            mediaplayer.prepare();
            mediaplayer.start();
            int m = music.getM_duration() / 60000;
            int s = (music.getM_duration() - m * 60000) / 1000;
            DecimalFormat decimalFormat = new DecimalFormat("00");

            String m2= decimalFormat .format(m);
            String s2 = decimalFormat .format(s);
            end_time.setText(m2 + ":" + s2 );
            music_name.setText(music.getM_name());
            music_singer.setText(music.getM_singer());
            seekBar.setMax(music.getM_duration());
            Toast.makeText(getContext(),music.getM_url(),Toast.LENGTH_SHORT);

            Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    try{
                    if (seekbarchange) return;
                    seekBar.setProgress(mediaplayer.getCurrentPosition());
                    int m = mediaplayer.getCurrentPosition() / 60000;
                    int s = (mediaplayer.getCurrentPosition() - m * 60000) / 1000;
                    String m1= decimalFormat .format(m);
                    String s1 = decimalFormat .format(s);
                    start_time.setText(m1 + ":" + s1 );}
                    catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean i = true;
                    while(i){
                        handler.sendEmptyMessage(0);
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                            i=false;
                        }
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (mediaplayer != null) {
            mediaplayer.stop();
            mediaplayer.release();
            mediaplayer = null;
        }
        super.onDestroy();
    }


}