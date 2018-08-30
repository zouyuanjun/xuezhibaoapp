package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.WXSignBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.WXSignInterface;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

public class WXSign {
    WXSignInterface wxSignInterface;

    public WXSign(WXSignInterface wxSignInterface) {
        this.wxSignInterface = wxSignInterface;
    }
    WXSignBean wxSignBean;
  Handler handler=new Handler(){
      @Override
      public void handleMessage(Message msg) {
          super.handleMessage(msg);
          int what=msg.what;
          String result= (String) msg.obj;
          if (what==3){
              String openid=JsonUtils.getStringValue(result,"openid");
              String nickname=JsonUtils.getStringValue(result,"nickname");
              String headimgurl=JsonUtils.getStringValue(result,"headimgurl");
              wxSignBean.setName(nickname);
              wxSignBean.setOpenid(openid);
              wxSignBean.setPhotourl(headimgurl);
          }else {
             int code=JsonUtils.getIntValue(result,"code");
              if (code==-100){
                  wxSignInterface.networkerr();
                  return;
              }else if(code==-200) {
                  wxSignInterface.networktimeout();
                  return;
              }
              //微信注册结果
              if (what==1){

              }
          }
      }
  };

    /**
     * 微信绑定手机号
     * @param phone
     * @param password
     * @param code
     */
    public void wxsign(String phone,String password,String code ){
        wxSignBean.setPhone(phone);
        wxSignBean.setPhone(password);
        wxSignBean.setCode(code);
        String data= JsonUtils.objectToString(wxSignBean);
        Network.getnetwork().postJson(data, Constants.URL,handler,1);
    }

    /**
     * 发送验证码
     * @param phone
     */
    public void getcode(String phone){
        Network.getnetwork().postform("phone",phone,Constants.URL,handler,2);
    }

    /**
     * 获取微信用户基本信息
     * @param token
     * @param openid
     */
    public void getwxinfo(String token, String openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        url = url.replace("ACCESS_TOKEN", token);
        url = url.replace("OPENID", openid);
        Network.getnetwork().postJson("", url, handler, 3);
    }
}
