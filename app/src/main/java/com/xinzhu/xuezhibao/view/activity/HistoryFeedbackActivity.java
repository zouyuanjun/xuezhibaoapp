package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.xinzhu.xuezhibao.R;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryFeedbackActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.im_1)
    ImageView im1;
    @BindView(R.id.im_2)
    ImageView im2;
    @BindView(R.id.im_3)
    ImageView im3;
    @BindView(R.id.linearLayout10)
    LinearLayout linearLayout10;
    @BindView(R.id.im_back1)
    ImageView imBack1;
    @BindView(R.id.im_back2)
    ImageView imBack2;
    @BindView(R.id.im_back3)
    ImageView imBack3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyfeedback);
        ButterKnife.bind(this);
        im1.setVisibility(View.VISIBLE);
        im2.setVisibility(View.VISIBLE);
        im3.setVisibility(View.VISIBLE);
        Glide.with(this).load("http://pic29.nipic.com/20130511/9252150_174018365301_2.jpg").
                into(im1);
        Glide.with(this).load("http://pic19.nipic.com/20120210/7827303_221233267358_2.jpg").
                into(im2);
        Glide.with(this).load("http://pic19.nipic.com/20120210/7827303_221233267358_2.jpg").
                into(im3);
        Glide.with(this).load("http://t2.hddhhn.com/uploads/tu/201610/198/gkowtcsq5sg.jpg").
                into(imBack1);
        Glide.with(this).load("http://pic19.nipic.com/20120210/7827303_221233267358_2.jpg").
                into(imBack2);
        Glide.with(this).load("http://t2.hddhhn.com/uploads/tu/201610/198/amcmsp2nuwp.jpg").
                into(imBack3);
    }
}
