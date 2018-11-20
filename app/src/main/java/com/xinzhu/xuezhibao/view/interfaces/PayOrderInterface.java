package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.AddressBean;

public interface PayOrderInterface {
     void  getaddress(AddressBean addressBean);
     void noMorepoint();
     void payfail();
     void paysuccessful();
}
