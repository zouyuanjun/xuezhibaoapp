package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.presenter.LoginPresenter;
import com.xinzhu.xuezhibao.view.interfaces.SplashInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.utils.StatusBar;

import java.lang.ref.WeakReference;

/**
 * Created by zou on 2018/11/13.
 */

public class SplashActivity extends BaseActivity implements SplashInterface{
    LoginPresenter loginPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_splash);
        StatusBar.setTransparent(this);
        loginPresenter=new LoginPresenter(new WeakReference<SplashInterface>(this).get());
        Thread myThread=new Thread(){//创建子线程
            @Override
            public void run() {
                try{
                    sleep(1000);
                  loginPresenter.autologin();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程

    }


    @Override
    public void login() {
        goToActivity(this,MainActivity.class);
    }

    @Override
    public void loginfall() {
        goToActivity(this,MainActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.cancelmessage();
        loginPresenter=null;
    }
}
