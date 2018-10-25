package com.xinzhu.xuezhibao.bean;

public class JiaojiaoCourseBean {
    String imurl;
    String title;
    String readnum;
    String teacher;
    String allclass;
    String alreadyclass;

    public JiaojiaoCourseBean(String imurl, String title, String readnum, String teacher, String allclass, String alreadyclass) {
        this.imurl = imurl;
        this.title = title;
        this.readnum = readnum;
        this.teacher = teacher;
        this.allclass = allclass;
        this.alreadyclass = alreadyclass;
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getAllclass() {
        return allclass;
    }

    public void setAllclass(String allclass) {
        this.allclass = allclass;
    }

    public String getAlreadyclass() {
        return alreadyclass;
    }

    public void setAlreadyclass(String alreadyclass) {
        this.alreadyclass = alreadyclass;
    }
}
