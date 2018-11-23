package com.xinzhu.xuezhibao.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.bean.AliPayBean;
import com.xinzhu.xuezhibao.bean.PayResquestBean;
import com.xinzhu.xuezhibao.utils.AliPay;
import com.xinzhu.xuezhibao.view.interfaces.PayInterface;

import java.util.logging.LogRecord;

public class AlipayPresenter extends BasePresenter {
PayInterface payInterface;

    public AlipayPresenter(PayInterface payInterface) {
        this.payInterface = payInterface;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    public void alipay(PayResquestBean payResquestBean, Activity activity){
        AliPayBean aliPayBean=new AliPayBean();
        aliPayBean.setOut_trade_no(payResquestBean.getOrderNo());
        aliPayBean.setBody(payResquestBean.getBody());
        aliPayBean.setTotal_amount(payResquestBean.getPrice());
        aliPayBean.setSubject(payResquestBean.getName());
        AliPay.payorder(aliPayBean,activity,handler);
    }

}
