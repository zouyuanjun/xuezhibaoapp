package com.xinzhu.xuezhibao.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.BannerImgBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.HomepageInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

public class HomepagePresenter {
    HomepageInterface homepageInterface;
    Context context;
    Network network;

    public HomepagePresenter(HomepageInterface homepageInterface, Context context) {
        this.homepageInterface = homepageInterface;
        this.context = context;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String result = (String) msg.obj;
            com.zou.fastlibrary.utils.Log.d(result);
            int code = -999;
            try {
                code = JsonUtils.getIntValue(result, "Code");
            } catch (Exception e) {
                com.zou.fastlibrary.utils.Log.d("异常了");
                homepageInterface.servererr();
            }
            if (code == -100) {
                homepageInterface.networkerr();
                return;
            }
            if (code == -200) {
                homepageInterface.networktimeout();
                return;
            }
            if (code == 100) {
                String data = JsonUtils.getStringValue(result, "Data");
                if (what == 1) {

                    List<ArticleBean> mDatas = JSON.parseArray(data, ArticleBean.class);
                    if (null != mDatas && mDatas.size() > 0) {
                        homepageInterface.getArticle(mDatas);
                    }
                } else if (what == 2) {

                    List<VideoVoiceBean> mDatas = JSON.parseArray(data, VideoVoiceBean.class);
                    if (null != mDatas && mDatas.size() > 0) {
                        homepageInterface.getVideodata(mDatas);
                    }
                } else if (what == 3) {

                    List<VideoVoiceBean> mDatas = JSON.parseArray(data, VideoVoiceBean.class);
                    if (null != mDatas && mDatas.size() > 0) {
                        homepageInterface.getVoicedata(mDatas);
                    }
                } else if (what == 4) {
                    List<BannerImgBean> mDatas = JSON.parseArray(data, BannerImgBean.class);
                    if (null != mDatas && mDatas.size() > 0) {
                        homepageInterface.getbanner(mDatas);
                    }
                }else if (what == 6) {
                    homepageInterface.ischock(data);
                }else if (what == 5) {
                    homepageInterface.chocksuccessful();
                }
            }
        }
    };

    public void initdata() {
        String data = JsonUtils.keyValueToString("type", 1);
        String data2 = JsonUtils.keyValueToString2("type", 2,"videoType",0);
        Network.getnetwork().postJson("", Constants.URL + "/guest/select-index-article", handler, 1);
        Network.getnetwork().postJson(data2, Constants.URL + "/guest/select-index-video", handler, 2);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/select-index-video", handler, 3);
        Network.getnetwork().postJson("", Constants.URL + "/guest/select-index-round", handler, 4);
        //是否签到
        String data3 = JsonUtils.keyValueToString("token", Constants.TOKEN);
        Network.getnetwork().postJson(data3, Constants.URL + "/app/check-clock-in", handler, 6);
    }
    /**
     * 签到
     */
    public void clockin() {
        String data = JsonUtils.keyValueToString2("token", Constants.TOKEN, "trackType", 2);
        Network.getnetwork().postJson(data, Constants.URL + "/app/clock-in", handler, 5);
    }
    public void cancelmessage() {
        handler.removeCallbacksAndMessages(null);
    }
}
