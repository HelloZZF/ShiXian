package com.example.shixian.classify;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shixian.R;

import java.util.List;

/**
 * Created by admin on 2017/7/25.
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

    private List<Goods> mgoods;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView foodImage;
        TextView introduction;
        TextView priceText;
        TextView thoughtText;
        TextView nameText;

        public ViewHolder(View view){
            super(view);
            foodImage = (ImageView) view.findViewById(R.id.foodimage);
            introduction = (TextView) view.findViewById(R.id.introduction);
            priceText = (TextView) view.findViewById(R.id.price);
            thoughtText = (TextView) view.findViewById(R.id.thought);
            nameText = (TextView) view.findViewById(R.id.name);
        }
    }

    public GoodsAdapter(List<Goods> mgoods) {
        this.mgoods = mgoods;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Goods goods = mgoods.get(position);
        holder.foodImage.setImageResource(goods.getFoodImageId());
        holder.introduction.setText(goods.getIntroduction());
        holder.priceText.setText(goods.getPrice());
        holder.thoughtText.setText(goods.getThought());
        holder.nameText.setText(goods.getName());
    }

    @Override
    public int getItemCount() {
        return mgoods.size();
    }
}
