package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.view.View;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.view.fragment.MyFamilyCourseFragment;
import com.zou.fastlibrary.activity.BaseTopTabActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import butterknife.BindView;

public class MyFamilyCourseActivity extends BaseTopTabActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_jiajiao);
        inittab("课程", "老师", "任务", "反馈");
        initfragment(MyFamilyCourseFragment.newInstance(1), MyFamilyCourseFragment.newInstance(2), MyFamilyCourseFragment.newInstance(3), MyFamilyCourseFragment.newInstance(4));
        bingview();
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
