package com.xinzhu.xuezhibao.view.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.immodule.utils.RequestCode;
import com.xinzhu.xuezhibao.immodule.view.ChatActivity;
import com.xinzhu.xuezhibao.utils.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.FileUtils;
import com.zou.fastlibrary.utils.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.tv_gongneng)
    TextView tvGongneng;
    @BindView(R.id.tv_xingneng)
    TextView tvXingneng;
    @BindView(R.id.tv_guzhang)
    TextView tvGuzhang;
    @BindView(R.id.linearLayout6)
    LinearLayout linearLayout6;
    @BindView(R.id.ed_feedback)
    EditText edFeedback;
    @BindView(R.id.im_addim)
    ImageView imAddim;
    @BindView(R.id.im_1)
    ImageView im1;
    @BindView(R.id.im_2)
    ImageView im2;
    @BindView(R.id.im_3)
    ImageView im3;
    @BindView(R.id.linearLayout9)
    LinearLayout linearLayout9;
    @BindView(R.id.bt_login)
    ShapeCornerBgView btLogin;
    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mContext=this;
        ButterKnife.bind(this);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        appbar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,HistoryFeedbackActivity.class));
            }
        });
    }

    @OnClick({R.id.tv_gongneng, R.id.tv_xingneng, R.id.tv_guzhang, R.id.ed_feedback, R.id.im_addim, R.id.im_1, R.id.im_2, R.id.im_3, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_gongneng:
                changeselect(tvGongneng);
                break;
            case R.id.tv_xingneng:
                changeselect(tvXingneng);

                break;
            case R.id.tv_guzhang:
                changeselect(tvGuzhang);

                break;
            case R.id.ed_feedback:
                break;
            case R.id.im_addim:
                int requestCode = RequestCode.PICK_IMAGE;
                if (ContextCompat.checkSelfPermission(FeedbackActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    EasyPermissions.requestPermissions(this, "需要获取相册读写权限", 0, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    //    Toast.makeText(this, "请在应用管理中打开“读写存储”访问权限！", Toast.LENGTH_LONG).show();
                } else {
                    Matisse.from(FeedbackActivity.this)
                            .choose(MimeType.ofAll(), false) // 选择 mime 的类型
                            .countable(true)
                            .maxSelectable(3) // 图片选择的最多数量
                            .theme(R.style.Matisse_Custom)
                            .gridExpectedSize(400)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f) // 缩略图的比例
                            .imageEngine(new Glide4Engine()) // 使用的图片加载引擎
                            .forResult(requestCode); // 设置作为标记的请求码

                }
                break;
            case R.id.im_1:
                break;
            case R.id.im_2:
                break;
            case R.id.im_3:
                break;
            case R.id.bt_login:
                break;
        }
    }

    public void changeselect(TextView textView){
        List<String> selectlist=new ArrayList<>();
        selectlist.clear();
        selectlist.add(textView.getText().toString());
        clearcolor();
        textView.setTextColor(Color.parseColor("#f87d28"));
    }

    public void clearcolor(){
        tvGongneng.setTextColor(Color.parseColor("#666666"));
        tvGuzhang.setTextColor(Color.parseColor("#666666"));
        tvXingneng.setTextColor(Color.parseColor("#666666"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        List<Uri> mSelected;
        mSelected = Matisse.obtainResult(data);
        Log.d("拿到了返回结果"+mSelected.size());
        for (int i=0;i<mSelected.size();i++){
            if (i==0) {
                Uri uri = mSelected.get(i);
                File file = new File(getRealFilePath(mContext, uri));
                try {
                    FileInputStream fis = new FileInputStream(file.getPath());
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    im1.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if (i==1) {
                Uri uri = mSelected.get(i);
                File file = new File(getRealFilePath(mContext, uri));
                try {
                    FileInputStream fis = new FileInputStream(file.getPath());
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    im2.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if (i==2) {
                Uri uri = mSelected.get(i);
                File file = new File(getRealFilePath(mContext, uri));
                try {
                    FileInputStream fis = new FileInputStream(file.getPath());
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    im3.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }

    }
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

}
