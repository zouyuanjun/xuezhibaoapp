package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.zou.fastlibrary.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsDetailActivity extends BaseActivity {
    @BindView(R.id.sdv_goodspicter)
    SimpleDraweeView sdvGoodspicter;
    @BindView(R.id.tv_goodstitle)
    TextView tvGoodstitle;
    @BindView(R.id.tv_goodsprice)
    TextView tvGoodsprice;
    @BindView(R.id.tv_paynum)
    TextView tvPaynum;
    @BindView(R.id.rv_goodsevaluate)
    RecyclerView rvGoodsevaluate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetail);
        ButterKnife.bind(this);
        sdvGoodspicter.setImageURI("http://img5.imgtn.bdimg.com/it/u=3717809908,1614242345&fm=26&gp=0.jpg");
    }
}
