package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.immodule.view.ConversationListActivity;
import com.xinzhu.xuezhibao.presenter.LoginPresenter;
import com.xinzhu.xuezhibao.view.interfaces.LoginInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginInterface{


    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.bt_sign)
    Button btSign;
    @BindView(R.id.imb_wxsign)
    ImageButton imbWxsign;
    @BindView(R.id.bt_forgetpassword)
    Button btForgetpassword;
    Context context;
    LoginPresenter login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context=this;
        login=new LoginPresenter(this);
    }

    @OnClick({ R.id.bt_login,R.id.bt_sign, R.id.imb_wxsign, R.id.bt_forgetpassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                Log.d("登陆");
                String phone=edPhone.getText().toString();
                String password=edPassword.getText().toString();
                if (StringUtil.isEmpty(phone)){
                    BToast.error(context).target(edPhone).animate(true).text("请填写正确的手机号").show();
                    break;
                }
                if (StringUtil.isEmpty(password)){
                    BToast.error(context).target(edPassword).animate(true).text("请输入密码").show();
                    break;
                }
                if (password.length()<6){
                    BToast.error(context).target(edPassword).animate(true).text("密码长度必须大于6位").show();
                    break;
                }
                login.phonelogin(phone,password);
                break;
            case R.id.bt_sign:
                startActivity(new Intent(context,SignActivity.class));
                break;
            case R.id.imb_wxsign:
                break;
            case R.id.bt_forgetpassword:
                startActivity(new Intent(context,ForgetPasswordActivity.class));
                break;
        }
    }

    boolean isexit=false;
    @Override
    public void onBackPressed() {
        if (isexit){
           finishAllActivity();
        }else {
            isexit=true;
          BToast.info(context).text("再按一次退出").show();
        }

    }

    @Override
    public void loginsuccessful() {
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void loginfail() {

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

