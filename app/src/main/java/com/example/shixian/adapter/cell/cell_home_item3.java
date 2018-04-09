package com.example.shixian.adapter.cell;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.DiyWareActivity;
import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.bean.HomeItem3;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;

import java.util.List;

/**
 * Created by admin on 2017/12/30.
 */

public class cell_home_item3 extends RVBaseCell<List<HomeItem3>> {

    public static final int Type = 3;
    private Context mContext;
    private List<HomeItem3> item3s;

    public cell_home_item3(List<HomeItem3> item3s) {
        super(item3s);
        this.item3s = item3s;
    }


    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_item_3, parent, false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view, listener);

        RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.Item3_List);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        RVSimpleAdapter Item3adapter = new RVSimpleAdapter();
        Item3adapter.OnlyOneItem(new cell_home_item3i(item3s), item3s.size());
        Item3adapter.setOnItemClickListener(new RVBaseAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {

                Intent intent = new Intent(mContext, DiyWareActivity.class);
                intent.putExtra("id", item3s.get(position).getMenu_id());
                mContext.startActivity(intent);
            }
        });
        recyclerView.setAdapter(Item3adapter);

        return holder;
    }


    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
