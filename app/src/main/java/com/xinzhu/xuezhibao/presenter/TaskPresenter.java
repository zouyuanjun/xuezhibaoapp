package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.MyTaskBean;
import com.xinzhu.xuezhibao.bean.UserBasicInfo;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.GetTaskInterface;
import com.xinzhu.xuezhibao.view.interfaces.TaskInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * Created by zou on 2018/11/13.
 */

public class TaskPresenter extends BasePresenter {
    TaskInterface taskInterface;
    GetTaskInterface getTaskInterface;

    public TaskPresenter(GetTaskInterface getTaskInterface) {
        this.getTaskInterface = getTaskInterface;
        taskPresenter();
    }

    public TaskPresenter(TaskInterface taskInterface) {
        this.taskInterface = taskInterface;
        taskPresenter();
    }

    public void taskPresenter() {
        super.handler = new Handler() {
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
                    if (null != taskInterface) {
                        taskInterface.servererr();
                    }
                }
                if (code == -100) {
                    if (null != taskInterface) {
                        taskInterface.networkerr();
                    }
                    return;
                }
                if (code == -200) {
                    if (null != taskInterface) {
                        taskInterface.networktimeout();
                    }
                    return;
                }
                if (code == 100) {
                    String data = JsonUtils.getStringValue(result, "Data");
                    if (what == 1) {

                        List<MyTaskBean> mDatas = JSON.parseArray(data, MyTaskBean.class);
                        if (null != mDatas && mDatas.size() > 0) {
                            taskInterface.get100task(mDatas);
                        } else {
                            if (null != taskInterface) {
                                taskInterface.nomoredata();
                            }
                        }
                    } else if (what == 2) {
                        List<MyTaskBean> mDatas = JSON.parseArray(data, MyTaskBean.class);
                        if (null != mDatas && mDatas.size() > 0) {
                            taskInterface.get1task(mDatas);
                        } else {
                            if (null != taskInterface) {
                                taskInterface.nomoredata();
                            }
                        }
                    } else if (what == 3) {
                        List<MyTaskBean> mDatas = JSON.parseArray(data, MyTaskBean.class);
                        if (null != mDatas && mDatas.size() > 0) {
                            taskInterface.get2task(mDatas);
                        } else {
                            if (null != taskInterface) {
                                taskInterface.nomoredata();
                            }
                        }
                    } else if (what == 4) {
                        taskInterface.chocksuccessful();
                    } else if (what == 5) {
                        getTaskInterface.accepttask();
                    } else if (what == 6) {
                        taskInterface.ischock(data);
                    } else if (what == 7) {
                        MyTaskBean myTaskBean = JsonUtils.stringToObject(data, MyTaskBean.class);
                        getTaskInterface.gettaskdetails(myTaskBean);
                    } else if (what == 8) {
                        Constants.userBasicInfo = JsonUtils.stringToObject(data, UserBasicInfo.class);
                    }
                } else if (code == 203) {
                    if (null != taskInterface) {
                        taskInterface.nomoredata();
                    }

                }
            }
        };
    }

    public void get100task(int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "stateType", 100);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/my-task", handler, 1);
    }

    public void get1task(int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "stateType", 1);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/my-task", handler, 2);
    }

    public void get2task(int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "stateType", 2);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/my-task", handler, 3);
    }

    public void clockin() {
        String data = JsonUtils.keyValueToString2("token", Constants.TOKEN, "trackType", 2);
        Network.getnetwork().postJson(data, Constants.URL + "/app/clock-in", handler, 4);
    }

    public void gettask(String id) {
        String data = JsonUtils.keyValueToString2("taskId", id, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/receive-task", handler, 5);
    }

    public void isclochin() {
        String data = JsonUtils.keyValueToString("token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/check-clock-in", handler, 6);
    }

    public void cancelmessage() {
        handler.removeCallbacksAndMessages(null);
    }

    public void gettaskdetail(String id, int state, String mytaskid) {
        String data = JsonUtils.keyValueToString2("taskId", id, "stateType", state);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        data = JsonUtils.addKeyValue(data, "myTaskId", mytaskid);
        Network.getnetwork().postJson(data, Constants.URL + "/app/select-task-by-id", handler, 7);
    }

    public void updatauserbasic(){
        String data = JsonUtils.keyValueToString("token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/find-by-account", handler, 8);
    }
}
