package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTaskDetailActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_tasktitle)
    TextView tvTasktitle;
    @BindView(R.id.tv_taskdetail)
    TextView tvTaskdetail;
    @BindView(R.id.tv_jifen)
    TextView tvJifen;
    @BindView(R.id.tv_taskstatu)
    ShapeCornerBgView tvTaskstatu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytaskdetail);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.tv_taskstatu)
    public void onViewClicked() {

    }
}
