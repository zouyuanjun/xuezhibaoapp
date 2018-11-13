package com.xinzhu.xuezhibao.bean;

public class CommentBean {
    String image;
    String creater;
    long createTime;
    String commentContent;
    String commentId;
    String productId;

    public CommentBean() {
    }

    public CommentBean(String abcimurl, String creater, long createTime, String commentContent, String commentId, String productId) {
        this.image = abcimurl;
        this.creater = creater;
        this.createTime = createTime;
        this.commentContent = commentContent;
        this.commentId = commentId;
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
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
