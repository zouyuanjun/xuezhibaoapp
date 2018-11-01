package com.xinzhu.xuezhibao.view.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.immodule.utils.RequestCode;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomDialog;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.Network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class EditTaskActivity extends BaseActivity {

    Context mContext;
    List<String> mSelected = new ArrayList<>();   //已选择的照片
    HashMap<String, String> map = new HashMap<>();
    @BindView(R.id.im_clean1)
    ImageView imClean1;
    @BindView(R.id.im_clean2)
    ImageView imClean2;
    @BindView(R.id.im_clean3)
    ImageView imClean3;
    int canSelectCount = 3;
    boolean im1canuse = true;
    boolean im2canuse = true;
    boolean im3canuse = true;
    @BindView(R.id.tv_canselectcount)
    TextView tvCanselectcount;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
        }
    };
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        map.clear();
        mSelected.clear();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittask);
        mContext = this;
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
                HashMap<String, String> data = new HashMap<>();
                for (String key : map.keySet()) {
                    mSelected.add(map.get(key));
                }
                String feedback = edFeedback.getText().toString();
                if (feedback.isEmpty()) {
                    return;
                }
                if (!Constants.TOKEN.isEmpty()) {
                    CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
                    builder.setTitle("提示");
                    builder.setMessage("您尚未登陆，现在就去登陆");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(EditTaskActivity.this, LoginActivity.class);
                            intent.putExtra(Constants.FROMAPP, "fss");
                            startActivity(intent);

                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                } else {
                    data.put("opinionContent", feedback);
                    data.put("token", Constants.TOKEN);
                    Network.getnetwork().uploadimg(data, Constants.URL + "/guest/opinion-insert", mSelected, handler);
                    // Network.getnetwork().uploadimg(data,"http://192.168.1.200:8080/upload",mSelected,handler);
                }
            }
        });
    }

    @OnClick({R.id.im_clean1, R.id.im_clean2, R.id.im_clean3, R.id.ed_feedback, R.id.im_addim, R.id.im_1, R.id.im_2, R.id.im_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_addim:
                int requestCode = RequestCode.PICK_IMAGE;
                if (ContextCompat.checkSelfPermission(EditTaskActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    EasyPermissions.requestPermissions(this, "需要获取相册读写权限", 0, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    //    Toast.makeText(this, "请在应用管理中打开“读写存储”访问权限！", Toast.LENGTH_LONG).show();
                } else if (canSelectCount > 0) {
                    Matisse.from(EditTaskActivity.this)
                            .choose(MimeType.ofAll(), false) // 选择 mime 的类型
                            .countable(true)
                            .maxSelectable(canSelectCount) // 图片选择的最多数量
                            .theme(R.style.Matisse_Custom)
                            .gridExpectedSize(400)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f) // 缩略图的比例
                            .imageEngine(new Glide4Engine()) // 使用的图片加载引擎
                            .forResult(requestCode); // 设置作为标记的请求码

                } else {
                    BToast.custom(mContext).text("最多只能选3张哦").show();
                }
                break;
            case R.id.im_clean1:
                im1.setVisibility(View.INVISIBLE);
                imClean1.setVisibility(View.INVISIBLE);
                im1canuse = true;
                canSelectCount++;
                map.remove("1");
                break;
            case R.id.im_clean2:
                im2.setVisibility(View.INVISIBLE);
                imClean2.setVisibility(View.INVISIBLE);
                im2canuse = true;
                canSelectCount++;
                map.remove("2");
                break;
            case R.id.im_clean3:
                im3.setVisibility(View.INVISIBLE);
                imClean3.setVisibility(View.INVISIBLE);
                im3canuse = true;
                canSelectCount++;
                map.remove("3");
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        List<Uri> Selected = Matisse.obtainResult(data);
        if (Selected.size() == 0) {
            return;
        }
        int selectcount = Selected.size();
        canSelectCount = canSelectCount - selectcount;
        tvCanselectcount.setText(3 - canSelectCount + "/3");
        for (int i = 0; i < Selected.size(); i++) {
            Uri uri = Selected.get(i);
            File file = new File(getRealFilePath(mContext, uri));

            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file.getPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            if (im1canuse) {
                im1.setImageBitmap(bitmap);
                im1.setVisibility(View.VISIBLE);
                imClean1.setVisibility(View.VISIBLE);
                im1canuse = false;
                map.put("1", getRealFilePath(mContext, uri));
            } else if (im2canuse) {
                im2.setImageBitmap(bitmap);
                im2.setVisibility(View.VISIBLE);
                imClean2.setVisibility(View.VISIBLE);
                map.put("2", getRealFilePath(mContext, uri));
                im2canuse = false;
            } else if (im3canuse) {
                im3.setImageBitmap(bitmap);
                im3.setVisibility(View.VISIBLE);
                imClean3.setVisibility(View.VISIBLE);
                map.put("3", getRealFilePath(mContext, uri));
                im3canuse = false;
            }
        }
    }
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
