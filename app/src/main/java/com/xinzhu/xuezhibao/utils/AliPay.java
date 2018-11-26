package com.xinzhu.xuezhibao.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

public class AliPay {
    public static  void payorder(final String payinfo, final Activity activity, final Handler handler){
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(payinfo, true);
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
