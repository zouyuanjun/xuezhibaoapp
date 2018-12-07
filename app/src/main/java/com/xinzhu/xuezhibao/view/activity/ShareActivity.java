package com.xinzhu.xuezhibao.view.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.utils.BitmapUtil;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.SelectPhotoUtils;
import com.xinzhu.xuezhibao.utils.WX_Share;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.CreatPopwindows;
import com.zou.fastlibrary.utils.DataKeeper;
import com.zou.fastlibrary.utils.ImageUtils;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.ScreenUtil;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShareActivity extends BaseActivity {
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    String path;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            Log.d(result);
            int code = JsonUtils.getIntValue(result, "Code");
            if (code == 100) {
                path = DataKeeper.getDiskCachePath(ShareActivity.this) + "/Pictures/";

                String url = JsonUtils.getStringValue(result, "Data");
                if (StringUtil.isEmpty(url)) {
                    return;
                }
                Glide.with(MyApplication.getContext())
                        .asBitmap()
                        .load(url)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                imgShare.setImageBitmap(resource);
                                ImageUtils.saveBitmapFile(resource, path, "share.jpg");
                            }
                        });
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        appbar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopwindow(v);
            }
        });
        Network.getnetwork().postJson("", Constants.URL + "/guest/select-qr-address", handler, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showpopwindow(View parentview) {
        final PopupWindow popupWindow = CreatPopwindows.creatpopwindows(this, R.layout.pop_share);
        View view = popupWindow.getContentView();
        TextView takephoto = view.findViewById(R.id.tv_timeline);
        TextView selectphoto = view.findViewById(R.id.tv_friends);
        TextView other = view.findViewById(R.id.tv_other);
        TextView cancle = view.findViewById(R.id.tv_cancel);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WX_Share.sharePicByFile(ShareActivity.this, path + "share.jpg", 1);
                popupWindow.dismiss();
            }
        });
        selectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WX_Share.sharePicByFile(ShareActivity.this, path + "share.jpg", 2);
                popupWindow.dismiss();
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WX_Share.sharePhotoToWX(ShareActivity.this, "", path + "share.jpg");
                popupWindow.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(parentview, Gravity.BOTTOM, 0, ScreenUtil.getNavigationBarHeight(ShareActivity.this));
    }
}
