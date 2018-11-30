package com.xinzhu.xuezhibao.bean;

public class VideoVoiceBean {
    String videoPicture;
    String videoTitle;
    String videoLook;
    String videlLike;
    long createTime;
    String videoDetails;
    String videoTeacher;
    String videoPrice;
    String videoUrl;
    String videoId;
    int  isBuy;
    int videoType;  //0免费，1收费

    public VideoVoiceBean() {
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

    public String getVideoLook() {
        return videoLook;
    }

    public void setVideoLook(String videoLook) {
        this.videoLook = videoLook;
    }

    public String getVidelLike() {
        return videlLike;
    }

    public void setVidelLike(String videlLike) {
        this.videlLike = videlLike;
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
