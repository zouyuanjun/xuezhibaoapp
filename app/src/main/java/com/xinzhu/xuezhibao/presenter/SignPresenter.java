package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xinzhu.xuezhibao.bean.SignBean;
import com.xinzhu.xuezhibao.utils.JimUtils;
import com.xinzhu.xuezhibao.view.activity.SignActivity;
import com.xinzhu.xuezhibao.view.interfaces.SignInterface;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;


public class SignPresenter {
    SignInterface signInterface;
    SignActivity signActivity;
    JimUtils jimUtils=new JimUtils();
    public SignPresenter(SignInterface signInterface) {
        this.signInterface = signInterface;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String result = (String) msg.obj;
            int code = JsonUtils.getIntValue(result, "code");
            if (code == -100) {
                signInterface.networkerr();
                return;
            }
            if (code == -200) {
                signInterface.networktimeout();
                return;
            }
            if (what == 2) {

            }
        }
    };

    public void sendcode(String phone) {
        Network.getnetwork().postform("phone", phone, "", handler, 1);
    }

    public void sign(final SignBean signBean) {
        jimUtils.sign(signBean.getPhone(),signBean.getPassword(),signBean.getName());


        //    Network.getnetwork().postJson(JsonUtils.objectToString(signBean), Constants.URL,handler,2);

    }

}
