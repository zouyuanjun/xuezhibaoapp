package com.xinzhu.xuezhibao.utils;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.xinzhu.xuezhibao.bean.UserBasicInfo;

public class Constants {
    public static final String APP_ID = "wxc56b70bd3c1c491f";
    public static IWXAPI api;   //第三方和微信通信的接口
  //  public static String URL="http://192.168.1.80:8081/";
  // public static String URL = "http://app.xuezhiben.com/";
    public static String URL = "http://demoapp.xuezhiben.com/";
    public static String APP_VERSION = "1.0.0";
    public static String AlipayAccount = "";
    public static String JPUSH_APPKEY = "68a8c864423f3b707c8c8da3";
    public static int EMOTICON_CLICK_TEXT = 1;
    public static int EMOTICON_CLICK_BIGIMAGE = 2;
    public static int SETTING_USENET = 1;
    public static int SETTING_CANNOTIFITION = 1;
    public static String TOKEN = "";
    public static String INTENT_ID = "id";
    public static String INTENT_ID2 = "title";
    public static String INTENT_ID3 = "type";
    public static String INTENT_ID4 = "type2";
    public static String FROMAPP = "fromapp";
    public static String INTENT_COURSE_CLASS = "COURSE";
    public static String INTENT_EDITITEM = "COURSE";    //修改个人资料时传入要修改的项目
    public static UserBasicInfo userBasicInfo = null;
    public static long PLAYTIME = 0;  //视频播放时长
    /**
     * 微信支付的订单号。由于微信没有在WXPayEntryActivity回调页面传入订单数据，所以用静态变量暂存。
     * 傻逼微信！
     */
    public static String wxOrdernum = "";
}
