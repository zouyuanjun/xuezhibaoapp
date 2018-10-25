package com.xinzhu.xuezhibao.bean;

public class JiatingCourseBean {
    String id;
    String imurl;
    String title;
    String readnum;
    String teacher;
  String courseclass;

    public JiatingCourseBean(String id, String imurl, String title, String readnum, String teacher, String courseclass) {
        this.id = id;
        this.imurl = imurl;
        this.title = title;
        this.readnum = readnum;
        this.teacher = teacher;
        this.courseclass = courseclass;
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
}
