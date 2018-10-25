package com.xinzhu.xuezhibao.bean;

public class TeacherBean  {
    String imurl;
    String name;
    String course;

    public TeacherBean(String imurl, String name, String course) {
        this.imurl = imurl;
        this.name = name;
        this.course = course;
    }

    public String getImurl() {
        return imurl;
    }

    public void setImurl(String imurl) {
        this.imurl = imurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
