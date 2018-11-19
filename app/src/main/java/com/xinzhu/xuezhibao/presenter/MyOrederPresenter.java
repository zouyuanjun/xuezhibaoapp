package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;

import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.MyOrderInterface;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

public class MyOrederPresenter extends BasePresenter{
    MyOrderInterface myOrderInterface;

    public MyOrederPresenter(MyOrderInterface myOrderInterface) {
        this.myOrderInterface = myOrderInterface;
    }
    public void inithandle(){
        super.handler=new Handler(){

        };
    }

    public void getOrderList(int page,int state ){
        String data=JsonUtils.keyValueToString2("state",state,"pageNo",page);
        data=JsonUtils.addKeyValue(data,"token",Constants.TOKEN);
        Network.getnetwork().postJson(data,Constants.URL+"/app/select-order-list",handler,1);
    }
}
