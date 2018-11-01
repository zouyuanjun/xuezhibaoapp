package com.xinzhu.xuezhibao.bean;

public class UserBasicInfo {
    String token;
    String memberId;
    String nickName;
    String studentName;
    String fatherName;
    String motherName;
    String regionId;
    String image;
    int studentAge;
    String dictionaryName;

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public UserBasicInfo() {
    }

    public UserBasicInfo(String nickName, String studentName, String fatherName, String motherName, String regionId, int studentAge) {
        this.nickName = nickName;
        this.studentName = studentName;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.regionId = regionId;
        this.studentAge = studentAge;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }
}
