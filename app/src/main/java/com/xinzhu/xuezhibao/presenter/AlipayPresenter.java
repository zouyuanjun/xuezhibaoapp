package com.xinzhu.xuezhibao.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.xinzhu.xuezhibao.bean.PayResult;
import com.xinzhu.xuezhibao.bean.WXPayBean;
import com.xinzhu.xuezhibao.utils.AliPay;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.WXpay;
import com.xinzhu.xuezhibao.view.interfaces.PayInterface;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.Map;

public class AlipayPresenter extends BasePresenter {
    PayInterface payInterface;
    Activity activity;
    String ordernum;
    int tpye;
    int trycount = 1;

    public AlipayPresenter(PayInterface payInterface, Activity activity) {
        this.payInterface = payInterface;
        this.activity = activity;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        checkAliPay();

                        trycount = 1;
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        payInterface.payfail();
                    }
                    break;
                }
                case 2: {   //支付宝支付请求回来的支付参数
                    String result = (String) msg.obj;
                    int code = JsonUtils.getIntValue(result, "Code");
                    if (code == 100) {
                        String data = JsonUtils.getStringValue(result, "Data");
                        String payinfo = JsonUtils.getStringValue(data, "data");
                        ordernum = JsonUtils.getStringValue(data, "orderNum");
                        AliPay.payorder(payinfo, activity, handler);
                    }
                    break;
                }
                case 3:{  //微信支付请求回来的参数
                    String result = (String) msg.obj;
                    int code = JsonUtils.getIntValue(result, "Code");
                    String tips=JsonUtils.getStringValue(result,"Tips");
                    Log.d(result);
                    if(code==100){
                        String data = JsonUtils.getStringValue(result, "Data");
                        String payinfo = JsonUtils.getStringValue(data, "data");
                        /**
                         * 保存订单号供回调页面查询支付结果
                         */
                        Constants.wxOrdernum = JsonUtils.getStringValue(data, "orderNum");
                        if (!StringUtil.isEmpty( Constants.wxOrdernum)){
                            WXPayBean wxPayBean=JsonUtils.stringToObject(payinfo,WXPayBean.class);
                            WXpay wXpay=new WXpay(activity);
                            wXpay.sendpay(wxPayBean);
                        }else {
                            payInterface.orderisexit();
                        }
                    }else {
                        payInterface.creatOrderfail(tips);
                    }

                    break;
                }
                case 5: {  //查询的支付宝支付结果
                    String result = (String) msg.obj;
                    int code = JsonUtils.getIntValue(result, "Code");
                    if (code == 100) {
                        String data = JsonUtils.getStringValue(result, "Data");
                        payInterface.paysuccessful();
                    } else {
                        if (trycount < 4) {
                            trycount++;
                            handler.sendEmptyMessageDelayed(6, 1000);
                        } else {
                            payInterface.payfail();
                        }

                    }
                    break;
                }
                case 6: {
                    checkAliPay();
                    break;
                }
                case 7:{
                    String result = (String) msg.obj;
                    Log.d(result);
                    int code = JsonUtils.getIntValue(result, "Code");
                    if (code == 100) {
                        String data = JsonUtils.getStringValue(result, "Data");
                        payInterface.paysuccessful();
                    } else {
                        if (trycount < 4) {
                            trycount++;
                            handler.sendEmptyMessageDelayed(8, 1000);
                        } else {
                            payInterface.payfail();
                        }

                    }
                    break;
                }
                case 8: {
                    checkWxPay();
                    break;
                }
            }
        }
    };

    public void Alibuycourse(String id) {
        if (StringUtil.isEmpty(Constants.TOKEN)) {
            return;
        }
        tpye = 3;
        String data = JsonUtils.keyValueToString2("token", Constants.TOKEN, "curriculumId", id);
        data = JsonUtils.addKeyValue(data, "dealWay", 2);
        Network.getnetwork().postJson(data, Constants.URL + "/app/curriculum-apply", handler, 2);
    }

    public void Wxbuycourse(String id) {
        tpye = 3;
        String data = JsonUtils.keyValueToString2("curriculumId", id, "token", Constants.TOKEN);
        data = JsonUtils.addKeyValue(data, "dealWay", 1);
        Network.getnetwork().postJson(data, Constants.URL + "/app/curriculum-apply", handler, 3);
    }

    public void aLiBuyVideo(String id) {
        tpye = 2;
        String data = JsonUtils.keyValueToString2("videoId", id, "token", Constants.TOKEN);
        data = JsonUtils.addKeyValue(data, "dealWay", 2);
        Network.getnetwork().postJson(data, Constants.URL + "/app/buy-video", handler, 2);
    }

    public void WxBuyVideo(String id) {
        tpye = 2;
        String data = JsonUtils.keyValueToString2("videoId", id, "token", Constants.TOKEN);
        data = JsonUtils.addKeyValue(data, "dealWay", 1);
        Network.getnetwork().postJson(data, Constants.URL + "/app/buy-video", handler, 3);
    }

    public void alipay(String payinfo, Activity activity) {
        AliPay.payorder(payinfo, activity, handler);
    }

    /**
     * 检查支付宝支付状态
     */
    public void checkAliPay() {
        String data = JsonUtils.keyValueToString2("dealOrderNum", ordernum, "dealType", tpye);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/check-alipay", handler, 5);
    }

    /**
     * 检查微信支付状态
     */
    public void checkWxPay() {
        String data = JsonUtils.keyValueToString2("dealOrderNum", Constants.wxOrdernum, "dealType", tpye);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/check-wx-pay", handler, 7);
    }
    public void alimemberup(){
        tpye = 1;
        String data = JsonUtils.keyValueToString2("dealWay", 2, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/member-top-up", handler, 2);
    }
    public void wxmemberup(){
        tpye = 1;
        String data = JsonUtils.keyValueToString2("dealWay", 1, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/member-top-up", handler, 3);
    }
}
