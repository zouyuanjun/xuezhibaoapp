package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.PayResquestBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;

import java.util.List;

public  interface VideoVoiceDetailInterface extends publiceviewinterface {
    void getVideodetail(VideoVoiceBean videoVoiceBean);
    void getVoicedetail(VideoVoiceBean videoVoiceBean);
    void getcomment(List<CommentBean> mDatas, int total);
    void nologin();
    void getcommentfail();

}
