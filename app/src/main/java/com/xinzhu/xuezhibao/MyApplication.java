package com.xinzhu.xuezhibao;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.bravin.btoast.BToast;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

public class MyApplication  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this.getApplicationContext());
        Bugly.init(getApplicationContext(), "15d797d434", false);
        SDKInitializer.initialize(this);  //初始化百度地图
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        JPushInterface.setDebugMode(true);
        JMessageClient.init(this,true);
        JPushInterface.init(this);
        BToast.Config.getInstance().apply(this);
    }
}
