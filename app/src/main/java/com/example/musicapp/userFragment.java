package com.example.musicapp;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.adapter.MusicAdapter;
import com.example.musicapp.object.Music;
import com.example.musicapp.object.MusicList;
import com.example.musicapp.tool.DownloadTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 用户个人界面
 包含上传、本地歌曲列表、歌单、修改用户信息等功能
 @author lc
 */
public class userFragment extends Fragment {
    private PlayViewModel viewModel;
    private UserViewModel mViewModel;
    private List<Music> MusicList=new ArrayList<Music>();
    public static userFragment newInstance() {
        return new userFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment,container,false);//绑定视图
        initMusicList();//初始化本地歌曲列表
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MusicAdapter(MusicList,getContext()) );
        ImageButton search_btn = view.findViewById(R.id.search) ;
        search_btn.setOnClickListener(new View.OnClickListener() {//跳转搜索activity
            public void onClick(View v) {
                TextView tex = view.findViewById(R.id.s_key);
                Intent intent=new Intent(getContext(), SearchActivity.class);
                intent.putExtra("key",tex.getText().toString());
                startActivity(intent);
            }
        });
        ImageButton up_btn = view.findViewById(R.id.upload) ;//上传歌曲
        up_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), UploadActivity.class);
                startActivity(intent);
            }
        });
        ImageView songa = view.findViewById(R.id.change_user) ;//改变用户
        songa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        ImageView changeinfo = view.findViewById(R.id.changeinfo) ;//改变用户
        changeinfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserApplication application1 = (UserApplication) LoginActivity.getInstance().getApplication();//将用户名存入Application
                String id =application1.getValue();
                Intent intent=new Intent(getContext(), ChangeUserInfoActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        Button play_button = view.findViewById(R.id.paly_all);
        play_button.setOnClickListener(new View.OnClickListener() {//点击播放按钮，将歌单存入ViewModel，跳转到播放页面
            public void onClick(View v) {
                viewModel = new ViewModelProvider(
                        requireActivity(),
                        new ViewModelProvider.NewInstanceFactory()).get(PlayViewModel.class);
                viewModel.setSelectMusic(MusicList);
                NavController controller= Navigation.findNavController(view);
                controller.popBackStack();//出栈，非常重要，否则会出现点击导航栏时，该fragment无法跳转的情况
                controller.navigate(R.id.play);
            }
        });
        return view;
    }

    private void initMusicList()
    {
        MusicList.clear();
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
            do{
                fileName = cursor.getString(1);
                title = cursor.getString(2);
                duration = cursor.getInt(3);
                singer = cursor.getString(4);
                size = (cursor.getString(6) == null) ? "未知" : cursor.getInt(6) / 1024 / 1024 + "MB";
                if (cursor.getString(7) != null) filePath = cursor.getString(7);
                System.out.println(filePath);
                song = new Music(null,title,singer,filePath,null,duration);
                MusicList.add(song);
                //大于30秒的
//                if (duration > 30000) {
//                    musicList.add(song);
//                }
            }while (cursor.moveToNext());
            // 释放资源
            cursor.close();
        }
        int len=MusicList.size();
        Toast.makeText(getContext(),"歌曲数量为"+len,Toast.LENGTH_SHORT).show();// 扫描到的歌曲数量
//        for(int i=0;i<musicList.size();i++){//用于测试读取的歌曲
//            Music music = (Music) musicList.get(i);
//            System.out.println(music.getName()+" "+music.getSinger()+" "+music.getDuration()+"\n");
//
//        }
    }

}