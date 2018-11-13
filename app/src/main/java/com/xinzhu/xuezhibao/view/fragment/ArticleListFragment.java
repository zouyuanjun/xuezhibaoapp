package com.xinzhu.xuezhibao.view.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bravin.btoast.BToast;
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
import com.zou.fastlibrary.utils.StringUtil;

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
    int POSITION = -1;
    ArticlePresenter articlePresenter;
    List<ArticleBean> list = new ArrayList<>();
    int pageindex = 1;
    boolean isvisible = false;
    @BindView(R.id.im_dataisnull)
    ImageView imDataisnull;
    @BindView(R.id.im_loading)
    ImageView imLoading;
    boolean isfirstload=false;
    @Override
    protected int setContentView() {
        return R.layout.fragment_onlylist;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            POSITION = getArguments().getInt("POSITION");
        }
        Log.d("类型" + POSITION);
        articlePresenter = new ArticlePresenter(this);
        isfirstload=true;
    }


    @Override
    protected void lazyLoad() {
        isvisible = true;
        articleListAdapter = new ArticleListAdapter(new WeakReference(getContext()), list);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager3);
        rvItem.setAdapter(articleListAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageindex = 1;
                list.clear();
                if (POSITION == 0) {
                    articlePresenter.getHotArticle(pageindex);
                } else if (POSITION == 1) {
                    articlePresenter.getNewArticle(pageindex);
                } else if (POSITION == 2) {
                    articlePresenter.getCollectArticle(pageindex);
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (POSITION == 0) {
                    articlePresenter.getHotArticle(pageindex);
                } else if (POSITION == 1) {
                    articlePresenter.getNewArticle(pageindex);
                } else if (POSITION == 2) {
                    articlePresenter.getCollectArticle(pageindex);
                }
            }
        });
        articleListAdapter.setOnItemClickListener(new ArticleListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = list.get(position).getArticleId();
                Intent intent = new Intent(getActivity(), ArticleDetilsActivity.class);
                intent.putExtra(Constants.INTENT_ID, id);
                getActivity().startActivity(intent);
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        if (POSITION == 2) {
            if (!StringUtil.isEmpty(Constants.TOKEN)){
            }else {
                BToast.error(getContext()).text("请登陆后再查看").show();
                imDataisnull.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        if (isfirstload){
            isfirstload=false;
            if (POSITION == 0) {
                articlePresenter.getHotArticle(pageindex);
            } else if (POSITION == 1) {
                articlePresenter.getNewArticle(pageindex);
            } else if (POSITION == 2) {
                if (!StringUtil.isEmpty(Constants.TOKEN)){
                    articlePresenter.getCollectArticle(pageindex);
                }
            }
            imLoading.setVisibility(View.VISIBLE);
            AnimationDrawable drawable = (AnimationDrawable) imLoading.getDrawable();
            drawable.start();
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @Override
    public void getDataSuccessful(List<ArticleBean> mDatas) {
        list.addAll(mDatas);
        if (isvisible) {
            articleListAdapter.notifyDataSetChanged();
        }
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh(true);
        pageindex++;
        imDataisnull.setVisibility(View.GONE);
        imLoading.setVisibility(View.GONE);
    }

    @Override
    public void getDataFail() {
        refreshLayout.finishLoadMoreWithNoMoreData();
        refreshLayout.finishRefresh(false);
        if (list.size()==0){
            imDataisnull.setVisibility(View.VISIBLE);
        }
        imLoading.setVisibility(View.GONE);
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
