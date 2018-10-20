package com.xinzhu.xuezhibao.bean;

public class CommentBean {
    String imurl;
    String username;
    String dianzan;
    String creattime;
    String commentdetils;

    public CommentBean(String imurl, String username, String dianzan, String creattime, String commentdetils) {
        this.imurl = imurl;
        this.username = username;
        this.dianzan = dianzan;
        this.creattime = creattime;
        this.commentdetils = commentdetils;
    }

    public String getImurl() {
        return imurl;
    }

    public void setImurl(String imurl) {
        this.imurl = imurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDianzan() {
        return dianzan;
    }

    public void setDianzan(String dianzan) {
        this.dianzan = dianzan;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public String getCommentdetils() {
        return commentdetils;
    }

    public void setCommentdetils(String commentdetils) {
        this.commentdetils = commentdetils;
    }
}
