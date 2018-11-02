package com.xinzhu.xuezhibao.bean;

public class CollectBean {
    String token;
    String objectId;
    String collectType;

    public CollectBean() {
    }

    public CollectBean(String token, String objectId, String likeType) {
        this.token = token;
        this.objectId = objectId;
        this.collectType = likeType;
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
        return collectType;
    }

    public void setLikeType(String likeType) {
        this.collectType = likeType;
    }
}
