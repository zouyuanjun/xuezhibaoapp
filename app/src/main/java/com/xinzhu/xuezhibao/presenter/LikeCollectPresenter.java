package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.ClickLikeBean;
import com.xinzhu.xuezhibao.bean.CollectBean;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.SendCommentBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.LikeCollectInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

public class LikeCollectPresenter {
    LikeCollectInterface likeCollectInterface;

    public LikeCollectPresenter(LikeCollectInterface likeCollectInterface) {
        this.likeCollectInterface = likeCollectInterface;
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
                code = JsonUtils.getIntValue(result, "Code");
            } catch (Exception e) {
                com.zou.fastlibrary.utils.Log.d("异常了");
                if (null != likeCollectInterface) {
                    likeCollectInterface.servererr();
                }
            }
            if (code == -100) {
                if (null != likeCollectInterface) {
                    likeCollectInterface.networkerr();
                }
                return;
            }
            if (code == -200) {
                if (null != likeCollectInterface) {
                    likeCollectInterface.networktimeout();
                }
                return;
            }
            else if (what == 7) {
                if (code==100){
                    likeCollectInterface.islike(JsonUtils.getbooleValue(result,"Data"));
                }
            }else if (what == 9) {
                if (code==100){
                    likeCollectInterface.iscollect(JsonUtils.getbooleValue(result,"Data"));
                }
            }
    }
    };
    public void like(String objectId,String type) {
        ClickLikeBean clickLikeBean = new ClickLikeBean(Constants.TOKEN, objectId, type);
        String data = JsonUtils.objectToString(clickLikeBean);
        Network.getnetwork().postJson(data, Constants.URL + "/app/add-like", handler, 6);
    }

    public void islike(String objectId,String type) {
        if (null != Constants.TOKEN && !Constants.TOKEN.isEmpty()) {
            ClickLikeBean clickLikeBean = new ClickLikeBean(Constants.TOKEN, objectId, type);
            String data = JsonUtils.objectToString(clickLikeBean);
            Network.getnetwork().postJson(data, Constants.URL + "/app/check-is-like", handler, 7);
        }
    }
    //收藏
    public void collect(String objectId,String type) {
        CollectBean clickLikeBean = new CollectBean(Constants.TOKEN, objectId, type);
        String data = JsonUtils.objectToString(clickLikeBean);
        Network.getnetwork().postJson(data, Constants.URL + "/app/insert-collect", handler, 8);
    }
    //是否收藏
    public void iscollect(String objectId,String type) {
        if (null != Constants.TOKEN && !Constants.TOKEN.isEmpty()) {
            CollectBean clickLikeBean = new CollectBean(Constants.TOKEN, objectId, type);
            String data = JsonUtils.objectToString(clickLikeBean);
            Network.getnetwork().postJson(data, Constants.URL + "/app/check-is-collect", handler, 9);
        }
    }
    //取消收餐
    public void cancelcollect(String objectId,String type) {
        if (null != Constants.TOKEN && !Constants.TOKEN.isEmpty()) {
            CollectBean clickLikeBean = new CollectBean(Constants.TOKEN, objectId, type);
            String data = JsonUtils.objectToString(clickLikeBean);
            Network.getnetwork().postJson(data, Constants.URL + "/app/cancel-collect", handler, 10);
        }
    }
    //取消点赞
    public void cancellike(String objectId,String type) {
        if (null != Constants.TOKEN && !Constants.TOKEN.isEmpty()) {
            ClickLikeBean clickLikeBean = new ClickLikeBean(Constants.TOKEN, objectId, type);
            String data = JsonUtils.objectToString(clickLikeBean);
            Network.getnetwork().postJson(data, Constants.URL + "/app/cancel-like", handler, 11);
        }
    }
    public void cancelmessage(){
        handler.removeCallbacksAndMessages(null);
    }
}
