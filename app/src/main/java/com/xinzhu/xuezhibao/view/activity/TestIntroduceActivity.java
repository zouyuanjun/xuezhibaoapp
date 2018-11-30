package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xinzhu.xuezhibao.R;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestIntroduceActivity extends BaseActivity {

    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testintroduce);
        ButterKnife.bind(this);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @OnClick(R.id.begintest)
    public void onViewClicked() {
        startActivity(new Intent(this, TestBeforeActivity.class));
    }
}
