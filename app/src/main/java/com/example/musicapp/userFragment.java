package com.example.musicapp;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * 用户个人界面
 * 包含
 * 上传
 * 搜索
 * 歌曲列表
 * 歌单
 */

public class userFragment extends Fragment {

    private UserViewModel mViewModel;

    public static userFragment newInstance() {
        return new userFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment,container,false);//绑定视图

        return view;
    }



}