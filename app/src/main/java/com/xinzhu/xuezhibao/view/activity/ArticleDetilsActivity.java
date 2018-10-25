package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.CommentAdapter;
import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.presenter.ArticlePresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.ArticleInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleDetilsActivity extends BaseActivity implements ArticleInterface {

    CommentAdapter commentAdapter;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_creattime)
    TextView tvCreattime;
    @BindView(R.id.tv_readnum)
    TextView tvReadnum;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    String articleid = "";
    ArticlePresenter articlePresenter;
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    List<CommentBean> list = new ArrayList<>();
    @BindView(R.id.smartrv)
    SmartRefreshLayout smartrv;
    int page = 1;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        articleid = getIntent().getStringExtra(Constants.INTENT_ARTICLE_ID);
        articlePresenter = new ArticlePresenter(this);
        ButterKnife.bind(this);
        init();
        articlePresenter.getComment(articleid, 1);
    }

    private void init() {
        articlePresenter.getArticleDetils(articleid);
        commentAdapter = new CommentAdapter(this, list);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvComment.setLayoutManager(linearLayoutManager3);
        rvComment.setNestedScrollingEnabled(false);
        rvComment.setAdapter(commentAdapter);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.TOKEN.isEmpty()) {

                } else {
                    startActivityForResult(new Intent(ArticleDetilsActivity.this, SendCommentActivity.class), 1);
                }

            }
        });
        smartrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                articlePresenter.getComment(articleid, page);
            }
        });
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    smartrv.autoLoadMore();
                }

            }
        });

    }


    @OnClick(R.id.ll_comment)
    public void onViewClicked() {


    }

    @Override
    public void getarticledetils(ArticleBean articleBean) {
        tvTitle.setText(articleBean.getArticleTitle());
        tvReadnum.setText("阅读：" + articleBean.getArticleRead());
        tvCreattime.setText("发布时间：" + TimeUtil.getWholeTime2(Long.parseLong(articleBean.getCreateTime())));
        tvDetails.setText(articleBean.getArticleContent());
    }

    @Override
    public void getcomment(List<CommentBean> mDatas,String total) {
        list.addAll(mDatas);
        page++;
        if (commentAdapter != null) {
            commentAdapter.notifyDataSetChanged();
        }
        tvCommentNum.setText("全部评论("+total+")");
        smartrv.finishLoadMore();
    }

    @Override
    public void getcommentfail() {
        smartrv.finishLoadMoreWithNoMoreData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String result = data.getExtras().getString("result");
            articlePresenter.sendComment(articleid, result);
        }

    }
}

