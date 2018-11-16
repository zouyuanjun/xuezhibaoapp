package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.TrickBean;

import java.util.List;

/**
 * Created by zou on 2018/11/13.
 */

public interface TrackInterface extends publiceviewinterface {
    void getMyTrack(List<TrickBean> list);

    void nomoredata();
}
