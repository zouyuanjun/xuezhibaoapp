package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.xinzhu.xuezhibao.R;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestBeforeActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_next)
    ShapeCornerBgView tvNext;
    @BindView(R.id.linearLayout26)
    LinearLayout linearLayout26;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testbefore);
        ButterKnife.bind(this);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        goToActivity(this,TestActivity.class);
        finish();
    }
}
