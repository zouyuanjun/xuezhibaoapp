package com.xinzhu.xuezhibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.bravin.btoast.BToast;
import com.xinzhu.xuezhibao.MyApplication;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.adapter.MyAddressAdapter;
import com.xinzhu.xuezhibao.bean.AddressBean;
import com.xinzhu.xuezhibao.presenter.AddressPresenter;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.AddressInterface;
import com.zou.fastlibrary.activity.BaseActivity;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zou on 2018/11/15.
 */

public class MyAddressActivity extends BaseActivity implements AddressInterface {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.rv_myaddress)
    RecyclerView rvMyaddress;
    AddressPresenter addressPresenter;
    MyAddressAdapter addressAdapter;
    List<AddressBean> addressBeanList = new ArrayList<>();
String source; //标记是否由下订单页面跳转过来
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaddress);
        ButterKnife.bind(this);
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        appbar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAddressActivity.this, AddAddressActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        source=getIntent().getStringExtra(Constants.INTENT_ID);
        addressAdapter = new MyAddressAdapter(this, addressBeanList);
        addressPresenter = new AddressPresenter(this);
        addressPresenter.getaddresslist();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMyaddress.setLayoutManager(linearLayoutManager);
        rvMyaddress.setAdapter(addressAdapter);
        addressAdapter.setOnItemClickListener(new MyAddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            if (source.isEmpty()){
                return;
            }
            Intent intent=new Intent();
            intent.putExtra(Constants.INTENT_ID,addressBeanList.get(position));
            setResult(2,intent);
            finish();
            }
            @Override
            public void onDetalClick(View view, int position) {
                addressPresenter.deleteaddress(addressBeanList.get(position).getAddressId());
                addressBeanList.remove(position);
                addressAdapter.notifyItemRemoved(position);
            }
            @Override
            public void onEditClick(View view, int position) {
                AddressBean addressBean=addressBeanList.get(position);
                Intent intent = new Intent(MyAddressActivity.this, AddAddressActivity.class);
                intent.putExtra(Constants.INTENT_ID,addressBean);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onCheckboxClick(View view, int position) {
              CheckBox checkBox= view.findViewById(R.id.cb_address);
                AddressBean addressBean=addressBeanList.get(position);
              if (checkBox.isChecked()){
                 addressBean.setIsDefault(1);

              }else {
                  addressBean.setIsDefault(0);
              }
                addressPresenter.updataaddress(JsonUtils.objectToString(addressBean));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && null != data) {
            addressPresenter.addAddress(data.getStringExtra(Constants.INTENT_ID));
        }else if (resultCode == 2 && null != data) {
            addressPresenter.updataaddress(data.getStringExtra(Constants.INTENT_ID));
        }
    }

    @Override
    public void getaddressList(List<AddressBean> list) {
        addressBeanList.addAll(list);
        addressAdapter.notifyDataSetChanged();
    }

    @Override
    public void setDefend(int code) {

    }

    @Override
    public void deliteaddress(int code) {

    }

    @Override
    public void nodata() {

    }

    @Override
    public void addaddress() {
        addressBeanList.clear();
        addressPresenter.getaddresslist();
        BToast.success(this).text("添加成功").show();
    }

    @Override
    public void addaddressfail() {
        BToast.error(this).text("添加失败了").show();
    }

    @Override
    public void updata(int code) {
        if (code==100){
            addressBeanList.clear();
            addressPresenter.getaddresslist();
            BToast.success(this).text("修改成功").show();
        }else {
            BToast.success(this).text("修改失败").show();
        }
    }
}
