package com.example.shixian;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

/**
 * Created by admin on 2017/11/13.
 */

public class CommodityFragment extends Fragment {

    private View view;
    private SliderLayout sliderLayout;
    private ScrollView scrollView;
    private myToolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_commodity, container, false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.commodity_slider);
//        scrollView = (ScrollView) view.findViewById(R.id.commodity_scroll);
//        scrollView.setBackgroundColor(R.color.green);
        initSliderLayout();
        return view;
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
