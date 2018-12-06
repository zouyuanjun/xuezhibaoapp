package com.xinzhu.xuezhibao.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import com.xinzhu.xuezhibao.view.activity.ArticleDetilsActivity;
import com.xinzhu.xuezhibao.view.activity.LoginActivity;
import com.zou.fastlibrary.ui.CustomDialog;

public class DialogUtils {


    /**
     * 登陆提示对话框
     * @param activity
     */
    public static void loginDia(final Activity activity) {
        CustomDialog.Builder builder = new CustomDialog.Builder(activity);
        builder.setTitle("提示");
        builder.setMessage("您尚未登陆，现在登陆?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.putExtra(Constants.FROMAPP, "fss");
                activity.startActivity(intent);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
