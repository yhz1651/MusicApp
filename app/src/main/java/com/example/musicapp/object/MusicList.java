package com.example.musicapp.object;
/**
歌单类，用来存放歌单的id,本地url，创建者ID，图片url
* */
import java.io.Serializable;

public class MusicList implements Serializable {
    private String ml_id;//歌单ID
    private String ml_name;//歌曲名
    private String ml_userid;//创建者ID
    public String getMl_id() {
        return ml_id;
    }

    public void setMl_id(String ml_id) {
        this.ml_id = ml_id;
    }

    public String getMl_name() {
        return ml_name;
    }

    public void setMl_name(String ml_name) {
        this.ml_name = ml_name;
    }

    public String getMl_userid() {
        return ml_userid;
    }

    public void setMl_userid(String ml_userid) {
        this.ml_userid = ml_userid;
    }




}
