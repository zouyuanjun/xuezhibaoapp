package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

public class MyorderPresenter extends BasePresenter {

    public void inithandle(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }
}
