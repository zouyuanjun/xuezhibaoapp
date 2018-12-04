package com.xinzhu.xuezhibao.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.tencent.smtt.sdk.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.bumptech.glide.Glide;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.FeedbackPictureBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;
import com.xinzhu.xuezhibao.utils.WebViewUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 历史反馈
 */
public class HistoryFeedbackActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.im_1)
    ImageView im1;
    @BindView(R.id.im_2)
    ImageView im2;
    @BindView(R.id.im_3)
    ImageView im3;
    @BindView(R.id.linearLayout10)
    LinearLayout linearLayout10;
    Activity context;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            Log.d(result);
            int code = JsonUtils.getIntValue(result, "Code");
            if (code == 100) {
                String data = JsonUtils.getStringValue(result, "Data");
                String opinionContent = JsonUtils.getStringValue(data, "opinionContent");
                String opinionReply = JsonUtils.getStringValue(data, "opinionReply");
                String accessoryList = JsonUtils.getStringValue(data, "accessoryList");
                List<FeedbackPictureBean> list = JSON.parseArray(accessoryList, FeedbackPictureBean.class);
                if (null != list && list.size() > 0) {
                    Log.d("List不为空");
                    for (int i = 0; i < list.size(); i++) {
                        if (i == 0) {
                            im1.setVisibility(View.VISIBLE);
                            Glide.with(context).load(list.get(0).getAccessoryUrl()).
                                    into(im1);
                        }
                        if (i == 1) {
                            im2.setVisibility(View.VISIBLE);
                            Glide.with(context).load(list.get(1).getAccessoryUrl()).
                                    into(im2);
                        }
                        if (i == 2) {
                            im3.setVisibility(View.VISIBLE);
                            Glide.with(context).load(list.get(2).getAccessoryUrl()).
                                    into(im3);
                        }
                    }
                }
                if (!opinionReply.isEmpty()) {
                    llRp.setVisibility(View.VISIBLE);
                    wbFeedback.loadDataWithBaseURL(null, opinionReply, "text/html", "UTF-8", null);
                }
                tvData.setText(opinionContent);
            }
        }
    };
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.wb_feedback)
    WebView wbFeedback;
    @BindView(R.id.ll_rp)
    LinearLayout llRp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyfeedback);
        ButterKnife.bind(this);
        context = this;
        wbFeedback.setWebViewClient(new WebViewUtil.MyWebViewClient(this,wbFeedback));
        if (Constants.TOKEN.isEmpty()) {
            BToast.error(this).text("您尚未登陆，没有反馈哦").show();
        } else {
            String data = JsonUtils.keyValueToString("token", Constants.TOKEN);
            Network.getnetwork().postJson(data, Constants.URL + "/app/find-newest-opinion", handler, 1);
        }
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != handler) {
            handler.removeMessages(1);
            handler = null;
        }

    }


}
