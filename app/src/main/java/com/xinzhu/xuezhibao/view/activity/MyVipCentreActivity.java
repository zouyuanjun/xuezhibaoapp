package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.bravin.btoast.BToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.PayResult;
import com.xinzhu.xuezhibao.presenter.AlipayPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.OrderInfoUtil2_0;
import com.xinzhu.xuezhibao.view.interfaces.PayInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.CreatPopwindows;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员中心
 */
public class MyVipCentreActivity extends BaseActivity implements PayInterface {
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_viplv)
    ShapeCornerBgView tvViplv;
    @BindView(R.id.sd_myphoto)
    SimpleDraweeView sdMyphoto;
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_recharge)
    TextView tvRecharge;
    @BindView(R.id.tv_momeny)
    TextView tvMomeny;
    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:{
                    try {
                        String result= (String) msg.obj;
                        String data=JsonUtils.getStringValue(result,"Data");
                        String describeContent=JsonUtils.getStringValue(data,"describeContent");
                        if (!StringUtil.isEmpty(describeContent)){
                            tvIntroduce.setText(Html.fromHtml(describeContent));
                        }else {
                            tvIntroduce.setText("获取数据失败，请稍后再试");
                        }
                    }catch (Exception e){
                        tvIntroduce.setText("获取数据失败，请稍后再试");
                    }

                }
                default:
                    break;
            }
        }
    };
AlipayPresenter alipayPresenter;
PopupWindow loadingPop;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vipcenter);
        ButterKnife.bind(this);
        String data= JsonUtils.keyValueToString("describeType",2);
        Network.getnetwork().postJson(data, Constants.URL+"/guest/integral-rules",handler,2);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        alipayPresenter=new AlipayPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvUsername.setText(Constants.userBasicInfo.getNickName());
        sdMyphoto.setImageURI(Constants.userBasicInfo.getImage());
        tvViplv.setText(Constants.userBasicInfo.getDictionaryName());
    }
//充值会员
    @OnClick(R.id.tv_recharge)
    public void onViewClicked() {

        final PopupWindow popupWindow=CreatPopwindows.creatWWpopwindows(this,R.layout.pop_pay);
        View view=popupWindow.getContentView();
        RadioGroup radioGroup=view.findViewById(R.id.rg_pay);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rd_alipay:
                        alipayPresenter.alimemberup();
                        popupWindow.dismiss();
                        loadingPop =CreatPopwindows.creatMMpopwindows(MyVipCentreActivity.this,R.layout.pop_loading);

                        loadingPop.showAtLocation(tvViplv, Gravity.CENTER, 0, 0);
                        break;
                    case R.id.rd_wxpay:
                        alipayPresenter.wxmemberup();
                        popupWindow.dismiss();
                        loadingPop =CreatPopwindows.creatMMpopwindows(MyVipCentreActivity.this,R.layout.pop_loading);
                        loadingPop.showAtLocation(tvViplv, Gravity.CENTER, 0, 0);
                        break;
                }
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);



    }

    @Override
    public void alipaysuccessful() {
        if (null!=loadingPop&&loadingPop.isShowing()){
            loadingPop.dismiss();
        }
        BToast.success(this).text("支付成功").show();
    }

    @Override
    public void alipayfail() {

    }
}
