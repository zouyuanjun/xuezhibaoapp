package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.CourseFeedbackBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.Network;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_feedback);
        ButterKnife.bind(this);
        courseFeedbackBean= (CourseFeedbackBean) getIntent().getSerializableExtra(Constants.INTENT_ID);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
 simpleDraweeView3.setImageURI(Constants.userBasicInfo.getImage());
        tvFeedbackcontent.setText(courseFeedbackBean.getContent());
    }
}
