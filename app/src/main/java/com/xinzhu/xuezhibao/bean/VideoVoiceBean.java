package com.xinzhu.xuezhibao.bean;

public class VideoVoiceBean {
    String videoPicture;
    String videoTitle;
    String videoLookFalse;
    String videoLikeFalse;
    long createTime;
    String videoDetails;
    String videoTeacher;
    String videoPrice;
    String videoUrl;
    String videoId;
    int  isBuy;
    int videoType;  //0免费，1收费
    int integralAllow; //是否允许积分购买
    String integralPrice; //积分购买价格
    int trySee; //是否试看
    public VideoVoiceBean() {
    }

    public int getIntegralAllow() {
        return integralAllow;
    }

    public void setIntegralAllow(int integralAllow) {
        this.integralAllow = integralAllow;
    }

    public int getTrySee() {
        return trySee;
    }

    public void setTrySee(int trySee) {
        this.trySee = trySee;
    }

    public String getIntegralPrice() {
        return integralPrice;
    }

    public void setIntegralPrice(String integralPrice) {
        this.integralPrice = integralPrice;
    }

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
    }

    public int getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(int isBuy) {
        this.isBuy = isBuy;
    }

    public String getVideoPicture() {
        return videoPicture;
    }

    public void setVideoPicture(String videoPicture) {
        this.videoPicture = videoPicture;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoLookFalse() {
        return videoLookFalse;
    }

    public void setVideoLookFalse(String videoLookFalse) {
        this.videoLookFalse = videoLookFalse;
    }

    public String getVideoLikeFalse() {
        return videoLikeFalse;
    }

    public void setVideoLikeFalse(String videoLikeFalse) {
        this.videoLikeFalse = videoLikeFalse;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getVideoDetails() {
        return videoDetails;
    }

    public void setVideoDetails(String videoDetails) {
        this.videoDetails = videoDetails;
    }

    public String getVideoTeacher() {
        return videoTeacher;
    }

    public void setVideoTeacher(String videoTeacher) {
        this.videoTeacher = videoTeacher;
    }

    public String getVideoPrice() {
        return videoPrice;
    }

    public void setVideoPrice(String videoPrice) {
        this.videoPrice = videoPrice;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
