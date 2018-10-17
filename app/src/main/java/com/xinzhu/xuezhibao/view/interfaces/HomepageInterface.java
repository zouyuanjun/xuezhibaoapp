package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.VideoBean;

import java.util.List;

public interface HomepageInterface extends publiceviewinterface {
    void getVideodata(List<VideoBean> mDatas);
    void  getVoicedata(List<VideoBean> mDatas);
    void getArticle(List<ArticleBean> mDatas);

}
