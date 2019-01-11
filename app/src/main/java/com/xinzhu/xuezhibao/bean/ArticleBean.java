package com.xinzhu.xuezhibao.bean;

public class ArticleBean {
    String articlePicture;
    String articleTitle;
    String articleReadFalse;
    String articleContent;
    int articleLikeFalse;
    String articleId;
    long createTime;
    String creater;

    public ArticleBean() {
    }

    public ArticleBean(String imurl, String title, String readnum) {
        this.articlePicture = imurl;
        this.articleTitle = title;
        this.articleReadFalse = readnum;
    }


    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getArticlePicture() {
        return articlePicture;
    }

    public void setArticlePicture(String articlePicture) {
        this.articlePicture = articlePicture;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleReadFalse() {
        return articleReadFalse;
    }

    public void setArticleReadFalse(String articleReadFalse) {
        this.articleReadFalse = articleReadFalse;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public int getArticleLikeFalse() {
        return articleLikeFalse;
    }

    public void setArticleLikeFalse(int articleLikeFalse) {
        this.articleLikeFalse = articleLikeFalse;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}
