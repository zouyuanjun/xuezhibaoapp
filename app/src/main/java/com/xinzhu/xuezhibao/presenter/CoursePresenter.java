package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.SelectConditionBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.FamilyCourseInterface;
import com.xinzhu.xuezhibao.view.interfaces.SubjectCourseInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.List;
//游客课程Presenter
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
                code = JsonUtils.getIntValue(result, "Code");
            } catch (Exception e) {
                com.zou.fastlibrary.utils.Log.d("异常了");
                if (null!=familyCourseInterface){
                    familyCourseInterface.servererr();
                }
                if (null!=subjectCourseInterface){
                    subjectCourseInterface.servererr();
                }

            }
            if (code == -100) {
                if (null!=familyCourseInterface){
                    familyCourseInterface.networkerr();
                }
                if (null!=subjectCourseInterface){
                    subjectCourseInterface.networkerr();
                }
                return;
            }
            if (code == -200) {
                if (null!=familyCourseInterface){
                    familyCourseInterface.networktimeout();
                }
                if (null!=subjectCourseInterface){
                    subjectCourseInterface.networktimeout();
                }
                return;
            }
            if (code==100){
                String data=JsonUtils.getStringValue(result,"Data");
                if (what==1){
                    List<CourseBean> list=JSON.parseArray(data,CourseBean.class);
                    if (null!=list&&list.size()>0){
                        familyCourseInterface.getFamilyCourse(list);
                    }else {
                        familyCourseInterface.noMoreData();
                    }

                }else if (what==2){
                      String classtype=JsonUtils.getStringValue(data,"class_type");
                    String course_type=JsonUtils.getStringValue(data,"subject_type");
                    List<SelectConditionBean> classtypelist=JSON.parseArray(classtype,SelectConditionBean.class);
                    List<SelectConditionBean> coursetypelist=JSON.parseArray(course_type,SelectConditionBean.class);
                    subjectCourseInterface.getSeachCondition(classtypelist,coursetypelist);
                }else if (what==3){
                    List<CourseBean> list=JSON.parseArray(data,CourseBean.class);
                    if (null!=list&&list.size()>0){
                        subjectCourseInterface.getSubjectCourse(list);
                    }else {
                        subjectCourseInterface.noMoreData();
                    }

                }
            }else if (code==203){
                if (null!=subjectCourseInterface){
                    subjectCourseInterface.noMoreData();
                }
                if (null!=familyCourseInterface){
                    familyCourseInterface.noMoreData();
                }
            }
        }
    };

    public void getFamilyCourse(int page){
        String data=JsonUtils.keyValueToString2("pageNo",page,"curriculumKind",1);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/all-curriculum",handler,1);
    }
    public void getFamilyHotCourse(int page){
        String data=JsonUtils.keyValueToString("pageNo",page);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/hottest-curriculum",handler,1);
    }

    public void getFamilyNewCourse(int page){
        String data=JsonUtils.keyValueToString("pageNo",page);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/newest-curriculum",handler,1);
    }
    public void getFamilyRecommendCourse(int page){
        String data=JsonUtils.keyValueToString2("pageNo",page,"isRecommend",1);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/newest-curriculum",handler,1);
    }
    public void getSubjectSearchCourse(int page,String curriculumTitle){
        String data=JsonUtils.keyValueToString2("pageNo",page,"curriculumTitle",curriculumTitle);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/all-curriculum",handler,1);
    }
    public void getSubjectGradeCourse(int page){

    }

    public void getseleectcondition(){

        Network.getnetwork().postJson("",Constants.URL+"/guest/choose-conditions",handler,2);
    }
    public void getSubjectCourse(int page,String subjectDictionaryId,String classDictionaryId){
        String data=JsonUtils.keyValueToString2("pageNo",page,"curriculumKind",2);
        data=JsonUtils.addKeyValue(data,"subjectDictionaryId",subjectDictionaryId);
        data=JsonUtils.addKeyValue(data,"classDictionaryId",classDictionaryId);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/all-curriculum",handler,3);
    }
    public void getSubjectHotCourse(int page ,String subjectDictionaryId,String classDictionaryId){
        String data=JsonUtils.keyValueToString2("pageNo",page,"curriculumKind",2);
        data=JsonUtils.addKeyValue(data,"subjectDictionaryId",subjectDictionaryId);
        data=JsonUtils.addKeyValue(data,"classDictionaryId",classDictionaryId);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/hottest-curriculum",handler,3);
    }

    public void getSubjectNewCourse(int page,String subjectDictionaryId,String classDictionaryId){
        String data=JsonUtils.keyValueToString2("pageNo",page,"curriculumKind",2);
        data=JsonUtils.addKeyValue(data,"subjectDictionaryId",subjectDictionaryId);
        data=JsonUtils.addKeyValue(data,"classDictionaryId",classDictionaryId);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/newest-curriculum",handler,3);
    }
    public void getSubjectRecommendCourse(int page,String subjectDictionaryId,String classDictionaryId){
        String data=JsonUtils.keyValueToString2("pageNo",page,"isRecommend",1);
        data=JsonUtils.addKeyValue(data,"curriculumKind",2);
        data=JsonUtils.addKeyValue(data,"subjectDictionaryId",subjectDictionaryId);
        data=JsonUtils.addKeyValue(data,"classDictionaryId",classDictionaryId);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/newest-curriculum",handler,3);
    }
    public void cancelmessage(){
        handler.removeCallbacksAndMessages(null);
    }
}
