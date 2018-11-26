package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.BannerImgBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.XuebaoInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.List;


public class XuebaoPresenter {
    XuebaoInterface xuebaoInterface;

    public XuebaoPresenter(XuebaoInterface xuebaoInterface) {
        this.xuebaoInterface = xuebaoInterface;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String result = (String) msg.obj;
            com.zou.fastlibrary.utils.Log.d(result);
            int code=-999;
            try {
                code = JsonUtils.getIntValue(result, "Code");
            }catch (Exception e){
                com.zou.fastlibrary.utils.Log.d("异常了");
                xuebaoInterface.servererr();
            }
            if (code == -100) {
                xuebaoInterface.networkerr();
                return;
            }
            if (code == -200) {
                xuebaoInterface.networktimeout();
                return;
            }
            if (code==100){
                String data=JsonUtils.getStringValue(result,"Data");
               if (what==3){
                    List<CourseBean> list=JSON.parseArray(data,CourseBean.class);
                    if (null!=list&&list.size()>0){
                        xuebaoInterface.getNewCourse(list);
                    }
                }else if (what==4){
                    List<CourseBean> list=JSON.parseArray(data,CourseBean.class);
                    if (null!=list&&list.size()>0){
                        xuebaoInterface.getHotCourse(list);
                    }
                }else if (what == 5) {
                    List<BannerImgBean> mDatas = JSON.parseArray(data, BannerImgBean.class);
                    if (null!=mDatas&&mDatas.size()>0){
                        xuebaoInterface.getBanner(mDatas);
                    }
                }
            }

        }
    };

    public void initdata(){
        String data3=JsonUtils.keyValueToString("pageNo",1);
        Network.getnetwork().postJson(data3,Constants.URL+"/guest/newest-curriculum",handler,3);
        Network.getnetwork().postJson(data3,Constants.URL+"/guest/hottest-curriculum",handler,4);

        Network.getnetwork().postJson(data3,Constants.URL+"/guest/select-study-index-round",handler,5);
    }
    public void cancelmessage(){
        handler.removeCallbacksAndMessages(null);
    }
}
