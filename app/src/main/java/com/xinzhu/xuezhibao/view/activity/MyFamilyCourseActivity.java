package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.MessageNum;
import com.xinzhu.xuezhibao.view.fragment.MyFamilyCourseFragment;
import com.zou.fastlibrary.activity.BaseTopTabActivity;
import com.zou.fastlibrary.bean.NetWorkMessage;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyFamilyCourseActivity extends BaseTopTabActivity {
    CustomNavigatorBar appbar;
    TabLayout tabLayout;
    TextView tvmessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    super.onCreate(savedInstanceState, R.layout.activity_jiajiao);
        inittab("课程", "老师", "作业与总结", "反馈");
        initfragment(MyFamilyCourseFragment.newInstance(1), MyFamilyCourseFragment.newInstance(2), MyFamilyCourseFragment.newInstance(3), MyFamilyCourseFragment.newInstance(4));
        bingview();
        appbar = findViewById(R.id.appbar);
        appbar.setMidText("成长之路");
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tabLayout = gettablayout();
        TabLayout.Tab tabItem = tabLayout.getTabAt(3);
        tabItem.setCustomView(R.layout.customtab);
        TextView textView = tabItem.getCustomView().findViewById(R.id.tabname);
        tvmessage = tabItem.getCustomView().findViewById(R.id.tv_message);
        textView.setText("反馈");

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void netWorkMessage(MessageNum messageEvent) {
        if (Integer.parseInt(messageEvent.getMessage()) > 0) {
            tvmessage.setText(messageEvent.getMessage());
            tvmessage.setVisibility(View.VISIBLE);
        } else {
            tvmessage.setVisibility(View.GONE);
        }

    }
}
