package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.utils.Constants;
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

    @OnClick({R.id.csl_1, R.id.csl_2, R.id.csl_3, R.id.csl_4, R.id.csl_5, R.id.csl_6})
    public void onViewClicked(View view) {
        Intent intent=new Intent(TestIntroduceActivity.this,TestActivity.class);
        switch (view.getId()) {
            case R.id.csl_1:
                intent.putExtra(Constants.INTENT_ID,0);
                break;
            case R.id.csl_2:
                intent.putExtra(Constants.INTENT_ID,1);
                break;
            case R.id.csl_3:
                intent.putExtra(Constants.INTENT_ID,2);
                break;
            case R.id.csl_4:
                intent.putExtra(Constants.INTENT_ID,3);
                break;
            case R.id.csl_5:
                intent.putExtra(Constants.INTENT_ID,4);
                break;
            case R.id.csl_6:
                intent.putExtra(Constants.INTENT_ID,5);
                break;
        }
        startActivity(intent);
    }
}
