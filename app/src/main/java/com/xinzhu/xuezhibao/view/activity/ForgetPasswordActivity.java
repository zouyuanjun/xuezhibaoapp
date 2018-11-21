package com.xinzhu.xuezhibao.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.immodule.TextWatcherAdapter;
import com.xinzhu.xuezhibao.presenter.SignPresenter;
import com.xinzhu.xuezhibao.view.interfaces.ForgetPasswordInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity implements ForgetPasswordInterface {
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
    SignPresenter signPresenter;
    String phone;
    String code;
    String password;
    CountDownTimer timer;
    boolean cansend = true;
    boolean passwordisture = false;
    boolean codeisture = false;
    @BindView(R.id.im_passwordtrue)
    ImageView imPasswordtrue;
    @BindView(R.id.im_codeistrue)
    ImageView imCodeistrue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        ButterKnife.bind(this);
        signPresenter = new SignPresenter(this);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        timer = new CountDownTimer(60 * 1000, 1000) {
            /**
             * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
             * @param millisUntilFinished
             */
            @Override
            public void onTick(long millisUntilFinished) {
                btGetcode.setText("重新发送(" + millisUntilFinished / 1000 + ")");
            }

            /**
             * 倒计时完成时被调用
             */
            @Override
            public void onFinish() {
                btGetcode.setText("重新发送");
                btGetcode.setTextColor(Color.parseColor("#f87d26"));
                cansend = true;
            }
        };

        etCode.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (s.length() > 4) {
                    phone=etPhone.getText().toString();
                    signPresenter.iscodeture(phone, s.toString());
                }
            }
        });
        etPassword.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                password = etConfirmPassword.getText().toString();
                if (s.toString().equals(password)) {
                    passwordisture = true;
                    imPasswordtrue.setVisibility(View.VISIBLE);
                    etConfirmPassword.setTextColor(Color.parseColor("#900000"));
                    tvUppassword.setBgColor(Color.parseColor("#f87d26"));
                } else {
                    passwordisture = false;
                    etConfirmPassword.setTextColor(Color.RED);
                    imPasswordtrue.setVisibility(View.GONE);
                    tvUppassword.setBgColor(Color.parseColor("#999999"));
                }
                ;
            }
        });
        etConfirmPassword.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                password = etPassword.getText().toString();
                if (s.toString().equals(password)) {
                    passwordisture = true;
                    imPasswordtrue.setVisibility(View.VISIBLE);
                    etConfirmPassword.setTextColor(Color.parseColor("#900000"));
                    if (codeisture){
                        tvUppassword.setBgColor(Color.parseColor("#f87d26"));
                    }

                } else {
                    passwordisture = false;
                    etConfirmPassword.setTextColor(Color.RED);
                    imPasswordtrue.setVisibility(View.GONE);
                    tvUppassword.setBgColor(Color.parseColor("#999999"));
                }
                ;
            }
        });
    }

    @OnClick({R.id.et_phone, R.id.et_code, R.id.bt_getcode, R.id.et_password, R.id.appbar, R.id.tv_uppassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_phone:
                phone = etPhone.getText().toString();
                break;
            case R.id.et_code:
                code = etCode.getText().toString();
                break;
            case R.id.bt_getcode:
                phone = etPhone.getText().toString();
                if (StringUtil.isEmpty(phone)) {
                    BToast.error(view.getContext()).text("请填写手机号").show();
                } else {
                    sendcode(phone);
                }
                break;
            case R.id.et_password:
                password = etPassword.getText().toString();
                break;
            case R.id.tv_uppassword:
                if (codeisture&&passwordisture){
                    if (password.length()<6){
                        BToast.error(view.getContext()).text("密码长度不能小于6位").show();
                        return;
                    }
                    phone = etPhone.getText().toString();
                    password = etPassword.getText().toString();
                    code = etCode.getText().toString();
                    signPresenter.updatapassword(phone,password,code);
                }
                break;
        }
    }

    //请求验证码
    public void sendcode(String phone) {
        Log.d("5555", phone + phone.length());
        if (phone.length() == 11) {
            if (cansend) {
                timer.start();
                cansend = false;
                btGetcode.setTextColor(Color.parseColor("#999999"));
                signPresenter.sendcode(phone,2);
            }

        } else {
            Toast.makeText(ForgetPasswordActivity.this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.onFinish();

    }

    @Override
    public void codeistrue() {
        if (passwordisture){
            tvUppassword.setBgColor(Color.parseColor("#f87d26"));
        }
        imCodeistrue.setVisibility(View.VISIBLE);
        codeisture=true;
    }

    @Override
    public void codeiserr(int code) {
        tvUppassword.setBgColor(Color.parseColor("#999999"));
        imCodeistrue.setVisibility(View.GONE);
        codeisture=false;
    }

    @Override
    public void successful() {
        finish();
    }

    @Override
    public void fail(int code) {
        BToast.error(ForgetPasswordActivity.this).text("修改失败"+code).show();
    }

    @Override
    public void usernotfind() {
        BToast.error(ForgetPasswordActivity.this).text("该用户不存在").show();
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
