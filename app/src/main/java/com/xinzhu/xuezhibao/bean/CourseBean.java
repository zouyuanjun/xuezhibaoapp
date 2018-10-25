package com.xinzhu.xuezhibao.bean;

public class CourseBean {
    String imgurl;
    String price;
    String title;
    String num;

    public CourseBean(String imgurl, String price, String title, String num) {
        this.imgurl = imgurl;
        this.price = price;
        this.title = title;
        this.num = num;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
