package com.xinzhu.xuezhibao;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.bravin.btoast.BToast;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.QbSdk;
import com.zou.fastlibrary.utils.Log;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

public class MyApplication  extends Application{
    static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this.getApplicationContext());
        Bugly.init(getApplicationContext(), "15d797d434", false);
        SDKInitializer.initialize(this);  //初始化百度地图
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
 //       LeakCanary.install(this);
        JPushInterface.setDebugMode(true);
        JMessageClient.init(this,true);
        JPushInterface.init(this);
        BToast.Config.getInstance().apply(this);
        context=this.getApplicationContext();

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }
    public static Context getContext(){
       return context;
    }
}
