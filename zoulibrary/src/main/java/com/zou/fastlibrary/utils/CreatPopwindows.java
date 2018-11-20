package com.zou.fastlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.zou.fastlibrary.R;

public class CreatPopwindows {


    public static PopupWindow creatpopwindows(final Activity activity, int ViewID){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.4f;
        activity.getWindow().setAttributes(lp);
        View contentView = LayoutInflater.from(activity).inflate(ViewID, null);
        PopupWindow mPopWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }
        });
        return mPopWindow;
    }
    public static PopupWindow creatWWpopwindows(final Activity activity, int ViewID){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.4f;
        activity.getWindow().setAttributes(lp);
        View contentView = LayoutInflater.from(activity).inflate(ViewID, null);
        PopupWindow mPopWindow = new PopupWindow();
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setContentView(contentView);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }
        });
        return mPopWindow;
    }
    /**
     * 创建限制宽高的popwindow
     * @param activity
     * @param ViewID
     * @param with
     * @param height
     * @return
     */
    public static PopupWindow creatpopwindows(final Activity activity, int ViewID, int with, int height){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.4f;
        activity.getWindow().setAttributes(lp);
        View contentView = LayoutInflater.from(activity).inflate(ViewID, null);
        PopupWindow mPopWindow = new PopupWindow(contentView,
                with, height, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }
        });
        return mPopWindow;
    }

}
