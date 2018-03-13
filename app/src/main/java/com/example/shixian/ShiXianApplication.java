package com.example.shixian;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.shixian.bean.User;
import com.example.shixian.utils.UserLocalData;

/**
 * Created by admin on 2017/12/27.
 */

public class ShiXianApplication extends Application{

    private static ShiXianApplication shiXianApplication;
    private User mUser;
    private Intent mIntent;

    public static ShiXianApplication getInstance(){
        return shiXianApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        shiXianApplication = this;
        initUser();
    }

    private void initUser() {

        this.mUser = UserLocalData.getUser(this);
    }

    public User getUser() {

        return mUser;
    }

    public void putUser(User user) {

        this.mUser = user;
        UserLocalData.putUser(this, user);
    }

    public void cleanUser() {

        this.mUser = null;
        UserLocalData.cleanUser(this);
    }

    public void putIntent(Intent intent) {

        this.mIntent = intent;
    }

    public Intent getIntent() {

        return mIntent;
    }

    public void jumpToTargetActivity(Context context){

        if (mIntent != null){

            context.startActivity(this.mIntent);
            this.mIntent = null;
        }
    }
}
