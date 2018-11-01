package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.CourseBean;

import java.util.List;

public interface SubjectCourseInterface extends publiceviewinterface {
    void getSubjectCourse(List<CourseBean> list);
    void getSubjectHotCourse(List<CourseBean> list);
    void searchFamilyCourse(List<CourseBean> list);
}
