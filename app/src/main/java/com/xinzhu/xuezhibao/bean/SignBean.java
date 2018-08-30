package com.xinzhu.xuezhibao.bean;

public class SignBean {
    String phone ;
    String password;
    String code;
    String name;

    public SignBean() {
    }

    public SignBean(String phone, String password, String code, String name) {
        this.phone = phone;
        this.password = password;
        this.code = code;
        this.name = name;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
