package com.xinzhu.xuezhibao.view.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.VideoVoiceListAdapter;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.presenter.VideoVoiceListPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.VideoDetilsActivity;
import com.xinzhu.xuezhibao.view.interfaces.VideoFragmentInterface;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoFragment extends LazyLoadFragment implements VideoFragmentInterface {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    VideoVoiceListAdapter payadapter;
    Unbinder unbinder;
    List<VideoVoiceBean> payBeanList = new ArrayList<>();
    int type = 0;
    int freepage = 1;
    int paypage = 1;
    @BindView(R.id.rv_item)
    RecyclerView rvVideocourselist;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    VideoVoiceListPresenter videoVoiceListPresenter;
    boolean isfirstload = true;

    @Override
    protected int setContentView() {
        return R.layout.fragment_homevideocourse;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void lazyLoad() {
        videoVoiceListPresenter = new VideoVoiceListPresenter(this);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvVideocourselist.setLayoutManager(linearLayoutManager3);
        payadapter = new VideoVoiceListAdapter(new WeakReference<>(getContext()).get(), payBeanList, 2);
        rvVideocourselist.setAdapter(payadapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    payBeanList.clear();
                    paypage=1;
                loaddata();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loaddata();
            }
        });
        loaddata();
        payadapter.setOnItemClickListener(new VideoVoiceListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = payBeanList.get(position).getVideoId();
                Intent intent = new Intent(getContext(), VideoDetilsActivity.class);
                intent.putExtra(Constants.INTENT_ID, id);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void loaddata() {
        if (isfirstload){
            videoVoiceListPresenter.getpayVideo(1);
            isfirstload =false;
        }else {
                    videoVoiceListPresenter.getpayVideo(paypage);
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


    @Override
    public void getpayVideo(List<VideoVoiceBean> List) {
        if (null!=refreshLayout){
            payBeanList.addAll(List);
            payadapter.notifyDataSetChanged();
            paypage++;
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh(true);
        }

    }

    @Override
    public void noData() {
        if (null!=refreshLayout){
            refreshLayout.finishRefresh(true);
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
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
