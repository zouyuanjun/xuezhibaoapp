package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xinzhu.xuezhibao.R;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendCommentActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.ed_comment)
    EditText edComment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendcomment);
        ButterKnife.bind(this);
        appbar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment=edComment.getText().toString();
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("result", comment);
                //设置返回数据
                SendCommentActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                SendCommentActivity.this.finish();
            }
        });
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
