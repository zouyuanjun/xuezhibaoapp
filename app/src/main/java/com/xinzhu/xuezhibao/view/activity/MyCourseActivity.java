package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ListViewPageAdapter;
import com.xinzhu.xuezhibao.view.fragment.MyCourseFragment;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的课程
 */
public class MyCourseActivity extends BaseActivity {

    ArrayList<Fragment> fragmentList = new ArrayList<>();
    String[] title = {"家庭教育", "学科教育"};
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tb_basetablayout)
    TabLayout tbJiajiao;
    @BindView(R.id.vp_baseviewpage)
    ViewPager vpJiajiao;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiajiao);
        ButterKnife.bind(this);
        tbJiajiao.addTab(tbJiajiao.newTab());
        tbJiajiao.addTab(tbJiajiao.newTab());
        fragmentList.add(new MyCourseFragment());
        fragmentList.add(new MyCourseFragment());
        ListViewPageAdapter listViewPageAdapter = new ListViewPageAdapter(getSupportFragmentManager(), fragmentList, title,1);
        vpJiajiao.setAdapter(listViewPageAdapter);
        tbJiajiao.setupWithViewPager(vpJiajiao);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        appbar.setMidText("我的课程");
    }

}
