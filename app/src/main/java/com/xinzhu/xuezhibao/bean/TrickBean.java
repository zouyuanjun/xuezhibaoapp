package com.xinzhu.xuezhibao.bean;

public class TrickBean {
    String createTime;
    String trackContent;
    String trackType;
    String trackId;

    public TrickBean() {
    }

    public String getTrackType() {
        return trackType;
    }

    public void setTrackType(String trackType) {
        this.trackType = trackType;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public TrickBean(String time, String event) {
        this.createTime = time;
        this.trackContent = event;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTrackContent() {
        return trackContent;
    }

    public void setTrackContent(String trackContent) {
        this.trackContent = trackContent;
    }
}
