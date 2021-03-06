package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.GoodsCommentAdapter;
import com.xinzhu.xuezhibao.bean.FeedbackPictureBean;
import com.xinzhu.xuezhibao.bean.GoodsBean;
import com.xinzhu.xuezhibao.bean.GoodsComment;
import com.xinzhu.xuezhibao.presenter.MyGoodsPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.helputils.GlideImageLoader;
import com.xinzhu.xuezhibao.view.interfaces.MyGoodsInterface;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.WebViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.tv_mypoints)
    TextView tvMypoints;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    GoodsBean goodsBean;
    @BindView(R.id.tb_goodstab)
    TabLayout tbGoodstab;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.ns_pingjia)
    NestedScrollView ns_pingjia;
    GoodsCommentAdapter goodsCommentAdapter;
    int page = 1;
    List<GoodsComment> goodsCommentList = new ArrayList<>();
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_nocomment)
    TextView tvNocomment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_goodsdetail);
        ButterKnife.bind(this);
        googdsid = getIntent().getStringExtra(Constants.INTENT_ID);
        myGoodsPresenter = new MyGoodsPresenter(this);
        myGoodsPresenter.getGoodDetail(googdsid);
        myGoodsPresenter.getgoodscomment(page, googdsid);
        myGoodsPresenter.getgrade(googdsid);
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
        webGoodsdetail.setWebViewClient(new WebViewUtil.MyWebViewClient(this, webGoodsdetail));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGoodsevaluate.setLayoutManager(linearLayoutManager);
        goodsCommentAdapter = new GoodsCommentAdapter(this, goodsCommentList);
        tbGoodstab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int POSITION = tab.getPosition();
                if (POSITION == 0) {
                    webGoodsdetail.setVisibility(View.VISIBLE);
                    ns_pingjia.setVisibility(View.GONE);
                } else {
                    webGoodsdetail.setVisibility(View.GONE);
                    ns_pingjia.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                myGoodsPresenter.getgoodscomment(page, googdsid);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                goodsCommentList.clear();
                myGoodsPresenter.getgoodscomment(page, googdsid);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvMypoints.setText(Constants.userBasicInfo.getIntegral() + "积分");
    }

    @Override
    public void getGoodsList(List<GoodsBean> list) {

    }

    @Override
    public void noMoreData() {
        Log.d("没有数据");
        goodsCommentAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void getGoodsDetail(GoodsBean goodsBean) {
        this.goodsBean = goodsBean;
        tvGoodstitle.setText(goodsBean.getProductName());
        tvGoodsprice.setText(goodsBean.getProductPrice() + "积分");
        tvPaynum.setText(goodsBean.getBuyNum() + "人已购买");
        List<String> img = new ArrayList<>();
        if (null != goodsBean.getAccessoryList() && goodsBean.getAccessoryList().size() > 0) {
            for (FeedbackPictureBean s : goodsBean.getAccessoryList()) {
                img.add(s.getAccessoryUrl());
            }
            if (null != banner) {
                banner.setImages(img);
                banner.start();
            }
        }

        webGoodsdetail.loadDataWithBaseURL(null, goodsBean.getProductDetails(), "text/html", "UTF-8", null);

    }

    @Override
    public void getgrade(float grade) {
        ratingBar.setRating(grade);
    }

    @Override
    public void getcomment(List<GoodsComment> list) {
        if (list.size()>0){
            tvNocomment.setVisibility(View.GONE);
            goodsCommentList.addAll(list);
            goodsCommentAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
            page++;}

    }

    @OnClick({R.id.im_back, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.tv_pay:
                Intent intent = new Intent(GoodsDetailActivity.this, PayOrderActivity.class);
                intent.putExtra(Constants.INTENT_ID, goodsBean);
                startActivity(intent);
                break;
        }
    }
}
