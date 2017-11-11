package com.example.shixian.shopcart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shixian.R;

/**
 * Created by admin on 2017/9/2.
 */

public class ShopCartFragment extends Fragment {

    Activity activity;

    private View statusBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_cart, container, false);
        statusBar = view.findViewById(R.id.shop_StatusbarView);
        activity = getActivity();
        ImageView imageView = (ImageView) view.findViewById(R.id.shopcart_image);
        imageView.setImageResource(R.drawable.shopcart);
        FitsStatusBar();
        return view;
    }

    private void FitsStatusBar() {

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initToolbar("我的佳肴");
    }

//    public Toolbar initToolbar(CharSequence title) {
//        AppCompatActivity mAppCompatActivity = (AppCompatActivity) activity;
//        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.shopcart_toolbar);
//        toolbar.setTitle(title);
//        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//        return toolbar;
//    }

}
