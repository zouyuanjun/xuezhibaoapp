package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.SelectConditionBean;

import java.util.List;

public interface SubjectCourseInterface extends publiceviewinterface {
    void getSubjectCourse(List<CourseBean> list);
    void getSubjectHotCourse(List<CourseBean> list);
    void  getSeachCondition(List<SelectConditionBean> classtypelist, List<SelectConditionBean> coursetypelist);
void noMoreData();
}
