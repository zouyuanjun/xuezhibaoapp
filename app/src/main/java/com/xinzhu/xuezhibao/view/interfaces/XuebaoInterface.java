package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.BannerImgBean;
import com.xinzhu.xuezhibao.bean.CourseBean;

import java.util.List;

public interface XuebaoInterface extends publiceviewinterface{
   void getHotCourse(List<CourseBean> list);
   void getNewCourse(List<CourseBean> list);
   void getBanner(List<BannerImgBean> list);
   void noHotCourse();
   void noNewCourse();
}
