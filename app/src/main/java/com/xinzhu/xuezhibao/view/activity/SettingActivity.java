package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.xinzhu.xuezhibao.R;
import com.zou.fastlibrary.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.st_notifition)
    Switch stNotifition;
    @BindView(R.id.st_netdownload)
    Switch stNetdownload;
    @BindView(R.id.im_couple_back)
    ImageView imCoupleBack;
    @BindView(R.id.im_about_us)
    ImageView imAboutUs;
    @BindView(R.id.im_clean_cache)
    ImageView imCleanCache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.st_notifition, R.id.st_netdownload, R.id.im_couple_back, R.id.im_about_us, R.id.im_clean_cache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.st_notifition:
                break;
            case R.id.st_netdownload:
                break;
            case R.id.im_couple_back:
                startActivity(new Intent(this,FeedbackActivity.class));
                break;
            case R.id.im_about_us:
                startActivity(new Intent(this,AboutUsActivity.class));
                break;
            case R.id.im_clean_cache:
                break;
        }
    }
}
