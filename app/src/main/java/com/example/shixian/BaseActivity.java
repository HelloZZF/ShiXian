package com.example.shixian;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.bean.User;
import com.example.shixian.fragment.LoginFragment;
import com.example.shixian.utils.ActivityCollector;

/**
 * Created by admin on 2017/7/24.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public void setStatusbar() {
        //让活动的布局显示在状态栏上并且把状态栏设置为透明色
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void FitsStatusBar(View statusBar) {

        int statusbarHeight = 0;

        //通过反射获取状态栏高度
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusbarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        //设置占位View的高度
        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
        layoutParams.height = statusbarHeight;

    }

    public void startActivity(Intent intent, boolean isNeedLogin) {

        User user = ShiXianApplication.getInstance().getUser();
        if (isNeedLogin == true){

            if (user != null){
                super.startActivity(intent);
            }else {
                ShiXianApplication.getInstance().putIntent(intent);
                Intent LoginIntent = new Intent(this, UserLoginActivity.class);
                super.startActivity(LoginIntent);
            }
        }else {

            super.startActivity(intent);
        }
    }
}
