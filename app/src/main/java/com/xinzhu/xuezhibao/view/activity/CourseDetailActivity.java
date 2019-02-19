package com.xinzhu.xuezhibao.view.activity;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.tencent.smtt.sdk.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wx.goodview.GoodView;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.CommentAdapter;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.immodule.JGApplication;
import com.xinzhu.xuezhibao.immodule.view.ChatActivity;
import com.xinzhu.xuezhibao.messagebean.PayResultMessage;
import com.xinzhu.xuezhibao.presenter.AlipayPresenter;
import com.xinzhu.xuezhibao.presenter.CourseDetailPresenter;
import com.xinzhu.xuezhibao.presenter.LikeCollectPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.CoursePlayInterface;
import com.xinzhu.xuezhibao.view.interfaces.LikeCollectInterface;
import com.xinzhu.xuezhibao.view.interfaces.PayInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.bean.NetWorkMessage;
import com.zou.fastlibrary.ui.CustomDialog;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.CreatPopwindows;
import com.zou.fastlibrary.utils.ScreenUtil;
import com.zou.fastlibrary.utils.StringUtil;
import com.zou.fastlibrary.utils.TimeUtil;
import com.xinzhu.xuezhibao.utils.WebViewUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;

import static com.xinzhu.xuezhibao.MyApplication.getContext;

/**
 * 课程详情页面
 */
