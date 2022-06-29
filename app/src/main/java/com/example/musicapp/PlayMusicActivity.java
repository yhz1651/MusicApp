//package com.example.musicapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.example.musicapp.object.Music;
//import com.example.musicapp.service.DatabaseHelper;
//
//import java.util.List;
//import java.util.Random;
//
//public class PlayMusicActivity extends AppCompatActivity {
//
//    private TextView music_name;
//    private TextView music_singer;
//    private TextView music_type;
//    private TextView start_time;
//    private TextView end_time;
//    private SeekBar seekBar;
//    private Button btn_music_mode;
//    private Button btn_music_pre;
//    private Button btn_music_play;
//    private Button btn_music_next;
//    private Button btn_music_list;
//    private RecyclerView recyclerView;
//
//    private int playmode=0; // 0为列表循环，1为单曲循环，2为随机播放
//    private int music_id=1;
//    private List<Music> musicList;//播放列表
//    public MediaPlayer mediaplayer = null;
//    private boolean isplay = false;//是否在播放
//    private boolean seekbarchange=false;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_play_music);
//        initview();
//
//
//    }
//    void initview()
//    {
//        music_name=(TextView) findViewById(R.id.music_name);
//        music_singer=(TextView) findViewById(R.id.music_singer);
//        music_type=(TextView) findViewById(R.id.music_type);
//        start_time=(TextView) findViewById(R.id.start_time);
//        end_time=(TextView) findViewById(R.id.end_time);
//        seekBar=(SeekBar) findViewById(R.id.seekbar);
//        btn_music_mode=(Button) findViewById(R.id.btn_music_mode);
//        btn_music_pre=(Button) findViewById(R.id.btn_music_pre);
//        btn_music_play=(Button) findViewById(R.id.btn_music_play);
//        btn_music_next=(Button) findViewById(R.id.btn_music_next);
//        btn_music_list=(Button) findViewById(R.id.btn_music_list);
//        recyclerView =(RecyclerView) findViewById(R.id.recyclerView);
//
//
//
//
//
//
//        //切换歌曲播放模式
//        btn_music_mode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                playmode=(++playmode) % 3;
//                if(playmode==0){// 0为列表循环
//                    btn_music_mode.setBackgroundResource(R.drawable.music_mode_list);
//                }else if(playmode==1){// 1为单曲循环
//                    btn_music_mode.setBackgroundResource(R.drawable.music_mode_single);
//                }else{// 2为随机播放
//                    btn_music_mode.setBackgroundResource(R.drawable.music_mode_random);
//                }
//            }
//        });
//
//        //暂停，播放
//        btn_music_play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(mediaplayer==null) return;
//                if(isplay==true){
//                    isplay=false;
//                    mediaplayer.pause();
//                    btn_music_play.setBackgroundResource(R.drawable.music_pause);
//                }else{
//                    isplay=true;
//                    mediaplayer.start();
//                    btn_music_play.setBackgroundResource(R.drawable.music_play);
//                }
//            }
//        });
//
//        //上一首下一首
//        btn_music_pre.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(mediaplayer==null) return;
//                if(playmode==0){// 0为列表循环
//                    music_id=(--music_id) % musicList.size();
//                    initMediaPlayer(musicList.get(music_id));
//                }else if(playmode==1){// 1为单曲循环
//
//                }else{// 2为随机播放
//                    music_id = new Random().nextInt(musicList.size());
//                    initMediaPlayer(musicList.get(music_id));
//                }
//            }
//        });
//        btn_music_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(mediaplayer==null) return;
//                if(playmode==0){// 0为列表循环
//                    music_id=(++music_id) % musicList.size();
//                    initMediaPlayer(musicList.get(music_id));
//                }else if(playmode==1){// 1为单曲循环
//
//                }else{// 2为随机播放
//                    music_id = new Random().nextInt(musicList.size());
//                    initMediaPlayer(musicList.get(music_id));
//                }
//            }
//        });
//
//
//        //进度条 开始时间 结束时间
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                seekbarchange = true;
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                seekbarchange = false;
//                mediaplayer.seekTo(seekBar.getProgress());
//                int m = mediaplayer.getCurrentPosition() / 60000;
//                int s = (mediaplayer.getCurrentPosition() - m * 60000) / 1000;
//                start_time.setText(m+":"+s);
//            }
//        });
//
//        //初始化播放列表
//        Cursor cursor;
//        DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(PlayMusicActivity.this);
//        SQLiteDatabase sdb = dbsqLiteOpenHelper.getWritableDatabase();
//
//        cursor=sdb.query("Music",new String[]{"m_id,m_name,m_singer"},null,null,null,null,null);
//        if(cursor.moveToFirst()){
//            do{
//                Music music1 =new Music(cursor.getString(0), cursor.getString(1),cursor.getString(2),null,null);
//                musicList.add(music1);
//            }while(cursor.moveToNext());
//            cursor.close();
//        }
//        recyclerView.setLayoutManager(new LinearLayoutManager(PlayMusicActivity.this));
//        MusicAdapter adapter=new MusicAdapter( musicList,PlayMusicActivity.this);
//        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(new MusicAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Music music = musicList.get(position);
//                initMediaPlayer(music);
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//            }
//        });
//
//
//
//        //获取歌曲信息
//
//
//    }
//
//
//
//    private void initMediaPlayer(Music music) {
//
//        try {
//            if (mediaplayer == null)
//                mediaplayer = new MediaPlayer();
//            mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    if(playmode==0){// 0为列表循环
//                        music_id=(++music_id) % musicList.size();
//                        initMediaPlayer(musicList.get(music_id));
//                    }else if(playmode==1){// 1为单曲循环
//
//                    }else{// 2为随机播放
//                        music_id = new Random().nextInt(musicList.size());
//                        initMediaPlayer(musicList.get(music_id));
//                    }
//                }
//            });
//            mediaplayer.reset();
//            mediaplayer.setDataSource(music.getUrl());
//            mediaplayer.prepare();
//            mediaplayer.start();
////            int m = music.getDuration() / 60000;
////            int s = (music.getDuration() - m * 60000) / 1000;
//            int m = 5;
//            int s = 0;
//            end_time.setText(m + ":" + s );
//            music_name.setText(music.getName());
////            seekBar.setMax(music.getDuration());
//            seekBar.setMax(5*60000);
//
//            Handler handler = new Handler(){
//                @Override
//                public void handleMessage(Message msg) {
//                    if (seekbarchange) return;
//                    seekBar.setProgress(mediaplayer.getCurrentPosition());
//                    int m = mediaplayer.getCurrentPosition() / 60000;
//                    int s = (mediaplayer.getCurrentPosition() - m * 60000) / 1000;
//                    start_time.setText(m + ":" + s );
//                }
//            };
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while(true){
//                        handler.sendEmptyMessage(0);
//                        try {
//                            Thread.sleep(1000);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }).start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        if (mediaplayer != null) {
//            mediaplayer.stop();
//            mediaplayer.release();
//            mediaplayer = null;
//        }
//        super.onDestroy();
//    }
//
//
//
//
//}