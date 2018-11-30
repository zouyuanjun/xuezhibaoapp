package com.xinzhu.xuezhibao.utils;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinzhu.xuezhibao.bean.WXPayBean;
import com.zou.fastlibrary.utils.Log;

public class WXpay {
      IWXAPI msgApi;

    Context context;

    public WXpay(Context context) {
        this.context = context;
    }


    public  void sendpay(WXPayBean wxPayBean){
        msgApi = WXAPIFactory.createWXAPI(context, Constants.APP_ID,true);
        msgApi.registerApp(Constants.APP_ID);
        PayReq request = new PayReq();
        request.appId = wxPayBean.getAppid();
        request.partnerId = wxPayBean.getPartnerid();
        request.prepayId= wxPayBean.getPrepayid();
        request.packageValue = "Sign=WXPay";
        request.nonceStr= wxPayBean.getNoncestr();
        request.timeStamp= wxPayBean.getTimestamp();
        request.sign= wxPayBean.getSign();
        Log.d("参数检查 "+request.checkArgs());
        msgApi.sendReq(request);
    }
}
