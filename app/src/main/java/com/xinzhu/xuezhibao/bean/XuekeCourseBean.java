package com.xinzhu.xuezhibao.bean;

public class XuekeCourseBean {
    String id;
    String imurl;
    String title;
    String readnum;
    String teacher;
    String courseclass;
    String courseclass2;

    public XuekeCourseBean(String id, String imurl, String title, String readnum, String teacher, String courseclass, String courseclass2) {
        this.id = id;
        this.imurl = imurl;
        this.title = title;
        this.readnum = readnum;
        this.teacher = teacher;
        this.courseclass = courseclass;
        this.courseclass2 = courseclass2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCourseclass() {
        return courseclass;
    }

    public void setCourseclass(String courseclass) {
        this.courseclass = courseclass;
    }

    public String getCourseclass2() {
        return courseclass2;
    }

    public void setCourseclass2(String courseclass2) {
        this.courseclass2 = courseclass2;
    }
}
