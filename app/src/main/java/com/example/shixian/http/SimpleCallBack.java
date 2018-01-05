package com.example.shixian.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2018/1/3.
 */

public abstract class SimpleCallBack<T> extends BaseCallBack<T> {
    @Override
    public void onRequestBefore() {

    }

    @Override
    public void onFailure(Call call, IOException e) {

    }


    @Override
    public void onAfterResponse(Response response) {

    }


    @Override
    public void onTokenError(int code) {

    }
}
