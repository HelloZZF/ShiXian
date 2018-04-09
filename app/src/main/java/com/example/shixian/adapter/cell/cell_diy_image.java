package com.example.shixian.adapter.cell;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.ShopCart;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zzf on 2018/3/27.
 */

public class cell_diy_image extends RVBaseCell<List<Uri>> {

    public static final int Type = 6;
    private List<Uri> mImageList;
    private Context mContext;

    public cell_diy_image(List<Uri> mImageList) {
        super(mImageList);
        this.mImageList = mImageList;
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

        Uri uri = mImageList.get(position);
        holder.getImageView(R.id.drawee_view).setImageURI(uri);
    }
}
