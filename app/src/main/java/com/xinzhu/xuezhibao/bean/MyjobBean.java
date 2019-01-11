package com.xinzhu.xuezhibao.bean;

import java.io.Serializable;
import java.util.List;

public class MyjobBean implements Serializable {
    String jobReplyId;
    String jobTitle;
    long createTime;
    int state;
    String jobContent;
    int replyState;
    String curriculumTitle;
    String replyContent;
    List<FeedbackPictureBean> accessoryList;
    List<FeedbackPictureBean> jobList;
    String teacherName;
    String teacherImage;
    String teacherEvaluation;
    String teacherScore;
    long editTime;

    public long getEditTime() {
        return editTime;
    }

    public void setEditTime(long editTime) {
        this.editTime = editTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherImage() {
        return teacherImage;
    }

    public void setTeacherImage(String teacherImage) {
        this.teacherImage = teacherImage;
    }

    public String getTeacherEvaluation() {
        return teacherEvaluation;
    }

    public void setTeacherEvaluation(String teacherEvaluation) {
        this.teacherEvaluation = teacherEvaluation;
    }

    public String getTeacherScore() {
        return teacherScore;
    }

    public void setTeacherScore(String teacherScore) {
        this.teacherScore = teacherScore;
    }

    public List<FeedbackPictureBean> getAccessoryList() {
        return accessoryList;
    }

    public void setAccessoryList(List<FeedbackPictureBean> accessoryList) {
        this.accessoryList = accessoryList;
    }

    public List<FeedbackPictureBean> getJobList() {
        return jobList;
    }

    public void setJobList(List<FeedbackPictureBean> jobList) {
        this.jobList = jobList;
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

    public String getJobReplyId() {
        return jobReplyId;
    }

    public void setJobReplyId(String jobReplyId) {
        this.jobReplyId = jobReplyId;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }


    public String getId() {
        return jobReplyId;
    }

    public void setId(String id) {
        this.jobReplyId = id;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
