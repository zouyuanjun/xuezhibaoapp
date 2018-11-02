package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.PayResult;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.OrderInfoUtil2_0;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyVipCentreActivity extends BaseActivity {
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
Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 1: {
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Toast.makeText(MyVipCentreActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(MyVipCentreActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                break;
        }
    }
};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vipcenter);
        ButterKnife.bind(this);
        tvUsername.setText(Constants.userBasicInfo.getNickName());
        sdMyphoto.setImageURI(Constants.userBasicInfo.getImage());

    }

    @OnClick(R.id.tv_recharge)
    public void onViewClicked() {

        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Constants.ALAPPID, true);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, Constants.privateKEY, true);
        final String orderInfo = orderParam + "&" + sign;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(MyVipCentreActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
