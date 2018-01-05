package com.example.shixian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.shixian.R;
import com.example.shixian.widget.myToolbar;

/**
 * Created by admin on 2017/11/13.
 */

public class CommodityFragment extends BaseFragment {

    private View view;
    private SliderLayout sliderLayout;

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_commodity, container, false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.commodity_slider);
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
                .image(R.drawable.item5_1);
        sliderLayout.addSlider(defaultSliderView1);
        defaultSliderView2
                .image(R.drawable.item5_2);
        sliderLayout.addSlider(defaultSliderView2);
        defaultSliderView3
                .image(R.drawable.item5_3);
        sliderLayout.addSlider(defaultSliderView3);
        sliderLayout.setCustomIndicator((PagerIndicator) view.findViewById(R.id.commodity_custom_indicator));
        sliderLayout.setDuration(3000);
    }
}
