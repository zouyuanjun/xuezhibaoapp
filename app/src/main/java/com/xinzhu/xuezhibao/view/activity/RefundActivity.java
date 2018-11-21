package com.xinzhu.xuezhibao.view.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.OrderBean;
import com.xinzhu.xuezhibao.presenter.MyOrederPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.MyOrderInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.ui.ShapeCornerBgView;
import com.zou.fastlibrary.utils.CreatPopwindows;
import com.zou.fastlibrary.utils.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RefundActivity extends BaseActivity implements MyOrderInterface {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.sdv_photo)
    SimpleDraweeView sdvPhoto;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.ed_conent)
    EditText edConent;
    OrderBean orderBean;
    MyOrederPresenter myOrederPresenter;
    @BindView(R.id.im_loading)
    ImageView imLoading;
boolean cancommint=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyrefund);
        ButterKnife.bind(this);
        orderBean = (OrderBean) getIntent().getSerializableExtra(Constants.INTENT_ID);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (null != orderBean) {
            sdvPhoto.setImageURI(orderBean.getPicture());
            tvTitle.setText(orderBean.getName());
            tvPrice.setText("￥" + orderBean.getPrice());
        }
        appbar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edConent.getText().toString();
                if (StringUtil.isEmpty(s)) {
                    BToast.error(RefundActivity.this).text("必须填写退款理由").show();
                    return;
                }
                if (cancommint){
                    cancommint=false;
                    imLoading.setVisibility(View.VISIBLE);
                    AnimationDrawable animationDrawable= (AnimationDrawable) imLoading.getDrawable();
                    animationDrawable.start();
                    myOrederPresenter = new MyOrederPresenter(RefundActivity.this);
                    myOrederPresenter.applyrefund(orderBean.getOrderId(), s);
                }

            }
        });
    }

    @Override
    public void getOrderList(List<OrderBean> orderBeans) {

    }

    @Override
    public void noMoredata() {

    }

    @Override
    public void applyrefund() {
        final PopupWindow popupWindow=CreatPopwindows.creatWWpopwindows(this,R.layout.pop_refund);
        View view=popupWindow.getContentView();
        ShapeCornerBgView shapeCornerBgView=view.findViewById(R.id.scb_iknow);
        shapeCornerBgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                finish();
            }
        });
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    @Override
    public void applyrefundfail(String tip) {

    }

    @Override
    public void confirmReceipt() {

    }

    @Override
    public void confirmReceiptfail(String tip) {

    }
}
