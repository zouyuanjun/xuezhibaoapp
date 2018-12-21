package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.GoodsBean;
import com.xinzhu.xuezhibao.bean.GoodsComment;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.MyGoodsInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

public class MyGoodsPresenter extends BasePresenter {
    MyGoodsInterface myorderInterface;

    public MyGoodsPresenter(MyGoodsInterface myorderInterface) {
        this.myorderInterface = myorderInterface;
        inithandle();
    }

    public void inithandle() {
        super.handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String result = (String) msg.obj;
                int what = msg.what;
                Log.d(result);
                int code;
                code = JsonUtils.getIntValue(result, "Code");
                if (what == 1) {
                    if (code == 100) {
                        String data = JsonUtils.getStringValue(result, "Data");
                        List<GoodsBean> list = JSON.parseArray(data, GoodsBean.class);
                        if (null != list && list.size() > 0) {
                            myorderInterface.getGoodsList(list);
                        } else {
                            myorderInterface.noMoreData();
                        }

                    }
                } else if (what == 2) {
                    if (code == 100) {
                        String data = JsonUtils.getStringValue(result, "Data");
                        GoodsBean goodsBean = JsonUtils.stringToObject(data, GoodsBean.class);
                        if (null != goodsBean) {
                            myorderInterface.getGoodsDetail(goodsBean);
                        }
                    }
                } else if (what == 3) {
                    if (code == 100) {
                        String data = JsonUtils.getStringValue(result, "Data");
                        if (!data.isEmpty()){
                            myorderInterface.getgrade(Float.parseFloat(data));
                        }

                    }
                }else if (what == 4) {
                    if (code == 100) {
                        String data = JsonUtils.getStringValue(result, "Data");
                        List<GoodsComment> list=JSON.parseArray(data,GoodsComment.class);
                        if (null!=list&&list.size()>0){
                            myorderInterface.getcomment(list);
                        }else {
                            myorderInterface.noMoreData();
                        }
                    }
                }
            }
        };
    }

    public void getGoodsList(int page, int min, int max) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "minNum", min);
        data = JsonUtils.addKeyValue(data, "maxNum", max);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/select-product-list", handler, 1);
    }
    public void getGoodDetail(String productId) {
        String data = JsonUtils.keyValueToString("productId", productId);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/select-product-by-id", handler, 2);
    }
    public void getgrade(String id) {
        String data = JsonUtils.keyValueToString("productId", id);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/select-product-score", handler, 3);
    }

    /**
     * 获取商品评论
     * @param page
     * @param id
     */
    public void getgoodscomment(int page, String id) {
        String data = JsonUtils.keyValueToString2("pageNo", page, "productId", id);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/select-product-evaluate", handler, 4);
    }
}
