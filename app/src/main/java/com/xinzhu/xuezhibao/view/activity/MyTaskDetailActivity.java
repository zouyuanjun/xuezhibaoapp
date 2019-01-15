package com.xinzhu.xuezhibao.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.MyTaskBean;
import com.xinzhu.xuezhibao.presenter.TaskPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.GetTaskInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 任务详情页面
 */
public class MyTaskDetailActivity extends BaseActivity implements GetTaskInterface {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_tasktitle)
    TextView tvTasktitle;
    @BindView(R.id.tv_taskdetail)
    WebView webTaskdetail;
    @BindView(R.id.tv_jifen)
    TextView tvJifen;
    MyTaskBean myTaskBean;
    TaskPresenter taskPresenter;
    @BindView(R.id.tv_completenum)
    TextView tvCompletenum;
    @BindView(R.id.tv_allnum)
    TextView tvAllnum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytaskdetail);
        ButterKnife.bind(this);
        myTaskBean = (MyTaskBean) getIntent().getSerializableExtra(Constants.INTENT_ID);
        tvJifen.setText(myTaskBean.getAwardIntegral() + "");
        tvTasktitle.setText(myTaskBean.getTaskTitle());
        taskPresenter = new TaskPresenter(this);
        tvAllnum.setText("/"+myTaskBean.getTaskNumber());
        tvCompletenum.setText(myTaskBean.getCount());

        if (myTaskBean.getStateType() == 0) {
            myTaskBean.setStateType(100);
        }
        taskPresenter.gettaskdetail(myTaskBean.getTaskId(), myTaskBean.getStateType(), myTaskBean.getMyTaskId());
        webTaskdetail.setWebViewClient(new MyWebViewClient(this, webTaskdetail));
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void gettaskdetails(MyTaskBean myTaskBean) {
        webTaskdetail.loadDataWithBaseURL(null, myTaskBean.getTaskContent(), "text/html", "UTF-8", null);
    }

    @Override
    public void accepttask() {
        BToast.success(this).text("领取成功").show();
        Intent intent = new Intent();
        intent.putExtra(Constants.INTENT_ID, 1);
        setResult(1, intent);
        finish();
    }

    @Override
    public void gettaskfall() {

    }

    public static class MyWebViewClient extends WebViewClient {
        Activity context;
        WebView webView;

        public MyWebViewClient(Activity context, WebView webView) {
            this.context = context;
            this.webView = webView;
            init(false);
        }

        public MyWebViewClient(Activity context, WebView webView, boolean horizontalScrollBarEnabled) {
            this.context = context;
            this.webView = webView;
            init(horizontalScrollBarEnabled);
        }

        private void init(boolean HorizontalScrollBarEnabled) {
            this.webView.addJavascriptInterface(this, "App");
            WebSettings webSettings = this.webView.getSettings();//获取webview设置属性
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
            webSettings.setJavaScriptEnabled(true);//支持js
            this.webView.setHorizontalScrollBarEnabled(false);//水平不显示
            this.webView.setVerticalScrollBarEnabled(false); //垂直不显示
            this.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            imgReset();
            super.onPageFinished(view, url);
            webView.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        private void imgReset() {
            webView.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName('img'); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "var img = objs[i];   " +
                    " img.style.maxWidth = '100%';img.style.height='auto';" +
                    "}" +
                    "})()");
        }

        @JavascriptInterface
        public void resize(final float height) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams layoutParams = webView.getLayoutParams();
                    layoutParams.width = (int) ((context.getResources().getDisplayMetrics().widthPixels * 0.9));
                    layoutParams.height = (int) (height * context.getResources().getDisplayMetrics().density) + 50;
                    Log.d(layoutParams.width + "高度是" + layoutParams.height + "原始" + height + "级" + context.getResources().getDisplayMetrics().density);
                    webView.setLayoutParams(layoutParams);
                    //Toast.makeText(getActivity(), height + "", Toast.LENGTH_LONG).show();
                    //    wbFeedback.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
                }
            });
        }
    }
}
