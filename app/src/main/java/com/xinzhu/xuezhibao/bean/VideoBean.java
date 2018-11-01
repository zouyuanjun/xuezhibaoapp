package com.xinzhu.xuezhibao.bean;

public class VideoBean {
    String videoPicture;
    String videoTitle;
    String videoId;

    public VideoBean() {
    }

    public VideoBean(String videoPicture, String videoTitle) {
        this.videoPicture = videoPicture;
        this.videoTitle = videoTitle;
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

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
