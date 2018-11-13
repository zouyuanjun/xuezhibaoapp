package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.CourseFeedbackBean;
import com.xinzhu.xuezhibao.bean.TeacherBean;

import java.util.List;

public interface MyCourseInterface extends publiceviewinterface {
    void getcourse(List<CourseBean> courseBeanList);
    void nodata();

    void getTeacher(List<TeacherBean> mDatas);

    void getCourseFeesback(List<CourseFeedbackBean> mDatas, String unread);
}
