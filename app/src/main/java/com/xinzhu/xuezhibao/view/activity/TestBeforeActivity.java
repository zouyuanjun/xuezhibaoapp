package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.cjt2325.cameralibrary.util.LogUtil;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.utils.Constants;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.StringUtil;
import com.zou.fastlibrary.utils.TimeUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestBeforeActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_next)
    ShapeCornerBgView tvNext;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView31)
    TextView textView31;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.ed_age)
    EditText edAge;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.rb_sexman)
    RadioButton rbSexman;
    @BindView(R.id.rb_sexf)
    RadioButton rbSexf;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    int sex = 0;
    String nickname;
    String age;
    String phone;
    int classindex;
    String dictionaryId;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String string = (String) msg.obj;
            Log.d(string);
            int code = JsonUtils.getIntValue(string, "Code");
            if (code == 100) {
                String registerId = JsonUtils.getStringValue(string, "Data");
                Intent intent = new Intent(TestBeforeActivity.this, TestActivity.class);
                intent.putExtra(Constants.INTENT_ID, classindex);
                intent.putExtra(Constants.INTENT_ID2, registerId);
                startActivity(intent);
                finish();
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testbefore);
        ButterKnife.bind(this);
        classindex = getIntent().getIntExtra(Constants.INTENT_ID, 0);
        dictionaryId = getIntent().getStringExtra(Constants.INTENT_ID2);
        textView5.setText(getIntent().getStringExtra(Constants.INTENT_ID3));
        textView31.setText(getIntent().getStringExtra(Constants.INTENT_ID4));
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        editText.setText(Constants.userBasicInfo.getNickName());
        String data;
        try {
            data=TimeUtil.getWholeTime(Long.parseLong(Constants.userBasicInfo.getStudentAge()));
        }catch (NumberFormatException e){
            data="1999";
        }

        int age1=Integer.parseInt(data.substring(0,4));
        edAge.setText(2019-age1+"");
        edPhone.setText(Constants.userBasicInfo.getAccount());
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_sexf: {
                        sex = 2;
                        break;
                    }
                    case R.id.rb_sexman: {
                        sex = 1;
                        break;
                    }
                }

            }
        });
    }

    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        nickname = editText.getText().toString();
        age = edAge.getText().toString();
        phone = edPhone.getText().toString();
        if (StringUtil.isEmpty(nickname)) {
            BToast.error(this).text("昵称必填").show();
            return;
        }
        if (StringUtil.isEmpty(age)) {
            BToast.error(this).text("年龄必填").show();
            return;
        }
        if (StringUtil.isEmpty(phone)) {
            BToast.error(this).text("电话号码必填").show();
            return;
        }
        if (isPhone(phone)) {

        } else {
           return;
        }

        if (sex == 0) {
            BToast.error(this).text("性别必选").show();
            return;
        }
        String data = JsonUtils.keyValueToString2("nickname", nickname, "age", age);
        data = JsonUtils.addKeyValue(data, "sex", sex);
        data = JsonUtils.addKeyValue(data, "phone", phone);
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        data = JsonUtils.addKeyValue(data, "dictionaryId", dictionaryId);
        Network.getnetwork().postJson(data, Constants.URL + "/app/add-appraisal-register", handler, 1);
    }

    public boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            BToast.info(TestBeforeActivity.this).text("手机号应为11位数").show();
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                BToast.info(TestBeforeActivity.this).text("请填入正确的手机号").show();
            }
            return isMatch;
        }
    }

}
