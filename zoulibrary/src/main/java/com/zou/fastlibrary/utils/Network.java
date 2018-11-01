package com.zou.fastlibrary.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private static Network instance = new Network();

    public static Network getnetwork() {
        return instance;
    }

    private Network() {
    }

    public void postform(String key, String value, String url, final Handler handler, final int i) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();//创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(key, value).build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketTimeoutException) {
                    //判断超时异常
                    Message message = new Message();
                    String s = "{\"message\":\"请求超时\",\"_code\":\"-200\",\"data\":[{}]}";
                    message.what = i;
                    message.obj = s;
                    handler.sendMessage(message);
                    Log.d("555", "请求超时");
                }
                if (e instanceof ConnectException) {
                    ////判断连接异常，
                    Message message = new Message();
                    String s = "{\"message\":\"连接异常\",\"_code\":\"-100\",\"data\":[{}]}";
                    message.what = i;
                    message.obj = s;
                    handler.sendMessage(message);
                    Log.d("555", "连接异常");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                String s = response.body().string();
                message.what = i;
                message.obj = s;
                handler.sendMessage(message);
            }
        });
    }


    /**
     * post提交Json数据
     *
     * @param date    要提交的数据
     * @param url     访问地址
     * @param handler 回调handler
     * @param i       请求标志
     */
    public void postJson(String date, String url, final Handler handler, final int i) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();//创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        String jsonStr = date;//json数据.
        Log.d("5555", "发送请求URL" + url + "请求体" + jsonStr);
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
                    Message message = new Message();
                    String s = "{\"message\":\"请求超时\",\"_code\":-200,\"data\":[{}]}";
                    message.what = i;
                    message.obj = s;
                    handler.sendMessage(message);
                    Log.d("555", "请求超时");
                }
                if (e instanceof ConnectException) {
                    ////判断连接异常，
                    Message message = new Message();
                    String s = "{\"message\":\"连接异常\",\"_code\":-100,\"data\":[{}]}";
                    message.what = i;
                    message.obj = s;
                    handler.sendMessage(message);
                    Log.d("555", "连接异常");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                String s = response.body().string();
                message.what = i;
                message.obj = s;
                handler.sendMessage(message);
            }
        });
    }
    public void  uploadimg(String data, String url, String paths, final Handler handler){
        HashMap<String,String> map=new HashMap<>();
        map.put("token",data);
        List<String> list=new ArrayList<>();
        list.add(paths);
        uploadimg(map,url,list,handler);
    }

    public void uploadimg(HashMap<String, String> data, String url, List<String> paths, final Handler handler) {
        OkHttpClient mOkHttpClent = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (String keyset : data.keySet()) {
            builder.addFormDataPart(keyset, data.get(keyset));
        }
        for (String path : paths) {
            com.zou.fastlibrary.utils.Log.d(path);
            builder.addFormDataPart("file", "file.jpg", RequestBody.create(MediaType.parse("image/png"), new File(path)));
        }

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketTimeoutException) {
                    //判断超时异常
                    Message message = new Message();
                    String s = "{\"message\":\"请求超时\",\"_code\":-200,\"data\":[{}]}";
                    message.obj = s;
                    handler.sendMessage(message);
                    Log.d("555", "请求超时");
                }
                if (e instanceof ConnectException) {
                    ////判断连接异常，
                    Message message = new Message();
                    String s = "{\"message\":\"连接异常\",\"_code\":-100,\"data\":[{}]}";
                    message.obj = s;
                    handler.sendMessage(message);
                    Log.d("555", "连接异常");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                com.zou.fastlibrary.utils.Log.d("上传成功" + response.body().string());
            }
        });
    }


    /**
     * 发送请求头的连接
     *
     * @param date
     * @param url
     * @param handler
     * @param headerkey
     * @param headervalue
     * @param i
     */
    public void connectnet(String date, String url, final Handler handler, String headerkey, String headervalue, final int i) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();//创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        String jsonStr = date;//json数据.
        Log.d("5555", "发送请求URL" + url + "请求体" + jsonStr);
        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .addHeader(headerkey, headervalue)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketTimeoutException) {
                    //判断超时异常
                    Message message = new Message();
                    String s = "{\"message\":\"请求超时\",\"retCode\":\"-2\",\"data\":[{}]}";
                    message.what = i;
                    message.obj = s;
                    handler.sendMessage(message);
                    Log.d("555", "请求超时");
                }
                if (e instanceof ConnectException) {
                    ////判断连接异常，
                    Message message = new Message();
                    String s = "{\"message\":\"连接异常\",\"retCode\":\"-1\",\"data\":[{}]}";
                    message.what = i;
                    message.obj = s;
                    handler.sendMessage(message);
                    Log.d("555", "连接异常");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                String s = response.body().string();
                message.what = i;
                message.obj = s;
                handler.sendMessage(message);
            }
        });
    }
}
