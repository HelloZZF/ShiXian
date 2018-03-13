package com.example.shixian.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by admin on 2018/1/3.
 */

public class OkHttpManager {

    private static OkHttpManager mInstance;
    private OkHttpClient mClient;
    private Handler mHandler;
    private Gson gson;

    public static final int TOKEN_MISSING=401;// token 丢失
    public static final int TOKEN_ERROR=402; // token 错误
    public static final int TOKEN_EXPIRE=403; // token 过期

    private OkHttpManager(){

        initOkHttp();
        mHandler = new Handler(Looper.getMainLooper());
        gson = new Gson();
    }

    public static synchronized OkHttpManager getInstance(){

        if (mInstance == null){
            mInstance = new OkHttpManager();
        }
        return mInstance;
    }

    private void initOkHttp(){

        //okHttp的配置
        mClient = new OkHttpClient().newBuilder()
                .writeTimeout(30000,TimeUnit.SECONDS)
                .readTimeout(30000, TimeUnit.SECONDS)
                .connectTimeout(30000, TimeUnit.SECONDS)
                .build();
    }

    public void request(SimpleHttpClient client, final BaseCallBack callBack){
        //发起请求之前的逻辑
        callBack.onRequestBefore();

        if (callBack == null){
            throw new NullPointerException("callBack is null");
        }

        mClient.newCall(client.buildRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendOnFailureMessage(callBack, call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()){

                    sendOnAfterResponse(callBack, response);

                    String result = response.body().string();

                    if (callBack.mType == null || callBack.mType == String.class){

                        sendOnSuccessMessage(callBack, response, result);

                    }else{

                        try{

                            Object object = gson.fromJson(result, callBack.mType);
                            sendOnSuccessMessage(callBack, response, object);

                        }catch(com.google.gson.JsonParseException e){
                            sendOnErrorMessage(callBack, response.code(), e);
                        }

                    }

                }else if (response.code() == TOKEN_ERROR||response.code() == TOKEN_EXPIRE ||response.code() == TOKEN_MISSING){
                    sendOnTokenErrorMessage(callBack, response.code());
                }else {
                    sendOnErrorMessage(callBack, response.code(), null);
                }
                //回收流
                if (response.body() != null){
                    response.body().close();
                }

            }
        });
    }

    private void sendOnSuccessMessage(final BaseCallBack callBack, final Response response, final Object result){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(response, result);
            }
        });
    }

    private void sendOnFailureMessage(final BaseCallBack callBack, final Call call, final IOException e){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(call, e);
            }
        });
    }

    private void sendOnErrorMessage(final BaseCallBack callBack, final int code, final Exception e){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onError(code, e);
            }
        });
    }

    private void sendOnTokenErrorMessage(final BaseCallBack callBack, final int code){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onTokenError(code);
            }
        });
    }

    private void sendOnAfterResponse(final BaseCallBack callBack, final Response response){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onAfterResponse(response);
            }
        });
    }


}
