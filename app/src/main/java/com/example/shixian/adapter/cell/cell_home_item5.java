package com.example.shixian.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.HomeItem1;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 2017/12/31.
 */

public class cell_home_item5 extends RVBaseCell<Object>{

    public static final int Type = 5;
    private Context mContext;
    private List<HomeItem1> item5s;

    public cell_home_item5(List<HomeItem1> item5s) {
        super(item5s);
        this.item5s = item5s;
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_item_5,parent,false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        Picasso.with(mContext).load(item5s.get(0).getImageurl())
                .into(holder.getImageView(R.id.image_top));

        Picasso.with(mContext).load(item5s.get(1).getImageurl())
                .into(holder.getImageView(R.id.image_left));

        Picasso.with(mContext).load(item5s.get(2).getImageurl())
                .into(holder.getImageView(R.id.image_head));

        Picasso.with(mContext).load(item5s.get(3).getImageurl())
                .into(holder.getImageView(R.id.image_bottom));
    }
}
