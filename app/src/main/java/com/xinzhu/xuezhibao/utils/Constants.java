package com.xinzhu.xuezhibao.utils;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.xinzhu.xuezhibao.bean.UserBasicInfo;

public class Constants {
    public static final String APP_ID="wx46b14ff64afefa78";
    public static IWXAPI api;   //第三方和微信通信的接口
   // public static String URL="http://192.168.1.112:8080/";
    public static String URL="http://192.168.1.159:8080";
    public static String APP_VERSION="1.0.0";
    public static String AlipayAccount="";
    public static String JPUSH_APPKEY = "68a8c864423f3b707c8c8da3";
    public static int EMOTICON_CLICK_TEXT = 1;
    public static int EMOTICON_CLICK_BIGIMAGE = 2;
    public static int SETTING_USENET=1;
    public static int SETTING_CANNOTIFITION=1;
    public static String TOKEN="";
    public static String INTENT_ID ="id";
    public static String FROMAPP="fromapp";
    public static String INTENT_COURSE_CLASS="COURSE";
    public static String INTENT_EDITITEM="COURSE";    //修改个人资料时传入要修改的项目
    public static UserBasicInfo userBasicInfo=null;
    public static long PLAYTIME=0;  //视频播放时长
  }
