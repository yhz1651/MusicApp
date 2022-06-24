package com.example.musicapp;

import java.io.Serializable;

public class Song implements Serializable {
    private String id;
    private String name;
    private String singer;
    private String kind;
    private String url;
    private int user;
    public Song() {
        super();
        //TODO Auto-generated constructor stub
    }
    public Song(String name, String singer,  String url, int user) {
        this.name = name;
        this.singer = singer;
        this.url = url;
        this.user = user;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
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
    @Override
    public String toString() {
        return "m_name='" + name + "', m_url='"
                + url + "', m_singer=" + singer  + ", m_type=0" + "m_userid="+user ;
    }


}
