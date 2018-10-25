package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.SignBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.JimUtils;
import com.xinzhu.xuezhibao.view.activity.SignActivity;
import com.xinzhu.xuezhibao.view.interfaces.SignInterface;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;


public class SignPresenter {
    SignInterface signInterface;
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
            com.zou.fastlibrary.utils.Log.d(result);
            int code=0;
            try {
                code = JsonUtils.getIntValue(result, "code");
            }catch (Exception e){
                signInterface.servererr();
            }
            if (code == -100) {
                signInterface.networkerr();
                return;
            }
            if (code == -200) {
                signInterface.networktimeout();
                return;
            }
            if (what == 1) {
                com.zou.fastlibrary.utils.Log.d(result);
            }
            if (what == 2) {

            }
        }
    };

    public void sendcode(String phone) {
        String data=JsonUtils.keyValueToString("account",phone);

    //    Network.getnetwork().postJson(data, Constants.URL+"/member/noteCode", handler, 1);
        Network.getnetwork().postform("account",phone, Constants.URL+"/member/noteCode", handler, 1);
    }

    public void sign(final SignBean signBean) {
        jimUtils.sign(signBean.getAccount(),signBean.getPassword(),signBean.getName());


        Network.getnetwork().postJson(JsonUtils.objectToString(signBean), Constants.URL+"/member/sign-in",handler,2);

    }

}
