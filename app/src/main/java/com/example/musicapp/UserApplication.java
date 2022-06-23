package com.example.musicapp;

import android.app.Application;

public class UserApplication extends Application {
    private int value;

    @Override
    public void onCreate(){
        super.onCreate();
        value = -1;
    }

    public void setValue(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}