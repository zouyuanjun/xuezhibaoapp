package com.xinzhu.xuezhibao.messagebean;

public class PayResultMessage {
    int code=999;

    public PayResultMessage(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
