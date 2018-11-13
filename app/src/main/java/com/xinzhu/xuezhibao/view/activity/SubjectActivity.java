package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ViewPagnorAdapter;
import com.xinzhu.xuezhibao.view.fragment.MyFamilyCourseFragment;
import com.xinzhu.xuezhibao.view.fragment.MySubjectCourseFragment;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页学宝标签的学科页面
 */
public class SubjectActivity extends BaseActivity {

    ArrayList<Fragment> fragmentList = new ArrayList<>();
    String[] title = {"课程", "老师", "作业","反馈"};
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
        appbar.setMidText("学科");
        tbJiajiao.addTab(tbJiajiao.newTab());
        tbJiajiao.addTab(tbJiajiao.newTab());
        tbJiajiao.addTab(tbJiajiao.newTab());
        tbJiajiao.addTab(tbJiajiao.newTab());
        fragmentList.add(MySubjectCourseFragment.newInstance(1));
        fragmentList.add(MySubjectCourseFragment.newInstance(2));
        fragmentList.add(MySubjectCourseFragment.newInstance(3));
        fragmentList.add(MySubjectCourseFragment.newInstance(4));
        ViewPagnorAdapter listViewPageAdapter = new ViewPagnorAdapter(getSupportFragmentManager(), fragmentList, title);
        vpJiajiao.setAdapter(listViewPageAdapter);
        tbJiajiao.setupWithViewPager(vpJiajiao);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
