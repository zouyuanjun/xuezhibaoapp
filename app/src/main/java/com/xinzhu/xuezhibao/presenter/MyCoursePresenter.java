package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.CourseFeedbackBean;
import com.xinzhu.xuezhibao.bean.MyjobBean;
import com.xinzhu.xuezhibao.bean.TeacherBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.MyCourseInterface;
import com.xinzhu.xuezhibao.view.interfaces.MyJobDetailInterpace;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

/**
 * 成长之路
 */
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
            String tip;
            try {
                code = JsonUtils.getIntValue(result, "Code");
                tip=JsonUtils.getStringValue(result,"Tips");
            } catch (Exception e) {
                com.zou.fastlibrary.utils.Log.d("异常了");
                if (null != myCourseInterface) {
                    myCourseInterface.servererr();
                } else if (null != myJobDetailInterpace) {
                    myJobDetailInterpace.servererr();
                }
                return;
            }
            if (code == -100) {
                if (null != myCourseInterface) {
                    myCourseInterface.networkerr();
                } else if (null != myJobDetailInterpace) {
                    myJobDetailInterpace.networkerr();
                }
                return;
            }
            if (code == -200) {
                if (null != myCourseInterface) {
                    myCourseInterface.networktimeout();
                } else if (null != myJobDetailInterpace) {
                    myJobDetailInterpace.networktimeout();
                }
                return;
            }
            if (code == 100) {
                String data = JsonUtils.getStringValue(result, "Data");
                if (what == 1) {
                    List<CourseBean> mDatas = JSON.parseArray(data, CourseBean.class);
                    if (null != mDatas && mDatas.size() > 0) {
                        myCourseInterface.getcourse(mDatas);
                    } else {
                        myCourseInterface.nodata();
                    }

                } else if (what == 2) {
                    try {
                        String unread = JsonUtils.getStringValue(data, "unread");
                        data = JsonUtils.getStringValue(data, "list");
                        List<CourseFeedbackBean> mDatas = JSON.parseArray(data, CourseFeedbackBean.class);
                        if (null != mDatas && mDatas.size() > 0) {
                            myCourseInterface.getCourseFeesback(mDatas, unread);
                        } else {
                            myCourseInterface.nodata();
                        }
                    } catch (Exception e) {
                        myCourseInterface.nodata();
                    }

                } else if (what == 3) {
                    List<TeacherBean> mDatas = JSON.parseArray(data, TeacherBean.class);
                    if (null != mDatas && mDatas.size() > 0) {
                        myCourseInterface.getTeacher(mDatas);
                    } else {
                        myCourseInterface.nodata();
                    }
                } else if (what == 4) {
                    List<CourseBean> mDatas = JSON.parseArray(data, CourseBean.class);
                    if (null != mDatas && mDatas.size() > 0) {
                        myCourseInterface.getMyjob(mDatas);
                    } else {
                        myCourseInterface.nodata();
                    }
                } else if (what == 5) {
                    MyjobBean mDatas = JsonUtils.stringToObject(data, MyjobBean.class);
                    myJobDetailInterpace.getjobbyid(mDatas);
                }if (what == 7) {
                    List<CourseBean> mDatas = JSON.parseArray(data, CourseBean.class);
                    if (null != mDatas && mDatas.size() > 0) {
                        myCourseInterface.getMyjob(mDatas);
                    } else {
                        myCourseInterface.nodata();
                    }

                }
            } else {
                if (null != myCourseInterface) {
                    myCourseInterface.nodata();
                } else if (null != myJobDetailInterpace) {
                    myJobDetailInterpace.getdatafail(tip);
                }
            }

        }
    };

    /**
     * 获取成长之路的课程
     * @param page
     * @param type
     */
    public void getcourse(int page, int type) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/all-curriculum", handler, 1);
    }
//只获取我的课程
    public void mygetcourse(int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/my-apply", handler, 1);
    }

    public void getcoursefeedback(int page, int type) {
        String data = JsonUtils.keyValueToString2("pageNo", page,"token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/page-by-curriculum-feedback", handler, 2);
    }
//获取老师列表
    public void getTeacher(int page, int type) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/page-select-teacher", handler, 3);
    }
//获取作业列表
    public void getjob(int page, int type) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/my-Job", handler, 4);
    }

    //获取作业分类
    public void getjobfoled(int page, int type) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/my-apply", handler, 7);
    }
//获取单个作业详情
    public void getjobbyid(String jobId) {
        String data = JsonUtils.keyValueToString2("jobReplyId", jobId, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/select-Job-by-id", handler, 5);
    }

    public void replyfeedback(String id){
        String data = JsonUtils.keyValueToString2("feedbackId", id, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/read-curriculum-feedback-reply", handler, 6);

    }

    public void cancelmessage() {
        handler.removeCallbacksAndMessages(null);
    }
}
