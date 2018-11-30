package com.zou.fastlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewUtil {

    public static class MyWebViewClient extends WebViewClient {
        Activity context;
        WebView webView;

        public MyWebViewClient(Activity context, WebView webView) {
            this.context = context;
            this.webView = webView;
            init( false);
        }
        public MyWebViewClient(Activity context, WebView webView,boolean horizontalScrollBarEnabled) {
            this.context = context;
            this.webView = webView;
            init( horizontalScrollBarEnabled);
        }
        private void init(boolean HorizontalScrollBarEnabled){
            this.webView.addJavascriptInterface(this, "App");
            WebSettings webSettings = this.webView.getSettings();//获取webview设置属性
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
            webSettings.setJavaScriptEnabled(true);//支持js
            this.webView.setHorizontalScrollBarEnabled(false);//水平不显示
            this.webView.setVerticalScrollBarEnabled(HorizontalScrollBarEnabled); //垂直不显示
            this.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            imgReset();
            webView.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
            super.onPageFinished(view, url);


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
                    layoutParams.width = (int)((context.getResources().getDisplayMetrics().widthPixels)*0.95);
                    layoutParams.height = (int) (height * context.getResources().getDisplayMetrics().density)+50;
                    Log.d(layoutParams.width+"高度是"+layoutParams.height+"原始"+height+"级"+context.getResources().getDisplayMetrics().density);
                    webView.setLayoutParams(layoutParams);
                    //Toast.makeText(getActivity(), height + "", Toast.LENGTH_LONG).show();
                    //    wbFeedback.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
                }
            });
        }
    }

}
