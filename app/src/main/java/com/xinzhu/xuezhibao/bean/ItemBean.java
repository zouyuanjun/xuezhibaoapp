package com.xinzhu.xuezhibao.bean;

public class ItemBean {
    String imurl;
    String title;
    String readnum;
    String dianzan;
    String creattime;
    int i;

    public ItemBean(String imurl, String title, String readnum, String dianzan, String creattime, int i) {
        this.imurl = imurl;
        this.title = title;
        this.readnum = readnum;
        this.dianzan = dianzan;
        this.creattime = creattime;
        this.i = i;
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

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }


}
