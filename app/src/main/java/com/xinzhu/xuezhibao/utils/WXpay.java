package com.xinzhu.xuezhibao.utils;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

public class WXpay {
    static  IWXAPI msgApi;
    public static void initwxpay(){
        msgApi.registerApp("wxd930ea5d5a258f4f");
    }
    public static void sendpay(String payid){
        PayReq request = new PayReq();
        request.appId = "wxd930ea5d5a258f4f";
        request.partnerId = "1900000109";
        request.prepayId= "1101000000140415649af9fc314aa427";
        request.packageValue = "Sign=WXPay";
        request.nonceStr= "1101000000140429eb40476f8896f4c9";
        request.timeStamp= "1398746574";
        request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
        msgApi.sendReq(request);

    }
}
