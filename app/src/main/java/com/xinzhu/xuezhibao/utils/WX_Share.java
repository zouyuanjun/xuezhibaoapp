package com.xinzhu.xuezhibao.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zou.fastlibrary.utils.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.xinzhu.xuezhibao.utils.Constants.api;

public class WX_Share {

    private static WX_Share instance = new WX_Share();

    public static WX_Share getinstance() {
        return instance;
    }

    private WX_Share() {
    }


    //分享多张照片到朋友圈
    public static void sharePhotosToWX(Context context, String text, List<String> imageUris) {
        ArrayList<Uri> uriArrayList = new ArrayList<>();
        uriArrayList.clear();

        for (int i = 0; i < imageUris.size(); i++) {
            File file = new File(imageUris.get(i));
            if (!file.exists()) {
                return;
            }
            //   uriArrayList.add(Uri.fromFile(file));
            uriArrayList.add(FileProvider.getUriForFile(context, "com.lejiaokeji.fentuan.fileprovider", file));
            Log.d("sdf", FileProvider.getUriForFile(context, "com.lejiaokeji.fentuan.fileprovider", file).getPath());
            Log.d("string", FileProvider.getUriForFile(context, "com.lejiaokeji.fentuan.fileprovider", file).toString());
        }
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setType("image/jpg");
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriArrayList);
        intent.putExtra("Kdescription", text);
        context.startActivity(intent);
    }

    //分享单张照片到朋友圈
    public static void sharePhotoToWX(Context context, String text, String imagepath) {
        File file = new File(imagepath);
        Uri uri = FileProvider.getUriForFile(context, "com.xinzhu.xuezhibao.provider", file);
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/jpg");
        context.startActivity(intent);
    }

    //判断是否安装微信客户端
    private static boolean uninstallSoftware(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            if (packageInfo != null) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void sharePicByFile(Context context, String picFile, String tag) {
        Bitmap pic = BitmapFactory.decodeFile(picFile);
        if (null == pic) {
            return;
        }
        if (!uninstallSoftware(context, "com.tencent.mm")) {
            Toast.makeText(context, "微信没有安装！", Toast.LENGTH_SHORT).show();
            return;
        }
        api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
        api.registerApp(Constants.APP_ID);

        WXImageObject imageObject = new WXImageObject( ImageUtils.resizeImage(pic, (float) 0.5));
        //这个构造方法中自动把传入的bitmap转化为2进制数据,或者你直接传入byte[]也行
        //注意传入的数据不能大于10M,开发文档上写的

        WXMediaMessage msg = new WXMediaMessage();  //这个对象是用来包裹发送信息的对象
        msg.mediaObject = imageObject;
        //msg.mediaObject实际上是个IMediaObject对象,
        //它有很多实现类,每一种实现类对应一种发送的信息,
        //比如WXTextObject对应发送的信息是文字,想要发送文字直接传入WXTextObject对象就行
        Bitmap thumbBitmap = Bitmap.createScaledBitmap(pic, 150, 150, true);
        pic.recycle();
        msg.thumbData = bitmap2byteArray(thumbBitmap);
        //在这设置缩略图
        //官方文档介绍这个bitmap不能超过32kb
        //如果一个像素是8bit的话换算成正方形的bitmap则边长不超过181像素,边长设置成150是比较保险的
        //或者使用msg.setThumbImage(thumbBitmap);省去自己转换二进制数据的过程
        //如果超过32kb则抛异常
        SendMessageToWX.Req req = new SendMessageToWX.Req();    //创建一个请求对象
        req.message = msg;  //把msg放入请求对象中
        req.scene = SendMessageToWX.Req.WXSceneTimeline;    //设置发送到朋友圈
        //req.scene = SendMessageToWX.Req.WXSceneSession;   //设置发送给朋友
        req.transaction = tag;  //这个tag要唯一,用于在回调中分辨是哪个分享请求
        boolean b = api.sendReq(req);   //如果调用成功微信,会返回true
    }

    /**
     * bitmap 转  byte[]数组
     */
    public static byte[] bitmap2byteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
}
