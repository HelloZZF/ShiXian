package com.example.shixian.adapter.cell;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.shixian.NewsActivity;
import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.fragment.HomePageFragment;

/**
 * Created by admin on 2017/12/30.
 */

public class cell_home_item4 extends RVBaseCell<Object> {

    public static final int Type = 4;
    private Context mContext;

    public cell_home_item4(Object o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_item_4, parent, false);
        return new RVBaseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        RelativeLayout foryouLayout = (RelativeLayout) holder.getView(R.id.foryou_layout);
        foryouLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewsActivity.class);
                intent.putExtra("type", 1);
                mContext.startActivity(intent);
            }
        });

        RelativeLayout fasionLayout = (RelativeLayout) holder.getView(R.id.fashion_layout);
        fasionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewsActivity.class);
                intent.putExtra("type", 2);
                mContext.startActivity(intent);
            }
        });
    }

}

