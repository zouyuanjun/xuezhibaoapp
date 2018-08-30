package com.zou.fastlibrary.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by zou on 2018/3/28.
 */

public class Network {
    private static Network instance=new Network();
    public static Network getnetwork(){
        return instance;
    }
    private Network() {
    }
    public void postform(String key ,String value, String url, final Handler handler, final int i){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();//创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(key,value).build();


        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketTimeoutException) {
                    //判断超时异常
                    Message message=new Message();
                    String s="{\"message\":\"请求超时\",\"code\":\"-200\",\"data\":[{}]}";
                    message.what=i;
                    message.obj=s;
                    handler.sendMessage(message);
                    Log.d("555","请求超时");
                }
                if (e instanceof ConnectException) {
                    ////判断连接异常，
                    Message message=new Message();
                    String s="{\"message\":\"连接异常\",\"code\":\"-100\",\"data\":[{}]}";
                    message.what=i;
                    message.obj=s;
                    handler.sendMessage(message);
                    Log.d("555","连接异常");
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message=new Message();
                String s=response.body().string();
                message.what=i;
                message.obj=s;
                handler.sendMessage(message);
            }
        });
    }





    public void postJson(String date , String url, final Handler handler, final int i){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();//创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        String jsonStr = date;//json数据.
        Log.d("5555","发送请求URL"+url+"请求体"+jsonStr);
        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketTimeoutException) {
                    //判断超时异常
                    Message message=new Message();
                    String s="{\"message\":\"请求超时\",\"code\":-200,\"data\":[{}]}";
                    message.what=i;
                    message.obj=s;
                    handler.sendMessage(message);
                    Log.d("555","请求超时");
                }
                if (e instanceof ConnectException) {
                    ////判断连接异常，
                    Message message=new Message();
                    String s="{\"message\":\"连接异常\",\"code\":-100,\"data\":[{}]}";
                    message.what=i;
                    message.obj=s;
                    handler.sendMessage(message);
                    Log.d("555","连接异常");
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
               Message message=new Message();
               String s=response.body().string();
               message.what=i;
               message.obj=s;
               handler.sendMessage(message);
            }
        });
    }

    /**
     * 发送请求头的连接
     * @param date
     * @param url
     * @param handler
     * @param headerkey
     * @param headervalue
     * @param i
     */
    public void connectnet(String date , String url, final Handler handler,String headerkey,String headervalue, final int i){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();//创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        String jsonStr = date;//json数据.
        Log.d("5555","发送请求URL"+url+"请求体"+jsonStr);
        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .addHeader(headerkey,headervalue)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketTimeoutException) {
                    //判断超时异常
                    Message message=new Message();
                    String s="{\"message\":\"请求超时\",\"retCode\":\"-2\",\"data\":[{}]}";
                    message.what=i;
                    message.obj=s;
                    handler.sendMessage(message);
                    Log.d("555","请求超时");
                }
                if (e instanceof ConnectException) {
                    ////判断连接异常，
                    Message message=new Message();
                    String s="{\"message\":\"连接异常\",\"retCode\":\"-1\",\"data\":[{}]}";
                    message.what=i;
                    message.obj=s;
                    handler.sendMessage(message);
                    Log.d("555","连接异常");
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message=new Message();
                String s=response.body().string();
                message.what=i;
                message.obj=s;
                handler.sendMessage(message);
            }
        });
    }
}
