package com.xinzhu.xuezhibao.view.activity;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.tencent.smtt.sdk.WebView;
import com.wx.goodview.GoodView;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.CommentAdapter;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.messagebean.PayResultMessage;
import com.xinzhu.xuezhibao.presenter.AlipayPresenter;
import com.xinzhu.xuezhibao.presenter.LikeCollectPresenter;
import com.xinzhu.xuezhibao.presenter.VideoVoiceDetailPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.WebViewUtil;
import com.xinzhu.xuezhibao.view.custom.VideoPlayer;
import com.xinzhu.xuezhibao.view.interfaces.LikeCollectInterface;
import com.xinzhu.xuezhibao.view.interfaces.PayInterface;
import com.xinzhu.xuezhibao.view.interfaces.VideoVoiceDetailInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.bean.NetWorkMessage;
import com.zou.fastlibrary.ui.CustomDialog;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.CreatPopwindows;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.ScreenUtil;
import com.zou.fastlibrary.utils.StringUtil;
import com.zou.fastlibrary.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xinzhu.xuezhibao.MyApplication.getContext;

public class VideoDetilsActivity extends BaseActivity implements VideoVoiceDetailInterface, LikeCollectInterface, PayInterface {
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
    long startplaytime;
    long stopplaytime;
    boolean haseplay = false; //是否观看过,用于统计视频播放时间
    @BindView(R.id.im_back)
    ImageView imBack;
    PopupWindow loadingPop = null;
    AlipayPresenter alipayPresenter;
    @BindView(R.id.tv_teacher)
    TextView tvTeacher;
    @BindView(R.id.tv_buyvideo2)
    ShapeCornerBgView tvBuyvideo2;
    VideoVoiceBean videoVoiceBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details2);
        ButterKnife.bind(this);
        context = this;
        tvDetails.setWebViewClient(new WebViewUtil.MyWebViewClient(this, tvDetails));
        //为了解决webview高度不稳定的问题，所以先加载一个小网页
        tvDetails.loadDataWithBaseURL(null, "正在加载", "text/html", "UTF-8", null);
        videoid = getIntent().getStringExtra(Constants.INTENT_ID);
        mGoodView = new GoodView(this);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        videoVoiceDetailPresenter = new VideoVoiceDetailPresenter(this);
        alipayPresenter = new AlipayPresenter(VideoDetilsActivity.this, this);
        likeCollectPresenter = new LikeCollectPresenter(this);
        commentAdapter = new CommentAdapter(this, commentBeanArrayList);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvComment.setLayoutManager(linearLayoutManager3);
        rvComment.setNestedScrollingEnabled(false);
        rvComment.setAdapter(commentAdapter);
        videoVoiceDetailPresenter.getVideoComment(videoid, 1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        detailPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != loadingPop && loadingPop.isShowing()) {
            loadingPop.dismiss();
        }
        videoVoiceDetailPresenter.getVideoDetail(videoid);
        likeCollectPresenter.islike(videoid, "2");
        likeCollectPresenter.iscollect(videoid, "2");
        detailPlayer.setCanpaly(false);
        init();
    }

    private void init() {
        orientationUtils = new OrientationUtils(this, detailPlayer);
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
        detailPlayer.startbuListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailPlayer.clickStartIcon();
                if (detailPlayer.isPlay()) {
                    Log.d("开始播放");
                    startplaytime = System.currentTimeMillis();
                    haseplay = true;


                } else {
                    Log.d("停止播放");
                    stopplaytime = System.currentTimeMillis();
                    Constants.PLAYTIME = Constants.PLAYTIME + stopplaytime - startplaytime;
                    startplaytime = stopplaytime;
                }
            }
        });

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

    @OnClick({R.id.ll_dianzan, R.id.ll_shoucan, R.id.tv_detail_comment, R.id.tv_buyvideo, R.id.tv_buyvideo2})
    public void onViewClicked(final View view) {
        if (Constants.TOKEN.isEmpty()) {
            showdia();
            return;
        }
        switch (view.getId()) {
            case R.id.ll_dianzan:

                if (islike) {
                    BToast.success(MyApplication.getContext()).text("已点赞").show();
                } else {
                    mGoodView.setTextInfo("+1", Color.parseColor("#f87d28"), 25);
                    mGoodView.show(view);
                    likenum++;
                    tvLike.setText(likenum + "");
                    tvLike.setTextColor(Color.parseColor("#f87d28"));
                    islike = true;
                    likeCollectPresenter.like(videoid, "2");
                    imLike.setImageResource(R.drawable.videodetails_btn_like_sel);
                }

                break;
            case R.id.ll_shoucan:

                if (iscollect) {
                    iscollect = false;
                    likeCollectPresenter.cancelcollect(videoid, "2");
                    imCollection.setImageResource(R.drawable.videodetails_btn_collection_nor);
                } else {
                    iscollect = true;
                    likeCollectPresenter.collect(videoid, "2");
                    imCollection.setImageResource(R.drawable.videodetails_btn_collection_sel);
                }

                break;
            case R.id.tv_detail_comment:
                showpop(view);
                break;
            case R.id.tv_buyvideo:
                detailPlayer.onVideoPause();

                final PopupWindow popupWindow = CreatPopwindows.creatpopwindows(this, R.layout.pop_pay);
                final View myview = popupWindow.getContentView();
                RadioGroup radioGroup = myview.findViewById(R.id.rg_pay);
                TextView textView = myview.findViewById(R.id.tv_cancle);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.rd_alipay:
                                alipayPresenter.aLiBuyVideo(videoid, 0);
                                popupWindow.dismiss();
                                loadingPop = CreatPopwindows.creatWWpopwindows(VideoDetilsActivity.this, R.layout.pop_loading);
                                loadingPop.showAtLocation(view, Gravity.CENTER, 0, 0);
                                break;
                            case R.id.rd_wxpay:
                                alipayPresenter.WxBuyVideo(videoid, 0);
                                popupWindow.dismiss();
                                loadingPop = CreatPopwindows.creatWWpopwindows(VideoDetilsActivity.this, R.layout.pop_loading);
                                loadingPop.showAtLocation(view, Gravity.CENTER, 0, 0);
                                break;
                        }
                    }
                });

                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, ScreenUtil.getNavigationBarHeight(VideoDetilsActivity.this));

                break;
            case R.id.tv_buyvideo2:
                final PopupWindow popupWindow2 = CreatPopwindows.creatWWpopwindows(this, R.layout.pop_pointsbuyvideo);
                popupWindow2.showAtLocation(view, Gravity.CENTER, 0, 0);
                Bitmap bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ffffffrad8);
                Drawable drawable = new BitmapDrawable(getContext().getResources(), bmp);
                popupWindow2.setBackgroundDrawable(drawable);
                TextView tvVideoprice = popupWindow2.getContentView().findViewById(R.id.tv_videoprice);
                TextView tvmypoints = popupWindow2.getContentView().findViewById(R.id.tv_mypoints);
                ShapeCornerBgView shapeCornerBgView = popupWindow2.getContentView().findViewById(R.id.scb_apply);
                tvVideoprice.setText("使用" + videoVoiceBean.getIntegralPrice() + "积分兑换此课程");
                tvmypoints.setText("当前积分:" + Constants.userBasicInfo.getIntegral());
                shapeCornerBgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alipayPresenter.pointsBuyVideo(videoid, 0);
                        popupWindow2.dismiss();
                        loadingPop = CreatPopwindows.creatWWpopwindows(VideoDetilsActivity.this, R.layout.pop_loading);
                        loadingPop.showAtLocation(view, Gravity.CENTER, 0, 0);
                    }
                });
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != loadingPop && loadingPop.isShowing()) {
            loadingPop.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
        if (haseplay) {
            stopplaytime = System.currentTimeMillis();
            Constants.PLAYTIME = Constants.PLAYTIME + stopplaytime - startplaytime;
            Log.d("观看时间" + Constants.PLAYTIME + "jieshu" + stopplaytime + "kaishi" + startplaytime);
            if (Constants.PLAYTIME > 15 * 60 * 1000) {
                videoVoiceDetailPresenter.playtime();
            }
        }
        EventBus.getDefault().unregister(this);
        videoVoiceDetailPresenter.cancelmessage();

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
        detailPlayer.setCanpaly(true);
        if (isPlay) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }

    @Override
    public void getVideodetail(VideoVoiceBean videoVoiceBean) {
        this.videoVoiceBean = videoVoiceBean;
        tvCreattime.setText("发布时间：" + TimeUtil.getWholeTime3(videoVoiceBean.getCreateTime()));
        tvDetails.loadDataWithBaseURL(null, videoVoiceBean.getVideoDetails(), "text/html", "UTF-8", null);
        tvTitle.setText(videoVoiceBean.getVideoTitle());
        if (videoVoiceBean.getVideoType() == 1) {
            cslBuy.setVisibility(View.VISIBLE);
            detailPlayer.setCanpaly(false);
            tvBuyvideo.setText("￥" + videoVoiceBean.getVideoPrice() + " 购买");
            if (videoVoiceBean.getIntegralAllow() == 0) {
                tvBuyvideo2.setVisibility(View.GONE);
            } else {
                tvBuyvideo2.setText(videoVoiceBean.getIntegralPrice() + "积分购买");
            }
        } else {
            cslBuy.setVisibility(View.GONE);
            detailPlayer.setCanpaly(true);
        }
        if (videoVoiceBean.getIsBuy() == 1) {
            detailPlayer.setCanpaly(true);
            cslBuy.setVisibility(View.GONE);
        }
        tvTeacher.setText(videoVoiceBean.getVideoTeacher());
        tvLike.setText(videoVoiceBean.getVideoLikeFalse());
        tvReadnum.setText("播放量：" + videoVoiceBean.getVideoLookFalse());
        try {
            likenum = Integer.parseInt(videoVoiceBean.getVideoLikeFalse());
        }catch (Exception e){

        }

        String source1 = videoVoiceBean.getVideoUrl();
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
                .setVideoTitle(videoVoiceBean.getVideoTitle())
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
        detailPlayer.showstartbt();
    }

    @Override
    public void getVoicedetail(VideoVoiceBean videoVoiceBean) {

    }

    @Override
    public void getcomment(List<CommentBean> mDatas, int total) {
        commentnum = total;
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
    public void islike(boolean like) {
        islike = like;
        if (like) {
            imLike.setImageResource(R.drawable.videodetails_btn_like_sel);
            tvLike.setTextColor(Color.parseColor("#f87d28"));
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
            window.setTouchable(true);
            window.setOutsideTouchable(true);
            window.update();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String commend = editText.getText().toString();
                    if (StringUtil.isEmpty(commend)) {
                        BToast.error(VideoDetilsActivity.this).text("请填写内容").show();
                        return;
                    }
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

    @Override
    public void paysuccessful() {
        videoVoiceDetailPresenter.getVideoDetail(videoid);
        if (null != loadingPop && loadingPop.isShowing()) {
            loadingPop.dismiss();
        }
        BToast.success(this).text("购买成功").show();
        cslBuy.setVisibility(View.GONE);
        detailPlayer.setCanpaly(true);
        detailPlayer.showstartbt();
    }

    @Override
    public void payfail() {
        if (null != loadingPop && loadingPop.isShowing()) {
            loadingPop.dismiss();
        }
        BToast.error(this).text("支付失败").show();
        cslBuy.setVisibility(View.VISIBLE);
    }

    @Override
    public void orderisexit() {

    }

    @Override
    public void creatOrderfail(String tips) {
        if (null != loadingPop && loadingPop.isShowing()) {
            loadingPop.dismiss();
        }
        BToast.error(this).text("抱歉" + tips).show();
    }

    @Override
    public void netWorkMessage(NetWorkMessage messageEvent) {
        BToast.error(this).text("抱歉请求失败" ).show();
    }

    @Subscribe
    public void PayMessage(PayResultMessage messageEvent) {
        int code = messageEvent.getCode();
        Log.d("微信支付返回码"+messageEvent.getCode());
        if (code == 0) {
            if (StringUtil.isEmpty(Constants.wxOrdernum)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("支付结果");
                builder.setMessage("您可能支付成功，但是由于网络异常，我们无法获取支付结果，请联系客服人员为您核实");
                builder.show();
            } else {
                alipayPresenter.checkWxPay();
            }
        } else if (code == 1||code==-2) {
            BToast.error(this).text("取消支付").show();
        } else {
            if (null != loadingPop && loadingPop.isShowing()) {
                loadingPop.dismiss();
            }
            BToast.error(this).text("微信支付失败").show();
        }
    }

}

