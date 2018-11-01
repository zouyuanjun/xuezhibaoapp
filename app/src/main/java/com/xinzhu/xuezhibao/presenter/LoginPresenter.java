package com.xinzhu.xuezhibao.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinzhu.xuezhibao.bean.UserBasicInfo;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.LoginInterface;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;


public class LoginPresenter {
    LoginInterface loginInterface;


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String result = (String) msg.obj;
            com.zou.fastlibrary.utils.Log.d(result);
            int code=-999;
            try {
                code = JsonUtils.getIntValue(result, "_code");
            }catch (Exception e){
                com.zou.fastlibrary.utils.Log.d("异常了");
                loginInterface.servererr();
            }
            if (code == -100) {
                loginInterface.networkerr();
                return;
            }
            if (code == -200) {
                loginInterface.networktimeout();
                return;
            }
            if (what == 1) {
                if (code==100){
                    String data=JsonUtils.getStringValue(result,"Data");
                    Constants.TOKEN=JsonUtils.getStringValue(data,"token");
                    Constants.userBasicInfo= (UserBasicInfo) JsonUtils.stringToObject(data,UserBasicInfo.class);
                    loginInterface.loginsuccessful();
                }
                if (code==0){
                    loginInterface.loginfail(code);
                }
            }
        }
    };


    public LoginPresenter(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }
    public void phonelogin(String phone,String password ){
//
//        JMessageClient.login(phone, password, new BasicCallback() {
//            @Override
//            public void gotResult(int responseCode, String responseMessage) {
//                Log.d("JIM登陆响应",responseMessage);
//                if (responseCode == 0) {
//                    UserInfo myUserInfo = JMessageClient.getMyInfo();
//                    //注册时更新头像
//                    loginInterface.loginsuccessful();
//                }
//            }
//        });
        String data=JsonUtils.keyValueToString("account",phone);
        data=JsonUtils.addKeyValue(data,"password",password);
        Network.getnetwork().postJson(data,Constants.URL+"/app/login",handler,1);
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
