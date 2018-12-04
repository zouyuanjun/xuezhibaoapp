package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ListViewPageAdapter;
import com.xinzhu.xuezhibao.view.fragment.MyOrderFragment;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tab_order)
    TabLayout tabOrder;
    @BindView(R.id.vp_myorder)
    ViewPager vpMyorder;
    String [] title={"已完成","待发货","待收货","待评价"};
    ArrayList<Fragment> fragmentList=new ArrayList<>();
    ListViewPageAdapter listViewPageAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myorder);
        ButterKnife.bind(this);
        tabOrder.addTab(tabOrder.newTab());
        tabOrder.addTab(tabOrder.newTab());
        tabOrder.addTab(tabOrder.newTab());
        tabOrder.addTab(tabOrder.newTab());
        fragmentList.add(new MyOrderFragment());
        fragmentList.add(new MyOrderFragment());
        fragmentList.add(new MyOrderFragment());
        fragmentList.add(new MyOrderFragment());
        listViewPageAdapter=new ListViewPageAdapter(getSupportFragmentManager(),fragmentList,title,2);
        vpMyorder.setAdapter(listViewPageAdapter);
        tabOrder.setupWithViewPager(vpMyorder);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
