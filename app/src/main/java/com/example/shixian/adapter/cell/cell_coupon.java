package com.example.shixian.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.Coupon;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by zzf on 2018/4/12.
 */
public class cell_coupon extends RVBaseCell<List<Coupon>> {

    private int TYPE = 1;
    private Context mContext;
    private List<Coupon> mCouponList;

    public cell_coupon(List<Coupon> coupons) {
        super(coupons);
        mCouponList = coupons;
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.template_coupon, parent, false);
        RVBaseViewHolder viewHolder = new RVBaseViewHolder(view, listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        holder.getTextView(R.id.coupon_name).setText(mCouponList.get(position).getName());
        holder.getTextView(R.id.coupon_content).setText(mCouponList.get(position).getContent());
        holder.getTextView(R.id.coupon_time).setText(mCouponList.get(position).getTime());
    }
}
