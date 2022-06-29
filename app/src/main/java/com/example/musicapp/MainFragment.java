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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.musicapp.object.Music;
import com.example.musicapp.service.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
/**
 * 主页面
* */
public class MainFragment extends Fragment {

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
        Cursor cursor;
        DatabaseHelper dbsqLiteOpenHelper = new DatabaseHelper(getContext());
        SQLiteDatabase sdb = dbsqLiteOpenHelper.getWritableDatabase();

        cursor=sdb.query("Music",new String[]{"m_id,m_name,m_singer"},null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Music music1 =new Music(cursor.getString(0), cursor.getString(1),cursor.getString(2),null,null);
                musicList.add(music1);
            }while(cursor.moveToNext());
            cursor.close();
        }
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



}