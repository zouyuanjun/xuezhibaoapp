package com.xinzhu.xuezhibao.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.LoginInterface;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;


public class LoginPresenter {
    LoginInterface loginInterface;


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    public LoginPresenter(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }
    public void phonelogin(String phone,String password ){

        JMessageClient.login(phone, password, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                Log.d("JIM登陆响应",responseMessage);
                if (responseCode == 0) {
                    UserInfo myUserInfo = JMessageClient.getMyInfo();
                    //注册时更新头像
                    loginInterface.loginsuccessful();

                }
            }
        });




    }
    public void wxlogin(Context context){
        Constants.api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, false);
        Constants.api.registerApp(Constants.APP_ID);
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "fentuanyizhuang";
        Constants.api.sendReq(req);
    }
}
