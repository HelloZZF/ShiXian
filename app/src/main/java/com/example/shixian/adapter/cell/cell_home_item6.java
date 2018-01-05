package com.example.shixian.adapter.cell;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.bean.HomeItem6;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;

import java.util.List;

/**
 * Created by admin on 2017/12/30.
 */

public class cell_home_item6 extends RVBaseCell<List<HomeItem6>> {

    public static final int Type = 6;
    private Context mContext;
    private List<HomeItem6> item5s;

    public cell_home_item6(List<HomeItem6> item5s) {
        super(item5s);
        this.item5s = item5s;
    }


    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_item_6, parent, false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view, listener);
        RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.Item5_List);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        RVSimpleAdapter Item5adapter = new RVSimpleAdapter();
        Item5adapter.OnlyOneItem(new cell_home_item6i(item5s), item5s.size());
        recyclerView.setAdapter(Item5adapter);

        return holder;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {



    }
}