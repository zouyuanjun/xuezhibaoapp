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
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseTaskActivity extends BaseActivity {
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
    @BindView(R.id.linearLayout16)
    LinearLayout linearLayout16;
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
    int status=1;
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
        tvTasktext.setText("奥卡拉圣诞节flask地方啊手动阀手动阀手动阀撒旦fasdfasdfsdfasdfasasdfasdfasdfasdfawerqwerfwasfaewrfwefasfwerfawerwerffwerw" +
                "阿三发射点发阀手动阀");
        if (status==0){
            tvStatus.setText("已完成");
            tvStatus.setTextColor(Color.GREEN);
            im1.setVisibility(View.VISIBLE);
            im2.setVisibility(View.VISIBLE);
            im3.setVisibility(View.VISIBLE);
            tvTasktext.setVisibility(View.VISIBLE);
            Glide.with(this).load("http://pic29.nipic.com/20130511/9252150_174018365301_2.jpg").
                    into(im1);
            Glide.with(this).load("http://pic19.nipic.com/20120210/7827303_221233267358_2.jpg").
                    into(im2);
            Glide.with(this).load("http://pic19.nipic.com/20120210/7827303_221233267358_2.jpg").
                    into(im3);
        }else {
            imCommittask.setVisibility(View.VISIBLE);
        }




    }

    @OnClick(R.id.im_committask)
    public void onViewClicked() {
        startActivity(new Intent(CourseTaskActivity.this,EditTaskActivity.class));
    }
}
