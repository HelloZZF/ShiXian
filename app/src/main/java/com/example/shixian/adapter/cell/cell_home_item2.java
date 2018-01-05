package com.example.shixian.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;

/**
 * Created by admin on 2017/12/30.
 */

public class cell_home_item2 extends RVBaseCell<Object> {

    public static final int Type = 2;
    private Context mContext;

    public cell_home_item2(Object o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_item_2, parent, false);
        return new RVBaseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
