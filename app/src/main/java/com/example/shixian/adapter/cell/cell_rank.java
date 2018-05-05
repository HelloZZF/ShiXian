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
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.bean.HomeItem3;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zzf on 2018/3/27.
 */

public class cell_rank extends RVBaseCell<List<HomeItem3>> {

    public static final int Type = 3;
    private Context mContext;
    private List<HomeItem3> item3s;

    public cell_rank(List<HomeItem3> item3s) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.template_rank_layout, parent, false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view, listener);

        return holder;
    }


    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        position += 2;

        holder.getTextView(R.id.rank_author_name).setText(item3s.get(position).getName());
        holder.getTextView(R.id.rank_author_name).setText(item3s.get(position).getName());
        holder.getTextView(R.id.rank_author_name).setText(item3s.get(position).getName());

        holder.getTextView(R.id.rank_food_name).setText(item3s.get(position).getFoodname());
        holder.getTextView(R.id.rank_food_name).setText(item3s.get(position).getFoodname());
        holder.getTextView(R.id.rank_food_name).setText(item3s.get(position).getFoodname());

        Picasso.with(mContext).load(item3s.get(position).getImageurl())
                .into(holder.getImageView(R.id.rank_food_icon));
        Picasso.with(mContext).load(item3s.get(position).getImageurl())
                .into(holder.getImageView(R.id.rank_food_icon));
        Picasso.with(mContext).load(item3s.get(position).getImageurl())
                .into(holder.getImageView(R.id.rank_food_icon));

        if (position < 9) {

            holder.getTextView(R.id.rank_text).setText("  " + (position+1) + ".");

        }else if (position == 9) {

            holder.getTextView(R.id.rank_text).setText(position+1 + ".");
        }

        final String id = item3s.get(position).getMenu_id();

        holder.getView(R.id.rank_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, DiyWareActivity.class);
                intent.putExtra("id", id);
                mContext.startActivity(intent);
            }
        });


    }
}
