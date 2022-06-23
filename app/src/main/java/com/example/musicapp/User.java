package com.example.musicapp;

import java.io.Serializable;

public class User implements Serializable{
    private int id;
    private String username;
    private String password;
    private String sex;
    private int age;
    private String phone;
    private String xingquregister;
    public User() {
        super();
        //TODO Auto-generated constructor stub
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
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password="
                + password + ", sex=" + sex + ", age=" + age + ", phone=" + phone + ", xingquregister=" + xingquregister + "]";
    }

}