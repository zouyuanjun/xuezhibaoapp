package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;

import java.util.List;

public interface MyCollectInterface extends publiceviewinterface {
    void getvideo(List<VideoVoiceBean> videoVoiceBeanList);
    void getvoice(List<VideoVoiceBean> videoVoiceBeanList);
    void getcourse(List<CourseBean> videoVoiceBeanList);
    void getarticle(List<ArticleBean> videoVoiceBeanList);

    void nodata();
}
