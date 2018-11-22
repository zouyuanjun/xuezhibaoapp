package com.xinzhu.xuezhibao.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zou.fastlibrary.utils.Log;

public class LivelyServer  extends Service {
long starttime;
long stoptime;
int  usertime;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("计时server开始创建");
        starttime=System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    stoptime=System.currentTimeMillis();
                    usertime= (int) ((stoptime-starttime)/1000);
                    Log.d("停留了"+usertime);

                }


            }
        }).start();



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
