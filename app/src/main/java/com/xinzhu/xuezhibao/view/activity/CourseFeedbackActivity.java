package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.CourseFeedbackBean;
import com.xinzhu.xuezhibao.bean.CourseFeedbackRpBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseFeedbackActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.simpleDraweeView3)
    SimpleDraweeView simpleDraweeView3;
    @BindView(R.id.tv_feedbackcontent)
    TextView tvFeedbackcontent;
    String feedbackid;
    CourseFeedbackBean courseFeedbackBean;
    List<CourseFeedbackRpBean> list;
    @BindView(R.id.sp_1)
    SimpleDraweeView sp1;
    @BindView(R.id.tv_rp1)
    TextView tvRp1;
    @BindView(R.id.ll_r1)
    LinearLayout llR1;
    @BindView(R.id.sp_2)
    SimpleDraweeView sp2;
    @BindView(R.id.tv_rp2)
    TextView tvRp2;
    @BindView(R.id.ll_r2)
    LinearLayout llR2;
    @BindView(R.id.sp_3)
    SimpleDraweeView sp3;
    @BindView(R.id.tv_rp3)
    TextView tvRp3;
    @BindView(R.id.ll_r3)
    LinearLayout llR3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_feedback);
        ButterKnife.bind(this);
        courseFeedbackBean = (CourseFeedbackBean) getIntent().getSerializableExtra(Constants.INTENT_ID);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        simpleDraweeView3.setImageURI(Constants.userBasicInfo.getImage());
        tvFeedbackcontent.setText(courseFeedbackBean.getContent());
        if (null!=courseFeedbackBean.getListReply()){
            for (int i=0;i<courseFeedbackBean.getListReply().size();i++){
                CourseFeedbackRpBean courseFeedbackRpBean=courseFeedbackBean.getListReply().get(i);
                if (i==0){
                    llR1.setVisibility(View.VISIBLE);
                    sp1.setImageURI(courseFeedbackRpBean.getHeadPortraitUrl());
                    tvRp1.setText(courseFeedbackRpBean.getReplyContent());
                }else if (i==1){
                    llR2.setVisibility(View.VISIBLE);
                    sp2.setImageURI(courseFeedbackRpBean.getHeadPortraitUrl());
                    tvRp2.setText(courseFeedbackRpBean.getReplyContent());
                }else if (i==3){
                    llR3.setVisibility(View.VISIBLE);
                    sp3.setImageURI(courseFeedbackRpBean.getHeadPortraitUrl());
                    tvRp3.setText(courseFeedbackRpBean.getReplyContent());
                }
            }
        }
    }
}
