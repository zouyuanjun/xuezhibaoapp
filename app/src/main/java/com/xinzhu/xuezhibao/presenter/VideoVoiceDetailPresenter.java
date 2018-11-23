package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.lljjcoder.Constant;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.PayResquestBean;
import com.xinzhu.xuezhibao.bean.SendCommentBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.VideoVoiceDetailInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.List;

public class VideoVoiceDetailPresenter {
    VideoVoiceDetailInterface videoVoiceDetailInterface;
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
                videoVoiceDetailInterface.servererr();
            }
            if (code == -100) {
                videoVoiceDetailInterface.networkerr();
                return;
            }
            if (code == -200) {
                videoVoiceDetailInterface.networktimeout();
                return;
            }
            if (what == 1) {
                if (code == 100) {
                    String data = JsonUtils.getStringValue(result, "Data");
                    VideoVoiceBean videoVoiceBean = (VideoVoiceBean) JsonUtils.stringToObject(data, VideoVoiceBean.class);
                    videoVoiceDetailInterface.getVideodetail(videoVoiceBean);
                } else if (code == 205) {
                    videoVoiceDetailInterface.nologin();
                }

            } else if (what == 5) {
                if (code == 203) {
                    videoVoiceDetailInterface.getcommentfail();
                } else if (code == 100) {
                    String data = JsonUtils.getStringValue(result, "Data");
                    int total = JsonUtils.getIntValue(data, "total");
                    data = JsonUtils.getStringValue(data, "rows");
                    List<CommentBean> mDatas = JSON.parseArray(data, CommentBean.class);
                    if (null != mDatas && mDatas.size() > 0) {
                        videoVoiceDetailInterface.getcomment(mDatas, total);
                    } else {
                        videoVoiceDetailInterface.getcommentfail();
                    }

                }

            } else if (what == 2) {
                String data = JsonUtils.getStringValue(result, "Data");
                VideoVoiceBean videoVoiceBean = (VideoVoiceBean) JsonUtils.stringToObject(data, VideoVoiceBean.class);
                videoVoiceDetailInterface.getVoicedetail(videoVoiceBean);
            } else if (what == 6) {
                if (code == 100) {
                    String data = JsonUtils.getStringValue(result, "Data");
                    PayResquestBean payResquestBean =  JsonUtils.stringToObject(data, PayResquestBean.class);
                    videoVoiceDetailInterface.successbuy(payResquestBean);
                } else if (code == 6) {
                    videoVoiceDetailInterface.alreadlybuy();
                }
            }
        }
    };

    public VideoVoiceDetailPresenter(VideoVoiceDetailInterface videoVoiceDetailInterface) {
        this.videoVoiceDetailInterface = videoVoiceDetailInterface;
    }


    public void getVideoDetail(String id) {
        String data;
        if (StringUtil.isEmpty(Constants.TOKEN)) {
            data = JsonUtils.keyValueToString("videoId", id);
        } else {
            data = JsonUtils.keyValueToString2("videoId", id, "token", Constants.TOKEN);
        }
        Network.getnetwork().postJson(data, Constants.URL + "/guest/select-video-by-videoId", handler, 1);
    }

    public void getVoiceDetail(String id) {
        String data = JsonUtils.keyValueToString("videoId", id);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/select-audio-by-videoId", handler, 2);
    }

    public void sendVideoComment(String id, String comment) {
        SendCommentBean sendCommentBean = new SendCommentBean(Constants.TOKEN, comment, id, "2");
        String data = JsonUtils.objectToString(sendCommentBean);
        Network.getnetwork().postJson(data, Constants.URL + "/app/insert-comment", handler, 4);
    }

    public void sendVoiceComment(String id, String comment) {
        SendCommentBean sendCommentBean = new SendCommentBean(Constants.TOKEN, comment, id, "3");
        String data = JsonUtils.objectToString(sendCommentBean);
        Network.getnetwork().postJson(data, Constants.URL + "/app/insert-comment", handler, 4);
    }

    public void getVideoComment(String id, int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "productId", id);
        data = JsonUtils.addKeyValue(data, "productType", 2);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/comment-find-by-productid", handler, 5);
    }

    public void getVoiceComment(String id, int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "productId", id);
        data = JsonUtils.addKeyValue(data, "productType", 3);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/comment-find-by-productid", handler, 5);
    }

    public void buyVideo(String id) {
        String data = JsonUtils.keyValueToString2("videoId", id, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/buy-video", handler, 6);
    }

    public void cancelmessage() {
        handler.removeCallbacksAndMessages(null);
    }

    //报告看了多久的视频
    public void playtime() {
        String data = JsonUtils.keyValueToString2("dictionaryId", 24, "token", Constants.TOKEN);
        long time = Constants.PLAYTIME / 1000 / 60;
        data = JsonUtils.addKeyValue(data, "time", time);
        Network.getnetwork().postJson(data, Constants.URL + "/app/complete-my-task", handler, 6);
    }
}
