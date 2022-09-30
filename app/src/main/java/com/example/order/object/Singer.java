package com.example.order.object;

import java.io.Serializable;
/**
 歌手类，用来存放歌手的id,姓名，国籍，简介，
 * */
public class Singer implements Serializable {

    private String s_id;
    private String s_name;
    private String s_region;
    private String s_intro;
    public String getS_region() {
        return s_region;
    }

    public void setS_region(String s_region) {
        this.s_region = s_region;
    }

    public String getS_intro() {
        return s_intro;
    }

    public void setS_intro(String s_intro) {
        this.s_intro = s_intro;
    }


    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    private String content;
    private String image_url;
}
