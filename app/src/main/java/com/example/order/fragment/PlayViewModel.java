package com.example.order.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.order.object.Music;

import java.util.List;

public class PlayViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<List<Music>> selectMusic = new MutableLiveData<List<Music>>();


    public void setSelectMusic(List<Music> music){
        selectMusic.setValue(music);
    }

    public MutableLiveData<List<Music>> getSelectMusic() {
        return selectMusic;
    }

}