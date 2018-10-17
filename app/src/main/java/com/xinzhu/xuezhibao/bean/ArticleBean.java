package com.xinzhu.xuezhibao.bean;

public class ArticleBean {
    String imurl;
    String title;
    String readnum;

    public ArticleBean(String imurl, String title, String readnum) {
        this.imurl = imurl;
        this.title = title;
        this.readnum = readnum;
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

    public String getReadnum() {
        return readnum;
    }

    public void setReadnum(String readnum) {
        this.readnum = readnum;
    }
}
