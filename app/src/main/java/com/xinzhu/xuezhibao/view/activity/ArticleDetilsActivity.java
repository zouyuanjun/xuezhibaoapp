package com.xinzhu.xuezhibao.view.activity;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.bravin.btoast.BToast;
import com.tencent.smtt.sdk.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wx.goodview.GoodView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.CommentAdapter;
import com.xinzhu.xuezhibao.bean.ArticleBean;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.presenter.ArticlePresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.ArticleInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomDialog;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StringUtil;
import com.zou.fastlibrary.utils.TimeUtil;
import com.xinzhu.xuezhibao.utils.WebViewUtil;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xinzhu.xuezhibao.MyApplication.getContext;

/**
 * 文章详情页
 */
public class ArticleDetilsActivity extends BaseActivity implements ArticleInterface {
    CommentAdapter commentAdapter;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_creattime)
    TextView tvCreattime;
    @BindView(R.id.tv_readnum)
    TextView tvReadnum;
    @BindView(R.id.tv_details)
    WebView tvDetails;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    String articleid = "";
    ArticlePresenter articlePresenter;
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    LinkedList<CommentBean> commentBeanArrayList = new LinkedList<>();
    @BindView(R.id.smartrv)
    SmartRefreshLayout smartrv;
    int page = 1;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.tv_detail_comment)
    TextView tvComment;
    @BindView(R.id.ll_dianzan)
    LinearLayout llDianzan;
    @BindView(R.id.ll_shoucan)
    LinearLayout llShoucan;
    Context context;
    @BindView(R.id.im_like)
    ImageView imLike;
    @BindView(R.id.im_collection)
    ImageView imCollection;
    boolean islike = true;
    boolean iscollect = true;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    int likenum;  //点赞数
    int commentnum; //评论数
    GoodView mGoodView;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.linearLayout14)
    LinearLayout linearLayout14;
    @BindView(R.id.linearLayout12)
    LinearLayout linearLayout12;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        context = this;
        articleid = getIntent().getStringExtra(Constants.INTENT_ID);
        articlePresenter = new ArticlePresenter(this);
        ButterKnife.bind(this);
        tvDetails.setWebViewClient(new WebViewUtil.MyWebViewClient(this, tvDetails));
        tvDetails.loadDataWithBaseURL(null, "正在加载", "text/html", "UTF-8", null);
        mGoodView = new GoodView(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        articlePresenter.islike(articleid);
        articlePresenter.iscollect(articleid);
    }

    private void init() {
        articlePresenter.getArticleDetils(articleid);
        articlePresenter.getComment(articleid, 1);
        commentAdapter = new CommentAdapter(this, commentBeanArrayList);
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

    @OnClick({R.id.ll_dianzan, R.id.ll_shoucan, R.id.tv_detail_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_dianzan:
                if (Constants.TOKEN.isEmpty()) {
                    showdia();
                } else {
                    if (islike) {
                        islike = false;
                        likenum--;
                        tvLike.setText(likenum + "");
                        articlePresenter.cancellike(articleid);
                        imLike.setImageResource(R.drawable.videodetails_btn_like_nor);
                    } else {
                        mGoodView.setImage(R.drawable.videodetails_btn_like_sel);
                        mGoodView.setTextInfo("+1", Color.parseColor("#f87d28"), 25);
                        mGoodView.show(view);
                        likenum++;
                        tvLike.setText(likenum + "");
                        islike = true;
                        articlePresenter.like(articleid);
                        imLike.setImageResource(R.drawable.videodetails_btn_like_sel);
                    }

                }
                break;
            case R.id.ll_shoucan:
                if (Constants.TOKEN.isEmpty()) {
                    showdia();
                } else {
                    if (iscollect) {
                        iscollect = false;
                        articlePresenter.cancelcollect(articleid);
                        imCollection.setImageResource(R.drawable.videodetails_btn_collection_nor);
                    } else {
                        iscollect = true;
                        articlePresenter.collect(articleid);
                        imCollection.setImageResource(R.drawable.videodetails_btn_collection_sel);
                    }
                }
                break;
            case R.id.tv_detail_comment:
                showpop(view);
                break;
        }
    }

    @Override
    public void getarticledetils(ArticleBean articleBean) {
        if (null != articleBean) {
            tvTitle.setText(articleBean.getArticleTitle());
            tvReadnum.setText("阅读：" + articleBean.getArticleRead());
            tvCreattime.setText("发布时间：" + TimeUtil.getWholeTime2(articleBean.getCreateTime()));
            tvDetails.loadDataWithBaseURL(null, articleBean.getArticleContent(), "text/html", "UTF-8", null);
            tvLike.setText(articleBean.getArticleLike() + "");
            likenum = articleBean.getArticleLike();
        }




    }

    @Override
    public void getcomment(List<CommentBean> mDatas, int total) {
        commentnum = total;
        tvCommentNum.setText("全部评论(" + commentnum + ")");
        commentBeanArrayList.addAll(mDatas);
        page++;
        if (commentAdapter != null) {
            commentAdapter.notifyDataSetChanged();
        }
        smartrv.finishLoadMore();
    }

    @Override
    public void getcommentfail() {
        smartrv.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void islike(boolean like) {
        islike = like;
        if (like) {
            imLike.setImageResource(R.drawable.videodetails_btn_like_sel);
        }
    }

    @Override
    public void iscollect(boolean collect) {
        iscollect = collect;
        if (collect) {
            imCollection.setImageResource(R.drawable.videodetails_btn_collection_sel);
        }

    }

    //弹出发送评论对话框
    private void showpop(View view) {
        if (Constants.TOKEN.isEmpty()) {
            showdia();
        } else {
            View view1 = LayoutInflater.from(context).inflate(R.layout.pop_commend, null);
            final EditText editText = view1.findViewById(R.id.ed_comment);
            TextView textView = view1.findViewById(R.id.tv_send);
            final PopupWindow window = new PopupWindow(view1, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            Bitmap bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ffffffrad8);
            Drawable drawable = new BitmapDrawable(getContext().getResources(), bmp);
            window.setBackgroundDrawable(drawable);
            window.setOutsideTouchable(true);
            window.setTouchable(true);
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String commend = editText.getText().toString();
                    if (StringUtil.isEmpty(commend)){
                        BToast.error(ArticleDetilsActivity.this).text("请填写内容").show();
                        return;
                    }
                    commentnum++;
                    tvCommentNum.setText("全部评论(" + commentnum + ")");
                    CommentBean commentBean = new CommentBean(Constants.userBasicInfo.getImage(), Constants.userBasicInfo.getNickName(), System.currentTimeMillis(), commend, "", "111");
                    commentBeanArrayList.addFirst(commentBean);
                    commentAdapter.notifyItemInserted(0);
                    articlePresenter.sendComment(articleid, commend);
                    window.dismiss();
                }
            });
        }
    }

    public void showdia() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("您尚未登陆，现在就去登陆");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(ArticleDetilsActivity.this, LoginActivity.class);
                intent.putExtra(Constants.FROMAPP, "fss");
                startActivity(intent);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


}

