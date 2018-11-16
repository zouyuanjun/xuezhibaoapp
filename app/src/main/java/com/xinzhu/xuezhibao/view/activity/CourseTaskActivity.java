package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.FeedbackPictureBean;
import com.xinzhu.xuezhibao.bean.MyjobBean;
import com.xinzhu.xuezhibao.presenter.MyCoursePresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.MyJobDetailInterpace;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseTaskActivity extends BaseActivity implements MyJobDetailInterpace {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_course)
    TextView tvCourse;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_task)
    TextView tvTask;
    @BindView(R.id.linearLayout15)
    LinearLayout linearLayout15;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_tasktext)
    TextView tvTasktext;
    @BindView(R.id.im_committask)
    ImageView imCommittask;
    @BindView(R.id.im_1)
    ImageView im1;
    @BindView(R.id.im_2)
    ImageView im2;
    @BindView(R.id.im_3)
    ImageView im3;
    String jobid;
    MyCoursePresenter myCoursePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_task);
        ButterKnife.bind(this);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        jobid = getIntent().getStringExtra(Constants.INTENT_ID);
        myCoursePresenter = new MyCoursePresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myCoursePresenter.getjobbyid(jobid);
    }

    @OnClick(R.id.im_committask)
    public void onViewClicked() {
        Intent intent = new Intent(CourseTaskActivity.this, EditTaskActivity.class);
        intent.putExtra(Constants.INTENT_ID, jobid);
        startActivity(intent);
    }

    @Override
    public void getjobbyid(MyjobBean myjobBean) {
        if (null == myjobBean) {
            return;
        }
        tvTask.setText(myjobBean.getJobContent());
        tvTitle.setText(myjobBean.getJobTitle());
        tvTime.setText("发布时间：" + myjobBean.getCreateTime());
        tvCourse.setText(myjobBean.getCurriculumTitle());
        if (myjobBean.getReplyState() > 0) {
            tvStatus.setText("已完成");
            tvStatus.setTextColor(Color.parseColor("#f87d28"));
            imCommittask.setVisibility(View.GONE);
            tvTasktext.setText(myjobBean.getReplyContent());
            if (myjobBean.getList().size() > 0) {
                int i = 1;
                for (FeedbackPictureBean feedbackPictureBean : myjobBean.getList()) {
                    if (i == 1) {
                        Glide.with(this).load(feedbackPictureBean.getAccessoryUrl()).
                                into(im1);
                        i++;
                    }
                    if (i == 2) {
                        Glide.with(this).load(feedbackPictureBean.getAccessoryUrl()).
                                into(im2);
                        i++;
                    }
                    if (i == 3) {
                        Glide.with(this).load(feedbackPictureBean.getAccessoryUrl()).
                                into(im3);
                    }
                }
                im1.setVisibility(View.VISIBLE);
                im2.setVisibility(View.VISIBLE);
                im3.setVisibility(View.VISIBLE);
                tvTasktext.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myCoursePresenter.cancelmessage();
    }
}
