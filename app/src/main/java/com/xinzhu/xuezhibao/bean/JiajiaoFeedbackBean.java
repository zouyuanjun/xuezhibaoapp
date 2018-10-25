package com.xinzhu.xuezhibao.bean;

public class JiajiaoFeedbackBean {
    String id;
    String title;
    String concent;
            String num;
            String feedbacktheme;

    public JiajiaoFeedbackBean(String id, String title, String concent, String num, String feedbacktheme) {
        this.id = id;
        this.title = title;
        this.concent = concent;
        this.num = num;
        this.feedbacktheme = feedbacktheme;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConcent() {
        return concent;
    }

    public void setConcent(String concent) {
        this.concent = concent;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getFeedbacktheme() {
        return feedbacktheme;
    }

    public void setFeedbacktheme(String feedbacktheme) {
        this.feedbacktheme = feedbacktheme;
    }
}
