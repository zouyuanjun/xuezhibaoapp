package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.BannerImgBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;

import java.util.List;

public interface HomepageInterface extends publiceviewinterface {
    void getVideodata(List<VideoVoiceBean> mDatas);
    void  getVoicedata(List<VideoVoiceBean> mDatas);
    void getArticle(List<ArticleBean> mDatas);
    void getbanner(List<BannerImgBean> bannerImgBeans);
    void ischock(String data);
    void chocksuccessful();
}
