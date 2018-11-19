package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.AddressBean;
import com.xinzhu.xuezhibao.bean.OrderBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.MyOrderInterface;
import com.xinzhu.xuezhibao.view.interfaces.PayOrderInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

public class MyOrederPresenter extends BasePresenter{
    MyOrderInterface myOrderInterface;
    PayOrderInterface payOrderInterface;

    public MyOrederPresenter(PayOrderInterface payOrderInterface) {
        this.payOrderInterface = payOrderInterface;
        inithandle();
    }

    public MyOrederPresenter(MyOrderInterface myOrderInterface) {
        this.myOrderInterface = myOrderInterface;
        inithandle();
    }
    public void inithandle(){
        super.handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String result= (String) msg.obj;
                Log.d(result);
                int what=msg.what;
                if (what==1){
                    String data=JsonUtils.getStringValue(result,"Data");
                    data=JsonUtils.getStringValue(data,"rows");
                    List<OrderBean> list=JSON.parseArray(data,OrderBean.class);
                    if (null!=list&&list.size()>0){
                        myOrderInterface.getOrderList(list);
                    }else {
                        myOrderInterface.noMoredata();
                    }

                }if (what==2){
                    String data=JsonUtils.getStringValue(result,"Data");
                    AddressBean addressBean=JsonUtils.stringToObject(data,AddressBean.class);
                    if (null!=payOrderInterface){
                        payOrderInterface.getaddress(addressBean);
                    }
                }
            }


        };
    }

    public void getOrderList(int page,int state ){
        String data=JsonUtils.keyValueToString2("state",state,"pageNo",page);
        data=JsonUtils.addKeyValue(data,"token",Constants.TOKEN);
        Network.getnetwork().postJson(data,Constants.URL+"/app/select-order-list",handler,1);
    }
    public void getdefendaddress(){
        String data=JsonUtils.keyValueToString2("token",Constants.TOKEN,"isDefault",1);
        Network.getnetwork().postJson(data,Constants.URL+"/app/select-default-take-address",handler,2);
    }
    public void pay(String addressid,String productid) {
        String data = JsonUtils.keyValueToString2("addressId", addressid, "objectId", productid);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/exchange-product", handler, 3);
    }
}
