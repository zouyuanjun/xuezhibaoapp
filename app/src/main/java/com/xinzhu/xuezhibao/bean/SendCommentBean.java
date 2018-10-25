package com.xinzhu.xuezhibao.bean;

public class SendCommentBean {
    String token;
    String commentContent;
    String productId;
    String productType;

    public SendCommentBean(String token, String commentContent, String productId, String productType) {
        this.token = token;
        this.commentContent = commentContent;
        this.productId = productId;
        this.productType = productType;
    }

    public SendCommentBean() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
