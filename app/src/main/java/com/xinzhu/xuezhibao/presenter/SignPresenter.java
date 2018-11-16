package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.SignBean;
import com.xinzhu.xuezhibao.bean.UserBasicInfo;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.JimUtils;
import com.xinzhu.xuezhibao.view.activity.SignActivity;
import com.xinzhu.xuezhibao.view.interfaces.ForgetPasswordInterface;
import com.xinzhu.xuezhibao.view.interfaces.SignInterface;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

/**
 * 负责注册和找回密码
 */
public class SignPresenter {
    SignInterface signInterface;
    ForgetPasswordInterface forgetPasswordInterface;

    public SignPresenter(SignInterface signInterface) {
        this.signInterface = signInterface;
    }

    public SignPresenter(ForgetPasswordInterface forgetPasswordInterface) {
        this.forgetPasswordInterface = forgetPasswordInterface;
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
                code = JsonUtils.getIntValue(result, "_code");
            }catch (Exception e){
                if (null!=signInterface){
                    signInterface.servererr();
                } else if (null!=forgetPasswordInterface) {
                    forgetPasswordInterface.servererr();
                }

            }
            if (code == -100) {
                if (null!=signInterface){
                    signInterface.networkerr();
                } else if (null!=forgetPasswordInterface) {
                    forgetPasswordInterface.networkerr();
                }
                return;
            }
            if (code == -200) {
                if (null!=signInterface){
                    signInterface.networktimeout();
                } else if (null!=forgetPasswordInterface) {
                    forgetPasswordInterface.networktimeout();
                }
            }

            if (what == 1) {
                if (code==6){
                    if (null!=signInterface){
                        signInterface.isexist();
                    }
                }else if (code==203){
                    if (null!=forgetPasswordInterface){
                        forgetPasswordInterface.usernotfind();
                    }
                }

                com.zou.fastlibrary.utils.Log.d(result);
            }
            if (what == 2) {

                if (code==100){
                    String data=JsonUtils.getStringValue(result,"Data");
                    Constants.TOKEN=JsonUtils.getStringValue(data,"token");
                    Constants.userBasicInfo= (UserBasicInfo) JsonUtils.stringToObject(data,UserBasicInfo.class);
                    signInterface.signsuccessful();
                }else {
                    signInterface.signinfail(code);
                }
            }if (what==3){
                if (code==100){
                    if (null!=signInterface){
                        signInterface.codeistrue();
                    } else if (null!=forgetPasswordInterface) {
                        forgetPasswordInterface.codeistrue();
                    }
                }else {
                    if (null!=signInterface){
                        signInterface.codeiserr();
                    } else if (null!=forgetPasswordInterface) {
                        forgetPasswordInterface.codeiserr(code);
                    }
                }

            }if (what==4){
                if (code==100){
                  forgetPasswordInterface.successful();
                }else {
                    forgetPasswordInterface.fail(code);
                }
            }
        }
    };

    public void sendcode(String phone,int type) {
        String data=JsonUtils.keyValueToString2("phone",phone,"templateTypeId",type);
        Network.getnetwork().postJson(data, Constants.URL+"/send/onSendCode", handler, 1);
      //  Network.getnetwork().postform("phone",phone,"templateTypeId",type+"", Constants.URL+"/send/onSendCode",handler,3);

    }

    public void sign(final SignBean signBean) {
        Network.getnetwork().postJson(JsonUtils.objectToString(signBean), Constants.URL+"/member/sign-in",handler,2);
    }

    public void updatapassword(String phone,String password,String code){
        String data=JsonUtils.keyValueToString2("account",phone,"password",password);
        data=JsonUtils.addKeyValue(data,"code",code);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/update-password",handler,4);
    }

    public void iscodeture(String phone,String code){
        String data=JsonUtils.keyValueToString2("phone",phone,"code",code);
        Network.getnetwork().postform("phone",phone,"code",code, Constants.URL+"/login/judge-note-code",handler,3);
    }
    public void cancelmessage(){
        handler.removeCallbacksAndMessages(null);
    }
}
