package com.xinzhu.xuezhibao.bean;

public class WXSignBean {
    String phone;
    String password;
    String name;
    String openid;
    String photourl;
String code;
    public WXSignBean(String phone, String password, String name, String openid, String photourl) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.openid = openid;
        this.photourl = photourl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }
}
