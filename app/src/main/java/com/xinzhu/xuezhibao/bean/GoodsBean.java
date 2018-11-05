package com.xinzhu.xuezhibao.bean;

public class GoodsBean {
    String iml;
    String title;
    String price;
    String paynum;

    public GoodsBean(String iml, String title, String price, String paynum) {
        this.iml = iml;
        this.title = title;
        this.price = price;
        this.paynum = paynum;
    }

    public String getIml() {
        return iml;
    }

    public void setIml(String iml) {
        this.iml = iml;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPaynum() {
        return paynum;
    }

    public void setPaynum(String paynum) {
        this.paynum = paynum;
    }
}
