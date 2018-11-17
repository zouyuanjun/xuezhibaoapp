package com.xinzhu.xuezhibao.view.interfaces;

import com.xinzhu.xuezhibao.bean.GoodsBean;

import java.util.List;

public interface MyGoodsInterface extends publiceviewinterface {
    void getGoodsList(List<GoodsBean> list);
    void noMoreData();
    void getGoodsDetail(GoodsBean goodsBean);
}
