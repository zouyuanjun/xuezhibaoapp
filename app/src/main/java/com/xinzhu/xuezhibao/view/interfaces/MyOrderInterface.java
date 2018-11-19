package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.OrderBean;

import java.util.List;

public interface MyOrderInterface extends publiceviewinterface {
    void  getOrderList(List<OrderBean> orderBeans);
}
