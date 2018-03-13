package com.example.shixian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.shixian.R;
import com.example.shixian.bean.Wares;
import com.example.shixian.utils.CartProvider;
import com.example.shixian.utils.ToastUtils;
import com.example.shixian.widget.myToolbar;

import java.io.Serializable;

/**
 * Created by admin on 2017/11/13.
 */

public class CommodityFragment extends BaseFragment {

    private View view;
    private SliderLayout sliderLayout;
    private Wares mWares;
    private Button mAddButton;
    private CartProvider mCartProvider;

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_commodity, container, false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.commodity_slider);
        mAddButton = view.findViewById(R.id.add_button);
        mCartProvider = new CartProvider(getContext());
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCartProvider.put(mWares);
                ToastUtils.show(getContext(), "已加入购物车");
            }
        });

        Serializable serializable = getActivity().getIntent().getSerializableExtra("ware");
        if (serializable == null){
            getActivity().finish();
        }else{
            mWares = (Wares) serializable;
        }

        return view;
    }

    @Override
    public void init() {
        initSliderLayout();
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
