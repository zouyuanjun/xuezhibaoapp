package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.VideoVoiceBean;

import java.util.List;

public interface VideoFragmentInterface extends publiceviewinterface {
    void getFreeVideo(List<VideoVoiceBean> freeBeanList);
void getpayVideo(List<VideoVoiceBean> payBeanList);
void  noData();
}