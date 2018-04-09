package com.example.shixian.adapter.cell;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.bean.HomeItem3;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zzf on 2018/3/27.
 */

public class cell_rank_head extends RVBaseCell<List<HomeItem3>> {

    public static final int Type = 33;
    private Context mContext;
    private List<HomeItem3> item3s;

    public cell_rank_head(List<HomeItem3> item3s) {
        super(item3s);
        this.item3s = item3s;
    }


    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.template_rank_head_layout, parent, false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view, listener);

        return holder;
    }


    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        holder.getTextView(R.id.rank_author_name1).setText(item3s.get(0).getName());
        holder.getTextView(R.id.rank_author_name2).setText(item3s.get(1).getName());
        holder.getTextView(R.id.rank_author_name3).setText(item3s.get(2).getName());

        holder.getTextView(R.id.rank_food_name1).setText(item3s.get(0).getFoodname());
        holder.getTextView(R.id.rank_food_name2).setText(item3s.get(1).getFoodname());
        holder.getTextView(R.id.rank_food_name3).setText(item3s.get(2).getFoodname());

        Picasso.with(mContext).load(item3s.get(0).getImageurl())
                .into(holder.getImageView(R.id.rank_food_icon1));
        Picasso.with(mContext).load(item3s.get(1).getImageurl())
                .into(holder.getImageView(R.id.rank_food_icon2));
        Picasso.with(mContext).load(item3s.get(2).getImageurl())
                .into(holder.getImageView(R.id.rank_food_icon3));
    }
}
