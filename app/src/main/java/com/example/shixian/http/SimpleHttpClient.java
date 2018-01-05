package com.example.shixian.http;


import android.net.Uri;
import android.os.Build;
import android.renderscript.Sampler;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by admin on 2018/1/3.
 */

public class SimpleHttpClient {

    private Builder mBuilder;

    private SimpleHttpClient(Builder builder){
        this.mBuilder = builder;
    }

    public Request buildRequest(){

        Request.Builder builder = new Request.Builder();

        if (mBuilder.method == "GET"){
            builder.get();
            builder.url(buildGetRequestParam());
        }else if (mBuilder.method == "POST"){

            try {
                builder.post(buildRequestBody());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            builder.url(mBuilder.url);
        }

        return builder.build();
    }

    private String buildGetRequestParam(){

        if (mBuilder.params == null){
            return this.mBuilder.url;
        }

        Uri.Builder builder = Uri.parse(mBuilder.url).buildUpon();

        Iterator iterator = mBuilder.params.entrySet().iterator();
        for (;iterator.hasNext();){
            Map.Entry entry = (Map.Entry) iterator.next();
            builder.appendQueryParameter((String) entry.getKey(), entry.getValue()==null ? "" : entry.getValue().toString());
        }

        String url = builder.build().toString();

        return url;
    }

    private RequestBody buildRequestBody() throws JSONException {

        if (mBuilder.isJsonPost){
            //添加json形式的body（一般不用）
            JSONObject jsonObject = new JSONObject();

            Iterator iterator = mBuilder.params.entrySet().iterator();
            for (;iterator.hasNext();){
                Map.Entry entry = (Map.Entry) iterator.next();
                jsonObject.put((String) entry.getKey(), entry.getValue());
            }

            String json =jsonObject.toString();

            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        }
        //添加表单形式的body
        FormBody.Builder builder = new FormBody.Builder();

        Iterator iterator = mBuilder.params.entrySet().iterator();
        for (;iterator.hasNext();){
            Map.Entry entry = (Map.Entry) iterator.next();
            builder.add((String) entry.getKey(), entry.getValue()==null ? "" : entry.getValue().toString());
        }


        return builder.build();
    }

    public void enqueue(final BaseCallBack baseCallBack){

        OkHttpManager.getInstance().request(this, baseCallBack);
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    //构建request
    public static class Builder{

        private String method = "GET";
        private boolean isJsonPost;
        private String url;
//        private List<RequestParams> params;
        private ArrayMap<String, Object> params;

        private Builder(){

        }

        public Builder url(String url){
            this.url = url;
            return this;
        }

        //默认以表单形式post数据
        public Builder post(){
            method = "POST";
            return this;
        }

        public Builder get(){
            method = "GET";
            return this;
        }

        //以json形式post数据
        public Builder jsonPost(){
            this.isJsonPost = true;
            return post();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public Builder addParams(String key, Object value){

            if (params == null){
                params = new ArrayMap<>();
            }
            params.put(key, value);
            return this;
        }

        public SimpleHttpClient build(){
            return new SimpleHttpClient(this);
        }
    }
}
