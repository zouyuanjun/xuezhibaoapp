package com.xinzhu.xuezhibao.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bravin.btoast.BToast;
import com.tencent.bugly.beta.Beta;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.immodule.takevideo.CameraActivity;
import com.xinzhu.xuezhibao.immodule.utils.RequestCode;
import com.xinzhu.xuezhibao.immodule.view.ChatActivity;
import com.xinzhu.xuezhibao.immodule.view.ConversationListActivity;
import com.xinzhu.xuezhibao.presenter.LoginPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.LoginInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.CommonUtil;
import com.zou.fastlibrary.utils.EditTextUtil;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.StatusBar;
import com.zou.fastlibrary.utils.StringUtil;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends BaseActivity implements LoginInterface{


    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.bt_login)
    ShapeCornerBgView btLogin;
    @BindView(R.id.bt_sign)
    Button btSign;
    @BindView(R.id.imb_wxsign)
    ImageButton imbWxsign;
    @BindView(R.id.bt_forgetpassword)
    Button btForgetpassword;
    Context context;
    LoginPresenter login;
    String FROMAPP="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBar.setColor(this,0xFFf87d28);
        ButterKnife.bind(this);
        FROMAPP=getIntent().getStringExtra(Constants.FROMAPP);
        context=this;
        String a="sdfs";
       // a.substring(5);
        Beta.checkUpgrade(false,false);
        login=new LoginPresenter(new WeakReference<>(this).get());
    }

    @OnClick({ R.id.bt_login,R.id.bt_sign, R.id.imb_wxsign, R.id.bt_forgetpassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                String phone=edPhone.getText().toString();
                String password=edPassword.getText().toString();
                if (StringUtil.isEmpty(phone)){
                    BToast.error(context).text("请填写正确的手机号").show();
                    break;
                }
                if (StringUtil.isEmpty(password)){
                    BToast.error(context).animate(true).text("请输入密码").show();
                    break;
                }
                if (password.length()<6){
                    BToast.error(context).animate(true).text("密码长度必须大于6位").show();
                    break;
                }
                EditTextUtil.hideKeyboard(context,edPassword);
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
        if (null==Constants.userBasicInfo.getNickName()||Constants.userBasicInfo.getNickName().isEmpty()){
            startActivity(new Intent(this,EditAllActivity.class));
            return;
        }
        if (null==FROMAPP||FROMAPP.isEmpty()){
                startActivity(new Intent(this,MainActivity.class));
        }

    }

    @Override
    public void loginfail(int code) {
        BToast.error(context).text("错误码："+code+",账号或密码错误").show();
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

