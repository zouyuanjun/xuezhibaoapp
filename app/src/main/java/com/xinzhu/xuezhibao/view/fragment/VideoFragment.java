package com.xinzhu.xuezhibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.VideoVoiceListAdapter;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.presenter.VideoVoiceListPresenter;
import com.xinzhu.xuezhibao.view.interfaces.VideoFragmentInterface;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoFragment extends LazyLoadFragment implements VideoFragmentInterface {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tab_video)
    TabLayout tabVideo;
    VideoVoiceListAdapter freeadapter;
    VideoVoiceListAdapter payadapter;
    Unbinder unbinder;
    boolean isfistaddtab = true;
    List<VideoVoiceBean> freeBeanList = new ArrayList<>();
    List<VideoVoiceBean> payBeanList = new ArrayList<>();
    int type = 0;
    int freepage = 1;
    int paypage = 1;
    @BindView(R.id.rv_item)
    RecyclerView rvVideocourselist;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    VideoVoiceListPresenter videoVoiceListPresenter;
    boolean isfirstloadfree=true;
    boolean isfirstloadpay=true;
    @Override
    protected int setContentView() {
        return R.layout.fragment_homevideocourse;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (isfistaddtab) {
            tabVideo.addTab(tabVideo.newTab().setText("免费视频"));
            tabVideo.addTab(tabVideo.newTab().setText("付费视频"));
            isfistaddtab = false;
        }
    }

    @Override
    protected void lazyLoad() {
        videoVoiceListPresenter=new VideoVoiceListPresenter(this);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvVideocourselist.setLayoutManager(linearLayoutManager3);
        freeadapter = new VideoVoiceListAdapter(new WeakReference(getContext()), freeBeanList);
        payadapter = new VideoVoiceListAdapter(new WeakReference(getContext()), payBeanList);
        rvVideocourselist.setAdapter(freeadapter);
        tabVideo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    type = 0;
                    rvVideocourselist.setAdapter(freeadapter);
                } else {
                    type = 1;
                    rvVideocourselist.setAdapter(payadapter);
                }
                loaddata();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loaddata();
            }
        });
        loaddata();
    }
private void loaddata(){
        if (type==0){
            if (isfirstloadfree){
                videoVoiceListPresenter.getfreeVideo(1);
                isfirstloadfree=false;
            }else {
                videoVoiceListPresenter.getfreeVideo(freepage);
            }
        }else {
            if (isfirstloadpay){
                videoVoiceListPresenter.getpayVideo(1);
                isfirstloadpay=false;
            }else {
                videoVoiceListPresenter.getpayVideo(paypage);
            }
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
        isfistaddtab = true;
        type = 0;
        freepage = 1;
        isfirstloadpay=true;
        isfirstloadfree=true;
    }

    @Override
    public void getFreeVideo(List<VideoVoiceBean> List) {
        freeBeanList.addAll(List);
        freeadapter.notifyDataSetChanged();
     freepage++;
        refreshLayout.finishLoadMore();
    }

    @Override
    public void getpayVideo(List<VideoVoiceBean> List) {
        payBeanList.addAll(List);
        payadapter.notifyDataSetChanged();
        paypage++;
        refreshLayout.finishLoadMore();
    }

    @Override
    public void noData() {
        refreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void networktimeout() {
        super.networktimeout();
    }

    @Override
    public void networkerr() {
        super.networkerr();
    }

    @Override
    public void servererr() {
        super.servererr();
    }
}
