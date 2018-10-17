package com.xinzhu.xuezhibao.bean;

public class VideoBean {
    String imurl;
    String title;

    public VideoBean(String imurl, String title) {
        this.imurl = imurl;
        this.title = title;
    }

    public String getImurl() {
        return imurl;
    }

    public void setImurl(String imurl) {
        this.imurl = imurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
