package com.example.musicapp.object;
/**
歌单类，用来存放歌单的id,本地url，创建者ID，图片url
* */
import java.io.Serializable;

public class MusicList implements Serializable {
    private String id;//歌单ID
    private String url;//歌曲本地地址
    private String user;//创建者ID
    private String imageurl;//图片url

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
