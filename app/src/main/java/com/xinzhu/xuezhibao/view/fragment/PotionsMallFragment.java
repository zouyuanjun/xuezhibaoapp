package com.xinzhu.xuezhibao.view.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import com.xinzhu.xuezhibao.adapter.PotionsGoodsAdapter;
import com.xinzhu.xuezhibao.bean.GoodsBean;
import com.xinzhu.xuezhibao.bean.GoodsComment;
import com.xinzhu.xuezhibao.presenter.MyGoodsPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.GoodsDetailActivity;
import com.xinzhu.xuezhibao.view.interfaces.MyGoodsInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PotionsMallFragment extends LazyLoadFragment implements MyGoodsInterface {
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    PotionsGoodsAdapter potionsGoodsAdapter;
    MyGoodsPresenter myoGoodsPresenter;
    public List<GoodsBean> goodsBeanList = new ArrayList<>();
    int page = 1;
    boolean isfirstload = true;
    int POSITION = 0;
    @BindView(R.id.im_loading)
    ImageView imLoading;
    @BindView(R.id.im_dataisnull)
    ImageView imDataisnull;

    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
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
    protected void lazyLoad() {
        myoGoodsPresenter = new MyGoodsPresenter(this);
        if (isfirstload) {
            isfirstload = false;
            imLoading.setVisibility(View.VISIBLE);
            AnimationDrawable drawable = (AnimationDrawable) imLoading.getDrawable();
            drawable.start();
            if (POSITION == 0) {
                myoGoodsPresenter.getGoodsList(page, 0, 100000);
            } else if (POSITION == 1) {
                myoGoodsPresenter.getGoodsList(page, 0, 2000);
            } else if (POSITION == 2) {
                myoGoodsPresenter.getGoodsList(page, 2000, 10000);
            } else if (POSITION == 3) {
                myoGoodsPresenter.getGoodsList(page, 10000, 100000);
            }
        }
        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), 2);
        rvItem.setLayoutManager(layoutManage);
        potionsGoodsAdapter = new PotionsGoodsAdapter(getContext(), goodsBeanList);
        potionsGoodsAdapter.setOnItemClickListener(new PotionsGoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_ID,goodsBeanList.get(position).getProductId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        rvItem.setAdapter(potionsGoodsAdapter);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (POSITION == 0) {
                    myoGoodsPresenter.getGoodsList(page, 0, 100000);
                } else if (POSITION == 1) {
                    myoGoodsPresenter.getGoodsList(page, 0, 2000);
                } else if (POSITION == 2) {
                    myoGoodsPresenter.getGoodsList(page, 2000, 10000);
                } else if (POSITION == 3) {
                    myoGoodsPresenter.getGoodsList(page, 10000, 100000);
                }
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                goodsBeanList.clear();
                page = 1;
                if (POSITION == 0) {
                    myoGoodsPresenter.getGoodsList(page, 0, 100000);
                } else if (POSITION == 1) {
                    myoGoodsPresenter.getGoodsList(page, 0, 2000);
                } else if (POSITION == 2) {
                    myoGoodsPresenter.getGoodsList(page, 2000, 10000);
                } else if (POSITION == 3) {
                    myoGoodsPresenter.getGoodsList(page, 10000, 100000);
                }
            }
        });
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
    public void getGoodsList(List<GoodsBean> list) {
        if (null!=refreshLayout){
            goodsBeanList.addAll(list);
            potionsGoodsAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
            page++;
            imLoading.setVisibility(View.GONE);
            imDataisnull.setVisibility(View.GONE);
        }

    }

    @Override
    public void noMoreData() {
        if (null!=refreshLayout){
            if (goodsBeanList.size() == 0) {
                imLoading.setVisibility(View.GONE);
                imDataisnull.setVisibility(View.VISIBLE);
            }
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
        }

    }

    @Override
    public void getGoodsDetail(GoodsBean goodsBean) {

    }

    @Override
    public void getgrade(float grade) {
        
    }

    @Override
    public void getcomment(List<GoodsComment> list) {

    }

    @Override
    public void getGoodsDetailfail() {

    }
}
