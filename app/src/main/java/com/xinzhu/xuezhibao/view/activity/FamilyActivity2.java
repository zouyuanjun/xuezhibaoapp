package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ViewPagnorAdapter;
import com.xinzhu.xuezhibao.view.fragment.MyFamilyCourseFragment;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.activity.BaseTopTabActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FamilyActivity2 extends BaseTopTabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_jiajiao);
        inittab("课程", "老师", "任务", "反馈");
        initfragment(MyFamilyCourseFragment.newInstance(1), MyFamilyCourseFragment.newInstance(2), MyFamilyCourseFragment.newInstance(3), MyFamilyCourseFragment.newInstance(4));
        bingview();
    }
}
