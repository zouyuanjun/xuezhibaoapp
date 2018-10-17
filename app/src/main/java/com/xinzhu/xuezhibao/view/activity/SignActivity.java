package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.SignBean;
import com.xinzhu.xuezhibao.presenter.SignPresenter;
import com.xinzhu.xuezhibao.view.interfaces.SignInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户注册页面
 */
public class SignActivity extends BaseActivity implements SignInterface {

    String phone;
    String password;
    String code;

    SignPresenter signPresenter;
    Context context;
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.bt_getcode)
    Button btGetcode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.tv_signup)
    ShapeCornerBgView tvSignup;
    @BindView(R.id.radioButton)
    RadioButton radioButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        signPresenter = new SignPresenter(this);
        context = this;
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({ R.id.et_phone, R.id.et_password, R.id.et_code, R.id.bt_getcode, R.id.tv_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_phone:
                break;
            case R.id.et_password:
                break;
            case R.id.et_code:
                break;
            case R.id.bt_getcode:
                phone = etPhone.getText().toString();
                if (StringUtil.isEmpty(phone)) {
                    BToast.error(view.getContext()).text("请填写手机号").show();
                } else {

                }
                break;
            case R.id.tv_signup:
                phone = etPhone.getText().toString();
                code = etCode.getText().toString();
                password = etPassword.getText().toString();
                if (StringUtil.isEmpty(phone) || StringUtil.isEmpty(code) || StringUtil.isEmpty(password)) {
                    BToast.error(view.getContext()).text("请填写完整信息再注册").show();
                } else {
                    signPresenter.sign(new SignBean(phone, password, code));
                }
                break;
        }
    }

    @Override
    public void signsuccessful() {
        BToast.info(context).text("注册成功").show();
    }
    @Override
    public void codeerr() {
        BToast.info(context).target(etCode).text("验证码错误").show();
    }
    @Override
    public void isexist() {
        BToast.info(context).target(etPhone).text("用户已存在").show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void networktimeout() {
        super.networktimeout();
    }
    @Override
    public void networkerr() {
        super.networkerr();
    }
    @Override
    public void servererr() {
        super.servererr();
    }

}
