package com.xinzhu.xuezhibao.view.activity;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.wx.goodview.GoodView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.CommentAdapter;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.presenter.LikeCollectPresenter;
import com.xinzhu.xuezhibao.presenter.VideoVoiceDetailPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.custom.VideoPlayer;
import com.xinzhu.xuezhibao.view.interfaces.LikeCollectInterface;
import com.xinzhu.xuezhibao.view.interfaces.VideoVoiceDetailInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomDialog;
import com.zou.fastlibrary.utils.ImageUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.TimeUtil;
import com.zou.fastlibrary.utils.WebViewUtil;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoDetilsActivity extends BaseActivity implements VideoVoiceDetailInterface, LikeCollectInterface {


    @BindView(R.id.standardGSYVideoPlayer)
    VideoPlayer detailPlayer;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_creattime)
    TextView tvCreattime;
    @BindView(R.id.tv_readnum)
    TextView tvReadnum;
    @BindView(R.id.tv_details)
    WebView tvDetails;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    OrientationUtils orientationUtils;
    CommentAdapter commentAdapter;
    boolean isPlay = false;
    Context context;
    String videoid = "";
    LinkedList<CommentBean> commentBeanArrayList = new LinkedList<>();
    VideoVoiceDetailPresenter videoVoiceDetailPresenter;
    @BindView(R.id.SmartRefreshLayout)
    SmartRefreshLayout smartrv;
    @BindView(R.id.tv_detail_comment)
    TextView tvDetailComment;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.ll_dianzan)
    LinearLayout llDianzan;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.ll_shoucan)
    LinearLayout llShoucan;
    int page = 1;
    @BindView(R.id.NestedScrollView)
    android.support.v4.widget.NestedScrollView NestedScrollView;
    LikeCollectPresenter likeCollectPresenter;
    boolean islike = true;
    boolean iscollect = true;
    @BindView(R.id.im_like)
    ImageView imLike;
    @BindView(R.id.im_collection)
    ImageView imCollection;
    @BindView(R.id.tv_buyvideo)
    TextView tvBuyvideo;
    @BindView(R.id.csl_buy)
    ConstraintLayout cslBuy;
    int likenum;  //点赞数
    int commentnum; //评论数
    GoodView mGoodView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details2);
        ButterKnife.bind(this);
        context = this;
        tvDetails.setWebViewClient(new WebViewUtil.MyWebViewClient(this,tvDetails));
        videoid = getIntent().getStringExtra(Constants.INTENT_ID);
        mGoodView = new GoodView(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        detailPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        videoVoiceDetailPresenter = new VideoVoiceDetailPresenter(this);
        likeCollectPresenter = new LikeCollectPresenter(this);
        videoVoiceDetailPresenter.getVideoComment(videoid, 1);
        commentAdapter = new CommentAdapter(this, commentBeanArrayList);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvComment.setLayoutManager(linearLayoutManager3);
        rvComment.setNestedScrollingEnabled(false);
        rvComment.setAdapter(commentAdapter);

        detailPlayer = findViewById(R.id.standardGSYVideoPlayer);

        videoVoiceDetailPresenter.getVideoDetail(videoid);
        likeCollectPresenter.islike(videoid, "2");
        likeCollectPresenter.iscollect(videoid, "2");
        init();
        detailPlayer.onVideoResume();
    }

    private void init() {

        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        ImageView imageView = new ImageView(context);
      //  imageView.setImageBitmap(ImageUtils.createVideoThumbnail("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4", MediaStore.Images.Thumbnails.MINI_KIND));
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView).setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(source1)
                .setCacheWithPlay(false)
                .setVideoTitle("测试视频")
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }

                    @Override
                    public void onClickStartIcon(String url, Object... objects) {
                        super.onClickStartIcon(url, objects);
                        // detailPlayer.startPlayLogic();
                        Log.d("点击了播放按钮");
                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock);
                        }
                    }
                }).build(detailPlayer);

        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                //  orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(context, true, true);
            }
        });
        detailPlayer.imbsetListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        detailPlayer.setCanpaly(true);
        smartrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                videoVoiceDetailPresenter.getVideoComment(videoid, page);
            }
        });
        NestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    smartrv.autoLoadMore();
                }

            }
        });
    }

    @OnClick({R.id.ll_dianzan, R.id.ll_shoucan, R.id.tv_detail_comment,R.id.tv_buyvideo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_dianzan:
                if (Constants.TOKEN.isEmpty()) {
                    showdia();
                } else {
                    if (islike) {
                        likenum--;
                        tvLike.setText(likenum+"");
                        islike = false;
                        likeCollectPresenter.cancellike(videoid, "2");
                        imLike.setImageResource(R.drawable.videodetails_btn_like_nor);
                    } else {
                        mGoodView.setTextInfo("+1",Color.parseColor("#f87d28"),25);
                        mGoodView.show(view);
                        likenum++;
                        tvLike.setText(likenum+"");
                        islike = true;
                        likeCollectPresenter.like(videoid, "2");
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
                        likeCollectPresenter.cancelcollect(videoid, "2");
                        imCollection.setImageResource(R.drawable.videodetails_btn_collection_nor);
                    } else {
                        iscollect = true;
                        likeCollectPresenter.collect(videoid, "2");
                        imCollection.setImageResource(R.drawable.videodetails_btn_collection_sel);
                    }
                }
                break;
            case R.id.tv_detail_comment:
                showpop(view);
                break;
            case R.id.tv_buyvideo:
                if (Constants.TOKEN.isEmpty()) {
                    showdia();
                }else {
                    videoVoiceDetailPresenter.buyVideo(videoid);
                }

                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            detailPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        detailPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }

    @Override
    public void getVideodetail(VideoVoiceBean videoVoiceBean) {
        tvCreattime.setText("发布时间："+TimeUtil.getWholeTime2(videoVoiceBean.getCreateTime()));
        tvDetails.loadDataWithBaseURL( null, videoVoiceBean.getVideoDetails() , "text/html", "UTF-8", null ) ;

        tvTitle.setText(videoVoiceBean.getVideoTitle());
        if (videoVoiceBean.getVideoType()==1){
            cslBuy.setVisibility(View.VISIBLE);
        }
        if (videoVoiceBean.isBuy()){
            cslBuy.setVisibility(View.GONE);
        }
        tvLike.setText(videoVoiceBean.getVidelLike());
        tvReadnum.setText("播放量："+videoVoiceBean.getVideoLook());
        likenum=Integer.parseInt(videoVoiceBean.getVidelLike());
    }

    @Override
    public void getVoicedetail(VideoVoiceBean videoVoiceBean) {

    }

    @Override
    public void getcomment(List<CommentBean> mDatas, int total) {
        commentnum=total;
        tvCommentNum.setText("全部评论(" + total + ")");
        commentBeanArrayList.addAll(mDatas);
        if (commentAdapter != null) {
            commentAdapter.notifyDataSetChanged();
        }
        page++;
        smartrv.finishLoadMore();

    }

    @Override
    public void nologin() {
        showdia();
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

    @Override
    public void getcommentfail() {
        smartrv.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void successbuy() {
        cslBuy.setVisibility(View.GONE);
    }

    @Override
    public void alreadlybuy() {
        cslBuy.setVisibility(View.GONE);
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
            window.setOutsideTouchable(true);
            window.setTouchable(true);
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String commend = editText.getText().toString();
                    CommentBean commentBean = new CommentBean(Constants.userBasicInfo.getImage(), Constants.userBasicInfo.getNickName(), System.currentTimeMillis(), commend, "", "111");
                  commentnum++;
                    tvCommentNum.setText("全部评论(" + commentnum + ")");
                    commentBeanArrayList.addFirst(commentBean);
                    commentAdapter.notifyItemInserted(0);
                    videoVoiceDetailPresenter.sendVideoComment(videoid, commend);
                    window.dismiss();
                }
            });
        }


    }

    public void showdia() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("登陆后才能继续，现在登陆?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(VideoDetilsActivity.this, LoginActivity.class);
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

    @OnClick(R.id.tv_buyvideo)
    public void onViewClicked() {
    }
}

