package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bravin.btoast.BToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.SelectPhotoUtils;
import com.zou.fastlibrary.bean.NetWorkMessage;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.CreatPopwindows;
import com.zou.fastlibrary.utils.EditTextUtil;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.ScreenUtil;
import com.zou.fastlibrary.utils.TimeUtil;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.model.TResult;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditAllActivity extends TakePhotoActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_jifenshop)
    TextView tvJifenshop;
    @BindView(R.id.sd_myphoto)
    SimpleDraweeView sdMyphoto;
    @BindView(R.id.ed_vipname)
    EditText edVipname;
    @BindView(R.id.ed_studenname)
    EditText edStudenname;
    @BindView(R.id.ed_fathername)
    EditText edFathername;
    @BindView(R.id.ed_mothername)
    EditText edMothername;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    String province;
    String city;
    String county;
    Context context;
    String citysrt = "";
    CityPickerView mPicker = new CityPickerView();
    String imgurl;//选择头像的地址
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            Log.d(result);
            if (null != loadingPop && loadingPop.isShowing()) {
                loadingPop.dismiss();
            }
            int code = -999;
            try {
                code = JsonUtils.getIntValue(result, "Code");
            } catch (Exception e) {
                Log.d("异常了");
                BToast.custom(context).text("内部错误，正在加紧修复，请稍后再试").show();
            }
            if (code == -100) {
                BToast.custom(context).text("连接超时，请重试或检查网络").show();
                return;
            }
            if (code == -200) {
                BToast.custom(context).text("网络连接失败，请重试或检查网络").show();
                return;
            }
            if (code == 100) {
                if (msg.what == 2) {
                    finish();
                    startActivity(new Intent(EditAllActivity.this, MainActivity.class));
                } else if (msg.what == 1) {
                    sdMyphoto.setImageURI(Uri.fromFile(new File(imgurl)));
                }

            } else {
                BToast.error(EditAllActivity.this).text("上传失败，请稍后再试").show();
            }
        }
    };
    PopupWindow loadingPop;
    @BindView(R.id.ed_studennage)
    TextView edStudennage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editalluserbase);
        EventBus.getDefault().register(this);
        mPicker.init(this);
        ButterKnife.bind(this);
        context = this;
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        appbar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditTextUtil.getString(edVipname).isEmpty()) {
                    BToast.info(context).text("昵称还没有填写哦").show();
                    return;
                }
                if (EditTextUtil.getString(edStudenname).isEmpty()) {
                    BToast.info(context).text("学生姓名还没有填写哦").show();
                    return;
                }
                if (edStudennage.getText().toString().isEmpty()) {
                    BToast.info(context).text("学生年龄还没有填写哦").show();
                    return;
                }
                if (EditTextUtil.getString(edFathername).isEmpty() && EditTextUtil.getString(edMothername).isEmpty()) {
                    BToast.info(context).text("父亲或母亲姓名必须填写一个").show();
                    return;
                }
                String address = tvAddress.getText().toString();
                if (null == address || address.isEmpty()) {
                    BToast.info(context).text("地区还没选择哦").show();
                    return;
                }
                String vipname = EditTextUtil.getString(edVipname);
                String studentname = EditTextUtil.getString(edStudenname);
                String studentage = edStudennage.getText().toString();
                String fathername = EditTextUtil.getString(edFathername);
                String mathername = EditTextUtil.getString(edMothername);
                if (Constants.TOKEN.isEmpty()) {
                    BToast.error(context).text("登陆失效，请重新登陆").show();
                } else {
                    Constants.userBasicInfo.setNickName(vipname);
                    Constants.userBasicInfo.setStudentName(studentname);
                    Constants.userBasicInfo.setFatherName(fathername);
                    Constants.userBasicInfo.setMotherName(mathername);
                    Constants.userBasicInfo.setProvince(province);
                    Constants.userBasicInfo.setCity(city);
                    Constants.userBasicInfo.setCounty(county);
                    Constants.userBasicInfo.setStudentAge(studentage);
                    String data = JsonUtils.objectToString(Constants.userBasicInfo);
                    Network.getnetwork().postJson(data, Constants.URL + "/app/update-member", handler, 2);
                    loadingPop = CreatPopwindows.creatWWpopwindows(EditAllActivity.this, R.layout.pop_loading);
                    loadingPop.showAtLocation(view, Gravity.CENTER, 0, 0);
                }

            }
        });

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void netWorkMessage(NetWorkMessage messageEvent) {
        String s=messageEvent.getMessage();
        BToast.error(EditAllActivity.this).text(s).show();
    }
    @Override
    public TakePhoto getTakePhoto() {
        return super.getTakePhoto();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        imgurl = result.getImage().getOriginalPath();

        Network.getnetwork().uploadimg(Constants.TOKEN, Constants.URL + "/guest/image-upload", result.getImage().getCompressPath(), handler, 1);

    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);

    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }


    @OnClick({R.id.ll_header, R.id.ll_address,R.id.ed_studennage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header:
                showpopwindow(view);
                break;
            case R.id.ll_address:
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
                        .setLineColor("#03a9f4")//中间横线的颜色
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
                            citysrt = provinc.getName().replace("省", "");
                            province = provinc.getName();
                        }
                        //城市
                        if (citys != null) {
                            city = citys.getName();
                            citysrt = citysrt + "·" + citys.getName().replace("市", "");
                        }
                        //地区
                        if (district != null) {
                            county = district.getName();
                            citysrt = citysrt + "·" + district.getName();

                        }
                        tvAddress.setText(citysrt);
                    }
                });
                mPicker.showCityPicker();
                break;
            case R.id.ed_studennage:{

                Calendar selectedDate = Calendar.getInstance();
                Calendar startDate = Calendar.getInstance();
                //startDate.set(2013,1,1);
                Calendar endDate = Calendar.getInstance();
                //endDate.set(2020,1,1);

                //正确设置方式 原因：注意事项有说明
                startDate.set(1990,0,1);
                endDate.set(2020,11,31);
                TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String datestring=sdf.format(date);
                        edStudennage.setText(datestring);
                        Constants.userBasicInfo.setStudentAge(datestring);
//                        Constants.userBasicInfo.setToken(Constants.TOKEN);
//                        String string=JsonUtils.objectToString(Constants.userBasicInfo);
//                        Network.getnetwork().postJson(string,Constants.URL+"/app/update-member",handler,3);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("出生日期")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(false)//是否循环滚动
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.parseColor("#666666"))//确定按钮文字颜色
                        .setCancelColor(Color.parseColor("#666666"))//取消按钮文字颜色
                        .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                        .setBgColor(0xFFffffff)//滚轮背景颜色 Night mode
                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                        .setRangDate(startDate,endDate)//起始终止年月日设定
                        .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                        .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime.show(view,true);
            }
        }
    }

    public void showpopwindow(View parentview) {
        final PopupWindow popupWindow = CreatPopwindows.creatpopwindows(this, R.layout.pop_selectphoto);
        View view = popupWindow.getContentView();
        TextView takephoto = view.findViewById(R.id.tv_take_photo);
        TextView selectphoto = view.findViewById(R.id.tv_select_photo);
        TextView cancle = view.findViewById(R.id.tv_cancel);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPhotoUtils.selectphoto(2, getTakePhoto());
                popupWindow.dismiss();
            }
        });
        selectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPhotoUtils.selectphoto(1, getTakePhoto());
                popupWindow.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(parentview, Gravity.BOTTOM, 0, ScreenUtil.getNavigationBarHeight(EditAllActivity.this));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != loadingPop && loadingPop.isShowing()) {
            loadingPop.dismiss();
        }
    }

}
