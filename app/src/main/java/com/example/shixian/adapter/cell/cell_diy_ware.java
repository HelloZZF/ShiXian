package com.example.shixian.adapter.cell;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.DiyWare;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zzf on 2018/4/3.
 */
public class cell_diy_ware extends RVBaseCell<List<DiyWare>> {

    private Context mContext;
    private List<DiyWare> mDiyWareList;
    private int TYPE = 1;

    public cell_diy_ware(List<DiyWare> diyWare) {
        super(diyWare);
        mDiyWareList = diyWare;
    }

    @Override
    public int getItemType() {
        return TYPE;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.template_diy_ware_layout, parent, false);

        return new RVBaseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        DiyWare diyWare = mDiyWareList.get(0);
        Picasso.with(mContext).load(diyWare.getSteps().get(position).getImg())
                .into(holder.getImageView(R.id.diy_ware_img));
        holder.getTextView(R.id.diy_ware_content).setText(diyWare.getSteps().get(position).getStep());

    }
}
