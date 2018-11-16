package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.MyPointsBean;

import java.util.List;

/**
 * Created by zou on 2018/11/15.
 */

public interface PointsInterface extends publiceviewinterface{
    void getdata(List<MyPointsBean> list);
    void nomoredata();
}
