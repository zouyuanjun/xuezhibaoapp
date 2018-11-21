package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.MyPointsBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.PointsInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zou on 2018/11/15.
 */

public class MyPointsPersenter extends BasePresenter {
    PointsInterface pointsInterface;

    public MyPointsPersenter(PointsInterface pointsInterface) {
        this.pointsInterface = pointsInterface;
        inithadle();
    }
    public void inithadle(){
        super.handler=new Handler(){
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
                    pointsInterface.servererr();
                }
                if (code == -100) {
                    pointsInterface.networkerr();
                    return;
                }
                if (code == -200) {
                    pointsInterface.networktimeout();
                    return;
                }
                if (code==100){
                    String data=JsonUtils.getStringValue(result,"Data");
                    if (what==1){
                        List<MyPointsBean> list= JSON.parseArray(data,MyPointsBean.class);
                        if (null!=list&&list.size()>0){
                            List<MyPointsBean> list2=new ArrayList<>();
                            for (MyPointsBean myPointsBean:list){
                                String content=myPointsBean.getTrackContent();
                                int index=content.indexOf("+");
                                if (index>0){
                                }else {
                                    index=content.indexOf("-");
                                }
                                String title=content.substring(0,index);
                                String num=content.substring(index);
                                myPointsBean.setTrackContent(title);
                                myPointsBean.setPointnum(num);
                                list2.add(myPointsBean);
                                pointsInterface.getdata(list2);
                            }
                        }else {
                            pointsInterface.nomoredata();
                        }


                    }
                }if (code==203){
                    pointsInterface.nomoredata();
                }
            }
        };
    }
    public void getdata(int page,int trackType ){
        String data= JsonUtils.keyValueToString2("pageNo",page,"trackType",trackType);
        data=JsonUtils.addKeyValue(data,"token", Constants.TOKEN);
        Network.getnetwork().postJson(data,Constants.URL+"/app/single-integral-trajectory",handler,1);
    };
    public void getalldata(int page){
        String data= JsonUtils.keyValueToString2("pageNo",page,"token",Constants.TOKEN);
        Network.getnetwork().postJson(data,Constants.URL+"/app/integral-trajectory",handler,1);
    };
    public void cancelmessage(){
        handler.removeCallbacksAndMessages(null);
    }
}
