package com.example.musicapp.object;
/*
歌曲类，用以存储歌曲的ID，歌曲名，歌手ID，歌曲种类，本地存储地址，上传用户ID*/
import java.io.Serializable;

public class Music implements Serializable {
    private String id;//歌曲ID
    private String name;//歌曲名
    private String singer;//歌手名
    private String kind;//类别
    private String url;//路径
    private String user;//上传用户ID


    public Music(String id, String name, String singer, String url, String user) {//对上传信息初始化
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.url = url;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger( String singer) {
        this.singer = singer;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



}
