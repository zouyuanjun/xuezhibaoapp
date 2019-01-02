package com.xinzhu.xuezhibao.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
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
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.CreatPopwindows;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.ScreenUtil;
import com.zou.fastlibrary.utils.TimeUtil;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.model.TResult;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

import static com.zou.fastlibrary.utils.TimeUtil.getTime;

public class UserBaseActivity extends TakePhotoActivity {
    Activity activity;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            com.zou.fastlibrary.utils.Log.d(result);
            int code=0;
            try {
                code = JsonUtils.getIntValue(result, "Code");
            }catch (Exception e){
                BToast.custom(activity).text("服务器发送错误"+result).show();
            }
            if (code == -100) {
                BToast.custom(activity).text("修改失败，网络连接不上").show();
                return;
            }
            if (code == -200) {
                BToast.custom(activity).text("修改失败，网络连接超时").show();
                return;
            }
            if (code!=100){
                BToast.error(activity).text("修改失败,错误码"+code).show();
                return;
            }
            if (code==100){
                BToast.success(activity).text("修改成功").show();
            }else {
                BToast.success(activity).text("修改失败").show();
            }
            Log.d(result);
        }
    };
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_jifenshop)
    TextView tvJifenshop;
    @BindView(R.id.sd_myphoto)
    SimpleDraweeView sdMyphoto;
    @BindView(R.id.tv_vipname)
    TextView tvVipname;
    @BindView(R.id.ll_vipname)
    LinearLayout llVipname;
    @BindView(R.id.tv_studenname)
    TextView tvStudenname;
    @BindView(R.id.ll_studenname)
    LinearLayout llStudenname;
    @BindView(R.id.tv_studennage)
    TextView tvStudennage;
    @BindView(R.id.ll_studenage)
    LinearLayout llStudenage;
    @BindView(R.id.tv_fathername)
    TextView tvFathername;
    @BindView(R.id.ll_fathername)
    LinearLayout llFathername;
    @BindView(R.id.tv_mothername)
    TextView tvMothername;
    @BindView(R.id.ll_mothername)
    LinearLayout llMothername;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    String citysrt="";
    CityPickerView mPicker=new CityPickerView();
    String sheng;
    String  shi;
    String qu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userbase);
        mPicker.init(this);
        ButterKnife.bind(this);
        activity = this;
        if (null!=Constants.userBasicInfo){
            tvVipname.setText(Constants.userBasicInfo.getNickName());
            tvStudenname.setText(Constants.userBasicInfo.getStudentName());
            String data;
            try {
                data=TimeUtil.getWholeTime(Long.parseLong(Constants.userBasicInfo.getStudentAge()));
            }catch (NumberFormatException e){
                data="请设置出生日期";
            }

            tvStudennage.setText(data);
            tvFathername.setText(Constants.userBasicInfo.getFatherName());
            tvMothername.setText(Constants.userBasicInfo.getMotherName());
            tvAddress.setText(Constants.userBasicInfo.getCity()+"-"+Constants.userBasicInfo.getCounty());
            sdMyphoto.setImageURI(Constants.userBasicInfo.getImage());
        }
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public TakePhoto getTakePhoto() {
        return super.getTakePhoto();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String imgurl=result.getImage().getOriginalPath();
        String s=result.getImage().getCompressPath();
        Log.d(imgurl+s);
        sdMyphoto.setImageURI(Uri.fromFile(new File(imgurl)));
        JMessageClient.updateUserAvatar(new File(imgurl), new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.d("极光用户头像更新"+s);
            }
        });
        Network.getnetwork().uploadimg(Constants.TOKEN,Constants.URL+"/guest/image-upload",result.getImage().getCompressPath(),handler,1);

    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @OnClick({R.id.sd_myphoto, R.id.ll_vipname, R.id.ll_studenname, R.id.ll_studenage, R.id.ll_fathername, R.id.ll_mothername, R.id.ll_address})
    public void onViewClicked(View view) {
        Intent intent=new Intent(UserBaseActivity.this,EditUserBasicActivity.class);
        switch (view.getId()) {
            case R.id.sd_myphoto:
                showpopwindow(view);
                break;
            case R.id.ll_vipname:
              intent.putExtra(Constants.INTENT_EDITITEM,"昵称");
              startActivityForResult(intent,1);
                break;
            case R.id.ll_studenname:
                intent.putExtra(Constants.INTENT_EDITITEM,"学生姓名");
                startActivityForResult(intent,2);
                break;
            case R.id.ll_studenage:

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
                        tvStudennage.setText(TimeUtil.getWholeTime(date));
                        Constants.userBasicInfo.setStudentAge(TimeUtil.getWholeTime(date));
                        Constants.userBasicInfo.setToken(Constants.TOKEN);
                        String string=JsonUtils.objectToString(Constants.userBasicInfo);
                        Network.getnetwork().postJson(string,Constants.URL+"/app/update-member",handler,2);
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
                break;
            case R.id.ll_fathername:
                intent.putExtra(Constants.INTENT_EDITITEM,"父亲姓名");
                startActivityForResult(intent,4);
                break;
            case R.id.ll_mothername:
                intent.putExtra(Constants.INTENT_EDITITEM,"母亲姓名");
                startActivityForResult(intent,5);
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
                        .province("江西省")//默认显示的省份
                        .city("南昌市")//默认显示省份下面的城市
                        .district("南昌县")//默认显示省市下面的区县数据
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
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        super.onSelected(province, city, district);
                        //省份
                        if (province != null) {
                            sheng=province.getName();
                            citysrt=province.getName();
                        }
                        //城市
                        if (city != null) {
                            shi=city.getName();
                            citysrt=citysrt+"-"+city.getName();
                        }
                        //地区
                        if (district != null) {
                            qu=district.getName();
                            citysrt=citysrt+"-"+district.getName();

                        }
                        tvAddress.setText(citysrt);
                        Constants.userBasicInfo.setProvince(sheng);
                        Constants.userBasicInfo.setCity(shi);
                        Constants.userBasicInfo.setCounty(qu);
                        Constants.userBasicInfo.setToken(Constants.TOKEN);
                        String string=JsonUtils.objectToString(Constants.userBasicInfo);
                        Network.getnetwork().postJson(string,Constants.URL+"/app/update-member",handler,2);
                    }
                });
                mPicker.showCityPicker( );
                break;

        }
    }

    public void showpopwindow(View parentview){
        final PopupWindow popupWindow=CreatPopwindows.creatpopwindows(this,R.layout.pop_selectphoto);
        View view=popupWindow.getContentView();
        TextView takephoto=view.findViewById(R.id.tv_take_photo);
        TextView selectphoto=view.findViewById(R.id.tv_select_photo);
        TextView cancle=view.findViewById(R.id.tv_cancel);
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
        popupWindow.showAtLocation(parentview, Gravity.BOTTOM,0, ScreenUtil.getNavigationBarHeight(UserBaseActivity.this));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result="";
        Log.d("返回码"+requestCode);
        if (null==data){
            return;
        }
        if (requestCode==1||requestCode==2||requestCode==3||requestCode==4||requestCode==5){
            if (null!=data){
                result=data.getStringExtra(Constants.INTENT_EDITITEM);
            }
            if (requestCode==1){
                tvVipname.setText(result);
                Constants.userBasicInfo.setNickName(result);
            }else if (requestCode==2){
                tvStudenname.setText(result);
                Constants.userBasicInfo.setStudentName(result);
            }else if (requestCode==3){

            }else if (requestCode==4){
                tvFathername.setText(result);
                Constants.userBasicInfo.setFatherName(result);
            }else if (requestCode==5){
                tvMothername.setText(result);
                Constants.userBasicInfo.setMotherName(result);
            }
            Constants.userBasicInfo.setToken(Constants.TOKEN);
            String string=JsonUtils.objectToString(Constants.userBasicInfo);
            Network.getnetwork().postJson(string,Constants.URL+"/app/update-member",handler,2);
        }

    }
}
