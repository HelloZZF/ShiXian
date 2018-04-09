package com.example.shixian.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.Wares;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zzf on 2018/3/25.
 */

public class cell_togo_ware extends RVBaseCell<List<Wares>> {

    public static final int Type = 66;
    private Context mContext;
    private List<Wares> mWares;

    public cell_togo_ware(List<Wares> mWares) {
        super(mWares);
        this.mWares = mWares;
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

        Picasso.with(mContext).load(mWares.get(position).getImageurl())
                .into(holder.getImageView(R.id.home_food_image));
        holder.getTextView(R.id.home_food_name).setText(mWares.get(position).getName());
        holder.getTextView(R.id.home_food_price).setText(mWares.get(position).getPrice()+"¥");
        holder.getTextView(R.id.food_evaluation).setText("好评:"+mWares.get(position).getThought());

    }
}
