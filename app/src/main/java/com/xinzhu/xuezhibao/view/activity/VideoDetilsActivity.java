package com.xinzhu.xuezhibao.view.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.CommentAdapter;
import com.xinzhu.xuezhibao.bean.CommentBean;
import com.zou.fastlibrary.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoDetilsActivity extends BaseActivity {


    @BindView(R.id.standardGSYVideoPlayer)
    StandardGSYVideoPlayer videoPlayer;
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
    OrientationUtils orientationUtils;
    CommentAdapter commentAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        ButterKnife.bind(this);
        init();
    }
    private void init() {
        commentAdapter = new CommentAdapter(this, initdata());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvComment.setLayoutManager(linearLayoutManager3);
        rvComment.setAdapter(commentAdapter);

        videoPlayer =  (StandardGSYVideoPlayer)findViewById(R.id.standardGSYVideoPlayer);

        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        videoPlayer.setUp(source1, true, "测试视频");

        //增加封面
//        ImageView imageView = new ImageView(this);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.xxx1);
//        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
    //    orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
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
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

    List<CommentBean> initdata(){
        List<CommentBean> list=new ArrayList<>();
        String url = "http://pic29.nipic.com/20130511/9252150_174018365301_2.jpg";
        String title = "哈哈哈这是什么和水水水水";
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url,"家长aa","25","2015.15.25","今天的菜真好吃啊啊啊啊啊啊啊"));
return list;


    }
}

