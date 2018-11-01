package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.CommentAdapter;
import com.xinzhu.xuezhibao.adapter.RvJiatingCourseAdapter;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.JiatingCourseBean;
import com.zou.fastlibrary.activity.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeacherDetailActivity extends BaseActivity {
    @BindView(R.id.detail_page_image)
    ImageView detailPageImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.rv_teacher_course)
    RecyclerView rvTeacherCourse;
    @BindView(R.id.tab_techer)
    TabLayout tabTecher;
    CommentAdapter commentAdapter;
    RvJiatingCourseAdapter adapter;
    WeakReference<Context> mContext;
    @BindView(R.id.im_like)
    ImageView imLike;
    @BindView(R.id.tv_likenum)
    TextView tvLikenum;
    @BindView(R.id.im_talk)
    ImageView imTalk;
    @BindView(R.id.tv_commend)
    TextView tvCommend;
    @BindView(R.id.tv_intro)
    TextView tvIntro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);//设置toolbar
        collapsingToolbar.setTitle("吴淑媛老师");//设置标题的名字
        collapsingToolbar.setExpandedTitleColor(Color.BLACK);//设置展开后标题的颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后标题的颜色
        tabTecher.addTab(tabTecher.newTab().setText("简介"));
        tabTecher.addTab(tabTecher.newTab().setText("课程"));
        tabTecher.addTab(tabTecher.newTab().setText("评价"));
        mContext = new WeakReference(this);

        commentAdapter = new CommentAdapter(this, initlist());
       // adapter = new RvJiatingCourseAdapter(mContext, initdata());

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvTeacherCourse.setLayoutManager(linearLayoutManager3);
        tabTecher.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    tvIntro.setVisibility(View.VISIBLE);
                    rvTeacherCourse.setVisibility(View.GONE);
                }else if (position==1){
                    rvTeacherCourse.setAdapter(adapter);
                }else if (position==2){
                    rvTeacherCourse.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position==0){
                    tvIntro.setVisibility(View.GONE);
                    rvTeacherCourse.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        initdata();

    }

    public List<CommentBean> initlist() {
        List<CommentBean> list = new ArrayList<>();
        String url = "http://pic29.nipic.com/20130511/9252150_174018365301_2.jpg";
        String title = "哈哈哈这是什么和水水水水";
        CommentBean commentBean = new CommentBean(url, title, "4442", "asdfasdfa", "sdfsdf", "45566");
        list.add(commentBean);
        list.add(commentBean);
        list.add(commentBean);
        list.add(commentBean);
        list.add(commentBean);
        list.add(commentBean);
        list.add(commentBean);
        list.add(commentBean);
        list.add(commentBean);
        list.add(commentBean);
        list.add(commentBean);
        list.add(commentBean);
        list.add(commentBean);
        list.add(commentBean);
        list.add(commentBean);
        return list;
    }

    @OnClick({R.id.im_like, R.id.im_talk, R.id.tv_commend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_like:
                break;
            case R.id.im_talk:
                break;
            case R.id.tv_commend:
                break;
        }
    }

    public List<JiatingCourseBean> initdata() {

        List<JiatingCourseBean> list = new ArrayList<>();
        String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String title = "哈哈哈这是什么和水水水水";
        JiatingCourseBean itemBean = new JiatingCourseBean("12123165",url, title, "2235","主讲老师：搜索", "注意力训练");
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
        list.add(itemBean);
       return list;
    }
}
