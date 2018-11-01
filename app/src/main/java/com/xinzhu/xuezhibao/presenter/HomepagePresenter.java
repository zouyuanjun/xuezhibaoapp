package com.xinzhu.xuezhibao.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.VideoBean;
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
            if (code==100){

            if (what==1){
                String data=JsonUtils.getStringValue(result,"Data");
                List<ArticleBean> mDatas=JSON.parseArray(data,ArticleBean.class);
                homepageInterface.getArticle(mDatas);
            }else if (what==2){
                String data=JsonUtils.getStringValue(result,"Data");
                List<VideoBean> mDatas=JSON.parseArray(data,VideoBean.class);
                homepageInterface.getVideodata(mDatas);
            }else if (what==3){
                String data=JsonUtils.getStringValue(result,"Data");
                List<VideoBean> mDatas=JSON.parseArray(data,VideoBean.class);
                homepageInterface.getVoicedata(mDatas);
            }
            }
        }
    };
    public void initdata(){
        String data=JsonUtils.keyValueToString("pageNo",1);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/article-newest",handler,1);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/video-newest-gratis",handler,2);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/video-newest",handler,3);

    }

}
