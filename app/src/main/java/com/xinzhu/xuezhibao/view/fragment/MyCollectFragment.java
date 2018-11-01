package com.xinzhu.xuezhibao.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ArticleListAdapter;
import com.xinzhu.xuezhibao.adapter.CourseMyCollectAdapter;
import com.xinzhu.xuezhibao.adapter.VideoVoiceListAdapter;
import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.VideoBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyCollectFragment extends LazyLoadFragment {
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    int POSITION = 0;
    CourseMyCollectAdapter courseMyCollectAdapter;
    ArticleListAdapter articleListAdapter;
    VideoVoiceListAdapter videoVoiceListAdapter;
    List<CourseBean> courseBeanList = new ArrayList<>();
    List<ArticleBean> articleBeanList = new ArrayList<>();
    List<VideoVoiceBean> videoVoiceBeanList = new ArrayList<>();
    boolean isfirstload = true;

    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
    }

    @Override
    protected void lazyLoad() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager);
        if (isfirstload) {
            if (POSITION == 0) {
                courseMyCollectAdapter=new CourseMyCollectAdapter(new WeakReference<Context>(getActivity()),courseBeanList);
                rvItem.setAdapter(courseMyCollectAdapter);
            } else if (POSITION == 1) {
                articleListAdapter=new ArticleListAdapter(new WeakReference<Context>(getActivity()),articleBeanList);
                rvItem.setAdapter(articleListAdapter);
            } else if (POSITION == 2||POSITION == 3) {
                videoVoiceListAdapter=new VideoVoiceListAdapter(new WeakReference<Context>(getActivity()),videoVoiceBeanList);
                rvItem.setAdapter(videoVoiceListAdapter);
            }
        }

    }

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
        unbinder.unbind();
    }
}
