package com.example.shixian.fragment;

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

public class ShopCartFragment extends BaseFragment {

    private View statusBar;
    private ImageView imageView;

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop_cart, container, false);
        statusBar = view.findViewById(R.id.shop_StatusbarView);
        imageView = (ImageView) view.findViewById(R.id.shopcart_image);
        imageView.setImageResource(R.drawable.shopcart);

        return view;
    }

    @Override
    public void init() {
        FitsStatusBar(statusBar);
    }


}
