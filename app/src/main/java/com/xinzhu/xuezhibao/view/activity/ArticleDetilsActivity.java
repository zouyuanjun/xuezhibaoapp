package com.xinzhu.xuezhibao.view.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class ArticleDetilsActivity extends BaseActivity {

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        commentAdapter = new CommentAdapter(this, initdata());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        rvComment.setLayoutManager(linearLayoutManager3);
        rvComment.setAdapter(commentAdapter);

    }

    List<CommentBean> initdata() {
        List<CommentBean> list = new ArrayList<>();
        String url = "http://pic29.nipic.com/20130511/9252150_174018365301_2.jpg";
        String title = "哈哈哈这是什么和水水水水";
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        list.add(new CommentBean(url, "家长aa", "25", "2015.15.25", "今天的菜真好吃啊啊啊啊啊啊啊"));
        return list;


    }
}

