package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends AppCompatActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.bt_getcode)
    Button btGetcode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.tv_uppassword)
    ShapeCornerBgView tvUppassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        ButterKnife.bind(this);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.et_phone, R.id.et_code, R.id.bt_getcode, R.id.et_password,R.id.appbar, R.id.tv_uppassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_phone:
                break;
            case R.id.et_code:
                break;
            case R.id.bt_getcode:
                break;
            case R.id.et_password:
                break;
            case R.id.appbar:
                break;
            case R.id.tv_uppassword:
                break;
        }
    }

}
