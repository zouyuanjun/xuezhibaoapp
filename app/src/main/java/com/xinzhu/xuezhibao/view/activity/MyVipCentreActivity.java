package com.xinzhu.xuezhibao.view.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.UserBasicInfo;
import com.xinzhu.xuezhibao.messagebean.PayResultMessage;
import com.xinzhu.xuezhibao.presenter.AlipayPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.PayInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.CreatPopwindows;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;
import com.zou.fastlibrary.utils.ScreenUtil;
import com.zou.fastlibrary.utils.StringUtil;

import org.greenrobot.eventbus.Subscribe;

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
    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result= (String) msg.obj;
            Log.d(result);
            switch (msg.what) {
                case 2: {
                    try {

                        String data = JsonUtils.getStringValue(result, "Data");
                        String describeContent = JsonUtils.getStringValue(data, "describeContent");
                        String price=JsonUtils.getStringValue(data,"memberPrice");
                        if (!StringUtil.isEmpty(describeContent)) {
                            tvIntroduce.setText(Html.fromHtml(describeContent));
                            tvIntroduce.setMovementMethod(ScrollingMovementMethod.getInstance());
                            tvRecharge.setText("￥"+price+"开通会员");
                        } else {
                            tvIntroduce.setText("获取数据失败，请稍后再试");
                        }
                    } catch (Exception e) {
                        tvIntroduce.setText("获取数据失败，请稍后再试");
                    }
                    break;
                }
                case 1:{

                    int code=JsonUtils.getIntValue(result,"Code");
                    if (code==100){
                        String data=JsonUtils.getStringValue(result,"Data");
                        Constants.userBasicInfo = JsonUtils.stringToObject(data, UserBasicInfo.class);
                        if (null!=tvUsername){
                            tvViplv.setText(Constants.userBasicInfo.getDictionaryName());
                        }

                    }
                    break;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vipcenter);
        ButterKnife.bind(this);
        //获取会员规则描述
        String data = JsonUtils.keyValueToString("describeType", 2);
        Network.getnetwork().postJson(data, Constants.URL + "/guest/integral-rules", handler, 2);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        alipayPresenter = new AlipayPresenter(this,this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null!=Constants.userBasicInfo){
            if (Constants.userBasicInfo.getDictionaryId()!=1){
                tvRecharge.setVisibility(View.GONE);
            }
            tvUsername.setText(Constants.userBasicInfo.getNickName());
            sdMyphoto.setImageURI(Constants.userBasicInfo.getImage());
            tvViplv.setText(Constants.userBasicInfo.getDictionaryName());
        }

    }

    //充值会员
    @OnClick(R.id.tv_recharge)
    public void onViewClicked() {

        final PopupWindow popupWindow = CreatPopwindows.creatpopwindows(this, R.layout.pop_pay);
        View view = popupWindow.getContentView();
        RadioGroup radioGroup = view.findViewById(R.id.rg_pay);
        TextView textView = view.findViewById(R.id.tv_cancle);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rd_alipay:
                        alipayPresenter.alimemberup();
                        popupWindow.dismiss();
                        loadingPop = CreatPopwindows.creatMMpopwindows(MyVipCentreActivity.this, R.layout.pop_loading);
                        loadingPop.showAtLocation(tvViplv, Gravity.CENTER, 0, 0);
                        break;
                    case R.id.rd_wxpay:
                        alipayPresenter.wxmemberup();
                        popupWindow.dismiss();
                        loadingPop = CreatPopwindows.creatMMpopwindows(MyVipCentreActivity.this, R.layout.pop_loading);
                        loadingPop.showAtLocation(tvViplv, Gravity.CENTER, 0, 0);
                        break;
                }
            }
        });
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, ScreenUtil.getNavigationBarHeight(MyVipCentreActivity.this));
    }

    @Override
    public void paysuccessful() {
        if (null != loadingPop && loadingPop.isShowing()) {
            loadingPop.dismiss();
        }
        BToast.success(this).text("支付成功").show();
        //刷新用户信息
        if (!StringUtil.isEmpty(Constants.TOKEN)){
            String data = JsonUtils.keyValueToString("token", Constants.TOKEN);
            Network.getnetwork().postJson(data, Constants.URL + "/app/find-by-account", handler, 1);
        }

    }

    @Override
    public void payfail() {
        if (null!=loadingPop&&loadingPop.isShowing()){
            loadingPop.dismiss();
        }
        BToast.error(this).text("支付失败").show();
    }

    @Override
    public void orderisexit() {
        if (null != loadingPop && loadingPop.isShowing()) {
            loadingPop.dismiss();
        }
        BToast.success(this).text("您已经是会员用户").show();
    }

    @Override
    public void creatOrderfail(String tips) {
        if (null != loadingPop && loadingPop.isShowing()) {
            loadingPop.dismiss();
        }
        BToast.error(this).text("抱歉"+tips).show();
    }

    @Subscribe
    public void PayMessage(PayResultMessage messageEvent) {
        int code = messageEvent.getCode();
        if (code==0){
            if (StringUtil.isEmpty(Constants.wxOrdernum)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("支付结果");
                builder.setMessage("您可能支付成功，但是由于网络异常，我们无法获取支付结果，请联系客服人员为您核实");
                builder.show();
            }else {
                alipayPresenter.checkWxPay();
            }
        }else if (code==1){
            BToast.error(this).text("取消支付").show();
        }else {
            if (null != loadingPop && loadingPop.isShowing()) {
                loadingPop.dismiss();
            }
            BToast.error(this).text("微信支付异常").show();
        }
    }
}
