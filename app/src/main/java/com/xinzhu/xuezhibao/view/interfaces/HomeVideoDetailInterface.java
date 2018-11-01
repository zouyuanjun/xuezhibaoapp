package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;

import java.util.List;

public interface HomeVideoDetailInterface extends publiceviewinterface {
    void getarticledetils(VideoVoiceBean videoVoiceBean);
    void getcomment(List<CommentBean> mDatas, String total);
    void getcommentfail();

}
