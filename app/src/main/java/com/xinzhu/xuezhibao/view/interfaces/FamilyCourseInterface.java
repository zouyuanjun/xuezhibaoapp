package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.CourseBean;

import java.util.List;

public interface FamilyCourseInterface extends publiceviewinterface{
    void getFamilyCourse(List<CourseBean> list);
    void  noMoreData();
}