public class CourseDetailActivity extends BaseActivity implements CoursePlayInterface, LikeCollectInterface, PayInterface {
    CommentAdapter commentAdapter;
    Context context;
    String courseid = "";
    LinkedList<CommentBean> commentBeanList = new LinkedList<>();
    CourseDetailPresenter coursePlayPresenter;
    LikeCollectPresenter likeCollectPresenter;
    int page = 1;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_creattime)
    TextView tvCreattime;
    @BindView(R.id.tv_details)
    WebView webView;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.SmartRefreshLayout)
    com.scwang.smartrefresh.layout.SmartRefreshLayout SmartRefreshLayout;
    @BindView(R.id.tv_courseteacher)
    TextView tvCourseteacher;
    @BindView(R.id.im_talkabout)
    ImageView imTalkabout;
    @BindView(R.id.NestedScrollView)
    android.support.v4.widget.NestedScrollView NestedScrollView;
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
    @BindView(R.id.standardGSYVideoPlayer)
    SimpleDraweeView standardGSYVideoPlayer;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.im_like)
    ImageView imLike;
    @BindView(R.id.im_collection)
    ImageView imCollection;
    boolean islike;
    boolean iscollect;
    @BindView(R.id.tv_allclass)
    TextView tvAllclass;
    @BindView(R.id.tv_alreadybuynum)
    TextView tvAlreadybuynum;
    @BindView(R.id.tv_buycourse)
    ShapeCornerBgView tvBuycourse;
    int likenum;
    int commentnum;
    GoodView mGoodView;
    @BindView(R.id.im_back)
    ImageView imBack;
    CourseBean mycourse;
    PopupWindow loadingPop = null;
    AlipayPresenter alipayPresenter ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_playcourse);
        ButterKnife.bind(this);
        context = this;
        courseid = getIntent().getStringExtra(Constants.INTENT_ID);
        webView.setWebViewClient(new WebViewUtil.MyWebViewClient(this, webView));
        webView.loadDataWithBaseURL(null, "正在加载", "text/html", "UTF-8", null);
        mGoodView = new GoodView(this);
        init();
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        alipayPresenter = new AlipayPresenter(CourseDetailActivity.this,CourseDetailActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != loadingPop && loadingPop.isShowing()) {
            loadingPop.dismiss();
        }
        likeCollectPresenter.islike(courseid, "4");
        likeCollectPresenter.iscollect(courseid, "4");
    }

    private void init() {
        coursePlayPresenter = new CourseDetailPresenter(this);
        likeCollectPresenter = new LikeCollectPresenter(this);
        coursePlayPresenter.getCourseDetail(courseid);
        coursePlayPresenter.getCourseComment(courseid, 1);
        commentAdapter = new CommentAdapter(this, commentBeanList);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvComment.setLayoutManager(linearLayoutManager3);
        rvComment.setNestedScrollingEnabled(false);
        rvComment.setAdapter(commentAdapter);


        SmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                coursePlayPresenter.getCourseComment(courseid, page);
            }
        });
        NestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    SmartRefreshLayout.autoLoadMore();
                }

            }
        });
    }

    @Override
    public void getCoursedetail(CourseBean courseBean) {
        this.mycourse = courseBean;
        if (null != tvAllclass) {
            tvCreattime.setText("发布时间:" + TimeUtil.getWholeTime3(courseBean.getCreateTime()));
            webView.loadDataWithBaseURL(null, courseBean.getCurriculumExplain(), "text/html", "UTF-8", null);
            tvTitle.setText(courseBean.getCurriculumTitle());
            tvCourseteacher.setText(courseBean.getDictionaryName());
            tvPrice.setText(courseBean.getCurriculumPrice());
            Glide.with(context).load(courseBean.getCurriculumPicture()).
                    into(standardGSYVideoPlayer);
            //  standardGSYVideoPlayer.setImageURI(courseBean.getCurriculumPicture());
            if (courseBean.getIsApply()==1) {
                tvBuycourse.setText("已购买");
                tvBuycourse.setBgColor(Color.parseColor("#f2f2f2"));
                tvBuycourse.setTextColor(Color.parseColor("#666666"));
            }
            likenum = courseBean.getCurriculumLikeFalse();
            tvLike.setText(likenum + "");
            tvAlreadybuynum.setText(courseBean.getCurriculumApplyFalse() + "人已购买");
            tvAllclass.setText("共"+ courseBean.getSumHour() + "节");
        }
    }

    @Override
    public void getcomment(List<CommentBean> mDatas, int total) {
        commentnum = total;
        tvCommentNum.setText("全部评论(" + total + ")");
        commentBeanList.addAll(mDatas);
        commentAdapter.notifyDataSetChanged();
        page++;
        SmartRefreshLayout.finishLoadMore();

    }

    @Override
    public void getcommentfail() {
        SmartRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void requestPayInfo(String payinfo, String ordernum) {

        alipayPresenter.alipay(payinfo, CourseDetailActivity.this);
        if (null != loadingPop) {
            loadingPop.dismiss();
        }

    }

    @Override
    public void alreadlybuy() {

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

    @OnClick({R.id.im_talkabout, R.id.ll_comment, R.id.ll_dianzan, R.id.ll_shoucan, R.id.tv_buycourse})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.im_talkabout:
                if (Constants.TOKEN.isEmpty()) {
                    showdia();
                    return;
                }
                if (null == JMessageClient.getMyInfo()){
                    BToast.error(this).text("聊天服务异常，请退出重试或电话联系我们").show();
                    return;
                }
                Intent notificationIntent = new Intent(CourseDetailActivity.this, ChatActivity.class);
                notificationIntent.putExtra(JGApplication.TARGET_ID, mycourse.getTeacherPhone());
                notificationIntent.putExtra(JGApplication.CONV_TITLE, mycourse.getRealName() + "老师");
                notificationIntent.putExtra(JGApplication.TARGET_APP_KEY, Constants.JPUSH_APPKEY);
                startActivity(notificationIntent);//自定义跳转到指定页面
                break;
            case R.id.ll_comment:
                showpop(view);
                break;
            case R.id.ll_dianzan:
                if (Constants.TOKEN.isEmpty()) {
                    showdia();
                } else {
                    if (islike) {
                        BToast.success(MyApplication.getContext()).text("已点赞").show();
                    } else {
                        islike = true;
                        likenum++;
                        mGoodView.setTextInfo("+1", Color.parseColor("#f87d28"), 25);
                        tvLike.setTextColor(Color.parseColor("#f87d28"));
                        mGoodView.show(view);
                        tvLike.setText(likenum + "");
                        likeCollectPresenter.like(courseid, "4");
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
                        likeCollectPresenter.cancelcollect(courseid, "4");
                        imCollection.setImageResource(R.drawable.videodetails_btn_collection_nor);
                    } else {
                        iscollect = true;
                        likeCollectPresenter.collect(courseid, "4");
                        imCollection.setImageResource(R.drawable.videodetails_btn_collection_sel);
                    }
                }
                break;
            case R.id.tv_buycourse:
                if (mycourse.getIsApply()==1) {
                    BToast.success(CourseDetailActivity.this).text("您已购买该课程，无需重复购买").show();
                    return;
                }
                if (Constants.TOKEN.isEmpty()) {
                    showdia();
                } else {
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
                                    alipayPresenter.Alibuycourse(courseid);
                                    popupWindow.dismiss();
                                    loadingPop = CreatPopwindows.creatMMpopwindows(CourseDetailActivity.this, R.layout.pop_loading);
                                    loadingPop.showAtLocation(view, Gravity.CENTER, 0, 0);
                                    break;
                                case R.id.rd_wxpay:
                                    alipayPresenter.Wxbuycourse(courseid);
                                    popupWindow.dismiss();
                                    loadingPop = CreatPopwindows.creatMMpopwindows(CourseDetailActivity.this, R.layout.pop_loading);
                                    loadingPop.showAtLocation(view, Gravity.CENTER, 0, 0);
                                    break;
                            }
                        }
                    });

                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, ScreenUtil.getNavigationBarHeight(CourseDetailActivity.this));
                }
                break;
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
                Intent intent = new Intent(CourseDetailActivity.this, LoginActivity.class);
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

    @OnClick(R.id.tv_buycourse)
    public void onViewClicked() {

    }

    @Override
    public void paysuccessful() {
        if (null!=loadingPop&&loadingPop.isShowing()){
            loadingPop.dismiss();
        }
        BToast.success(this).text("购买成功").show();
        tvBuycourse.setText("已购买");
        tvBuycourse.setBgColor(Color.parseColor("#f2f2f2"));
        tvBuycourse.setTextColor(Color.parseColor("#666666"));
        mycourse.setIsApply(1);
    }

    @Override
    public void payfail() {
        if (null!=loadingPop&&loadingPop.isShowing()){
            loadingPop.dismiss();
        }
        BToast.error(this).text("支付失败").show();
    }

    @Override
    public void orderisexit() {

    }

    @Override
    public void creatOrderfail(String tips) {
        if (null != loadingPop && loadingPop.isShowing()) {
            loadingPop.dismiss();
        }
        BToast.error(this).text("抱歉"+tips).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=loadingPop&&loadingPop.isShowing()){
            loadingPop.dismiss();
        }

    }

    //弹出发送评论对话框
    private void showpop(View view) {
        if (Constants.TOKEN.isEmpty()) {
            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("您尚未登陆，现在就去登陆");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(CourseDetailActivity.this, LoginActivity.class);
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
                        BToast.error(CourseDetailActivity.this).text("请填写内容").show();
                        return;
                    }
                    CommentBean commentBean = new CommentBean(Constants.userBasicInfo.getImage(), Constants.userBasicInfo.getNickName(), System.currentTimeMillis(), commend, "", "111");
                    commentnum++;
                    tvCommentNum.setText("全部评论(" + commentnum + ")");
                    commentBeanList.addFirst(commentBean);
                    commentAdapter.notifyItemInserted(0);
                    coursePlayPresenter.sendCourseComment(courseid, commend);
                    window.dismiss();
                }
            });
        }
    }
    @Subscribe
    public void PayMessage(PayResultMessage messageEvent) {
        int code = messageEvent.getCode();
        if (code==0){
            if (StringUtil.isEmpty(Constants.wxOrdernum)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("支付结果");
                builder.setMessage("您可能支付成功，但是由于网络异常，我们无法获取支付结果，请联系客服人员为您核实");
                builder.show();
            }else {
                alipayPresenter.checkWxPay();
            }
        }else if (code==1||code==-2){
            BToast.error(this).text("取消支付").show();
        }else {
            if (null != loadingPop && loadingPop.isShowing()) {
                loadingPop.dismiss();
            }
            BToast.error(this).text("微信支付失败").show();
        }
    }
    @Override
    public void netWorkMessage(NetWorkMessage messageEvent) {
        super.netWorkMessage(messageEvent);
        if (null!=loadingPop&&loadingPop.isShowing()){
            loadingPop.dismiss();
        }
    }
}

