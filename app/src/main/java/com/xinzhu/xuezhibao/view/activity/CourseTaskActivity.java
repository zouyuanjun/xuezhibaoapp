package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.bumptech.glide.Glide;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.MyjobBean;
import com.xinzhu.xuezhibao.presenter.MyCoursePresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.MyJobDetailInterpace;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.DataKeeper;
import com.zou.fastlibrary.utils.DownloadUtil;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.TimeUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.tv_download)
    TextView tvDownload;
    @BindView(R.id.img_download_ioc)
    ImageView imgDownloadIoc;
    @BindView(R.id.llfujian)
    LinearLayout llfujian;
    boolean isdownload = false;
    String fileurl;
    File myfile;
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

    }

    @Override
    public void getjobbyid(MyjobBean myjobBean) {
        if (null == myjobBean) {
            return;
        }
        tvTask.setText(myjobBean.getJobContent());
        tvTitle.setText(myjobBean.getJobTitle());
        tvTime.setText("发布时间：" + TimeUtil.getWholeTime2(myjobBean.getCreateTime()));
        tvCourse.setText(myjobBean.getCurriculumTitle());
        if (myjobBean.getState() > 2) {
            tvStatus.setText("已完成");
            tvStatus.setTextColor(Color.parseColor("#f87d28"));
            imCommittask.setVisibility(View.GONE);
            tvTasktext.setText(myjobBean.getReplyContent());
            if (null != myjobBean.getAccessoryList() && myjobBean.getAccessoryList().size() > 0) {

                for (int i = 0; i < myjobBean.getAccessoryList().size(); i++) {
                    if (i == 0) {
                        Glide.with(this).load(myjobBean.getAccessoryList().get(i).getAccessoryUrl()).
                                into(im1);
                        im1.setVisibility(View.VISIBLE);
                    }
                    if (i == 1) {
                        Glide.with(this).load(myjobBean.getAccessoryList().get(i).getAccessoryUrl()).
                                into(im2);
                        im2.setVisibility(View.VISIBLE);
                    }
                    if (i == 2) {
                        Glide.with(this).load(myjobBean.getAccessoryList().get(i).getAccessoryUrl()).
                                into(im3);
                        im3.setVisibility(View.VISIBLE);
                    }
                }
                tvTasktext.setVisibility(View.VISIBLE);
            }


        }
        if (null != myjobBean.getJobList() && myjobBean.getJobList().size() > 0) {
            llfujian.setVisibility(View.VISIBLE);
            fileurl = myjobBean.getJobList().get(0).getAccessoryUrl();
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

    @OnClick({R.id.llfujian, R.id.im_committask})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llfujian:
                if (isdownload) {
                    DownloadUtil.openFile(CourseTaskActivity.this, myfile);
                } else {
                    int suffixindex = fileurl.lastIndexOf(".");
                    String suffix = fileurl.substring(suffixindex);
                    String path = DataKeeper.getDiskCachePath(this) + "/download/";
                    DownloadUtil.get().download(fileurl, path, jobid + suffix, new DownloadUtil.OnDownloadListener() {
                        @Override
                        public void onDownloadSuccess(File file) {
                            myfile=file;
                            isdownload = true;
                            imgDownloadIoc.setBackgroundResource(R.drawable.task_btn_download_sel);
                        }

                        @Override
                        public void onDownloading(int progress) {
                            Log.d("下载进度" + progress);
                        }

                        @Override
                        public void onDownloadFailed(Exception e) {
                        }
                    });
                }
                break;
            case R.id.im_committask:
                Intent intent = new Intent(CourseTaskActivity.this, EditTaskActivity.class);
                intent.putExtra(Constants.INTENT_ID, jobid);
                startActivity(intent);
                break;
        }
    }
}
