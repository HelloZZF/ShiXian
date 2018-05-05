package com.example.shixian.adapter.cell;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.DiyWareActivity;
import com.example.shixian.R;
import com.example.shixian.ShiXianApplication;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.DiyWare;
import com.example.shixian.bean.HomeItem3;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zzf on 2018/4/12.
 */
public class cell_mydiy extends RVBaseCell<List<HomeItem3>> {

    private int TYPE = 1;
    private Context mContext;
    private List<HomeItem3> mDiyWareList;

    public cell_mydiy(List<HomeItem3> diyWares) {
        super(diyWares);
        mDiyWareList = diyWares;
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.template_mydiy, parent, false);
        RVBaseViewHolder viewHolder = new RVBaseViewHolder(view, listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, final int position) {

        HomeItem3 diyWare = mDiyWareList.get(position);
        Picasso.with(mContext).load(diyWare.getImageurl()).into(holder.getImageView(R.id.md_food_icon));

        holder.getTextView(R.id.md_food_name).setText(diyWare.getFoodname());
        holder.getTextView(R.id.md_author_name).setText(ShiXianApplication.getInstance().getUser().getName());

        holder.getView(R.id.mydiy_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, DiyWareActivity.class);
                intent.putExtra("id", mDiyWareList.get(position).getMenu_id());
                mContext.startActivity(intent);
            }
        });

    }
}
