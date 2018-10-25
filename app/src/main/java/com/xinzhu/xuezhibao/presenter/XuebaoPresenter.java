package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.view.interfaces.XuebaoInterface;


public class XuebaoPresenter {
    XuebaoInterface xuebaoInterface;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public void getHotCourse(){

    }

    public void getNewCourse(){

    }

    public void  getRecommentCourse(){

    }
    public void getAllCourse(){

    }
}
