package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.CourseBean;

import java.util.List;

public  interface CoursePlayInterface extends publiceviewinterface {
    void getCoursedetail(CourseBean courseBean);
    void getcomment(List<CommentBean> mDatas, int total);
    void getcommentfail();

    void requestPayInfo(String payinfo, String ordernum);

    void alreadlybuy();
}
