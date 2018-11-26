package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.MyCollectInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.List;

public class MyCollectPresenter {
    MyCollectInterface myCollectInterface;

    public MyCollectPresenter(MyCollectInterface myCollectInterface) {
        this.myCollectInterface = myCollectInterface;
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

                myCollectInterface.servererr();

                return;
            }
            if (code == -100) {

                myCollectInterface.networkerr();

                return;
            }
            if (code == -200) {

                myCollectInterface.networktimeout();

                return;
            }
            if (code == 203) {
                    myCollectInterface.nodata();
                return;
            }
            if (code == 100) {
                String data = JsonUtils.getStringValue(result, "Data");
                if (what == 1) {
                    List<VideoVoiceBean> mDatas = JSON.parseArray(data, VideoVoiceBean.class);
                    if (null!=mDatas&&mDatas.size()>0){
                        myCollectInterface.getvideo(mDatas);
                    }else {
                        myCollectInterface.nodata();
                    }

                } else if (what == 2) {
                    List<VideoVoiceBean> mDatas = JSON.parseArray(data, VideoVoiceBean.class);
                    if (null!=mDatas&&mDatas.size()>0){
                        myCollectInterface.getvoice(mDatas);
                    }else {
                        myCollectInterface.nodata();
                    }

                } else if (what == 3) {
                    List<ArticleBean> mDatas = JSON.parseArray(data, ArticleBean.class);
                    if (null!=mDatas&&mDatas.size()>0){
                        myCollectInterface.getarticle(mDatas);
                    }else {
                        myCollectInterface.nodata();
                    }

                } else if (what == 4) {
                    List<CourseBean> mDatas = JSON.parseArray(data, CourseBean.class);
                    if (null!=mDatas&&mDatas.size()>0){
                        myCollectInterface.getcourse(mDatas);
                    }else {
                        myCollectInterface.nodata();
                    }

                }
            }

        }
    };
    public void getCollectVideo(int page) {
        if (!StringUtil.isEmpty(Constants.TOKEN)){
            String data = JsonUtils.keyValueToString2("pageNo", page,"token",Constants.TOKEN);
            data=JsonUtils.addKeyValue(data,"type",2);
            Network.getnetwork().postJson(data, Constants.URL + "/app/page-by-collect-videos", handler, 1);
        }
    }

    public void getCollectVoice(int page) {
        if (!StringUtil.isEmpty(Constants.TOKEN)){
            String data = JsonUtils.keyValueToString2("pageNo", page,"token",Constants.TOKEN);
            data=JsonUtils.addKeyValue(data,"type",1);
            Network.getnetwork().postJson(data, Constants.URL + "/app/page-by-collect-videos", handler, 2);
        }
    }

    public void getCollectVAreticle(int page) {
        if (!StringUtil.isEmpty(Constants.TOKEN)){
            String data = JsonUtils.keyValueToString2("pageNo", page,"token",Constants.TOKEN);
            data=JsonUtils.addKeyValue(data,"type",1);
            Network.getnetwork().postJson(data, Constants.URL + "/app/page-by-collect-article", handler, 3);
        }
    }
    public void getCollectVCourse(int page) {
        if (!StringUtil.isEmpty(Constants.TOKEN)){
            String data = JsonUtils.keyValueToString2("pageNo", page,"token",Constants.TOKEN);
            data=JsonUtils.addKeyValue(data,"type",1);
            Network.getnetwork().postJson(data, Constants.URL + "/app/page-by-collect-curriculum", handler, 4);
        }
    }
    public void cancelmessage(){
        handler.removeCallbacksAndMessages(null);
    }
}
