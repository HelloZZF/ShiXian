package com.example.shixian;

import android.app.Application;

/**
 * Created by admin on 2017/12/27.
 */

public class ShiXianApplication extends Application{

    private static ShiXianApplication shiXianApplication;

    public static ShiXianApplication getInstance(){
        return shiXianApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
