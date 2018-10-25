package com.xinzhu.xuezhibao.bean;

public class TaskBean {
    String id;
    String taskname;
    String time;
    String status;

    public TaskBean(String id, String taskname, String time, String status) {
        this.id = id;
        this.taskname = taskname;
        this.time = time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
