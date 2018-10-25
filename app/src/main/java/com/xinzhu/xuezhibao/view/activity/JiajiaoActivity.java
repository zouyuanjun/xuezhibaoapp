package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ListViewPageAdapter;
import com.xinzhu.xuezhibao.adapter.ViewPagnorAdapter;
import com.xinzhu.xuezhibao.view.fragment.ClassFragment;
import com.xinzhu.xuezhibao.view.fragment.JiajiaoCourseFragment;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JiajiaoActivity extends BaseActivity {

    ArrayList<Fragment> fragmentList = new ArrayList<>();
    String[] title = {"课程", "老师", "任务","反馈"};
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tb_jiajiao)
    TabLayout tbJiajiao;
    @BindView(R.id.vp_jiajiao)
    ViewPager vpJiajiao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_jiajiao);
        ButterKnife.bind(this);
        tbJiajiao.addTab(tbJiajiao.newTab().setText("热门"));
        tbJiajiao.addTab(tbJiajiao.newTab().setText("最新"));
        tbJiajiao.addTab(tbJiajiao.newTab().setText("我的收藏"));
        tbJiajiao.addTab(tbJiajiao.newTab().setText("我的收藏"));
        fragmentList.add(JiajiaoCourseFragment.newInstance(1));
        fragmentList.add(JiajiaoCourseFragment.newInstance(2));
        fragmentList.add(JiajiaoCourseFragment.newInstance(3));
        fragmentList.add(JiajiaoCourseFragment.newInstance(4));
        ViewPagnorAdapter listViewPageAdapter = new ViewPagnorAdapter(getSupportFragmentManager(), fragmentList, title);
        vpJiajiao.setAdapter(listViewPageAdapter);
        tbJiajiao.setupWithViewPager(vpJiajiao);
    }

}
