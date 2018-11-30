package com.xinzhu.xuezhibao.view.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.OrderBean;
import com.xinzhu.xuezhibao.presenter.MyOrederPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.OrderDetailInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity implements OrderDetailInterface {
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
    @BindView(R.id.tv_price2)
    TextView tvPrice2;
    @BindView(R.id.constraintLayout2)
    ConstraintLayout constraintLayout2;
    @BindView(R.id.linearLayout29)
    LinearLayout linearLayout29;
    @BindView(R.id.textView28)
    TextView textView28;
    MyOrederPresenter myOrederPresenter;
    OrderBean orderBean;
    String myaddressid;
    @BindView(R.id.ll_action)
    LinearLayout llAction;
    @BindView(R.id.tv_ordernum)
    TextView tvOrdernum;
    @BindView(R.id.tv_ordertime)
    TextView tvOrdertime;
    @BindView(R.id.tv_deliver_goodstime)
    TextView tvDeliverGoodstime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        ButterKnife.bind(this);
        orderBean = (OrderBean) getIntent().getSerializableExtra(Constants.INTENT_ID);
        myOrederPresenter = new MyOrederPresenter(this);
        if (null != orderBean) {
            if (orderBean.getPrice() > 0) {
                tvPrice2.setVisibility(View.VISIBLE);
                tvPrice2.setText("￥ " + orderBean.getPrice());
            }
            if (orderBean.getOrderPrice() > 0) {
                tvPrice.setVisibility(View.VISIBLE);
                tvPrice.setText(orderBean.getOrderPrice() + "积分");
            }
            imageView19.setImageURI(orderBean.getPicture());
            tvTitle.setText(orderBean.getName());
            tvOrdernum.setText("订单编号：" + orderBean.getOrderNum());
            tvOrdertime.setText("订单时间:" + TimeUtil.getWholeTime2(orderBean.getCreateTime()));
            tvDeliverGoodstime.setText("发货时间:" + TimeUtil.getWholeTime2(orderBean.getCreateTime()));
            //因为除了完成状态外，其他状态的订单的type为空
            if (null != orderBean.getType()) {
                if (!orderBean.getType().equals("3")) {
                    cslAddress.setVisibility(View.GONE);
                    llAction.setVisibility(View.GONE);
                }
                //是否为待收货订单
                if (!orderBean.getState().equals("3")) {
                    llAction.setVisibility(View.GONE);
                }
                if (orderBean.getType().equals("1")) {
                    tvOrdertype.setText("家庭教育课程");
                } else if (orderBean.getType().equals("2")) {
                    tvOrdertype.setText("视频课程");
                } else if (orderBean.getType().equals("3")) {
                    tvOrdertype.setText("积分商城");
                    myOrederPresenter.selectgoodsorderbyid(orderBean.getOrderId());
                }
            } else {
                tvOrdertype.setText("积分商城");
                myOrederPresenter.selectgoodsorderbyid(orderBean.getOrderId());
            }

        }


        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void getorderdetail(OrderBean orderBean) {
        tvAddress.setText(orderBean.getAddress());
        tvPhone.setText(orderBean.getNickname() + "  " + orderBean.getLinkPhone());

    }

    @Override
    public void affirmorder() {
        BToast.success(this).text("收货成功").show();
        finish();
    }

    @Override
    public void affirmorderfail(String tip) {

    }

    @OnClick(R.id.tv_actionone)
    public void onViewClicked() {
        myOrederPresenter.confirmReceipt(orderBean.getOrderId());
    }
}
