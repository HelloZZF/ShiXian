package com.example.shixian.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.Ware;
import com.example.shixian.bean.Wares;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 2017/12/31.
 */

public class cell_classify_ware extends RVBaseCell<List<Wares>>{

    public static final int Type = 6;
    private List<Wares> wares;
    private Context mContext;

    public cell_classify_ware(List<Wares> wares) {
        super(wares);
        this.wares = wares;
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.template_wares_layout, parent,false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        Wares ware = wares.get(position);
        Picasso.with(mContext).load(ware.getImageurl()).into(holder.getImageView(R.id.foodimage));
        holder.getTextView(R.id.name).setText(ware.getName());
        holder.getTextView(R.id.introduction).setText(ware.getIntroduction());
        holder.getTextView(R.id.price).setText("¥"+ware.getPrice());
        holder.getTextView(R.id.thought).setText("好评:"+ware.getThought());
    }

}
