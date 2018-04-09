package com.example.shixian;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.shixian.bean.User;
import com.example.shixian.fragment.LoginFragment;
import com.example.shixian.utils.ActivityCollector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/24.
 */

public class BaseActivity extends AppCompatActivity {

    private  List<OnSoftKeyboardStateChangedListener> mKeyboardStateListeners;
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener;
    private boolean mIsSoftKeyboardShowing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityCollector.addActivity(this);

    }

    private void setOnGlobalLayoutListener() {

        mIsSoftKeyboardShowing = false;

        final View decorView = getWindow().getDecorView();
        mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("gggg", "1111");
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                //计算出可见屏幕高度
                int displayHeight = rect.bottom - rect.top;
                //得到整体屏幕高度
                int height = decorView.getHeight();
                //根据可见屏幕高度和整体屏幕的高度的比例判断键盘是否显示
                boolean isKeyboardShowing = (double) displayHeight / height < 0.8;
                //获取键盘的输入法工具条的高度
//                int keystatusBarHeight = 0;
//                try {
//                    Class<?> c = Class.forName("com.android.internal.R$dimen");
//                    Object obj = c.newInstance();
//                    Field field = c.getField("status_bar_height");
//                    int x = Integer.parseInt(field.get(obj).toString());
//                    keystatusBarHeight = getResources().getDimensionPixelSize(x);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                int NavigationHeight = 0;
                //判断是否支持虚拟导航栏
                if (checkDeviceHasNavigationBar()){
                    //通过反射获取虚拟导航栏高度
//                    if (isNavigationBarShow()) {
                        Resources res = getResources();
                        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
                        if (resourceId > 0) {
                            NavigationHeight = res.getDimensionPixelSize(resourceId);
                        }
//                    }


                }

                //通过反射获取状态栏高度
                int statusbarHeight = 0;
                int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    statusbarHeight = getResources().getDimensionPixelSize(resourceId);
                }

//                if (height == displayHeight + statusbarHeight) {
//                    NavigationHeight = 0;
//                }

                Log.d("nnn","naviga: "+NavigationHeight);
                //得到键盘高度
                Log.d("nnn","\ndisplay: "+displayHeight);
                Log.d("nnn","\nheight: "+ height);
                Log.d("nnn","\nstatus: "+ statusbarHeight);
                //Log.d("nnn","\nstatusbar: "+ keystatusBarHeight);
                int keyboardHeight = height - displayHeight - statusbarHeight - NavigationHeight;
                Log.d("nnn","\nkeybord: "+ keyboardHeight);

//                if (displayHeight == 682) {
//                    keyboardHeight = keyboardHeight + 96;
//                }

                if ((mIsSoftKeyboardShowing && !isKeyboardShowing) || (!mIsSoftKeyboardShowing && isKeyboardShowing)) {

                    mIsSoftKeyboardShowing = isKeyboardShowing;
                    for (int i = 0; i < mKeyboardStateListeners.size(); i++) {

                        OnSoftKeyboardStateChangedListener listener = mKeyboardStateListeners.get(i);
                        listener.OnSoftKeyboardStateChananged(mIsSoftKeyboardShowing, keyboardHeight);
                    }
                }
            }
        };
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);

    }


    //获取是否存在NavigationBar
    public boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }

    //是否显示虚拟键
//    public boolean isNavigationBarShow(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            Display display = getWindowManager().getDefaultDisplay();
//            Point size = new Point();
//            Point realSize = new Point();
//            display.getSize(size);
//            display.getRealSize(realSize);
//            boolean  result  = realSize.y!=size.y;
//            return realSize.y!=size.y;
//        }else {
//            boolean menu = ViewConfiguration.get(this).hasPermanentMenuKey();
//            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
//            if(menu || back) {
//                return false;
//            }else {
//                return true;
//            }
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);

        //移除布局变化监听 否则会内存泄漏
        if (mLayoutChangeListener != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(mLayoutChangeListener);
            } else {
                getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(mLayoutChangeListener);
            }
        }

    }

    public void setStatusbar() {
        //让活动的布局显示在状态栏上并且把状态栏设置为透明色
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public int FitsStatusBar(View statusBar) {

        int statusbarHeight = 0;

        //通过反射获取状态栏高度
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusbarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        //设置占位View的高度
        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
        layoutParams.height = statusbarHeight;
        return statusbarHeight;
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

    //键盘状态改变的接口
    public interface OnSoftKeyboardStateChangedListener {
        void OnSoftKeyboardStateChananged(boolean isKeyBoardShow, int keyboardHeight);
    }

    //注册软键盘状态变化监听
    public void addSoftKeyboardChangedListener(OnSoftKeyboardStateChangedListener listener) {
        if (listener != null) {

            if (mKeyboardStateListeners == null) {

                mKeyboardStateListeners = new ArrayList<>();
            }

            mKeyboardStateListeners.add(listener);
            setOnGlobalLayoutListener();
        }
    }
    //取消软键盘状态变化监听
    public void removeSoftKeyboardChangedListener(OnSoftKeyboardStateChangedListener listener) {
        if (listener != null) {
            mKeyboardStateListeners.remove(listener);
        }
    }

}
