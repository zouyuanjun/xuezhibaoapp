package com.xinzhu.xuezhibao.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ListViewPageAdapter;
import com.xinzhu.xuezhibao.view.fragment.ArticleListFragment;
import com.xinzhu.xuezhibao.view.fragment.HomeVideoVoiceListFragment;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页视频列表，音频列表，文章列表公用activity
 * 通过向fragment传递参数type区分资源类型，position参数区分tab标签位置
 */
public class HomeListActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tb_class)
    TabLayout tbClass;
    @BindView(R.id.vp_itemlist)
    ViewPager vpItemlist;
    ArrayList<Fragment> fragmentList=new ArrayList<>();
    String [] title={"热门","最新","我的收藏"};
    int TYPE=0;
    ListViewPageAdapter listViewPageAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);
        TYPE=getIntent().getIntExtra("TYPE",0);
        ButterKnife.bind(this);
        tbClass.addTab(tbClass.newTab());
        tbClass.addTab(tbClass.newTab());
        tbClass.addTab(tbClass.newTab());
        tbClass.setTabTextColors(Color.parseColor("#333333"),Color.parseColor("#f87d28"));
        if (TYPE==1){
            appbar.setMidText("视频公开课");
            fragmentList.clear();
            fragmentList.add(HomeVideoVoiceListFragment.newInstance(1));
            fragmentList.add(HomeVideoVoiceListFragment.newInstance(1));
            fragmentList.add(HomeVideoVoiceListFragment.newInstance(1));
            listViewPageAdapter=new ListViewPageAdapter(getSupportFragmentManager(),fragmentList,title,1);
        }else if (TYPE==2){
            appbar.setMidText("音频公开课");
            fragmentList.clear();
            fragmentList.add(HomeVideoVoiceListFragment.newInstance(2));
            fragmentList.add(HomeVideoVoiceListFragment.newInstance(2));
            fragmentList.add(HomeVideoVoiceListFragment.newInstance(2));
            listViewPageAdapter=new ListViewPageAdapter(getSupportFragmentManager(),fragmentList,title,2);
        }else if (TYPE==3){
            appbar.setMidText("亲子文章");
            fragmentList.clear();
            fragmentList.add(new ArticleListFragment());
            fragmentList.add(new ArticleListFragment());
            fragmentList.add(new ArticleListFragment());
            listViewPageAdapter=new ListViewPageAdapter(getSupportFragmentManager(),fragmentList,title,3);
        }
       vpItemlist.setAdapter(listViewPageAdapter);
        tbClass.setupWithViewPager(vpItemlist);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
