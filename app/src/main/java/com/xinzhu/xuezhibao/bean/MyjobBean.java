package com.xinzhu.xuezhibao.bean;

import java.io.Serializable;
import java.util.List;

public class MyjobBean implements Serializable{
    String jobId;
    String jobTitle;
    long createTime;
    String state;
    String jobContent;
    int replyState;
    String curriculumTitle;
    String replyContent;
    List<FeedbackPictureBean> accessoryList;

    public List<FeedbackPictureBean> getAccessoryList() {
        return accessoryList;
    }

    public void setAccessoryList(List<FeedbackPictureBean> accessoryList) {
        this.accessoryList = accessoryList;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public List<FeedbackPictureBean> getList() {
        return accessoryList;
    }

    public void setList(List<FeedbackPictureBean> list) {
        this.accessoryList = list;
    }

    public String getCurriculumTitle() {
        return curriculumTitle;
    }

    public void setCurriculumTitle(String curriculumTitle) {
        this.curriculumTitle = curriculumTitle;
    }

    public int getReplyState() {
        return replyState;
    }

    public void setReplyState(int replyState) {
        this.replyState = replyState;
    }

    public MyjobBean() {
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }


    public String getId() {
        return jobId;
    }

    public void setId(String id) {
        this.jobId = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
