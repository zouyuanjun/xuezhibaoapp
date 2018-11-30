package com.xinzhu.xuezhibao.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tencent.bugly.beta.Beta;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.utils.Constants;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.CommonUtil;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_appversion)
    TextView tvAppversion;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_ipc)
    TextView tvIpc;
    @BindView(R.id.imageView8)
    SimpleDraweeView imageView8;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    String address;
    String phone;
    String ipc;
    String about;
    String logourl;
    String l1;   //纬度
    String l2;  //经度
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            Log.d(result);
            int code = 0;
            code = JsonUtils.getIntValue(result, "Code");
            if (code == 100) {
                String data = JsonUtils.getStringValue(result, "Data");
                logourl = JsonUtils.getStringValue(data, "logo");
                about = JsonUtils.getStringValue(data, "companyInfo");
                phone = JsonUtils.getStringValue(data, "contact");
                address = JsonUtils.getStringValue(data, "address");
                ipc = JsonUtils.getStringValue(data, "copyright");
                l1 = JsonUtils.getStringValue(data, "longitude");
                l2 = JsonUtils.getStringValue(data, "latitude");
                initmap(l1, l2);
                tvAbout.setText(Html.fromHtml(about));
                tvPhone.setText(phone);
                tvIpc.setText(ipc);
                tvAddress.setText(address);
                imageView8.setImageURI(logourl);

            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        ButterKnife.bind(this);
        mMapView = findViewById(R.id.mapview);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Network.getnetwork().postJson("", Constants.URL + "/guest/find-by-time", handler, 1);
        try {
            tvAppversion.setText(this.getPackageManager().getPackageInfo("com.xinzhu.xuezhibao", 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Beta.checkUpgrade(true,false);
    }

    @OnClick({R.id.tv_appversion, R.id.tv_phone, R.id.tv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_appversion:
                break;
            case R.id.tv_phone:
                if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)) {
                    EasyPermissions.requestPermissions(this, "需要获取打电话权限才能打电话哦", 1, Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    CommonUtil.call(this, tvPhone.getText().toString());
                }
                break;
            case R.id.tv_address:

                break;
        }
    }

    @AfterPermissionGranted(1)
    public void onPermissionsGranted() {

        CommonUtil.call(this, tvPhone.getText().toString());

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

    private void initmap(String l2, String l1) {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        LatLng cenpt = new LatLng(Double.parseDouble(l1), Double.parseDouble(l2));//设定中心点坐标
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(cenpt).zoom(19.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //定义Maker坐标点

        LatLng point = new LatLng(Double.parseDouble(l1), Double.parseDouble(l2));
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.address_logo);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        mBaiduMap.addOverlay(option);
    }
}
