package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.SendCommentBean;
import com.xinzhu.xuezhibao.bean.TeacherBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.TeacherInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

public class TeacherPresenter {
    TeacherInterface teacherInterface;

    public TeacherPresenter(TeacherInterface teacherInterface) {
        this.teacherInterface = teacherInterface;
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
                teacherInterface.servererr();
            }
            if (code == -100) {
                teacherInterface.networkerr();
                return;
            }
            if (code == -200) {
                teacherInterface.networktimeout();
                return;
            }
            if (code==100){
                String data = JsonUtils.getStringValue(result, "Data");
                if (what==1){

                    TeacherBean teacherBean=JsonUtils.stringToObject(data,TeacherBean.class);
                    teacherInterface.getTeacherDetail(teacherBean);
                }else if (what==2){
                    String total = JsonUtils.getStringValue(data, "total");
                    data = JsonUtils.getStringValue(data, "rows");
                    List<CommentBean> mDatas = JSON.parseArray(data, CommentBean.class);
                    teacherInterface.getTeacherComment(mDatas, total);
                }else if (what==3){
                    List<CourseBean> mDatas = JSON.parseArray(data, CourseBean.class);
                    teacherInterface.getTeacherCourse(mDatas);
                }
            }
        }
    };
    public void getTeacherDetail(String  teacherid){
        String data=JsonUtils.keyValueToString("userId",teacherid);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/find-teacher-by-id",handler,1);
    };
    public void getTeacherCourse(String  teacherid,int page){
        String data=JsonUtils.keyValueToString2("userId",teacherid,"pageNo",page);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/select-list-by-teacher",handler,3);
    };
    public void getTeacherComment(String  teacherid){

    };
    public void sendComment(String id, String comment) {
        SendCommentBean sendCommentBean = new SendCommentBean(Constants.TOKEN, comment, id, "5");
        String data = JsonUtils.objectToString(sendCommentBean);
        Network.getnetwork().postJson(data, Constants.URL + "/app/insert-comment", handler, 3);
    }
    public void getComment(String id, int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "productId", id);
        data=JsonUtils.addKeyValue(data,"productType",5);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/comment-find-by-productid", handler, 2);
    }

}
