package com.example.order.object;
/*
歌曲类，用以存储歌曲的ID，歌曲名，歌手ID，歌曲种类，本地存储地址，上传用户ID*/
import java.io.Serializable;

public class Music implements Serializable {
    private String m_id;//歌曲ID
    private String m_name;//歌曲名
    private String m_singer;//歌手名
    private String kind;//类别
    private String m_url;//路径
    private String m_userid;//上传用户ID
    private int m_duration;
    private boolean local;//是否在本地

    public Music(String id, String name, String singer, String url, String user,int duration) {//对上传信息初始化
        this.m_id = id;
        this.m_name = name;
        this.m_singer = singer;
        this.m_url = url;
        this.m_userid = user;
        this.m_duration =duration;
        this.local=true;
    }
    public boolean getLocal() {
        return local;
    }
    public void setLocal(boolean local) {
        this.local = local;
    }
    public int getM_duration() {
        return m_duration;
    }

    public void setM_duration(int m_duration) {
        this.m_duration = m_duration;
    }

    public String getM_userid() {
        return m_userid;
    }

    public void setM_userid(String m_userid) {
        this.m_userid = m_userid;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_singer() {
        return m_singer;
    }

    public void setM_singer(String m_singer) {
        this.m_singer = m_singer;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getM_url() {
        return m_url;
    }

    public void setM_url(String m_url) {
        this.m_url = m_url;
    }



}
