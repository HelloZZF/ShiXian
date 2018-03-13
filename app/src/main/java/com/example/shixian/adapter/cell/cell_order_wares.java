package com.example.shixian.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.ShopCart;
import com.example.shixian.bean.Ware;
import com.example.shixian.bean.Wares;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 2017/12/31.
 */

public class cell_order_wares extends RVBaseCell<List<ShopCart>>{

    public static final int Type = 6;
    private List<ShopCart> mCarts;
    private Context mContext;

    public cell_order_wares(List<ShopCart> carts) {
        super(carts);
        this.mCarts = carts;
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.template_order_wares, parent,false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        ShopCart cart = mCarts.get(position);
        Picasso.with(mContext).load(cart.getImageurl()).into(holder.getImageView(R.id.drawee_view));
    }

}
