package com.xinzhu.xuezhibao.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.xinzhu.xuezhibao.R;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        ButterKnife.bind(this);
        mMapView = (MapView) findViewById(R.id.mapview);
appbar.setLeftImageOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();
    }
});

    }

    @OnClick({R.id.tv_appversion, R.id.tv_phone, R.id.tv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_appversion:
                break;
            case R.id.tv_phone:
                if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED)) {
                    EasyPermissions.requestPermissions(this, "需要获取打电话权限才能打电话哦", 1, Manifest.permission.CALL_PHONE);
                } else {
                    CommonUtil.call(this, "18702508050");
                }
                break;
            case R.id.tv_address:
                initmap();
                break;
        }
    }

    @AfterPermissionGranted(1)
    public void onPermissionsGranted() {
        CommonUtil.call(this, "18702508050");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    private void initmap() {

        mMapView.setVisibility(View.VISIBLE);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        LatLng cenpt = new LatLng(28.682552, 115.997666);//设定中心点坐标
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(cenpt).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //定义Maker坐标点

        LatLng point = new LatLng(28.682552, 115.997666);
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.address_logo);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        mBaiduMap.addOverlay(option);
    }
}
