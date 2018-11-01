package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.adapter.VideoVoiceListAdapter;
import com.xinzhu.xuezhibao.bean.ArticleBean;

import java.util.List;

public interface ArticleListInterface extends publiceviewinterface{
    void getNewArticle(List<ArticleBean> mDatas);
    void getHotArticle(List<ArticleBean> mDatas);
    void getCollect(List<ArticleBean> mDatas);
    void getDataFail();
}
