package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.OrderBean;

import java.util.List;

public interface MyOrderInterface extends publiceviewinterface {
    void  getOrderList(List<OrderBean> orderBeans);
    void noMoredata();
    void applyrefund();
    void applyrefundfail(String tip);
    void confirmReceipt();
    void confirmReceiptfail(String tip);
}
