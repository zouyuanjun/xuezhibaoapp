package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.AddressBean;
import com.xinzhu.xuezhibao.bean.OrderBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.MyOrderInterface;
import com.xinzhu.xuezhibao.view.interfaces.OrderDetailInterface;
import com.xinzhu.xuezhibao.view.interfaces.PayOrderInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

public class MyOrederPresenter extends BasePresenter {
    MyOrderInterface myOrderInterface;
    PayOrderInterface payOrderInterface;
    OrderDetailInterface orderDetailInterface;

    public MyOrederPresenter(OrderDetailInterface orderDetailInterface) {
        this.orderDetailInterface = orderDetailInterface;
        inithandle();
    }

    public MyOrederPresenter(PayOrderInterface payOrderInterface) {
        this.payOrderInterface = payOrderInterface;
        inithandle();
    }

    public MyOrederPresenter(MyOrderInterface myOrderInterface) {
        this.myOrderInterface = myOrderInterface;
        inithandle();
    }

    public void inithandle() {
        super.handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String result = (String) msg.obj;
                Log.d(result);
                int what = msg.what;
                int code = JsonUtils.getIntValue(result, "Code");
                String tip = JsonUtils.getStringValue(result, "Tip");
                if (what == 1) {
                    if (code == 100) {
                        String data = JsonUtils.getStringValue(result, "Data");
                        List<OrderBean> list = JSON.parseArray(data, OrderBean.class);
                        if (null != list && list.size() > 0) {
                            myOrderInterface.getOrderList(list);
                        } else {
                            myOrderInterface.noMoredata();
                        }
                    }

                }
                if (what == 2) {
                    if (code == 100) {
                        String data = JsonUtils.getStringValue(result, "Data");
                        AddressBean addressBean = JsonUtils.stringToObject(data, AddressBean.class);
                        if (null != payOrderInterface) {
                            payOrderInterface.getaddress(addressBean);
                        }
                    }

                } else if (what == 3) {
                    String data = JsonUtils.getStringValue(result, "Data");
                    if (null != payOrderInterface) {
                        if (code == 203) {
                            payOrderInterface.noMorepoint();
                        } else if (code == 100) {
                            payOrderInterface.paysuccessful();
                        }
                    }
                } else if (what == 4) {
                    if (code == 100) {
                        myOrderInterface.applyrefund();
                    } else {
                        myOrderInterface.applyrefundfail(tip);
                    }
                } else if (what == 6) {
                    if (code == 100) {
                        if (null!=myOrderInterface){
                            myOrderInterface.confirmReceipt();
                        }else if (null!=orderDetailInterface){
                            orderDetailInterface.affirmorder();
                        }

                    } else {
                        if (null!=myOrderInterface){
                            myOrderInterface.confirmReceiptfail(tip);
                        }else if (null!=orderDetailInterface){
                            orderDetailInterface.affirmorderfail(tip);
                        }


                    }
                } else if (what == 5) {
                    if (code == 100) {
                        String data = JsonUtils.getStringValue(result, "Data");
                        OrderBean orderBean = JsonUtils.stringToObject(data, OrderBean.class);
                        orderDetailInterface.getorderdetail(orderBean);
                    }

                }
            }


        };
    }

    /**
     * 查询我的订单列表
     *
     * @param page
     * @param state 订单状态
     */
    public void getOrderList(int page, int state) {
        String data = JsonUtils.keyValueToString2("state", state, "pageNo", page);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/select-order-list", handler, 1);
    }

    //获取默认收货地址
    public void getdefendaddress() {
        String data = JsonUtils.keyValueToString2("token", Constants.TOKEN, "isDefault", 1);
        Network.getnetwork().postJson(data, Constants.URL + "/app/select-default-take-address", handler, 2);
    }

    public void pay(String addressid, String productid) {
        String data = JsonUtils.keyValueToString2("addressId", addressid, "objectId", productid);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/exchange-product", handler, 3);
    }

    //申请退款
    public void applyrefund(String id, String re) {
        String data = JsonUtils.keyValueToString2("applyId", id, "refundCause", re);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/apply-refund", handler, 4);
    }

    //查询单个课程订单详情
    public void selectbyid(String id) {
        String data = JsonUtils.keyValueToString2("applyId", id, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/select-my-apply-by-id", handler, 5);
    }

    //查询单个积分商品订单详情
    public void selectgoodsorderbyid(String id) {
        String data = JsonUtils.keyValueToString2("orderId", id, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/select-order-details", handler, 5);
    }

    //确认收获
    public void confirmReceipt(String id) {
        String data = JsonUtils.keyValueToString2("orderId", id, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/confirm-receipt", handler, 6);
    }
}
