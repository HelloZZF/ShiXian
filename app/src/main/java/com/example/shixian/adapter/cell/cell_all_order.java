package com.example.shixian.adapter.cell;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.CommodityDetailsAcitivity;
import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.Wares;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zzf on 2018/4/12.
 */
public class cell_all_order extends RVBaseCell<List<Wares>> {

    private int Type = 1;
    private Context mContext;
    private List<Wares> mWaresList;
    private String ispaid;

    public cell_all_order(List<Wares> wares, String ispaid, Context context) {
        super(wares);
        this.mWaresList = wares;
        this.ispaid = ispaid;
        mContext = context;
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.template_all_order, parent, false);

        return new RVBaseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        if (ispaid.equals("1")) {
            holder.getImageView(R.id.ispaid_image).setVisibility(View.VISIBLE);
            holder.getImageView(R.id.unpaid_image).setVisibility(View.GONE);
        }else if (ispaid.equals("0")) {
            holder.getImageView(R.id.ispaid_image).setVisibility(View.GONE);
            holder.getImageView(R.id.unpaid_image).setVisibility(View.VISIBLE);
        }

        final Wares ware = mWaresList.get(position);
        Picasso.with(mContext).load(ware.getImageurl()).into(holder.getImageView(R.id.foodimage));
        holder.getTextView(R.id.name).setText(ware.getName());
        holder.getTextView(R.id.introduction).setText(ware.getIntroduction());
        holder.getTextView(R.id.time).setText(ware.getTime());

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CommodityDetailsAcitivity.class);
                intent.putExtra("ware", ware);
                mContext.startActivity(intent);
            }
        });
    }
}
