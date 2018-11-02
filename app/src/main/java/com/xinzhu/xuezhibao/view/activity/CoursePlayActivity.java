package com.xinzhu.xuezhibao.view.activity;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.CommentAdapter;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.xinzhu.xuezhibao.bean.CourseBean;
import com.xinzhu.xuezhibao.bean.VideoVoiceBean;
import com.xinzhu.xuezhibao.presenter.CoursePlayPresenter;
import com.xinzhu.xuezhibao.presenter.VideoVoiceDetailPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.CoursePlayInterface;
import com.xinzhu.xuezhibao.view.interfaces.VideoVoiceDetailInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomDialog;
import com.zou.fastlibrary.utils.ImageUtils;
import com.zou.fastlibrary.utils.TimeUtil;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoursePlayActivity extends BaseActivity implements CoursePlayInterface {
    OrientationUtils orientationUtils;
    CommentAdapter commentAdapter;
    boolean isPlay = false;
    Context context;
    String courseid = "";
    LinkedList<CommentBean> commentBeanList = new LinkedList<>();
    CoursePlayInterface coursePlayInterface;
    CoursePlayPresenter coursePlayPresenter;
    int page = 1;
    @BindView(R.id.standardGSYVideoPlayer)
    StandardGSYVideoPlayer detailPlayer;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_creattime)
    TextView tvCreattime;
    @BindView(R.id.tv_details)
    TextView tvDetails;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playcourse);
        ButterKnife.bind(this);
        context = this;
        courseid = getIntent().getStringExtra(Constants.INTENT_ID);
        init();
    }

    private void init() {
        coursePlayPresenter = new CoursePlayPresenter(this);
        coursePlayPresenter.getCourseDetail(courseid);
        coursePlayPresenter.getCourseComment(courseid, 1);
        commentAdapter = new CommentAdapter(this, commentBeanList);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvComment.setLayoutManager(linearLayoutManager3);
        rvComment.setNestedScrollingEnabled(false);
        rvComment.setAdapter(commentAdapter);

        detailPlayer = findViewById(R.id.standardGSYVideoPlayer);

        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(ImageUtils.createVideoThumbnail("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4", MediaStore.Images.Thumbnails.MINI_KIND));
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
                }).setLockClickListener(new LockClickListener() {
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
    protected void onPause() {
        super.onPause();
        detailPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    //    @Override
//    public void onBackPressed() {
//        //先返回正常状态
//        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            detailPlayer.getFullscreenButton().performClick();
//            return;
//        }
//        //释放所有
//        detailPlayer.setVideoAllCallBack(null);
//        super.onBackPressed();
//    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }
    @Override
    public void getCoursedetail(CourseBean courseBean) {
        tvCreattime.setText(TimeUtil.getWholeTime2(courseBean.getCreatTime()));
        tvDetails.setText(courseBean.getCurriculumExplain());
        tvTitle.setText(courseBean.getCurriculumTitle());
    }

    @Override
    public void getcomment(List<CommentBean> mDatas, String total) {
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
                    Intent intent = new Intent(CoursePlayActivity.this, LoginActivity.class);
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
            window.setOutsideTouchable(true);
            window.setTouchable(true);
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String commend = editText.getText().toString();
                    CommentBean commentBean = new CommentBean("", "sdfsd", "1540785350000", commend, "", "111");
                    commentBeanList.addFirst(commentBean);
                    commentAdapter.notifyItemInserted(0);
                    coursePlayPresenter.sendCourseComment(courseid, commend);
                    window.dismiss();
                }
            });
        }
    }

    @OnClick({R.id.im_talkabout, R.id.ll_comment, R.id.ll_dianzan, R.id.ll_shoucan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_talkabout:
                break;
            case R.id.ll_comment:
                showpop(view);
                break;
            case R.id.ll_dianzan:
                break;
            case R.id.ll_shoucan:
                break;
        }
    }
}

