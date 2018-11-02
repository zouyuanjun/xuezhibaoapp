package com.xinzhu.xuezhibao.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.RvJiaojiaoCourseAdapter;
import com.xinzhu.xuezhibao.adapter.RvJiaojiaoFeedbackAdapter;
import com.xinzhu.xuezhibao.adapter.RvJiaojiaoTaskAdapter;
import com.xinzhu.xuezhibao.adapter.RvJiaojiaoTeacherAdapter;
import com.xinzhu.xuezhibao.bean.JiajiaoFeedbackBean;
import com.xinzhu.xuezhibao.bean.JiaojiaoCourseBean;
import com.xinzhu.xuezhibao.bean.TaskBean;
import com.xinzhu.xuezhibao.bean.TeacherBean;
import com.xinzhu.xuezhibao.view.activity.CourseFeedbackActivity;
import com.xinzhu.xuezhibao.view.activity.CourseTaskActivity;
import com.xinzhu.xuezhibao.view.activity.TeacherDetailActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的课程fragment，包含家教课程，学科课程
 */
public class MyCourseFragment extends LazyLoadFragment {
    int POSITION =0;
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    RvJiaojiaoCourseAdapter rvJiaojiaoCourseAdapter;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    WeakReference<Context> mContext;

    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
    }

    @Override
    protected void lazyLoad() {
        mContext=new WeakReference(MyApplication.getContext());
      if (POSITION ==0){
          initdata();
      }else if (POSITION ==1){
          initdata();
      }
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(mContext.get());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager3);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            POSITION = getArguments().getInt("POSITION");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       rvItem.setAdapter(null);
        unbinder.unbind();
    }


    public void initdata() {

        List<JiaojiaoCourseBean> list = new ArrayList<>();
        String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String title = "哈哈哈这是什么和水水水水";
        JiaojiaoCourseBean itemBean = new JiaojiaoCourseBean(url, title, "225", "主讲老师：搜索", "41节/", "12");
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
        rvJiaojiaoCourseAdapter = new RvJiaojiaoCourseAdapter(mContext, list);
        rvItem.setAdapter(rvJiaojiaoCourseAdapter);
    }

}
