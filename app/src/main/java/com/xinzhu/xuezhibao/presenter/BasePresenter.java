package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;

/**
 * Created by zou on 2018/11/14.
 */

public class BasePresenter {

     public Handler handler;

    public void cancelmessage() {
        this.handler.removeCallbacksAndMessages(null);
    }
}
