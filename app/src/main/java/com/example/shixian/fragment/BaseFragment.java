package com.example.shixian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 2017/12/27.
 */

public abstract class BaseFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = CreateView(inflater, container, savedInstanceState);
        init();
        return view;
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

    public abstract void init();

    public abstract View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


}
