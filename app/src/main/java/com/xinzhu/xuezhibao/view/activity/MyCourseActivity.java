package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ListViewPageAdapter;
import com.xinzhu.xuezhibao.adapter.ViewPagnorAdapter;
import com.xinzhu.xuezhibao.view.fragment.JiajiaoCourseFragment;
import com.xinzhu.xuezhibao.view.fragment.MyCourseFragment;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCourseActivity extends BaseActivity {

    ArrayList<Fragment> fragmentList = new ArrayList<>();
    String[] title = {"家庭教育", "学科教育"};
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
        fragmentList.add(new MyCourseFragment());
        fragmentList.add(new MyCourseFragment());
        ListViewPageAdapter listViewPageAdapter = new ListViewPageAdapter(getSupportFragmentManager(), fragmentList, title,1);
        vpJiajiao.setAdapter(listViewPageAdapter);
        tbJiajiao.setupWithViewPager(vpJiajiao);
    }

}
