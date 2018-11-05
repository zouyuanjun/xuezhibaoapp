package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ListViewPageAdapter;
import com.xinzhu.xuezhibao.view.fragment.HomeVideoVoiceListFragment;
import com.xinzhu.xuezhibao.view.fragment.PotionsMallFragment;
import com.youth.banner.Banner;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PointsMallTabActivity extends BaseActivity {
    @BindView(R.id.bn_pointsmall)
    Banner bnPointsmall;
    @BindView(R.id.tab_potionsmall)
    TabLayout tabPotionsmall;
    @BindView(R.id.vp_potions)
    ViewPager vpPotions;
    ArrayList<Fragment> fragmentList=new ArrayList<>();
    String [] title={"全部","0~2000积分","2000~1万积分","1万~10万积分",};
    ListViewPageAdapter listViewPageAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointsmall);
        ButterKnife.bind(this);

        tabPotionsmall.addTab(tabPotionsmall.newTab());
        tabPotionsmall.addTab(tabPotionsmall.newTab());
        tabPotionsmall.addTab(tabPotionsmall.newTab());
        tabPotionsmall.addTab(tabPotionsmall.newTab());
        fragmentList.add(new PotionsMallFragment());
        fragmentList.add(new PotionsMallFragment());
        fragmentList.add(new PotionsMallFragment());
        fragmentList.add(new PotionsMallFragment());
        listViewPageAdapter=new ListViewPageAdapter(getSupportFragmentManager(),fragmentList,title,2);
        vpPotions.setAdapter(listViewPageAdapter);
        tabPotionsmall.setupWithViewPager(vpPotions);
    }
}
