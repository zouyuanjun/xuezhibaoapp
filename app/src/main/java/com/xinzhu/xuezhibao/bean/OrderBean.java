package com.xinzhu.xuezhibao.bean;

import java.io.Serializable;

public class OrderBean implements Serializable {
    String ordertype;
    String orderTime;
    String orderNum;
    String orderPrice;
    String payTime;
    String picture;
    String shipmentsTime;
    String state;
    String objectId;
    String dictionaryName;
    String name;
    String price;

    public OrderBean() {
    }

    public String getOrdertype() {
        return ordertype;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getShipmentsTime() {
        return shipmentsTime;
    }

    public void setShipmentsTime(String shipmentsTime) {
        this.shipmentsTime = shipmentsTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
