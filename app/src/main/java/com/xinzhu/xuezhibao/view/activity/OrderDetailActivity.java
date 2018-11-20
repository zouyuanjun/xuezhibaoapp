package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.AddressBean;
import com.xinzhu.xuezhibao.bean.GoodsBean;
import com.xinzhu.xuezhibao.bean.OrderBean;
import com.xinzhu.xuezhibao.presenter.MyOrederPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.PayOrderInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity implements PayOrderInterface {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.imageView23)
    ImageView imageView23;
    @BindView(R.id.textView23)
    TextView textView23;
    @BindView(R.id.textView25)
    TextView textView25;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.imageView24)
    ImageView imageView24;
    @BindView(R.id.csl_address)
    ConstraintLayout cslAddress;
    @BindView(R.id.linearLayout28)
    LinearLayout linearLayout28;
    @BindView(R.id.tv_ordertype)
    TextView tvOrdertype;
    @BindView(R.id.linearLayout24)
    LinearLayout linearLayout24;
    @BindView(R.id.imageView19)
    SimpleDraweeView imageView19;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_mintitel)
    TextView tvMintitel;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.constraintLayout2)
    ConstraintLayout constraintLayout2;
    @BindView(R.id.linearLayout29)
    LinearLayout linearLayout29;
    @BindView(R.id.textView28)
    TextView textView28;
    MyOrederPresenter myOrederPresenter;
    OrderBean orderBean;
    String myaddressid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        ButterKnife.bind(this);
        orderBean = (OrderBean) getIntent().getSerializableExtra(Constants.INTENT_ID);
        if (null != orderBean) {
            tvPrice.setText(orderBean.getPrice() + "积分");
            imageView19.setImageURI(orderBean.getPicture());
            tvTitle.setText(orderBean.getName());
        }
        myOrederPresenter = new MyOrederPresenter(this);
        myOrederPresenter.selectbyid(orderBean.getObjectId());
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void getaddress(AddressBean addressBean) {
        tvPhone.setText(addressBean.getLinkPhone());
        tvAddress.setText(addressBean.getProvince()+addressBean.getCity()+addressBean.getCounty()+addressBean.getAddress());
        myaddressid = addressBean.getAddressId();
    }

    @Override
    public void noMorepoint() {
        BToast.error(this).text("您的积分不足，无法下单").show();
    }

    @Override
    public void payfail() {
        BToast.error(this).text("奖品兑换失败，请稍后再试").show();
    }

    @Override
    public void paysuccessful() {
        BToast.error(this).text("下单成功").show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&null!=data){
            AddressBean addressBean= (AddressBean) data.getSerializableExtra(Constants.INTENT_ID);
            tvPhone.setText(addressBean.getLinkPhone());
            tvAddress.setText(addressBean.getProvince()+addressBean.getCity()+addressBean.getCounty()+addressBean.getAddress());
            myaddressid = addressBean.getAddressId();

        }
    }
}
