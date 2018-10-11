package com.xinzhu.xuezhibao;

import android.app.Application;

import com.bravin.btoast.BToast;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

public class MyApplication  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(getApplicationContext(), "15d797d434", false);
        JPushInterface.setDebugMode(true);
        JMessageClient.init(this,true);
        JPushInterface.init(this);
        BToast.Config.getInstance().apply(this);
    }
}
