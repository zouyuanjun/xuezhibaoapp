package com.xinzhu.xuezhibao.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
                if (null!=list&&list.size() > 0) {
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
        WebSettings webSettings = wbFeedback.getSettings();//获取webview设置属性
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
        webSettings.setJavaScriptEnabled(true);//支持js
        wbFeedback.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wbFeedback.setWebViewClient(new MyWebViewClient());
        wbFeedback.addJavascriptInterface(this, "App");
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

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            imgReset();
            wbFeedback.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
            super.onPageFinished(view, url);


        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        private void imgReset() {
            wbFeedback.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName('img'); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "var img = objs[i];   " +
                    " img.style.maxWidth = '100%';img.style.height='auto';" +
                    "}" +
                    "})()");
        }
    }

    @JavascriptInterface
    public void resize(final float height) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) wbFeedback.getLayoutParams();


                layoutParams.width = (int) ((getResources().getDisplayMetrics().widthPixels) * 0.7);
                layoutParams.height = (int) (height * getResources().getDisplayMetrics().density) + 50;
                Log.d(layoutParams.width + "高度是" + layoutParams.height + "原始" + height + "级" + getResources().getDisplayMetrics().density);
                wbFeedback.setLayoutParams(layoutParams);
                //Toast.makeText(getActivity(), height + "", Toast.LENGTH_LONG).show();
                //    wbFeedback.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
    }


}
