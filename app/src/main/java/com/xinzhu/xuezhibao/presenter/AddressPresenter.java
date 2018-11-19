package com.xinzhu.xuezhibao.presenter;

import android.os.Handler;
import android.os.Message;

import com.xinzhu.xuezhibao.bean.AddressBean;
import com.xinzhu.xuezhibao.bean.MyTaskBean;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.view.interfaces.AddressInterface;
import com.zou.fastlibrary.utils.JSON;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Network;

import java.util.List;

/**
 * Created by zou on 2018/11/15.
 */

public class AddressPresenter extends BasePresenter {
    AddressInterface addressInterface;

    public AddressPresenter(AddressInterface addressInterface) {
        this.addressInterface = addressInterface;
        inithandle();
    }

    public void inithandle() {
        super.handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int what = msg.what;
                String result = (String) msg.obj;
                com.zou.fastlibrary.utils.Log.d(result);
                int code = -999;
                try {
                    code = JsonUtils.getIntValue(result, "_code");
                } catch (Exception e) {
                    com.zou.fastlibrary.utils.Log.d("异常了");
                    if (null != addressInterface) {
                        addressInterface.servererr();
                    }
                }
                if (code == -100) {
                    if (null != addressInterface) {
                        addressInterface.networkerr();
                    }
                    return;
                }
                if (code == -200) {
                    if (null != addressInterface) {
                        addressInterface.networktimeout();
                    }
                    return;
                }

                String data = JsonUtils.getStringValue(result, "Data");
                if (what == 1) {
                    if (code == 100) {
                        List<AddressBean> mDatas = JSON.parseArray(data, AddressBean.class);
                        if (null != mDatas && mDatas.size() > 0) {
                            addressInterface.getaddressList(mDatas);
                        } else {
                            addressInterface.nodata();
                        }
                    }
                } else if (what == 2) {
                    if (code == 100) {
                        addressInterface.addaddress();
                    } else {
                        addressInterface.addaddressfail();
                    }

                }else if (what == 4) {
                        addressInterface.updata(code);
                }

            }
        };
    }

    public void getaddresslist() {
        String data = JsonUtils.keyValueToString("token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/my-take-address-list", handler, 1);
    }

    public void addAddress(String data) {
        data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/insert-take-address", handler, 2);
    }

    public void deleteaddress(String id) {
        String data = JsonUtils.keyValueToString2("addressId", id, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/delete-take-address", handler, 3);
    }
    public void updataaddress(String data) {
         data = JsonUtils.addKeyValue(data, "token", Constants.TOKEN);
        Network.getnetwork().postJson(data, Constants.URL + "/app/update-take-address", handler, 4);
    }
}
