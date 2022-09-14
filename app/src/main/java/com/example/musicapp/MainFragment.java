package com.example.musicapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaScannerConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.musicapp.adapter.MusicAdapter;
import com.example.musicapp.adapter.PlayListAdapter;
import com.example.musicapp.object.Music;
import com.example.musicapp.object.MusicList;
import com.example.musicapp.object.User;
import com.example.musicapp.service.DatabaseHelper;
import com.example.musicapp.service.UserService;
import com.example.musicapp.tool.CallAble;
import com.example.musicapp.tool.DownloadTool;
import com.example.musicapp.tool.JsonTool;
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
    private PlayViewModel viewModel;
    private MainViewModel mViewModel;
    private List<Music> musicList =new ArrayList<Music>();
    private RecyclerView recyclerView;
    private ImageButton search_btn;
    private ImageButton up_btn;
    private TextView tex;
    private JsonTool jsontool;
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
        jsontool = new JsonTool();
        ask();//调用网络请求，请求歌曲列表
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MusicAdapter adapter=new MusicAdapter( musicList,getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Music music = musicList.get(position);
                if(music.getLocal()){//如果本地有，则播放本地
                    List<Music> mlist =new ArrayList<Music>();
                    mlist.add(music);
                    viewModel = new ViewModelProvider(
                            requireActivity(),
                            new ViewModelProvider.NewInstanceFactory()).get(PlayViewModel.class);
                    viewModel.setSelectMusic(mlist);
                    NavController controller= Navigation.findNavController(view);
                    controller.popBackStack();//出栈，非常重要，否则会出现点击导航栏时，该fragment无法跳转的情况
                    controller.navigate(R.id.play);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

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
        CallAble.askMusic callable = new CallAble.askMusic();
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