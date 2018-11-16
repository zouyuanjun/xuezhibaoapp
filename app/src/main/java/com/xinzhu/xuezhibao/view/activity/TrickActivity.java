package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.TrickAdapter;
import com.xinzhu.xuezhibao.bean.TrickBean;
import com.xinzhu.xuezhibao.presenter.TrackPresenter;
import com.xinzhu.xuezhibao.view.interfaces.TrackInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrickActivity extends BaseActivity implements TrackInterface {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.rv_mystrick)
    RecyclerView rvMystrick;
    TrackPresenter trackPresenter;
    int page = 1;
    List<TrickBean> trickBeanArrayList = new ArrayList<>();
    TrickAdapter trickAdapter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrick);
        ButterKnife.bind(this);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvMystrick.setLayoutManager(linearLayoutManager3);
        trackPresenter = new TrackPresenter(this);
        trackPresenter.gettrick(page);
        trickAdapter = new TrickAdapter(MyApplication.getContext(), trickBeanArrayList);
        rvMystrick.setAdapter(trickAdapter);
        refreshLayout.setPrimaryColorsId(R.color.appcolor, android.R.color.white);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                trackPresenter.gettrick(page);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                trickBeanArrayList.clear();
                page=1;
                trackPresenter.gettrick(page);
            }
        });
    }

    @Override
    public void getMyTrack(List<TrickBean> list) {
        trickBeanArrayList.addAll(list);
        if (null != trickAdapter) {
            trickAdapter.notifyDataSetChanged();
        }
        page++;
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void nomoredata() {
        refreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trackPresenter.cancelmessage();
    }
}
