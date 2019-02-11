package com.xinzhu.xuezhibao.bean;

import java.io.Serializable;
import java.util.List;

public class GoodsBean implements Serializable {
    String iml;
    String productName;
    String productImg;
    int buyNum;
    String productDetails;
    String productId;
    String sellNumber;
    List<FeedbackPictureBean> accessoryList;
String productPrice;
int productVirtual;
    public GoodsBean() {
    }

    public int getProductVirtual() {
        return productVirtual;
    }

    public void setProductVirtual(int productVirtual) {
        this.productVirtual = productVirtual;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSellNumber() {
        return sellNumber;
    }

    public void setSellNumber(String sellNumber) {
        this.sellNumber = sellNumber;
    }

    public List<FeedbackPictureBean> getAccessoryList() {
        return accessoryList;
    }

    public void setAccessoryList(List<FeedbackPictureBean> accessoryList) {
        this.accessoryList = accessoryList;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }



    public String getIml() {
        return iml;
    }

    public void setIml(String iml) {
        this.iml = iml;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }
}
