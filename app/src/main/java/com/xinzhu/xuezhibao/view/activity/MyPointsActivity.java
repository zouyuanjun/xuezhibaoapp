package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ListViewPageAdapter;
import com.xinzhu.xuezhibao.view.fragment.MyPointsFragment;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPointsActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tab_order)
    TabLayout tabOrder;
    @BindView(R.id.vp_myorder)
    ViewPager vpMyorder;
    String[] title = {"全部", "收入", "支出"};
    ArrayList<Fragment> fragmentList = new ArrayList<>();
    ListViewPageAdapter listViewPageAdapter;
    @BindView(R.id.tv_mypoints)
    TextView tvMypoints;
    @BindView(R.id.tv_pointsrule)
    TextView tvPointsrule;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypoints);
        ButterKnife.bind(this);
        tabOrder.addTab(tabOrder.newTab().setText("热门"));
        tabOrder.addTab(tabOrder.newTab().setText("最新"));
        tabOrder.addTab(tabOrder.newTab().setText("我的收藏"));
        tabOrder.setTabTextColors(0x333333,0xf87d28);
        fragmentList.add(new MyPointsFragment());
        fragmentList.add(new MyPointsFragment());
        fragmentList.add(new MyPointsFragment());
        listViewPageAdapter = new ListViewPageAdapter(getSupportFragmentManager(), fragmentList, title, 2);
        vpMyorder.setAdapter(listViewPageAdapter);
        tabOrder.setupWithViewPager(vpMyorder);
    }

    @OnClick(R.id.tv_pointsrule)
    public void onViewClicked() {
        goToActivity(this,PointsRuleActivity.class);
    }
}
