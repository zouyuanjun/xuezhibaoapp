package com.xinzhu.xuezhibao.bean;

public class ArticleBean {
    String articlePicture;
    String articleTitle;
    String articleRead;
    String articleContent;
    int articleLike;
    String articleId;
    long createTime;
    String creater;

    public ArticleBean() {
    }

    public ArticleBean(String imurl, String title, String readnum) {
        this.articlePicture = imurl;
        this.articleTitle = title;
        this.articleRead = readnum;
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

    public String getArticleRead() {
        return articleRead;
    }

    public void setArticleRead(String articleRead) {
        this.articleRead = articleRead;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public int getArticleLike() {
        return articleLike;
    }

    public void setArticleLike(int articleLike) {
        this.articleLike = articleLike;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}
