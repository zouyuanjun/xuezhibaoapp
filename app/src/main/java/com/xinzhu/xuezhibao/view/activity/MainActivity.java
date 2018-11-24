package com.xinzhu.xuezhibao.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.server.LivelyServer;
import com.xinzhu.xuezhibao.view.fragment.HomeFragemt;
import com.xinzhu.xuezhibao.view.fragment.TestFragment;
import com.xinzhu.xuezhibao.view.fragment.UserCentreFragment;
import com.xinzhu.xuezhibao.view.fragment.VideoFragment;
import com.xinzhu.xuezhibao.view.fragment.XuebaoFragment;
import com.zou.fastlibrary.activity.BaseBottomTabActivity;
import com.zou.fastlibrary.utils.EditTextUtil;
import com.zou.fastlibrary.utils.StatusBar;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseBottomTabActivity {
    List<String> tabTextlist=new ArrayList<>();
    private List<Fragment> fragmentList=new ArrayList<>();
    List<Integer> iocdef=new ArrayList<>();
    List<Integer> iocsel=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditTextUtil.hideKeyboard(this,getCurrentFocus());
        Fresco.initialize(this.getApplicationContext());
        StatusBar.setColor(this,0xFFf87d28);
        tabTextlist.add("首页");
        tabTextlist.add("精品课");
        tabTextlist.add("学宝");
        tabTextlist.add("家长荟");
        tabTextlist.add("我的");
        iocdef.add(R.drawable.tab_btn_home_nor);
        iocdef.add(R.drawable.tab_btn_video_nor);
        iocdef.add(R.drawable.tab_btn_study_nor);
        iocdef.add(R.drawable.tab_btn_study_nor);
        iocdef.add(R.drawable.tab_btn_my_nor);
        iocsel.add(R.drawable.tab_btn_home_sel);
        iocsel.add(R.drawable.tab_btn_video_sel);
        iocsel.add(R.drawable.tab_btn_study_sel);
        iocsel.add(R.drawable.tab_btn_study_sel);
        iocsel.add(R.drawable.tab_btn_my_sel);

        creatNormalTab(iocdef,iocsel,tabTextlist);
        fragmentList.add(new HomeFragemt());
        fragmentList.add(new VideoFragment());
        fragmentList.add(new XuebaoFragment());
        fragmentList.add(new TestFragment());
        fragmentList.add(new UserCentreFragment());
        setViewPagerAdaptr(fragmentList);
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED)) {
            EasyPermissions.requestPermissions(this, "允许必要权限才可以正常使用哦", 1, Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

    }
}
