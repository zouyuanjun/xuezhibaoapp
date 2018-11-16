package com.xinzhu.xuezhibao.bean;

import java.io.Serializable;
import java.util.List;

public class CourseFeedbackBean implements Serializable {
    String feedbackId;
    String content;
    String replyContent;
    int replyCount;
    String curriculumTitle;
    int replyState; //1是已阅读，100未阅读
    List<CourseFeedbackRpBean> listReply;

    public CourseFeedbackBean() {
    }

    public List<CourseFeedbackRpBean> getListReply() {
        return listReply;
    }

    public void setListReply(List<CourseFeedbackRpBean> listReply) {
        this.listReply = listReply;
    }

    public int getReplyState() {
        return replyState;
    }

    public void setReplyState(int replyState) {
        this.replyState = replyState;
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

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getCurriculumTitle() {
        return curriculumTitle;
    }

    public void setCurriculumTitle(String curriculumTitle) {
        this.curriculumTitle = curriculumTitle;
    }
}
