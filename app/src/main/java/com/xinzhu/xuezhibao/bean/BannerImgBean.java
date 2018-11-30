package com.xinzhu.xuezhibao.bean;

public class BannerImgBean {
    String adUrl;
    String linkAddress;
int newPlace;
    public BannerImgBean() {
    }

    public int getNewPlace() {
        return newPlace;
    }

    public void setNewPlace(int newPlace) {
        this.newPlace = newPlace;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }
}
