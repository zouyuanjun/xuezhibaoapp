package com.xinzhu.xuezhibao.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.bean.SignBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.SignActivity;
import com.xinzhu.xuezhibao.view.interfaces.SignInterface;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;


public class SignPresenter {
    SignInterface signInterface;
    SignActivity signActivity;
    public SignPresenter(SignInterface signInterface) {
        this.signInterface = signInterface;
    }


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
          int what=msg.what;
          String result= (String) msg.obj;
          int code=JsonUtils.getIntValue(result,"code");
          if (code==-100){
              signInterface.networkerr();
              return;
          }
          if (code==-200){
              signInterface.networktimeout();
              return;
          }
          if (what==2){

          }
        }
    };

    public void sendcode(String phone){
        Network.getnetwork().postform("phone",phone,"",handler,1);
    }
    public  void sign(final SignBean signBean){
        JMessageClient.register(signBean.getPhone(), signBean.getPassword(), new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                } else {
                    signInterface.codeerr();
                    Log.d("JIM返回",s+i);
                }
            }
        });

        JMessageClient.login(signBean.getPhone(), signBean.getPassword(), new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                Log.d("JIM",responseMessage);
                if (responseCode == 0) {
                    UserInfo myUserInfo = JMessageClient.getMyInfo();
                    if (myUserInfo != null) {
                        myUserInfo.setNickname(signBean.getName());
                    }
                    //注册时候更新昵称
                    JMessageClient.updateMyInfo(UserInfo.Field.nickname, myUserInfo, new BasicCallback() {
                        @Override
                        public void gotResult(final int status, String desc) {
                            //更新跳转标志
                            if (status == 0) {
                               signInterface.signsuccessful();
                            }
                        }
                    });
                    //注册时更新头像
                }
            }
        });


    //    Network.getnetwork().postJson(JsonUtils.objectToString(signBean), Constants.URL,handler,2);

}

}
