package com.example.shixian.http;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2017/11/25.
 */

public abstract class BaseCallBack<T> {

    public Type mType;

    //把T转换成Type(解析T的类型)
    static Type getSuperclassTypeParameter(Class<?> subclass) {

        Type superclass = subclass.getGenericSuperclass();

        if (superclass instanceof Class) {

            throw new RuntimeException("Missing type parameter.");

        }

        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public BaseCallBack() {

        mType = getSuperclassTypeParameter(getClass());
    }
    //发起请求之前需要处理的逻辑
    public abstract void onRequestBefore();
    //请求网络出现不可恢复错误时调用该方法
    public abstract void onFailure(Call call, IOException e);
    //状态码大于200，小于300 时调用此方法
    public abstract void onSuccess(Response response, T t);
    //请求成功时需要处理的逻辑
    public abstract void onAfterResponse(Response response);
    //状态码400，404，403，500等时调用此方法
    public abstract void onError(int code, Exception e);
    //Token 验证失败。状态码401,402,403 等时调用此方法
    public abstract void onTokenError(int code);
}
