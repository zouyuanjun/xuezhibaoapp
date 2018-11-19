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
import com.zou.fastlibrary.activity.BaseTopTabActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPointsActivity2 extends BaseTopTabActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    String[] title = {"全部", "收入", "支出"};
    ArrayList<Fragment> fragmentList = new ArrayList<>();
    ListViewPageAdapter listViewPageAdapter;
    @BindView(R.id.tv_mypoints)
    TextView tvMypoints;
    @BindView(R.id.tv_pointsrule)
    TextView tvPointsrule;
    @BindView(R.id.tb_basetablayout)
    TabLayout tbBasetablayout;
    @BindView(R.id.vp_baseviewpage)
    ViewPager vpBaseviewpage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState,R.layout.activity_mypoints);
        ButterKnife.bind(this);
        inittab("全部", "收入", "支出");
        initfragment(new MyPointsFragment(), new MyPointsFragment(), new MyPointsFragment());
        bingview(1);
        tvMypoints.setText(Constants.userBasicInfo.getIntegral() + "");
    }
    @OnClick(R.id.tv_pointsrule)
    public void onViewClicked() {
        goToActivity(this, PointsRuleActivity.class);
    }
}
