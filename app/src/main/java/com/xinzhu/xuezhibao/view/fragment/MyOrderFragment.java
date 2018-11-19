package com.xinzhu.xuezhibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.MyorderAdapter;
import com.xinzhu.xuezhibao.bean.OrderBean;
import com.xinzhu.xuezhibao.presenter.MyOrederPresenter;
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

    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
    }

    @Override
    protected void lazyLoad() {
        myorderAdapter = new MyorderAdapter(getContext(), orderBeanList);
        myOrederPresenter = new MyOrederPresenter(this);
        if (POSITION == 0) {
            myOrederPresenter.getOrderList(page, 1);
        }
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

    }
}
