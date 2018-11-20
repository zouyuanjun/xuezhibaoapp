package com.xinzhu.xuezhibao.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.bean.UserBasicInfo;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.JimUtils;
import com.xinzhu.xuezhibao.view.interfaces.LoginInterface;
import com.xinzhu.xuezhibao.view.interfaces.SplashInterface;
import com.zou.fastlibrary.utils.DataKeeper;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import org.json.JSONException;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;


public class LoginPresenter {
    LoginInterface loginInterface;
SplashInterface splashInterface;

    public LoginPresenter(SplashInterface splashInterface) {
        this.splashInterface = splashInterface;
    }

    String myphone;
    String mypassword;
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
            }catch (com.alibaba.fastjson.JSONException e){
                com.zou.fastlibrary.utils.Log.d("异常了");
                if (null!=loginInterface){
                    loginInterface.servererr();
                }else if (null!=splashInterface){
                    splashInterface.servererr();
                }
            }
            if (code == -100) {
                if (null!=loginInterface){
                    loginInterface.networkerr();
                }else if (null!=splashInterface){
                    splashInterface.networkerr();
                }
                return;
            }
            if (code == -200) {
                if (null!=loginInterface){
                    loginInterface.networktimeout();
                }else if (null!=splashInterface){
                    splashInterface.networktimeout();
                }
                return;
            }
            if (what == 1) {
                if (code==100){
                    String data=JsonUtils.getStringValue(result,"Data");
                    Constants.TOKEN=JsonUtils.getStringValue(data,"token");
                    Constants.userBasicInfo= (UserBasicInfo) JsonUtils.stringToObject(data,UserBasicInfo.class);
                    loginInterface.loginsuccessful();
                    SharedPreferences sharedPreferences=DataKeeper.getRootSharedPreferences(MyApplication.getContext());
                    DataKeeper.save(sharedPreferences,"PHONE",myphone);
                    DataKeeper.save(sharedPreferences,"PASSWORD",mypassword);

                }else {
                    loginInterface.loginfail(code);
                }
            }else if (what == 2) {
                if (code==100){
                    String data=JsonUtils.getStringValue(result,"Data");
                    Constants.TOKEN=JsonUtils.getStringValue(data,"token");
                    Constants.userBasicInfo= (UserBasicInfo) JsonUtils.stringToObject(data,UserBasicInfo.class);
                    SharedPreferences sharedPreferences=DataKeeper.getRootSharedPreferences(MyApplication.getContext());
                    DataKeeper.save(sharedPreferences,"PHONE",myphone);
                    DataKeeper.save(sharedPreferences,"PASSWORD",mypassword);
                    splashInterface.login();
                }else {
                    splashInterface.loginfall();
                }
            }
        }
    };


    public LoginPresenter(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }
    public void phonelogin(final String phone, final String password ){
        myphone=phone;
        mypassword=password;
        JMessageClient.login(phone, "xzb123456", new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                Log.d("JIM登陆响应",responseCode+responseMessage);
                if (responseCode == 0) {
                    //注册时更新头像
                }
            }
        });
        String data=JsonUtils.keyValueToString("account",phone);
        data=JsonUtils.addKeyValue(data,"password",password);
        Network.getnetwork().postJson(data,Constants.URL+"/app/login",handler,1);
    }
    public void slpashlogin(final String phone, final String password ){
        myphone=phone;
        mypassword=password;
        JMessageClient.login(phone, "xzb123456", new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                Log.d("JIM登陆响应",responseCode+responseMessage);
                if (responseCode == 0) {
                    //注册时更新头像
                }
            }
        });
        String data=JsonUtils.keyValueToString("account",phone);
        data=JsonUtils.addKeyValue(data,"password",password);
        Network.getnetwork().postJson(data,Constants.URL+"/app/login",handler,2);
    }
    public void wxlogin(Context context){
        Constants.api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, false);
        Constants.api.registerApp(Constants.APP_ID);
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "fentuanyizhuang";
        Constants.api.sendReq(req);
    }
    public void autologin(){
        SharedPreferences sharedPreferences=DataKeeper.getRootSharedPreferences(MyApplication.getContext());
        String phone=sharedPreferences.getString("PHONE","");
        String password=sharedPreferences.getString("PASSWORD","");
        if (phone.isEmpty()||password.isEmpty()){
            splashInterface.loginfall();
        }else {
            slpashlogin(phone,password);
        }
    }
    public void cancelmessage(){
        handler.removeCallbacksAndMessages(null);
    }
}
