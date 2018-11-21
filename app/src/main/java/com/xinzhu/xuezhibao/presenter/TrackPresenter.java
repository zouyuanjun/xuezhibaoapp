package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.TrickBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.TrackInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

/**
 * Created by zou on 2018/11/13.
 */

public class TrackPresenter {
    TrackInterface trackInterface;

    public TrackPresenter(TrackInterface trackInterface) {
        this.trackInterface = trackInterface;
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
                if (null != trackInterface) {
                    trackInterface.servererr();
                }
            }
            if (code == -100) {
                if (null != trackInterface) {
                    trackInterface.networkerr();
                }
                return;
            }
            if (code == -200) {
                if (null != trackInterface) {
                    trackInterface.networktimeout();
                }
                return;
            }
            if (code==100){
                if (what==1){
                    String data = JsonUtils.getStringValue(result, "Data");
                    List<TrickBean> mDatas = JSON.parseArray(data, TrickBean.class);
                    trackInterface.getMyTrack(mDatas);
                }

            }else if (code==203){
                trackInterface.nomoredata();
            }
        }
    };
    public void gettrick(int page){
        String data=JsonUtils.keyValueToString2("pageNo",page,"token", Constants.TOKEN);
        Network.getnetwork().postJson(data,Constants.URL+"/app/personal-trajectory",handler,1);
    }
    public void cancelmessage(){
        handler.removeCallbacksAndMessages(null);
    }
}
