package com.example.order.object;

import java.io.Serializable;

public class Comment implements Serializable
{
    private String c_id; // 评论ID
    private String c_content; // 评论内容
    private int c_good; // 点赞数
    private String c_userid; // 用户id
    private String c_username; // 用户名字

    public Comment(String id, String content, int good, String userid, String c_username) { // 构造函数初始化
        this.c_id = id;
        this.c_content = content;
        this.c_good = good;
        this.c_userid = userid;
        this.c_username = c_username;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_content() {
        return c_content;
    }

    public void setC_content(String c_content) {
        this.c_content = c_content;
    }

    public int getC_good() {
        return c_good;
    }

    public void setC_good(int c_good) {
        this.c_good = c_good;
    }

    public String getC_userid() {
        return c_userid;
    }

    public void setC_userid(String c_userid) {
        this.c_userid = c_userid;
    }

    public String getC_username() {
        return c_username;
    }

    public void setC_username(String c_username) {
        this.c_username = c_username;
    }
}
