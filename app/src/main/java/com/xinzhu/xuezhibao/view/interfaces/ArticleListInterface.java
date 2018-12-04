package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.ArticleBean;

import java.util.List;

public interface ArticleListInterface extends publiceviewinterface{
    void getDataSuccessful(List<ArticleBean> mDatas);
    void getDataFail();
}
