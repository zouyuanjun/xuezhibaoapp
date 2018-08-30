package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.SignBean;
import com.xinzhu.xuezhibao.presenter.SignPresenter;
import com.xinzhu.xuezhibao.view.interfaces.SignInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends BaseActivity implements SignInterface {
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.imb_back)
    ImageButton imbBack;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.getcode)
    Button getcode;
    @BindView(R.id.bt_sign)
    Button btSign;
    String phone ;
    String password;
    String code;
    String name;

    SignPresenter signPresenter;
    Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        signPresenter=new SignPresenter(this);
        context=this;
    }

    @OnClick({R.id.imb_back, R.id.ed_phone, R.id.ed_password, R.id.ed_code, R.id.ed_name, R.id.getcode, R.id.bt_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imb_back:
                finish();
                break;
            case R.id.ed_phone:
                break;
            case R.id.ed_password:
                break;
            case R.id.ed_code:

                break;
            case R.id.ed_name:
                break;
            case R.id.getcode:
                phone=edPhone.getText().toString();
                if (StringUtil.isEmpty(phone)){
                    BToast.error(view.getContext()).text("请填写手机号").show();
                }else {

                }
                break;
            case R.id.bt_sign:
                phone=edPhone.getText().toString();
                name=edName.getText().toString();
                code=edCode.getText().toString();
                password=edPassword.getText().toString();
                if (StringUtil.isEmpty(phone)||StringUtil.isEmpty(name)||StringUtil.isEmpty(code)||StringUtil.isEmpty(password)){
                    BToast.error(view.getContext()).text("请填写完整信息再注册").show();
                }else {
                    signPresenter.sign(new SignBean(phone,password,code,name));
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
        BToast.info(context).target(edCode).text("验证码错误").show();
    }

    @Override
    public void isexist() {
        BToast.info(context).target(edPhone).text("用户已存在").show();
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
