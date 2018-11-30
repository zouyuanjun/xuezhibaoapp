package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.MyjobBean;

/**
 * Created by zou on 2018/11/14.
 * 作业详情接口
 */

public interface MyJobDetailInterpace extends publiceviewinterface {
    void getjobbyid(MyjobBean myjobBean);
    void getdatafail(String tip);
}
