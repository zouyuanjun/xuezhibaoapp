package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.MyTaskBean;

import java.util.List;

/**
 * Created by zou on 2018/11/13.
 * 用于获取任务列表
 */

public interface TaskInterface extends publiceviewinterface{
    void get2task(List<MyTaskBean> taskBeans);
    void get1task(List<MyTaskBean> taskBeans);
    void nomoredata();
    void chocksuccessful();
    void chockfail(int code);
    void ischock(String data);
}
