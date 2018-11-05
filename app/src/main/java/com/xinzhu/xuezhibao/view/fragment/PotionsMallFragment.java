package com.xinzhu.xuezhibao.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.PotionsGoodsAdapter;
import com.xinzhu.xuezhibao.bean.GoodsBean;
import com.xinzhu.xuezhibao.view.activity.GoodsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PotionsMallFragment extends LazyLoadFragment {
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    PotionsGoodsAdapter potionsGoodsAdapter;
    public List<GoodsBean> goodsBeanList=new ArrayList<>();
    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
    }

    @Override
    protected void lazyLoad() {
        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), 2);
        rvItem.setLayoutManager(layoutManage);
        initdata();
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
    public void initdata(){
        String url="https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=49764040,3750999451&fm=27&gp=0.jpg";
        GoodsBean goodsBean=new GoodsBean(url,"函数的积分哈四大皆空放假放假","30000积分","2530人购买");
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        goodsBeanList.add(goodsBean);
        potionsGoodsAdapter=new PotionsGoodsAdapter(getContext(),goodsBeanList);
        potionsGoodsAdapter.setOnItemClickListener(new PotionsGoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(),GoodsDetailActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        rvItem.setAdapter(potionsGoodsAdapter);
    }
}
