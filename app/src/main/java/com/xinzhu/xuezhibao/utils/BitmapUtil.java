package com.xinzhu.xuezhibao.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BitmapUtil {
    private static BitmapUtil instance=new BitmapUtil();
    public static BitmapUtil getinstance(){
        return instance;
    }
    private BitmapUtil() {
    }

    public Bitmap toConformBitmap(Bitmap background, Bitmap foreground,int size,int x) {
        if( background == null ) {
            return null;
        }

        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        //int fgWidth = foreground.getWidth();
        //int fgHeight = foreground.getHeight();
        //create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
        Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        //draw bg into
        cv.drawBitmap(background, 0, 0, null);//在 0，0坐标开始画入bg
        //draw fg into
        cv.drawBitmap(foreground, x, size, null);//在 0，0坐标开始画入fg ，可以从任意位置画入
        //save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);//保存
        //store
        cv.restore();//存储
        return newbmp;
    }
    public Bitmap addBitmap(Bitmap background, Bitmap foreground) {
        if( background == null ) {
            return null;
        }
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        //int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        //创建一个新的高度相加的位图
        Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight+fgHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        //draw bg into
        cv.drawBitmap(background, 0, 0, null);//在 0，0坐标开始画入bg
        //draw fg into
        cv.drawBitmap(foreground, 0, bgHeight, null);//在 0，0坐标开始画入fg ，可以从任意位置画入
        //save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);//保存
        //store
        cv.restore();//存储
        return newbmp;
    }

    //保存bitmap为文件
    public  void saveFile(Context context,Bitmap bmp ,String fileName) throws IOException {

        File dirFile = new File(Environment.getExternalStorageDirectory()+ "/FenTuan_IMG/");
        if(!dirFile.exists()){
            Log.d("路径不存在","sd");
            dirFile.mkdir();
        }
        File myCaptureFile = new File(dirFile,fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bmp.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }




    //缩放BITMAP
    public Bitmap resizeImage(Bitmap bitmap, float scale) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }
    //创建二维码
    public static Bitmap createQRBitmap(String str,int size){
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, size, size);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e){
            e.printStackTrace();
        } catch (IllegalArgumentException iae){ // ?
            return null;
        }
        return bitmap;
    }
    public static byte[] bitmap2array(Bitmap bmp){
        int bytes = bmp.getByteCount();

        ByteBuffer buf = ByteBuffer.allocate(bytes);
        bmp.copyPixelsToBuffer(buf);

        byte[] byteArray = buf.array();
return byteArray;
    }
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {

        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0,i, j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 50,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                Log.d("bimap大小",arrayOfByte.length+"");
                return arrayOfByte;
            } catch (Exception e) {
                //F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

    }
}
