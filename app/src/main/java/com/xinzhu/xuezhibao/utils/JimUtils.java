package com.xinzhu.xuezhibao.utils;

import android.util.Log;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;

public class JimUtils {

    public void sign(final String username, final String password, String nickname){

        final RegisterOptionalUserInfo optionalUserInfo=new RegisterOptionalUserInfo();
        optionalUserInfo.setNickname(nickname);
        final int count=1;
        JMessageClient.register(username, password,optionalUserInfo, new BasicCallback(true) {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {

                } else {
                    if (count<3){
                        i++;
                        JMessageClient.register(username, password, optionalUserInfo, new BasicCallback(true) {
                            @Override
                            public void gotResult(int i, String s) {
                                if (i==0){

                                }else {

                                }
                            }
                        });
                    }
                }
            }
        });


    }

    public void login(String username,String password){
        JMessageClient.login(username, password, new BasicCallback(true) {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                Log.d("JIM", responseMessage);
                if (responseCode == 0) {

                }
            }
        });
    }
    public void updatepassword(String oldPassword,String newPassword){
        JMessageClient.updateUserPassword(oldPassword, newPassword, new BasicCallback(true) {
            @Override
            public void gotResult(int i, String s) {

            }
        });
    }
}
