package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
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
import com.zou.fastlibrary.utils.TimeUtil;

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
            int what=msg.what;
            if (what==1){
                if (code == 100) {
                    String url = JsonUtils.getStringValue(result, "Data");
                    url=JsonUtils.getStringValue(url,"qrAddress");
                    if (StringUtil.isEmpty(url)) {
                        return;
                    }
                    Glide.with(MyApplication.getContext())
                            .asBitmap()
                            .load(url)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap photo, @Nullable Transition<? super Bitmap> transition) {
                                    int with=photo.getWidth();
                                    int higt=photo.getHeight();
                                    Log.d(with+"sd"+higt);
                                    Canvas canvas = new Canvas(photo);// 初始化画布绘制的图像到icon上
                                    Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
                                    textPaint.setTextSize(40);// 字体大小
                                    textPaint.setTypeface(Typeface.DEFAULT );// 采用默认的宽度
                                    textPaint.setColor(Color.parseColor("#ff5436"));// 采用的颜色
                                    // textPaint.setFakeBoldText(true);

                                    canvas.drawText(TimeUtil.getWholeTime(System.currentTimeMillis())+"", with/2-85, higt/2, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
                                    canvas.save(Canvas.ALL_SAVE_FLAG);
                                    canvas.restore();
                                    imgShare.setImageBitmap(photo);
                                    ImageUtils.saveBitmapFile(photo, path, "share.jpg");
                                    photo=null;
                                }
                            });
                }
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
                Intent intent=new Intent(ShareActivity.this,MyTaskActivity.class);
                startActivity(intent);
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
        path = DataKeeper.getDiskCachePath(ShareActivity.this) + "/Pictures/";
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showpopwindow(View parentview) {
        final PopupWindow popupWindow = CreatPopwindows.creatpopwindows(this, R.layout.pop_share);
        View view = popupWindow.getContentView();
        TextView takephoto = view.findViewById(R.id.tv_timeline);
        TextView tv_fridend = view.findViewById(R.id.tv_friends);
        TextView other = view.findViewById(R.id.tv_other);
        TextView cancle = view.findViewById(R.id.tv_cancel);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WX_Share.sharePicByFile(ShareActivity.this, path + "share.jpg", 1);
                popupWindow.dismiss();
                shareimg();
            }
        });
        tv_fridend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WX_Share.sharePicByFile(ShareActivity.this, path + "share.jpg", 2);
                popupWindow.dismiss();
                shareimg();
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WX_Share.sharePhotoToWX(ShareActivity.this, "", path + "share.jpg");
                shareimg();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ShareActivity.this,MyTaskActivity.class);
        startActivity(intent);
        finish();
    }

    public void shareimg(){
        String data=JsonUtils.keyValueToString2("stateType",2,"token",Constants.TOKEN);
        Network.getnetwork().postJson(data,Constants.URL+"/app/finish-task",handler,2);
    }
}
