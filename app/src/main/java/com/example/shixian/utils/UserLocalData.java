package com.example.shixian.utils;

import android.content.Context;
import android.text.TextUtils;

import com.example.shixian.Contants;
import com.example.shixian.bean.User;

/**
 * Created by zzf on 2018/2/18.
 */

public class UserLocalData {

    public static void putUser(Context context, User user){

        String user_json = JSONUtil.toJSON(user);
        PreferencesUtils.putString(context, Contants.USER_JSON, user_json);
    }

    public static User getUser(Context context){

        String user_json = PreferencesUtils.getString(context, Contants.USER_JSON);
        if (!TextUtils.isEmpty(user_json)){

            return JSONUtil.fromJson(user_json, User.class);
        }
        return null;
    }

    public static void cleanUser(Context context){

        PreferencesUtils.putString(context, Contants.USER_JSON, "");
    }
}
