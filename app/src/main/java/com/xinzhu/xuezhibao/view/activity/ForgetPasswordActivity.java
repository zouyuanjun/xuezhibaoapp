package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends AppCompatActivity {
    @BindView(R.id.tv_toolbar_xueyuan)
    TextView tvToolbarXueyuan;
    @BindView(R.id.toolbar_findpassword)
    Toolbar toolbarFindpassword;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.bt_getcode)
    Button btGetcode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_updatpassword)
    Button btUpdatpassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.et_phone, R.id.et_code, R.id.bt_getcode, R.id.et_password, R.id.bt_updatpassword})
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
            case R.id.bt_updatpassword:
                break;
        }
    }
}
