package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.FamilyCourseInterface;
import com.xinzhu.xuezhibao.view.interfaces.SubjectCourseInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

public class CoursePresenter {
    FamilyCourseInterface familyCourseInterface;
SubjectCourseInterface subjectCourseInterface;

    public CoursePresenter(FamilyCourseInterface familyCourseInterface) {
        this.familyCourseInterface = familyCourseInterface;
    }

    public CoursePresenter(SubjectCourseInterface subjectCourseInterface) {
        this.subjectCourseInterface = subjectCourseInterface;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String result = (String) msg.obj;
            com.zou.fastlibrary.utils.Log.d(result);
            int code = -999;
            try {
                code = JsonUtils.getIntValue(result, "_code");
            } catch (Exception e) {
                com.zou.fastlibrary.utils.Log.d("异常了");
                if (null!=familyCourseInterface){
                    subjectCourseInterface.servererr();
                }
                if (null!=familyCourseInterface){
                    subjectCourseInterface.servererr();
                }

            }
            if (code == -100) {
                if (null!=familyCourseInterface){
                    subjectCourseInterface.networkerr();
                }
                if (null!=familyCourseInterface){
                    subjectCourseInterface.networkerr();
                }
                return;
            }
            if (code == -200) {
                if (null!=familyCourseInterface){
                    subjectCourseInterface.networktimeout();
                }
                if (null!=familyCourseInterface){
                    subjectCourseInterface.networktimeout();
                }
                return;
            }
            if (code==100){
                String data=JsonUtils.getStringValue(result,"Data");
                List<CourseBean> list=JSON.parseArray(data,CourseBean.class);
                familyCourseInterface.getFamilyCourse(list);
            }
        }
    };

    public void getFamilyCourse(int page){
        String data=JsonUtils.keyValueToString("pageNo",page);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/synthesize-curriculum",handler,1);
    }
    public void getFamilyHotCourse(int page){
        String data=JsonUtils.keyValueToString("pageNo",page);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/hottest-curriculum",handler,1);
    }
    public void getFamilyNewCourse(int page){
        String data=JsonUtils.keyValueToString("pageNo",page);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/curriculum",handler,1);
    }
    public void getFamilyRecommendCourse(int page){
        String data=JsonUtils.keyValueToString("pageNo",page);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/curriculum",handler,1);
    }
    public void getFamilySearchCourse(int page){
        String data=JsonUtils.keyValueToString("pageNo",page);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/hottest-curriculum",handler,1);
    }
    public void getSubjectGradeCourse(int page){

    }
}
