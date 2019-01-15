package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.utils.Constants;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 课程反馈页面
 */
public class MyCourseFeedBackActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.ed_feedback)
    EditText edFeedback;
    Context context;
    String curriculumId;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            Log.d(result);
            int code = -999;
            try {
                code = JsonUtils.getIntValue(result, "Code");
            } catch (Exception e) {
            }
            if (code != 100) {
                BToast.error(context).text("发送评论失败").show();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursefeedback);
        context = this;
        ButterKnife.bind(this);
        curriculumId = getIntent().getStringExtra(Constants.INTENT_ID);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        appbar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = edFeedback.getText().toString();
                if (StringUtil.isEmpty(content, true)) {
                    BToast.error(context).text("请输入内容再提交").show();
                } else if (StringUtil.isEmpty(curriculumId, true)) {
                    BToast.error(context).text("异常！没有评论对象ID").show();
                } else {
                    String data = JsonUtils.keyValueToString2("token", Constants.TOKEN, "content", content);
                    data = JsonUtils.addKeyValue(data, "curriculumId", curriculumId);
                    Network.getnetwork().postJson(data, Constants.URL + "/app/add-curriculum-feedback", handler, 1);
                    finish();
                }
            }
        });
    }
}
