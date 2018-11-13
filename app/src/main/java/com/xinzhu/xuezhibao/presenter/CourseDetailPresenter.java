package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.ClickLikeBean;
import com.xinzhu.xuezhibao.bean.CollectBean;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.SendCommentBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.CoursePlayInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.List;

public class CourseDetailPresenter {
    CoursePlayInterface coursePlayInterface;
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
                coursePlayInterface.servererr();
            }
            if (code == -100) {
                coursePlayInterface.networkerr();
                return;
            }
            if (code == -200) {
                coursePlayInterface.networktimeout();
                return;
            }
            if (what == 1) {
                if (code == 100) {
                    String data = JsonUtils.getStringValue(result, "Data");
                    CourseBean videoVoiceBean = JsonUtils.stringToObject(data, CourseBean.class);
                    coursePlayInterface.getCoursedetail(videoVoiceBean);
                }
            } else if (what == 2) {
                if (code == 203) {
                    coursePlayInterface.getcommentfail();
                } else if (code == 100) {
                    String data = JsonUtils.getStringValue(result, "Data");
                    int  total = JsonUtils.getIntValue(data, "total");
                    data = JsonUtils.getStringValue(data, "rows");
                    List<CommentBean> mDatas = JSON.parseArray(data, CommentBean.class);
                    coursePlayInterface.getcomment(mDatas, total);
                }
            }
        }
    };

    public CourseDetailPresenter(CoursePlayInterface videoVoiceDetailInterface) {
        this.coursePlayInterface = videoVoiceDetailInterface;
    }


    public void getCourseDetail(String id) {
        String data = JsonUtils.keyValueToString("curriculumId", id);
        if (!StringUtil.isEmpty(Constants.TOKEN)){
            data=JsonUtils.addKeyValue(data,"token",Constants.TOKEN);
        }
        Network.getnetwork().postJson(data, Constants.URL + "/guest/find-by-curriculum-id", handler, 1);
    }

    public void getCourseComment(String id, int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "productId", id);
        data=JsonUtils.addKeyValue(data,"productType",4);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/comment-find-by-productid", handler, 2);
    }

    public void sendCourseComment(String id, String comment) {
        SendCommentBean sendCommentBean = new SendCommentBean(Constants.TOKEN, comment, id, "1");
        String data = JsonUtils.objectToString(sendCommentBean);
        data=JsonUtils.addKeyValue(data,"productType",4);
        Network.getnetwork().postJson(data, Constants.URL + "/app/insert-comment", handler, 3);
    }
    public void buycourse(String id){
        if (StringUtil.isEmpty(Constants.TOKEN)){
            return;
        }
        String data=JsonUtils.keyValueToString2("token",Constants.TOKEN,"curriculumId",id);
        Network.getnetwork().postJson(data, Constants.URL + "/app/curriculum-apply", handler, 4);
    }
}
