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
import com.example.shixian.bean.Wares;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 2017/12/31.
 */

public class cell_home_item6i extends RVBaseCell<List<Wares>> {

    public static final int Type = 61;
    private Context mContext;
    private List<Wares> item6s;

    public cell_home_item6i(List<Wares> homeItem6s) {
        super(homeItem6s);
        this.item6s = homeItem6s;
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

        Picasso.with(mContext).load(item6s.get(position).getImageurl())
                .into(holder.getImageView(R.id.home_food_image));
        holder.getTextView(R.id.home_food_name).setText(item6s.get(position).getName());
        holder.getTextView(R.id.home_food_price).setText(item6s.get(position).getPrice()+"¥");
        holder.getTextView(R.id.food_evaluation).setText("好评:"+item6s.get(position).getThought());

    }
}
