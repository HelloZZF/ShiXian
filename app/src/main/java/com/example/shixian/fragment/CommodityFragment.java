package com.example.shixian.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.shixian.CommodityDetailsAcitivity;
import com.example.shixian.Contants;
import com.example.shixian.CreateOrderActivity;
import com.example.shixian.R;
import com.example.shixian.ShiXianApplication;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.Wares;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.utils.CartProvider;
import com.example.shixian.utils.ToastUtils;
import com.example.shixian.widget.myToolbar;

import java.io.Serializable;

import okhttp3.Response;

/**
 * Created by admin on 2017/11/13.
 */

public class CommodityFragment extends BaseFragment {

    private View view;
    private SliderLayout sliderLayout;
    private Wares mWares;
    private Button mAddButton;
    private CartProvider mCartProvider;
    private myToolbar mToolbar;
    private ScrollView mScrollView;
    private TextView mName;
    private TextView mPrice;
    private Button mBuyButton;
    private ImageButton mCollectButton;

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_commodity, container, false);
        sliderLayout = view.findViewById(R.id.commodity_slider);
        mAddButton = view.findViewById(R.id.add_button);
        mCartProvider = new CartProvider(getContext());
        mToolbar = getActivity().findViewById(R.id.commodity_details_toolbar);
        mScrollView = view.findViewById(R.id.commodity_scroll);
        mName = view.findViewById(R.id.commodity_name);
        mPrice = view.findViewById(R.id.commodity_price);
        mBuyButton = view.findViewById(R.id.buy_button);
        mCollectButton = view.findViewById(R.id.collect_button);

        Serializable serializable = getActivity().getIntent().getSerializableExtra("ware");
        if (serializable == null){
            getActivity().finish();
        }else{
            mWares = (Wares) serializable;
        }

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void init() {

        initSliderLayout();
        initButton();
        initScrollView();
        initWare();
    }

    private void initWare() {

        mName.setText(mWares.getName());
        mPrice.setText(mWares.getPrice() + "¥");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initScrollView() {

        mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onScrollChange(View view, int x, int y, int oldx, int oldy) {

                if (y >= 150 && !mToolbar.isShowTabLayout()) {
                    mToolbar.showTabLayout();
                    mToolbar.setBackgroundColor(0x73000000);
                }
                if (y == 0 && mToolbar.isShowTabLayout()) {
                    mToolbar.hideTabLayout();
                    mToolbar.setBackgroundColor(0x000000);
                }
            }
        });


    }

    private void initButton() {

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCartProvider.put(mWares);
                ToastUtils.show(getContext(), "已加入购物车");
            }
        });

        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateOrderActivity.class);
                intent.putExtra("buy", 1);
                intent.putExtra("ware", mWares);
                startActivity(intent);
            }
        });

        mCollectButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                addCollect();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void addCollect() {

        int userId = Integer.parseInt(ShiXianApplication.getInstance().getUser().getId());
        int wareId = Integer.parseInt(mWares.getId());
        SimpleHttpClient.newBuilder()
                .url(Contants.API.COLLECTION_ADD)
                .post()
                .addParams("user_id", userId)
                .addParams("ware_id", wareId)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg msg) {

                        if (msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {
                            ToastUtils.show(getContext(), "收藏成功");
                        }

                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });

    }

    private void initSliderLayout() {

        DefaultSliderView defaultSliderView1 = new DefaultSliderView(getActivity());
        DefaultSliderView defaultSliderView2 = new DefaultSliderView(getActivity());
        DefaultSliderView defaultSliderView3 = new DefaultSliderView(getActivity());
        defaultSliderView1
                .image(mWares.getImageurl());
        sliderLayout.addSlider(defaultSliderView1);
        defaultSliderView2
                .image(mWares.getImageurl());
        sliderLayout.addSlider(defaultSliderView2);
        defaultSliderView3
                .image(mWares.getImageurl());
        sliderLayout.addSlider(defaultSliderView3);
        sliderLayout.setCustomIndicator((PagerIndicator) view.findViewById(R.id.commodity_custom_indicator));
        sliderLayout.setDuration(3000);
    }
}
