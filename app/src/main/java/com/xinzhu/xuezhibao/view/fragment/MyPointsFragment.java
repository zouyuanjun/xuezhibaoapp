package com.xinzhu.xuezhibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.MyPointsAdapter;
import com.xinzhu.xuezhibao.bean.MyPointsBean;
import com.xinzhu.xuezhibao.presenter.MyPointsPersenter;
import com.xinzhu.xuezhibao.view.interfaces.PointsInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyPointsFragment extends LazyLoadFragment implements PointsInterface {
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    int POSITION = 0;
    List<MyPointsBean> myPointsBeanList = new ArrayList<>();
    MyPointsAdapter myPointsAdapter;
    boolean isfirstload = true;
    MyPointsPersenter myPointsPersenter;
    int page = 1;
    @BindView(R.id.im_loading)
    ImageView imLoading;
    @BindView(R.id.im_dataisnull)
    ImageView imDataisnull;

    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
    }

    @Override
    protected void lazyLoad() {
        myPointsAdapter = new MyPointsAdapter(getContext(), myPointsBeanList);
        myPointsPersenter = new MyPointsPersenter(this);
        if (isfirstload) {
            isfirstload = false;
            imLoading.setVisibility(View.VISIBLE);
            if (POSITION == 0) {
                myPointsPersenter.getalldata(page);
            } else if (POSITION == 1) {
                myPointsPersenter.getdata(1, 3);
            } else if (POSITION == 2) {
                myPointsPersenter.getdata(1, 5);
            }
        }
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager3);
        rvItem.setAdapter(myPointsAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                myPointsBeanList.clear();
                page = 1;
                if (POSITION == 0) {
                    myPointsPersenter.getalldata(page);
                } else if (POSITION == 1) {
                    myPointsPersenter.getdata(1, 3);
                } else if (POSITION == 2) {
                    myPointsPersenter.getdata(1, 5);
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (POSITION == 0) {
                    myPointsPersenter.getalldata(page);
                } else if (POSITION == 1) {
                    myPointsPersenter.getdata(page, 3);
                } else if (POSITION == 2) {
                    myPointsPersenter.getdata(page, 5);
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isfirstload = true;
        if (getArguments() != null) {
            POSITION = getArguments().getInt("POSITION");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
    public void getdata(List<MyPointsBean> list) {
        myPointsBeanList.addAll(list);
        myPointsAdapter.notifyDataSetChanged();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        imLoading.setVisibility(View.GONE);
        imDataisnull.setVisibility(View.GONE);
        page++;
    }

    @Override
    public void nomoredata() {
        imLoading.setVisibility(View.GONE);
        if (myPointsBeanList.size()==0){
            imDataisnull.setVisibility(View.VISIBLE);
        }
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
