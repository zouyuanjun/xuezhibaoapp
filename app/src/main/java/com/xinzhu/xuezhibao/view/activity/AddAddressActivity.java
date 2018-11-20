package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.AddressBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.EditTextUtil;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zou on 2018/11/15.
 */

public class AddAddressActivity extends BaseActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.tv_selectaddress)
    TextView tvSelectaddress;
    @BindView(R.id.ed_addressdetail)
    EditText edAddressdetail;
    @BindView(R.id.tv_commint)
    ShapeCornerBgView tvCommint;
    CityPickerView mPicker=new CityPickerView();
    String city;
    String county;
    Context context;
    String province;
    String citysrt="";
    AddressBean addressBean;
    int EDITMODE=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddress);
        ButterKnife.bind(this);
        mPicker.init(this);
        addressBean= (AddressBean) getIntent().getSerializableExtra(Constants.INTENT_ID);
        if (null!=addressBean){
            EDITMODE=2;
            tvName.setText(addressBean.getLinkman());
            edPhone.setText(addressBean.getLinkPhone());
            tvSelectaddress.setText(addressBean.getProvince()+addressBean.getCity()+addressBean.getCounty());
            edAddressdetail.setText(addressBean.getAddress());
        }
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.tv_selectaddress, R.id.tv_commint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_selectaddress:
                EditTextUtil.hideKeyboard(this,tvSelectaddress);
                CityConfig cityConfig = new CityConfig.Builder()
                        .title("选择城市")//标题
                        .titleTextSize(18)//标题文字大小
                        .titleTextColor("#585858")//标题文字颜  色
                        .titleBackgroundColor("#E9E9E9")//标题栏背景色
                        .confirTextColor("#585858")//确认按钮文字颜色
                        .confirmText("确认")//确认按钮文字
                        .confirmTextSize(16)//确认按钮文字大小
                        .cancelTextColor("#585858")//取消按钮文字颜色
                        .cancelText("取消")//取消按钮文字
                        .cancelTextSize(16)//取消按钮文字大小
                        .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                        .showBackground(true)//是否显示半透明背景
                        .visibleItemsCount(5)//显示item的数量
                        .province("浙江省")//默认显示的省份
                        .city("杭州市")//默认显示省份下面的城市
                        .district("滨江区")//默认显示省市下面的区县数据
                        .provinceCyclic(false)//省份滚轮是否可以循环滚动
                        .cityCyclic(false)//城市滚轮是否可以循环滚动
                        .districtCyclic(false)//区县滚轮是否循环滚动
//                        .setCustomItemLayout(R.layout.item_city)//自定义item的布局
//                        .setCustomItemTextViewId(R.id.item_city_name_tv)//自定义item布局里面的textViewid
                        .drawShadows(false)//滚轮不显示模糊效果
                        .setLineColor("#f87d28")//中间横线的颜色
                        .setLineHeigh(5)//中间横线的高度
                        .setShowGAT(true)//是否显示港澳台数据，默认不显示
                        .build();

//设置自定义的属性配置
                mPicker.setConfig(cityConfig);
                mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean provinc, CityBean citys, DistrictBean district) {
                        super.onSelected(provinc, citys, district);
                        //省份
                        if (provinc != null) {
                            citysrt=provinc.getName().replace("省","");
                            province=provinc.getName();
                        }
                        //城市
                        if (citys != null) {
                            city=citys.getName();
                            citysrt=citysrt+"·"+citys.getName().replace("市","");
                        }
                        //地区
                        if (district != null) {
                            county=district.getName();
                            citysrt=citysrt+"·"+district.getName();

                        }
                        tvSelectaddress.setText(citysrt);
                    }
                });
                mPicker.showCityPicker( );
                break;
            case R.id.tv_commint:
                String name=tvName.getText().toString();
                String phone=edPhone.getText().toString();
                String details=edAddressdetail.getText().toString();
                if (StringUtil.isEmpty(name)||StringUtil.isEmpty(phone)||StringUtil.isEmpty(details)||StringUtil.isEmpty(citysrt)){
                    BToast.error(this).text("请填写完整在提交").show();
                }else {
                    if (EDITMODE==1){
                        AddressBean addressBean=new AddressBean(province,phone,name,details,city,county,0);
                        Intent intent=new Intent();
                        intent.putExtra(Constants.INTENT_ID, JsonUtils.objectToString(addressBean));
                        setResult(1,intent);
                        finish();
                    }else {
                        addressBean.setAddress(details);
                        addressBean.setProvince(province);
                        addressBean.setCity(city);
                        addressBean.setCounty(county);
                        addressBean.setLinkman(name);
                        addressBean.setLinkPhone(phone);
                        Intent intent=new Intent();
                        intent.putExtra(Constants.INTENT_ID, JsonUtils.objectToString(addressBean));
                        setResult(2,intent);
                        finish();
                    }
                }
                break;
        }
    }
}
