package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.GoodsBean;
import com.xinzhu.xuezhibao.bean.GoodsComment;

import java.util.List;

public interface MyGoodsInterface extends publiceviewinterface {
    void getGoodsList(List<GoodsBean> list);
    void noMoreData();
    void getGoodsDetail(GoodsBean goodsBean);
    void getgrade(float grade);
    void getcomment(List<GoodsComment> list);
}
