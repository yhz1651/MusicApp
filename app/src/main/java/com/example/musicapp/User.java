package com.example.musicapp;

import java.io.Serializable;

public class User implements Serializable{
    private int id;
    private String username;
    private String password;
    private String xingquregister;
    private String sex;
    public User() {
        super();
        //TODO Auto-generated constructor stub
    }
    public User(String username, String password, String xingquregister, String sex) {
        super();
        this.username = username;
        this.password = password;
        this.xingquregister = xingquregister;
        this.sex = sex;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
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
    public String getXingqu() {
        return xingquregister;
    }
    public void setXingqu(String xingquregister) {
        this.xingquregister = xingquregister;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password="
                + password + ", xingquregister=" + xingquregister + ", sex=" + sex + "]";
    }

}