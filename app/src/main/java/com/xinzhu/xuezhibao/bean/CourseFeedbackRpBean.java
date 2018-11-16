package com.xinzhu.xuezhibao.bean;

import java.io.Serializable;

/**
 * Created by zou on 2018/11/13.
 */

public class CourseFeedbackRpBean implements Serializable {
    String replyContent;
    String headPortraitUrl;

    public CourseFeedbackRpBean() {
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }
}
