package com.xinzhu.xuezhibao.bean;

public class GoodsComment {
    String picture;
    String nickname;
    String content;
    long createTime;
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public GoodsComment() {
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
