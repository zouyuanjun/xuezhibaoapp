package com.xinzhu.xuezhibao.view.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bravin.btoast.BToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.bean.UserBasicInfo;
import com.xinzhu.xuezhibao.utils.Constants;
import com.xinzhu.xuezhibao.utils.SelectPhotoUtils;
import com.zou.fastlibrary.ui.CustomNavigatorBar;
import com.zou.fastlibrary.utils.EditTextUtil;
import com.zou.fastlibrary.utils.JsonUtils;
import com.zou.fastlibrary.utils.Log;
import com.zou.fastlibrary.utils.Network;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.model.TResult;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditAllActivity extends TakePhotoActivity {
    @BindView(R.id.appbar)
    CustomNavigatorBar appbar;
    @BindView(R.id.tv_jifenshop)
    TextView tvJifenshop;
    @BindView(R.id.sd_myphoto)
    SimpleDraweeView sdMyphoto;
    @BindView(R.id.ed_vipname)
    EditText edVipname;
    @BindView(R.id.ed_studenname)
    EditText edStudenname;
    @BindView(R.id.ed_studennage)
    EditText edStudennage;
    @BindView(R.id.ed_fathername)
    EditText edFathername;
    @BindView(R.id.ed_mothername)
    EditText edMothername;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    Context context;
    Handler handler=new Handler(){

    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editalluserbase);
        ButterKnife.bind(this);
        context=this;
        appbar.setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        appbar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditTextUtil.getString(edVipname).isEmpty()){
                    BToast.custom(context).text("昵称还没有填写哦").show();
                    return;
                }
                if (EditTextUtil.getString(edStudenname).isEmpty()){
                    BToast.custom(context).text("学生姓名还没有填写哦").show();
                    return;
                }
                if (EditTextUtil.getString(edStudennage).isEmpty()){
                    BToast.custom(context).text("学生年龄还没有填写哦").show();
                    return;
                }
                if (EditTextUtil.getString(edFathername).isEmpty()&&EditTextUtil.getString(edMothername).isEmpty()){
                    BToast.custom(context).text("父亲或母亲姓名必须填写一个").show();
                    return;
                }
                String address=tvAddress.getText().toString();
                if (null==address||address.isEmpty()){
                    BToast.custom(context).text("地区还没选择哦").show();
                    return;
                }

                String vipname=EditTextUtil.getString(edVipname);
                String studentname=EditTextUtil.getString(edStudenname);
                int studentage=Integer.parseInt(EditTextUtil.getString(edStudennage));
                String fathername=EditTextUtil.getString(edFathername);
                String mathername=EditTextUtil.getString(edMothername);
                UserBasicInfo userBasicInfo=new UserBasicInfo(vipname,studentname,fathername,mathername,address,studentage);
                String data=JsonUtils.objectToString(userBasicInfo);
                Network.getnetwork().postJson(data,Constants.URL+"/member/update-member",handler,2);
            }
        });

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
        Network.getnetwork().uploadimg(Constants.TOKEN,Constants.URL+"/guest/image-upload",result.getImage().getCompressPath(),handler);

    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);

    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @OnClick(R.id.ll_header)
    public void onViewClicked() {
        SelectPhotoUtils.selectphoto(1, getTakePhoto());
    }
}
