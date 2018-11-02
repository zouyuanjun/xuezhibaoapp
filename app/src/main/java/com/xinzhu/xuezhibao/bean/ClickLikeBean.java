package com.xinzhu.xuezhibao.bean;

public class ClickLikeBean {
    String token;
    String objectId;
    String likeType;

    public ClickLikeBean() {
    }

    public ClickLikeBean(String token, String objectId, String likeType) {
        this.token = token;
        this.objectId = objectId;
        this.likeType = likeType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getLikeType() {
        return likeType;
    }

    public void setLikeType(String likeType) {
        this.likeType = likeType;
    }
}
