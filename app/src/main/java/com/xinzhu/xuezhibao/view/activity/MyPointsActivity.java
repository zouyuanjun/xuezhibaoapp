package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ListViewPageAdapter;
import com.xinzhu.xuezhibao.utils.Constants;
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
        tabOrder.addTab(tabOrder.newTab());
        tabOrder.addTab(tabOrder.newTab());
        tabOrder.addTab(tabOrder.newTab());
        fragmentList.add(new MyPointsFragment());
        fragmentList.add(new MyPointsFragment());
        fragmentList.add(new MyPointsFragment());
        listViewPageAdapter = new ListViewPageAdapter(getSupportFragmentManager(), fragmentList, title, 2);
        vpMyorder.setAdapter(listViewPageAdapter);
        tabOrder.setupWithViewPager(vpMyorder);
        tvMypoints.setText(Constants.userBasicInfo.getIntegral()+"");
    }

    @OnClick(R.id.tv_pointsrule)
    public void onViewClicked() {
        goToActivity(this,PointsRuleActivity.class);
    }
}
