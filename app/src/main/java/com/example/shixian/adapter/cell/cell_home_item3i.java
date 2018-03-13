package com.example.shixian.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.HomeItem3;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 2017/12/31.
 */

public class cell_home_item3i extends RVBaseCell<List<HomeItem3>>{

    public static final int Type = 31;
    private Context mContext;
    private List<HomeItem3> item3s;

    public cell_home_item3i(List<HomeItem3> homeItem3s) {
        super(homeItem3s);
        this.item3s = homeItem3s;
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_item_3i, parent, false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        Picasso.with(mContext).load(item3s.get(position).getIcon())
                .into(holder.getImageView(R.id.user_image));
        Picasso.with(mContext).load(item3s.get(position).getImageurl())
                .into(holder.getImageView(R.id.food_image));

        holder.getTextView(R.id.user_name).setText(item3s.get(position).getName());
        holder.getTextView(R.id.food_name).setText(item3s.get(position).getFoodname());

    }
}
