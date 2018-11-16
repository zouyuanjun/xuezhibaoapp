package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.AddressBean;

import java.util.List;

/**
 * Created by zou on 2018/11/15.
 */

public interface AddressInterface extends publiceviewinterface {
    void getaddressList(List<AddressBean> list);
    void setDefend(int code);
    void deliteaddress(int code);
    void nodata();
    void addaddress();
    void addaddressfail();
    void updata(int code);
}
