package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ListViewPageAdapter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.fragment.FamilyCourseFragment;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 学宝标签中课程页面
 */
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
    int courseclass=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcourse);
        courseclass=getIntent().getIntExtra(Constants.INTENT_COURSE_CLASS,0);
        ButterKnife.bind(this);
        tbClass.addTab(tbClass.newTab());
     //   tbClass.addTab(tbClass.newTab());
        fragmentList.add(new FamilyCourseFragment());
    //    fragmentList.add(new SubjectCourseFragment());
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initview();
    }
    void initview(){
        if (courseclass==1){
            appbar.setMidText("热门课程");
            listViewPageAdapter=new ListViewPageAdapter(getSupportFragmentManager(),fragmentList,title,1);
        }else if (courseclass==2){
            appbar.setMidText("最新课程");
            listViewPageAdapter=new ListViewPageAdapter(getSupportFragmentManager(),fragmentList,title,2);
        }else if (courseclass==3){
            appbar.setMidText("推荐课程");
            listViewPageAdapter=new ListViewPageAdapter(getSupportFragmentManager(),fragmentList,title,3);
        }else if (courseclass==4){
            appbar.setMidText("全部课程");
            listViewPageAdapter=new ListViewPageAdapter(getSupportFragmentManager(),fragmentList,title,4);
        }
        vpItemlist.setAdapter(listViewPageAdapter);
        tbClass.setupWithViewPager(vpItemlist);
    }



}
