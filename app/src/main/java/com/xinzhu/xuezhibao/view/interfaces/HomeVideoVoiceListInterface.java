package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.VideoVoiceBean;

import java.util.List;

public interface HomeVideoVoiceListInterface extends publiceviewinterface{
    void getVideo(List<VideoVoiceBean> videoVoiceBeanList);
    void nodata();
}
