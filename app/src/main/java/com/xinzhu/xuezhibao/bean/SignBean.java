package com.xinzhu.xuezhibao.bean;

public class SignBean {
    String account;
    String password;
    String code;
    String name;

    public SignBean() {
    }

    public SignBean(String phone, String password, String code) {
        this.account = phone;
        this.password = password;
        this.code = code;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
