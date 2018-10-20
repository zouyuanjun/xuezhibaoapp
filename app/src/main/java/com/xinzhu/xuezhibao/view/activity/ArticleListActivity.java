package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TableLayout;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ListViewPageAdapter;
import com.xinzhu.xuezhibao.view.fragment.ClassFragment;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleListActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tb_class)
    TabLayout tbClass;
    @BindView(R.id.vp_itemlist)
    ViewPager vpItemlist;
    ArrayList<Fragment> fragmentList=new ArrayList<>();
    String [] title={"热门","最新","我的收藏"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_itemlist);
        ButterKnife.bind(this);
        tbClass.addTab(tbClass.newTab().setText("热门"));
        tbClass.addTab(tbClass.newTab().setText("最新"));
        tbClass.addTab(tbClass.newTab().setText("我的收藏"));
        fragmentList.add(new ClassFragment());
        fragmentList.add(new ClassFragment());
        fragmentList.add(new ClassFragment());
        ListViewPageAdapter listViewPageAdapter=new ListViewPageAdapter(getSupportFragmentManager(),fragmentList,title);
        vpItemlist.setAdapter(listViewPageAdapter);
        tbClass.setupWithViewPager(vpItemlist);
    }

}
