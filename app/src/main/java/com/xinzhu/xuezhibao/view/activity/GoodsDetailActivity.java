package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.FeedbackPictureBean;
import com.xinzhu.xuezhibao.bean.GoodsBean;
import com.xinzhu.xuezhibao.presenter.MyGoodsPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.helputils.GlideImageLoader;
import com.xinzhu.xuezhibao.view.interfaces.MyGoodsInterface;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.utils.WebViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsDetailActivity extends BaseActivity implements MyGoodsInterface {
    @BindView(R.id.sdv_goodspicter)
    Banner banner;
    @BindView(R.id.tv_goodstitle)
    TextView tvGoodstitle;
    @BindView(R.id.tv_goodsprice)
    TextView tvGoodsprice;
    @BindView(R.id.tv_paynum)
    TextView tvPaynum;
    @BindView(R.id.rv_goodsevaluate)
    RecyclerView rvGoodsevaluate;
    String googdsid;
    MyGoodsPresenter myGoodsPresenter;
    @BindView(R.id.web_goodsdetail)
    WebView webGoodsdetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetail);
        ButterKnife.bind(this);
        googdsid = getIntent().getStringExtra(Constants.INTENT_ID);
        myGoodsPresenter = new MyGoodsPresenter(this);
        myGoodsPresenter.getGoodDetail(googdsid);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        //   banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        webGoodsdetail.setWebViewClient(new WebViewUtil.MyWebViewClient(this,webGoodsdetail));
    }

    @Override
    public void getGoodsList(List<GoodsBean> list) {

    }

    @Override
    public void noMoreData() {

    }

    @Override
    public void getGoodsDetail(GoodsBean goodsBean) {
        tvGoodstitle.setText(goodsBean.getProductName());
        tvGoodsprice.setText(goodsBean.getProductPrice());
        tvPaynum.setText(goodsBean.getBuyNum());
        List<String> img = new ArrayList<>();
        for (FeedbackPictureBean s : goodsBean.getAccessoryList()) {
            img.add(s.getAccessoryUrl());
        }
        if (null != banner) {
            banner.setImages(img);
            banner.start();
        }
        webGoodsdetail.loadDataWithBaseURL( null, goodsBean.getProductDetails(), "text/html", "UTF-8", null ) ;

    }
}
