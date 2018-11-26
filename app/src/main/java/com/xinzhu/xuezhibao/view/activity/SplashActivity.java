package com.xinzhu.xuezhibao.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.immodule.utils.ImageUtil;
import com.xinzhu.xuezhibao.presenter.LoginPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.SplashInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.utils.DataKeeper;
import com.zou.fastlibrary.utils.ImageUtils;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.StatusBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zou on 2018/11/13.
 */

public class SplashActivity extends BaseActivity implements SplashInterface {
    LoginPresenter loginPresenter;
    @BindView(R.id.im_logo)
    ImageView imLogo;
    String path;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            int code = JsonUtils.getIntValue(result, "Code");
            if (code == 100) {
                String data = JsonUtils.getStringValue(result, "Data");
                data = JsonUtils.getStringValue(data, "adUrl");
                Log.d(data+path);
                Glide.with(SplashActivity.this)
                        .asBitmap()
                        .load(data)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            //  imLogo.setImageBitmap(resource);
                              ImageUtils.saveBitmapFile(resource, path,"splogo.jpg");
                            }
                        });



            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
//        StatusBar.setTransparent(this);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        DataKeeper.init(this);

        path = DataKeeper.getDiskCachePath(this) + "/Pictures/";
//        File file = new File(path);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(path+"splogo.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fs);
        if (null==bitmap){
            imLogo.setBackgroundResource(R.drawable.splogo);
        }else {
            imLogo.setImageBitmap(bitmap);
        }

        loginPresenter = new LoginPresenter(new WeakReference<SplashInterface>(this).get());
        Thread myThread = new Thread() {//创建子线程
            @Override
            public void run() {
                try {
                    sleep(2000);
                    loginPresenter.autologin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程
        Network.getnetwork().postJson("", Constants.URL + "/guest/select-index-img", handler, 1);
    }


    @Override
    public void login() {
        goToActivity(this, MainActivity.class);
    }

    @Override
    public void loginfall() {
        goToActivity(this, MainActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.cancelmessage();
        loginPresenter = null;
    }
}
