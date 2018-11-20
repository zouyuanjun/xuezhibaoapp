package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.CourseFeedbackBean;
import com.xinzhu.xuezhibao.bean.MyjobBean;
import com.xinzhu.xuezhibao.bean.TeacherBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.MyCourseInterface;
import com.xinzhu.xuezhibao.view.interfaces.MyJobDetailInterpace;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

public class MyCoursePresenter {
    MyCourseInterface myCourseInterface;
MyJobDetailInterpace myJobDetailInterpace;

    public MyCoursePresenter(MyJobDetailInterpace myJobDetailInterpace) {
        this.myJobDetailInterpace = myJobDetailInterpace;
    }

    public MyCoursePresenter(MyCourseInterface myCourseInterface) {
        this.myCourseInterface = myCourseInterface;
    }

    Handler handler = new Handler() {
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
                if (null!=myCourseInterface){
                    myCourseInterface.servererr();
                }else if (null!=myJobDetailInterpace){
                    myJobDetailInterpace.servererr();
                }
                return;
            }
            if (code == -100) {
                if (null!=myCourseInterface){
                    myCourseInterface.networkerr();
                }else if (null!=myJobDetailInterpace){
                    myJobDetailInterpace.networkerr();
                }
                return;
            }
            if (code == -200) {
                if (null!=myCourseInterface){
                    myCourseInterface.networktimeout();
                }else if (null!=myJobDetailInterpace){
                    myJobDetailInterpace.networktimeout();
                }
                return;
            }
            if (code == 100) {
                String data = JsonUtils.getStringValue(result, "Data");
                if (what == 1) {
                    List<CourseBean> mDatas = JSON.parseArray(data, CourseBean.class);
                    if (null!=mDatas&&mDatas.size()>0){
                        myCourseInterface.getcourse(mDatas);
                    }else {
                        myCourseInterface.nodata();
                    }

                }else if (what==2){
                    String unread=JsonUtils.getStringValue(data,"unread");
                    data=JsonUtils.getStringValue(data,"list");
                    List<CourseFeedbackBean> mDatas = JSON.parseArray(data, CourseFeedbackBean.class);
                    if (null!=mDatas&&mDatas.size()>0){
                        myCourseInterface.getCourseFeesback(mDatas,unread);
                    }else {
                        myCourseInterface.nodata();
                    }
                }else if (what==3){
                    List<TeacherBean> mDatas = JSON.parseArray(data, TeacherBean.class);
                    if (null!=mDatas&&mDatas.size()>0){
                        myCourseInterface.getTeacher(mDatas);
                    }else {
                        myCourseInterface.nodata();
                    }
                }else if (what==4){
                    data = JsonUtils.getStringValue(data, "rows");
                    List<MyjobBean> mDatas = JSON.parseArray(data, MyjobBean.class);
                    if (null!=mDatas&&mDatas.size()>0){
                        myCourseInterface.getMyjob(mDatas);
                    }else {
                        myCourseInterface.nodata();
                    }
                }else if (what==5){
                    MyjobBean mDatas = JsonUtils.stringToObject(data,MyjobBean.class);
                    myJobDetailInterpace.getjobbyid(mDatas);
                }
            }else {
                if (null!=myCourseInterface){
                    myCourseInterface.nodata();
                }else if (null!=myJobDetailInterpace){
                }
            }

        }
    };

    public void getcourse(int page, int type) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "curriculumKind", type);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/my-apply", handler, 1);
    }

    public void getcoursefeedback(int page, int type){
        String data = JsonUtils.keyValueToString2("pageNo", page, "curriculumKind", type);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/page-by-curriculum-feedback", handler, 2);
    }
    public void getTeacher(int page, int type){
        String data = JsonUtils.keyValueToString2("pageNo", page, "curriculumKind", type);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/page-select-teacher", handler, 3);
    }
    public void getjob(int page, int type){
        String data = JsonUtils.keyValueToString2("pageNo", page, "curriculumKind", type);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/my-CurriculumJob", handler, 4);
    }
    public void getjobbyid(String jobId){
        String data = JsonUtils.keyValueToString2("jobId", jobId, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/select-curriculumJob-by-id", handler, 5);
    }
    public void cancelmessage(){
        handler.removeCallbacksAndMessages(null);
    }
}
