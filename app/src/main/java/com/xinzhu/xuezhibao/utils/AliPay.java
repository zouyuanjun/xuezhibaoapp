package com.xinzhu.xuezhibao.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.xinzhu.xuezhibao.bean.AliPayBean;
import com.zou.fastlibrary.utils.JsonUtils;

import java.util.Map;

public class AliPay {
    public static  void payorder(AliPayBean  aliPayBean, final Activity activity, final Handler handler){
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(JsonUtils.objectToString(aliPayBean),Constants.ALAPPID, true);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, Constants.privateKEY, true);
        final String orderInfo = orderParam + "&" + sign;
        com.zou.fastlibrary.utils.Log.d(orderInfo);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
