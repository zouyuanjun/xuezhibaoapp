package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.presenter.WXSign;
import com.xinzhu.xuezhibao.view.interfaces.WXSignInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WXSignActivity extends BaseActivity implements WXSignInterface {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.bt_getcode)
    Button btGetcode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_bind)
    Button btBind;
    @BindView(R.id.tv_toolbar_xueyuan)
    TextView tvToolbarXueyuan;
    @BindView(R.id.toolbar_bindphone)
    Toolbar toolbarBindphone;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    CountDownTimer timer;
    boolean cansend=true;
    String token;
    String openid;
    Context context;
    WXSign wxSign;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin_signin);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        openid = intent.getStringExtra("openid");
        wxSign=new WXSign(this);
        wxSign.getwxinfo(token,openid);
        timer = new CountDownTimer(60 * 1000, 1000) {
            /**
             * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
             * @param millisUntilFinished
             */
            @Override
            public void onTick(long millisUntilFinished) {
                btGetcode.setText("重新发送（" + millisUntilFinished/1000 + ")秒");
            }
            /**
             * 倒计时完成时被调用
             */
            @Override
            public void onFinish() {
                btGetcode.setText("重新发送");
                cansend = true;
            }
        };
    }

    @OnClick({R.id.et_phone, R.id.bt_getcode, R.id.et_password, R.id.bt_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_phone:
                break;
            case R.id.bt_getcode:
                String codephone=etPhone.getText().toString();
                if (StringUtil.isEmpty(codephone)||codephone.length()!=11){
                    BToast.error(context).target(etPhone).text("手机号错误").animate(true).show();
                    break;
                }
                if (cansend){
                    wxSign.getcode(codephone);
                    cansend=false;
                    timer.start();
                }
                break;
            case R.id.et_password:
                break;
            case R.id.bt_bind:
                String phone=etPhone.getText().toString();
                String password=etPassword.getText().toString();
                String code=etCode.getText().toString();
                if (StringUtil.isEmpty(phone)||phone.length()!=11){
                    BToast.error(context).target(etPhone).text("手机号错误").animate(true).show();
                    break;
                }
                if (StringUtil.isEmpty(password)){
                    BToast.error(context).target(etPassword).text("密码未填写").animate(true).show();
                    break;
                }
                if (StringUtil.isEmpty(code)){
                    BToast.error(context).target(etCode).animate(true).text("验证码未填写").show();
                    break;
                }
                break;
        }
    }


    @Override
    public void successful() {

    }

    @Override
    public void codeerr() {

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
