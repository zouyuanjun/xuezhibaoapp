package com.xinzhu.xuezhibao.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzhu.xuezhibao.R;
import com.xinzhu.xuezhibao.utils.SelectPhotoUtils;
import com.zou.fastlibrary.utils.CreatPopwindows;
import com.zou.fastlibrary.utils.FileUtils;
import com.zou.fastlibrary.utils.Network;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.model.TResult;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserBaseActivity extends TakePhotoActivity {
    @BindView(R.id.sd_myphoto)
    SimpleDraweeView sdMyphoto;
    Activity activity;
Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        String result= (String) msg.obj;
        com.zou.fastlibrary.utils.Log.d(result);
    }
};
    public UserBaseActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userbase);
        ButterKnife.bind(this);
        activity=this;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public TakePhoto getTakePhoto() {
        return super.getTakePhoto();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        sdMyphoto.setImageURI(Uri.fromFile(new File(result.getImage().getOriginalPath())));
        Network.getnetwork().uploadimg("http://192.168.1.200:8080/upload",new File(result.getImage().getCompressPath()),handler);
        Log.d("头像地址",Uri.fromFile(new File(result.getImage().getOriginalPath())).toString());
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @OnClick(R.id.sd_myphoto)
    public void onViewClicked() {
        PopupWindow pop_myphoto= CreatPopwindows.creatpopwindows(activity,R.layout.pop_myphoto);
        Button mybutton=pop_myphoto.getContentView().findViewById(R.id.bt_changephoto);
        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPhotoUtils.selectphoto(1,getTakePhoto());
            }
        });
        pop_myphoto.showAtLocation(findViewById(R.id.sd_myphoto), Gravity.CENTER_HORIZONTAL,0,0);
    }
}
