package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;

import java.util.List;

public  interface CoursePlayInterface extends publiceviewinterface {
    void getCoursedetail(CourseBean courseBean);
    void getcomment(List<CommentBean> mDatas, int total);
    void getcommentfail();

}
