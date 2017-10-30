package com.example.shixian.shopcart;

import android.app.Activity;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_cart, container, false);
        activity = getActivity();
        ImageView imageView = (ImageView) view.findViewById(R.id.shopcart_image);
        imageView.setImageResource(R.drawable.shopcart);
  //      initToolbar("购物车");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar("购物车");
    }

    public Toolbar initToolbar(CharSequence title) {
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) activity;
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.shopcart_toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.classify);
     //   mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return toolbar;
    }

}
