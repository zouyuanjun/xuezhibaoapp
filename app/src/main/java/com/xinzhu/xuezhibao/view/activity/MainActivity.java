package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.xinzhu.xuezhibao.view.fragment.HomeFragemt;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.activity.BaseTabActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseTabActivity {
    List<String> tabTextlist=new ArrayList<>();
    private List<Fragment> fragmentList=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this.getApplicationContext());
        tabTextlist.add("我的基金");
        tabTextlist.add("排行");
        tabTextlist.add("对比");
        creatOnlyTextTab(tabTextlist);
        fragmentList.add(new HomeFragemt());
        fragmentList.add(new HomeFragemt());
        fragmentList.add(new HomeFragemt());
        setViewPagerAdaptr(fragmentList);

    }
}
