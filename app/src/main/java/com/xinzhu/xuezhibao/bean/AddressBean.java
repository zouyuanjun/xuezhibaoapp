package com.xinzhu.xuezhibao.bean;

import java.io.Serializable;

/**
 * Created by zou on 2018/11/15.
 */

public class AddressBean implements Serializable{
    String province;
    String postcode;
    String linkPhone;
    String linkman;
    String addressId;
    String address;
    String city;
    String county;
    int isDefault;

    public AddressBean() {
    }

    public AddressBean(String province, String linkPhone, String linkman, String address, String city, String county, int isDefault) {
        this.province = province;
        this.linkPhone = linkPhone;
        this.linkman = linkman;
        this.address = address;
        this.city = city;
        this.county = county;
        this.isDefault = isDefault;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
}
