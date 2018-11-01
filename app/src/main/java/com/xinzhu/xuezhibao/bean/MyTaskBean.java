package com.xinzhu.xuezhibao.bean;

public class MyTaskBean {
    String id;
    String tasktitle;
    String jifen;

    public MyTaskBean() {
    }

    public MyTaskBean(String id, String tasktitle, String jifen) {
        this.id = id;
        this.tasktitle = tasktitle;
        this.jifen = jifen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTasktitle() {
        return tasktitle;
    }

    public void setTasktitle(String tasktitle) {
        this.tasktitle = tasktitle;
    }

    public String getJifen() {
        return jifen;
    }

    public void setJifen(String jifen) {
        this.jifen = jifen;
    }
}
