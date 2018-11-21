package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ListViewPageAdapter;
import com.xinzhu.xuezhibao.bean.BannerImgBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.fragment.PotionsMallFragment;
import com.xinzhu.xuezhibao.view.helputils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.WebActivity;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xinzhu.xuezhibao.MyApplication.getContext;

public class PointsMallTabActivity extends BaseActivity {
    @BindView(R.id.bn_pointsmall)
    Banner banner;
    @BindView(R.id.tab_potionsmall)
    TabLayout tabPotionsmall;
    @BindView(R.id.vp_potions)
    ViewPager vpPotions;
    ArrayList<Fragment> fragmentList = new ArrayList<>();
    String[] title = {"全部", "0~2000积分", "2000~1万积分", "1万~10万积分",};
    ListViewPageAdapter listViewPageAdapter;
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        String result= (String) msg.obj;
        Log.d(result);
        String data = JsonUtils.getStringValue(result, "Data");
        final List<BannerImgBean> bannerImgBeans = JSON.parseArray(data, BannerImgBean.class);
        if (null != bannerImgBeans && bannerImgBeans.size() > 0) {
            //设置banner样式
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置banner动画效果
           // banner.setBannerAnimation(Transformer.DepthPage);
            //设置标题集合（当banner样式有显示title时）
            //   banner.setBannerTitles(titles);
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(2500);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.CENTER);
            //banner设置方法全部调用完毕时最后调用
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("URL", bannerImgBeans.get(position).getLinkAddress());
                    startActivity(intent);
                }
            });
            List<String> mDatas = new ArrayList<>();
            for (BannerImgBean bannerImgBean : bannerImgBeans) {
                mDatas.add(bannerImgBean.getAdUrl());
            }
            if (null != banner) {
                banner.setImages(mDatas);
                banner.start();
            }
        }
    }
};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointsmall);
        ButterKnife.bind(this);
        tabPotionsmall.addTab(tabPotionsmall.newTab());
        tabPotionsmall.addTab(tabPotionsmall.newTab());
        tabPotionsmall.addTab(tabPotionsmall.newTab());
        tabPotionsmall.addTab(tabPotionsmall.newTab());
        fragmentList.add(new PotionsMallFragment());
        fragmentList.add(new PotionsMallFragment());
        fragmentList.add(new PotionsMallFragment());
        fragmentList.add(new PotionsMallFragment());
        listViewPageAdapter = new ListViewPageAdapter(getSupportFragmentManager(), fragmentList, title, 2);
        vpPotions.setAdapter(listViewPageAdapter);
        tabPotionsmall.setupWithViewPager(vpPotions);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        appbar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(PointsMallTabActivity.this,MyPointsActivity2.class);
            }
        });
        Network.getnetwork().postJson("",Constants.URL+"/guest/select-product-index-round",handler,1);
    }
}
