package com.xinzhu.xuezhibao.bean;

import java.io.Serializable;

public class CourseFeedbackBean implements Serializable {
    String feedbackId;
    String content;
    String replyContent;
    String replyCount;
    String curriculumTitle;
    int replyState; //1是已阅读，100未阅读

    public CourseFeedbackBean() {
    }

    public int getReplyState() {
        return replyState;
    }

    public void setReplyState(int replyState) {
        this.replyState = replyState;
    }

    public CourseFeedbackBean(String id, String title, String concent, String num, String feedbacktheme) {
        this.feedbackId = id;
        this.content = title;
        this.replyContent = concent;
        this.replyCount = num;
        this.curriculumTitle = feedbacktheme;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public String getCurriculumTitle() {
        return curriculumTitle;
    }

    public void setCurriculumTitle(String curriculumTitle) {
        this.curriculumTitle = curriculumTitle;
    }
}
