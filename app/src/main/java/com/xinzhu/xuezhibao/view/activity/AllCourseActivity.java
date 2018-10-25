package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ListViewPageAdapter;
import com.xinzhu.xuezhibao.view.fragment.ClassFragment;
import com.xinzhu.xuezhibao.view.fragment.FragmentXuekeCourse;
import com.xinzhu.xuezhibao.view.fragment.Fragment_Jiating;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllCourseActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tb_class)
    TabLayout tbClass;
    @BindView(R.id.vp_itemlist)
    ViewPager vpItemlist;
    ArrayList<Fragment> fragmentList=new ArrayList<>();
    ListViewPageAdapter listViewPageAdapter;
    String [] title={"家庭教育","学科教育"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcourse);
        ButterKnife.bind(this);
        tbClass.addTab(tbClass.newTab().setText("热门"));
        tbClass.addTab(tbClass.newTab().setText("最新"));
        fragmentList.add(new Fragment_Jiating());
        fragmentList.add(new FragmentXuekeCourse());
        listViewPageAdapter=new ListViewPageAdapter(getSupportFragmentManager(),fragmentList,title,2);
        vpItemlist.setAdapter(listViewPageAdapter);
        tbClass.setupWithViewPager(vpItemlist);
    }



}
