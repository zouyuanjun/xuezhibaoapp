package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.MyTaskBean;

/**
 * Created by zou on 2018/11/13.
 */

public interface GetTaskInterface extends publiceviewinterface {
     void gettaskdetails(MyTaskBean myTaskBean);
     void accepttask();
     void gettaskfall();
}
