package com.xinzhu.xuezhibao.view.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.utils.WX_Share;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.DataKeeper;
import com.zou.fastlibrary.utils.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShareActivity extends BaseActivity {
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    String path;

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
                WX_Share.sharePicByFile(ShareActivity.this, path + "share.jpg","aa");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Drawable db = getResources().getDrawable(R.drawable.share);
        BitmapDrawable drawable = (BitmapDrawable) db;
        Bitmap bitmap = drawable.getBitmap();
        path = DataKeeper.getDiskCachePath(this) + "/Pictures/";
        ImageUtils.saveBitmapFile(bitmap, path, "share.jpg");

    }
}
