package com.xinzhu.xuezhibao.bean;

import com.xinzhu.xuezhibao.view.activity.SplashActivity;

import java.io.Serializable;

public class MyTaskBean implements Serializable {
    String taskId;
    String taskTitle;
    long awardIntegral;
int stateType;
String taskContent;
String myTaskId;
    String count;
    String taskNumber;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getMyTaskId() {
        return myTaskId;
    }

    public void setMyTaskId(String myTaskId) {
        this.myTaskId = myTaskId;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public int getStateType() {
        return stateType;
    }

    public void setStateType(int stateType) {
        this.stateType = stateType;
    }

    public MyTaskBean() {
    }

    public MyTaskBean(String id, String tasktitle, long jifen) {
        this.taskId = id;
        this.taskTitle = tasktitle;
        this.awardIntegral = jifen;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public long getAwardIntegral() {
        return awardIntegral;
    }

    public void setAwardIntegral(long awardIntegral) {
        this.awardIntegral = awardIntegral;
    }
}
