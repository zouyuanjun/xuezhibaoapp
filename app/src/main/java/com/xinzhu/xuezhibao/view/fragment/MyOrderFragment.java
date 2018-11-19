package com.xinzhu.xuezhibao.view.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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
import com.xinzhu.xuezhibao.adapter.MyorderAdapter;
import com.xinzhu.xuezhibao.bean.OrderBean;
import com.xinzhu.xuezhibao.presenter.MyOrederPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.OrderDetailActivity;
import com.xinzhu.xuezhibao.view.interfaces.MyOrderInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyOrderFragment extends LazyLoadFragment implements MyOrderInterface {
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    int POSITION = 0;
    @BindView(R.id.im_dataisnull)
    ImageView imDataisnull;
    MyorderAdapter myorderAdapter;
    MyOrederPresenter myOrederPresenter;
    List<OrderBean> orderBeanList = new ArrayList<>();
    int page = 1;
    @BindView(R.id.im_loading)
    ImageView imLoading;

    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
    }

    @Override
    protected void lazyLoad() {
        myorderAdapter = new MyorderAdapter(getContext(), orderBeanList);
        myOrederPresenter = new MyOrederPresenter(this);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager);
        rvItem.setAdapter(myorderAdapter);
        AnimationDrawable animationDrawable = (AnimationDrawable) imLoading.getDrawable();
        animationDrawable.start();
        initdata();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initdata();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (POSITION == 0) {
                    myOrederPresenter.getOrderList(page, 100);
                } else if (POSITION == 1) {
                    myOrederPresenter.getOrderList(page, 3);
                } else if (POSITION == 2) {
                    myOrederPresenter.getOrderList(page, 3);
                } else if (POSITION == 3) {
                    myOrederPresenter.getOrderList(page, 4);
                }
            }
        });
        myorderAdapter.setOnItemClickListener(new MyorderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getContext(),OrderDetailActivity.class);
                intent.putExtra(Constants.INTENT_ID,orderBeanList.get(position).getObjectId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

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
    public void getOrderList(List<OrderBean> orderBeans) {
        if (null != orderBeans && null != myorderAdapter) {
            orderBeanList.addAll(orderBeans);
            myorderAdapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMoreWithNoMoreData();
            page++;
        }
        imLoading.setVisibility(View.GONE);
        imDataisnull.setVisibility(View.GONE);
    }

    @Override
    public void noMoredata() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMoreWithNoMoreData();
        imLoading.setVisibility(View.GONE);
        if (orderBeanList.size() == 0) {
            imDataisnull.setVisibility(View.VISIBLE);
        }

    }

    public void initdata() {
        page = 1;
        orderBeanList.clear();
        if (POSITION == 0) {
            myOrederPresenter.getOrderList(page, 100);
        } else if (POSITION == 1) {
            myOrederPresenter.getOrderList(page, 3);
        } else if (POSITION == 2) {
            myOrederPresenter.getOrderList(page, 3);
        } else if (POSITION == 3) {
            myOrederPresenter.getOrderList(page, 4);
        }
    }
}
