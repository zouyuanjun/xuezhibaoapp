package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.TeacherBean;

import java.util.List;

public interface TeacherInterface extends publiceviewinterface{
    void getTeacherDetail(TeacherBean teacherBean);
    void getTeacherCourse(List<CourseBean> list);
    void getTeacherComment(List<CommentBean> list, String total);
    void nomoredata();
    void err(int code);
}
