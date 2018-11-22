package com.xinzhu.xuezhibao.view.activity;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.utils.Constants;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.EditTextUtil;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 修改单项个人资料
 */
public class EditUserBasicActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_item)
    TextView tvItem;
    @BindView(R.id.ed_basic)
    EditText edBasic;
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituserbasic);
        ButterKnife.bind(this);
        activity=this;
        String itemstring=getIntent().getStringExtra(Constants.INTENT_EDITITEM);
        tvItem.setText(itemstring);
        appbar.setMidText("修改"+itemstring);
        if (itemstring.equals("学生年龄")){
            edBasic.setInputType(InputType.TYPE_CLASS_NUMBER);
        }


        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        appbar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data=edBasic.getText().toString();
                if (StringUtil.isEmpty(data,true)){
                    BToast.custom(activity).text("输入的信息是空的哦！").show();
                }else {
                    Intent intent=new Intent();
                    intent.putExtra(Constants.INTENT_EDITITEM,data);
                    activity.setResult(1,intent);
                    finish();
                }
            }
        });
    }
}
