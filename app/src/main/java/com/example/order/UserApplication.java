package com.example.order;

import android.app.Application;

public class UserApplication extends Application {
    private String value;

    @Override
    public void onCreate(){
        super.onCreate();
        value = "S1";
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}