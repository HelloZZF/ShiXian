package com.example.shixian.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.HomeItem6;

import java.util.List;

/**
 * Created by admin on 2017/12/31.
 */

public class cell_home_item6i extends RVBaseCell<List<HomeItem6>> {

    public static final int Type = 61;
    private Context mContext;
    private List<HomeItem6> item5s;

    public cell_home_item6i(List<HomeItem6> homeItem5s) {
        super(homeItem5s);
        this.item5s = homeItem5s;
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_item_6i, parent, false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        holder.getImageView(R.id.home_food_image).setImageResource(item5s.get(position).getHomeFoodImageId());
        holder.getTextView(R.id.home_food_name).setText(item5s.get(position).getHomeFoodName());
        holder.getTextView(R.id.home_food_price).setText(item5s.get(position).getHomeFoodPrice());

    }
}
