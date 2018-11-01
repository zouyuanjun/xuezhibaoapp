package com.xinzhu.xuezhibao.bean;

public class FeedBackDictionaryBean {
    String dictionaryId;
    String dictionaryName;

    public FeedBackDictionaryBean() {
    }

    public FeedBackDictionaryBean(String dictionaryId, String dictionaryName) {
        this.dictionaryId = dictionaryId;
        this.dictionaryName = dictionaryName;
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }
}
