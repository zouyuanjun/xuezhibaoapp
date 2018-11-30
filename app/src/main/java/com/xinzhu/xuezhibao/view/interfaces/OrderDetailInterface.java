package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.OrderBean;

public interface OrderDetailInterface {
    void getorderdetail(OrderBean orderBean);
    void affirmorder();
    void affirmorderfail(String tip);
}
