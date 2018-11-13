package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.CourseFeedbackBean;
import com.xinzhu.xuezhibao.bean.TeacherBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.MyCourseInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

public class MyCoursePresenter {
    MyCourseInterface myCourseInterface;

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
                myCourseInterface.servererr();
            }
            if (code == -100) {
                myCourseInterface.networkerr();
                return;
            }
            if (code == -200) {
                myCourseInterface.networktimeout();
                return;
            }
            if (code == 100) {
                String data = JsonUtils.getStringValue(result, "Data");
                if (what == 1) {
                    List<CourseBean> mDatas = JSON.parseArray(data, CourseBean.class);
                    myCourseInterface.getcourse(mDatas);
                }else if (what==2){
                    String unread=JsonUtils.getStringValue(data,"unread");
                    data=JsonUtils.getStringValue(data,"list");
                    List<CourseFeedbackBean> mDatas = JSON.parseArray(data, CourseFeedbackBean.class);
                    myCourseInterface.getCourseFeesback(mDatas,unread);
                }else if (what==3){
                    List<TeacherBean> mDatas = JSON.parseArray(data, TeacherBean.class);
                    myCourseInterface.getTeacher(mDatas);
                }

            }else {
                myCourseInterface.nodata();
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
}
