package com.xinzhu.xuezhibao.view.fragment;

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
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.ArticleListAdapter;
import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.presenter.ArticlePresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.ArticleDetilsActivity;
import com.xinzhu.xuezhibao.view.interfaces.ArticleListInterface;
import com.zou.fastlibrary.utils.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ArticleListFragment extends LazyLoadFragment implements ArticleListInterface {
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    ArticleListAdapter articleListAdapter;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int POSITION=-1;
    ArticlePresenter articlePresenter;
    List<ArticleBean> list = new ArrayList<>();
    int pageindex=1;
    boolean isvisible=false;
    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            POSITION=getArguments().getInt("POSITION");
        }
        Log.d("类型"+POSITION);
        articlePresenter=new ArticlePresenter(this);
        if (POSITION==0){
            articlePresenter.getHotArticle(pageindex);
        }else if (  POSITION==1){
            articlePresenter.getNewArticle(pageindex);
        }
    }


    @Override
    protected void lazyLoad() {
        isvisible=true;
        articleListAdapter = new ArticleListAdapter(new WeakReference(getContext()), list);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager3);
        rvItem.setAdapter(articleListAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (POSITION==0){
                    articlePresenter.getHotArticle(pageindex);
                }else if (  POSITION==1){
                    articlePresenter.getNewArticle(pageindex);
                }
            }
        });
        articleListAdapter.setOnItemClickListener(new ArticleListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id=list.get(position).getArticleId();
                Intent intent=new Intent(getActivity(),ArticleDetilsActivity.class);
                intent.putExtra(Constants.INTENT_ID,id);
                getActivity().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

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
    public void getNewArticle(List<ArticleBean> mDatas) {
        list.addAll(mDatas);
        if (isvisible){
            articleListAdapter.notifyDataSetChanged();
        }
        refreshLayout.finishLoadMore();
        pageindex++;
    }

    @Override
    public void getHotArticle(List<ArticleBean> mDatas) {
        list.addAll(mDatas);
        if (isvisible){
            articleListAdapter.notifyDataSetChanged();
        }
        refreshLayout.finishLoadMore();
        pageindex++;
    }

    @Override
    public void getCollect(List<ArticleBean> mDatas) {

    }

    @Override
    public void getDataFail() {
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
