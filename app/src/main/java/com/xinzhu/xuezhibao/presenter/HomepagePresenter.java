package com.xinzhu.xuezhibao.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.VideoBean;
import com.xinzhu.xuezhibao.view.activity.QRActivity;
import com.xinzhu.xuezhibao.view.interfaces.HomepageInterface;
import com.zou.fastlibrary.utils.Network;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

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
        }
    };
    public void requestVideodata(){
        homepageInterface.getVideodata(initdata());
    }
    public void requestVoicedata(){
        homepageInterface.getVoicedata(initdata());
    }
    public void requestArticledata(){
        homepageInterface.getArticle(initdata2());
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

    private List<ArticleBean> initdata2(){
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
