package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.GrideViewAdapter;
import com.xinzhu.xuezhibao.bean.MyjobBean;
import com.xinzhu.xuezhibao.presenter.MyCoursePresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.MyJobDetailInterpace;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.TimeUtil;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xinzhu.xuezhibao.MyApplication.getContext;

/**
 * 课程作业
 */
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
    ShapeCornerBgView tvStatus;
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
    MyjobBean myjobBean;
    @BindView(R.id.gd_img)
    RecyclerView gdImg;
    GrideViewAdapter adapter;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout4;
    @BindView(R.id.sdv_photo)
    SimpleDraweeView sdvPhoto;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    @BindView(R.id.tv_creattime)
    TextView tvCreattime;
    @BindView(R.id.tv_comment_detils)
    TextView tvCommentDetils;
    @BindView(R.id.tv_source)
    TextView tvSource;
    @BindView(R.id.tv_teacher1)
    TextView tvTeacher1;
    @BindView(R.id.rv_repimglist)
    RecyclerView rvRepimglist;

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
        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), 3);
        gdImg.setLayoutManager(layoutManage);
        gdImg.setNestedScrollingEnabled(false);

        GridLayoutManager layoutManage2 = new GridLayoutManager(getContext(), 3);
        rvRepimglist.setLayoutManager(layoutManage2);
        rvRepimglist.setNestedScrollingEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myCoursePresenter.getjobbyid(jobid);
    }

    @OnClick(R.id.im_committask)
    public void onViewClicked() {

    }

    @Override
    public void getjobbyid(final MyjobBean myjobBean) {
        if (null == myjobBean) {
            return;
        }
        this.myjobBean = myjobBean;
        tvTask.setText(myjobBean.getJobContent());
        tvTitle.setText(myjobBean.getJobTitle());
        tvTime.setText("发布时间：" + TimeUtil.getWholeTime3(myjobBean.getCreateTime()));
        tvCourse.setText(myjobBean.getCurriculumTitle());
        if (myjobBean.getState() == 3) {
            tvStatus.setText("待审批");
            tvStatus.setTextColor(Color.parseColor("#f87d28"));
            tvStatus.setBorderColor(Color.parseColor("#f87d28"));
            imCommittask.setVisibility(View.GONE);
            constraintLayout4.setVisibility(View.GONE);
            tvTasktext.setText(myjobBean.getReplyContent());
            tvTasktext.setVisibility(View.VISIBLE);
        } else if (myjobBean.getState() == 4) {
            tvStatus.setText("已完成");
            tvStatus.setTextColor(Color.parseColor("#12cd8e"));
            tvStatus.setBorderColor(Color.parseColor("#12cd8e"));
            imCommittask.setVisibility(View.GONE);
            constraintLayout4.setVisibility(View.VISIBLE);
            tvTeacher1.setVisibility(View.VISIBLE);
            tvTasktext.setText(myjobBean.getReplyContent());
            tvTasktext.setVisibility(View.VISIBLE);
            tvCommentDetils.setText(myjobBean.getTeacherEvaluation());
            tvUserName.setText(myjobBean.getTeacherName());
            sdvPhoto.setImageURI(myjobBean.getTeacherImage());
            tvSource.setText(myjobBean.getTeacherScore());
            tvCreattime.setText(TimeUtil.getWholeTime3(myjobBean.getEditTime()));
        }
        if (myjobBean.getState() > 2) {
            if (null != myjobBean.getAccessoryList() && myjobBean.getAccessoryList().size() > 0) {
                GrideViewAdapter grideViewAdapter = new GrideViewAdapter(myjobBean.getAccessoryList());
                rvRepimglist.setAdapter(grideViewAdapter);
                grideViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent(CourseTaskActivity.this, PhotoViewActivity.class);
                        intent.putExtra(Constants.INTENT_ID, (Serializable) myjobBean.getAccessoryList());
                        intent.putExtra(Constants.INTENT_ID2, position);
                        startActivity(intent);
                    }
                });
                tvTasktext.setVisibility(View.VISIBLE);
            }
        }
        if (null != myjobBean.getJobList() && myjobBean.getJobList().size() > 0) {

            adapter = new GrideViewAdapter(myjobBean.getJobList());
            gdImg.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(CourseTaskActivity.this, PhotoViewActivity.class);
                    intent.putExtra(Constants.INTENT_ID, (Serializable) myjobBean.getJobList());
                    intent.putExtra(Constants.INTENT_ID2, position);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void getdatafail(String tip) {
        BToast.error(this).text("数据异常：" + tip).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myCoursePresenter.cancelmessage();
    }

    @OnClick({R.id.im_committask})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_committask:
                Intent intent = new Intent(CourseTaskActivity.this, EditTaskActivity.class);
                intent.putExtra(Constants.INTENT_ID, jobid);
                startActivity(intent);
                break;
        }
    }
}
