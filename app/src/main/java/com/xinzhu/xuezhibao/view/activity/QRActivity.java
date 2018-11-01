package com.xinzhu.xuezhibao.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.zxing.activity.CaptureActivity;
import com.zxing.view.ViewfinderView;

import java.lang.ref.WeakReference;

public class QRActivity extends CaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);
        init(new WeakReference<Activity>(this).get(), (SurfaceView) findViewById(R.id.svCameraScan), (ViewfinderView) findViewById(R.id.vfvCameraScan));
    }


}
