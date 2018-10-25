package com.xinzhu.xuezhibao.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.VideoBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.HomepageInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;

import java.util.ArrayList;
import java.util.List;

public class HomepagePresenter {
    HomepageInterface homepageInterface;
    Activity context;
    Network network;

    public HomepagePresenter(HomepageInterface homepageInterface, Activity context) {
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
            if (what==1){
                String data=JsonUtils.getStringValue(result,"Data");
                List<ArticleBean> mDatas=JSON.parseArray(data,ArticleBean.class);
                Log.d(mDatas.toString()+mDatas.size());
                homepageInterface.getArticle(mDatas);
            }
        }
    };
    public void requestVideodata(){
        homepageInterface.getVideodata(initdata());
    }
    public void requestVoicedata(){
        homepageInterface.getVoicedata(initdata());
    }
    public void requestArticledata(){
        String data=JsonUtils.keyValueToString("pageNo",1);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/article-newest",handler,1);
    }
    private List<VideoBean> initdata(){
        List<VideoBean> mDatas=new ArrayList<>();
        String url="http://pic29.nipic.com/20130511/9252150_174018365301_2.jpg";
        String title="哈哈哈这是什么和水水水水";
        VideoBean videoBean=new VideoBean(url,title);
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        return  mDatas;
    }

    private List<ArticleBean> initArticle(){



        List<ArticleBean> mDatas=new ArrayList<>();
        String url="http://pic29.nipic.com/20130511/9252150_174018365301_2.jpg";
        String title="哈哈哈这是什么和水水水水";
        ArticleBean videoBean=new ArticleBean(url,title,"555");
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        mDatas.add(videoBean);
        return  mDatas;
    }


}
