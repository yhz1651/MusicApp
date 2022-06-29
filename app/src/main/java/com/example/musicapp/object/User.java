package com.example.musicapp.object;

import java.io.Serializable;
/*
歌曲类，用以存储歌曲的ID，歌曲名，歌手ID，歌曲种类，本地存储地址，上传用户ID*/
public class User implements Serializable{
    private String id;
    private String username;
    private String password;
    private String sex;
    private int age;
    private String phone;
    private String xingquregister;

    public User(){

    }
    public User(String username, String password, String sex, int age, String phone,String xingquregister) {
        super();
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.xingquregister = xingquregister;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getXingqu() {
        return xingquregister;
    }
    public void setXingqu(String xingquregister) {
        this.xingquregister = xingquregister;
    }

}