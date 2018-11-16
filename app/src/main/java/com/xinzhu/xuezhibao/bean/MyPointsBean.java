package com.xinzhu.xuezhibao.bean;

/**
 * Created by zou on 2018/11/15.
 */

public class MyPointsBean {
    int trackId;
    int trackType;
    String trackContent;
    long createTime;
    String pointnum;

    public String getPointnum() {
        return pointnum;
    }

    public void setPointnum(String pointnum) {
        this.pointnum = pointnum;
    }

    public MyPointsBean() {
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getTrackType() {
        return trackType;
    }

    public void setTrackType(int trackType) {
        this.trackType = trackType;
    }

    public String getTrackContent() {
        return trackContent;
    }

    public void setTrackContent(String trackContent) {
        this.trackContent = trackContent;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
