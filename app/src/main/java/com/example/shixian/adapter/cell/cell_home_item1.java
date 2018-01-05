package com.example.shixian.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;

/**
 * Created by admin on 2017/12/30.
 */

public class cell_home_item1 extends RVBaseCell<Object> {

    public static final int Type = 1;
    private SliderLayout mSliderLayout;
    private DefaultSliderView defaultSliderView1;
    private DefaultSliderView defaultSliderView2;
    private DefaultSliderView defaultSliderView3;
    private Context mContext;

    public cell_home_item1(Object o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_item_1, parent, false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view, listener);
        mSliderLayout = (SliderLayout) holder.getView(R.id.slider);
        defaultSliderView1 = new DefaultSliderView(mContext);
        defaultSliderView2 = new DefaultSliderView(mContext);
        defaultSliderView3 = new DefaultSliderView(mContext);

        mSliderLayout.addSlider(defaultSliderView1);
        mSliderLayout.addSlider(defaultSliderView2);
        mSliderLayout.addSlider(defaultSliderView3);

        defaultSliderView1.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(mContext, "111", Toast.LENGTH_SHORT).show();
            }
        });

        defaultSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(mContext, "222", Toast.LENGTH_SHORT).show();
            }
        });

        defaultSliderView3.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(mContext, "333", Toast.LENGTH_SHORT).show();
            }
        });

        mSliderLayout.setCustomIndicator((PagerIndicator) holder.getView(R.id.custom_indicator));
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSliderLayout.setDuration(3000);

        return holder;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        defaultSliderView1
                .image(R.drawable.item6_1);

        defaultSliderView2
                .image(R.drawable.item6_2);

        defaultSliderView3
                .image(R.drawable.item6_3);

    }
}
