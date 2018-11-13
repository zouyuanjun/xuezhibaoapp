package com.xinzhu.xuezhibao.view.helputils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.activity.LoginActivity;
import com.zou.fastlibrary.ui.CustomDialog;

public class CreatDiag {
    public static void shoudia(final Activity activity){

        CustomDialog.Builder builder = new CustomDialog.Builder(activity);
        builder.setTitle("提示");
        builder.setMessage("登陆后才可以继续操作，现在就去登陆");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent=new Intent(activity,LoginActivity.class);
                intent.putExtra(Constants.FROMAPP,"fss");
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
