package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.SignBean;
import com.xinzhu.xuezhibao.immodule.TextWatcherAdapter;
import com.xinzhu.xuezhibao.presenter.SignPresenter;
import com.xinzhu.xuezhibao.view.interfaces.SignInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.CreatPopwindows;
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
    CheckBox radioButton;
    boolean isconsent = false; //是否同意用户协议
    boolean cansend = true;  //能否发送验证码
    boolean passwordisture = false; //验证码是否一致
    CountDownTimer timer;
    @BindView(R.id.im_codeistrue)
    ImageView imCodeistrue;
    @BindView(R.id.im_passwordtrue)
    ImageView imPasswordtrue;
    boolean cansignin = true;

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
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isconsent = b;
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
        etPassword.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                password = etConfirmPassword.getText().toString();
                if (s.toString().equals(password)) {
                    passwordisture = true;
                    imPasswordtrue.setVisibility(View.VISIBLE);
                    etConfirmPassword.setTextColor(Color.parseColor("#900000"));

                    tvSignup.setBgColor(Color.parseColor("#f87d26"));
                } else {
                    passwordisture = false;
                    etConfirmPassword.setTextColor(Color.RED);
                    imPasswordtrue.setVisibility(View.GONE);
                    tvSignup.setBgColor(Color.parseColor("#999999"));
                }
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

                    tvSignup.setBgColor(Color.parseColor("#f87d26"));
                } else {
                    passwordisture = false;
                    etConfirmPassword.setTextColor(Color.RED);
                    imPasswordtrue.setVisibility(View.GONE);
                    tvSignup.setBgColor(Color.parseColor("#999999"));
                }
            }
        });


    }

    @OnClick({R.id.et_phone, R.id.et_password, R.id.et_code, R.id.bt_getcode, R.id.tv_signup,R.id.tv_user_agreement})
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
                    sendcode(phone);
                }
                break;
            case R.id.tv_signup:
                phone = etPhone.getText().toString();
                code = etCode.getText().toString();
                password = etPassword.getText().toString();
                if (StringUtil.isEmpty(phone) || StringUtil.isEmpty(code) || StringUtil.isEmpty(password)) {
                    BToast.error(view.getContext()).text("请填写完整信息再注册").show();
                } else if (!isconsent) {
                    BToast.error(view.getContext()).text("必须同意学之宝用户协议才可以注册哦").show();
                } else if (!passwordisture) {
                    BToast.error(view.getContext()).text("请确认密码是否一样").show();
                } else if (password.length() < 6) {
                    BToast.error(view.getContext()).text("密码必须大于6位").show();
                } else if (cansignin) {
                    cansignin = false;
                    signPresenter.sign(new SignBean(phone, password, code));
                }
                break;
            case R.id.tv_user_agreement:
                signPresenter.getUserAgreement();
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
                signPresenter.sendcode(phone, 1);
            }
        } else {
            BToast.error(SignActivity.this).text("请填写正确的11位手机号").show();

        }
    }

    @Override
    public void signsuccessful() {
        goToActivity(SignActivity.this, EditAllActivity.class);
        finish();
        BToast.success(context).text("注册成功").show();
    }

    @Override
    public void codeiserr() {
    }

    @Override
    public void isexist() {
        BToast.error(context).text("用户已存在,请直接登陆").show();
    }

    @Override
    public void codeistrue() {
    }

    @Override
    public void signinfail(int code, String tip) {
        cansignin = true;
        BToast.info(context).target(etCode).text("注册失败，错误码" + code + tip).show();
    }

    @Override
    public void getuseragment(String s) {
        Log.d("sdsdd",s);
        final PopupWindow popupWindow=CreatPopwindows.creatWWpopwindows(SignActivity.this,R.layout.pop_agreementcontent);
        View view=popupWindow.getContentView();
        TextView textView=view.findViewById(R.id.tv_agreementContent);
        textView.setText(Html.fromHtml(s));
        ShapeCornerBgView shapeCornerBgView=view.findViewById(R.id.scb_agreementContent);
        shapeCornerBgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(etCode,Gravity.CENTER,0,0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        signPresenter.cancelmessage();
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

    @OnClick(R.id.textView6)
    public void onViewClicked() {
        finish();
    }

}
