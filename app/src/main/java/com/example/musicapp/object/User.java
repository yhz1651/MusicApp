package com.example.musicapp.object;

import java.io.Serializable;
/*
歌曲类，用以存储歌曲的ID，歌曲名，歌手ID，歌曲种类，本地存储地址，上传用户ID*/
public class User implements Serializable{
    private String u_id;
    private String u_username;
    private String u_password;
    private String u_sex;
    private int u_age;
    private String u_phone;
    private String u_hobby;
    private int u_type;

    public String getU_hobby() {
        return u_hobby;
    }

    public void setU_hobby(String u_hobby) {
        this.u_hobby = u_hobby;
    }

    public int getU_type() {
        return u_type;
    }

    public void setU_type(int u_type) {
        this.u_type = u_type;
    }

    public User(){

    }
    public User(String username, String password, String sex, int age, String phone,String xingquregister) {
        super();
        this.u_username = username;
        this.u_password = password;
        this.u_sex = sex;
        this.u_age = age;
        this.u_phone = phone;
        this.u_hobby = xingquregister;
    }
    public String getU_id() {
        return u_id;
    }
    public void setU_id(String u_id) {
        this.u_id = u_id;
    }
    public String getU_username() {
        return u_username;
    }
    public void setU_username(String u_username) {
        this.u_username = u_username;
    }
    public String getU_password() {
        return u_password;
    }
    public void setU_password(String u_password) {
        this.u_password = u_password;
    }
    public String getU_sex() {
        return u_sex;
    }
    public void setU_sex(String u_sex) {
        this.u_sex = u_sex;
    }
    public int getU_age() {
        return u_age;
    }
    public void setU_age(int u_age) {
        this.u_age = u_age;
    }
    public String getU_phone() {
        return u_phone;
    }
    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

}