package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.SendCommentBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.VideoVoiceDetailInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

public class VideoVoiceDetailPresenter {
    VideoVoiceDetailInterface videoVoiceDetailInterface;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String result = (String) msg.obj;
            com.zou.fastlibrary.utils.Log.d(result);
            int code=-999;
            try {
                code = JsonUtils.getIntValue(result, "_code");
            }catch (Exception e){
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
            if (code!=100){
                videoVoiceDetailInterface.servererr();
                return;
            }
            if (what==1){
                String data = JsonUtils.getStringValue(result, "Data");
                VideoVoiceBean videoVoiceBean = (VideoVoiceBean) JsonUtils.stringToObject(data,VideoVoiceBean.class);
                videoVoiceDetailInterface.getVideodetail(videoVoiceBean);
            }else if (what == 5) {
                if (code == 203) {
                    videoVoiceDetailInterface.getcommentfail();
                } else if (code == 100) {
                    String data = JsonUtils.getStringValue(result, "Data");
                    String total = JsonUtils.getStringValue(data, "total");
                    data = JsonUtils.getStringValue(data, "rows");
                    List<CommentBean> mDatas = JSON.parseArray(data, CommentBean.class);
                    videoVoiceDetailInterface.getcomment(mDatas, total);
                }

            }
        }
    };
    public VideoVoiceDetailPresenter(VideoVoiceDetailInterface videoVoiceDetailInterface) {
        this.videoVoiceDetailInterface = videoVoiceDetailInterface;
    }


    public void getVideoDetail(String id){
        String data=JsonUtils.keyValueToString("videoId",id);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/find-by-video-id",handler,1);
    }
    public void getVoiceDetail(String id){
        String data=JsonUtils.keyValueToString("videoId",id);
        Network.getnetwork().postJson(data,Constants.URL+"/guest/find-by-video-id",handler,2);
    }
    public void sendComment(String id, String comment) {
        SendCommentBean sendCommentBean = new SendCommentBean(Constants.TOKEN, comment, id, "1");
        String data = JsonUtils.objectToString(sendCommentBean);
        Network.getnetwork().postJson(data, Constants.URL + "/app/comment-add-article", handler, 4);
    }

    public void getComment(String id, int page) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "productId", id);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/comment-article", handler, 5);
    }

}
