package com.example.musicapp;

public class lc {
    public int getUid() {
        return uid;
    }

    private  int uid;
    private String picId;
    private String name;
    public lc(int uid,String picId, String name) {
        this.uid = uid;
        this.picId = picId;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getPicId() {
        return picId;
    }
}
