package com.xinzhu.xuezhibao.bean;

public class CommentBean {
    String abcimurl;
    String creater;
    String createTime;
    String commentContent;
    String commentId;
    String productId;

    public CommentBean() {
    }

    public CommentBean(String abcimurl, String creater, String createTime, String commentContent, String commentId, String productId) {
        this.abcimurl = abcimurl;
        this.creater = creater;
        this.createTime = createTime;
        this.commentContent = commentContent;
        this.commentId = commentId;
        this.productId = productId;
    }

    public String getAbcimurl() {
        return abcimurl;
    }

    public void setAbcimurl(String abcimurl) {
        this.abcimurl = abcimurl;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
