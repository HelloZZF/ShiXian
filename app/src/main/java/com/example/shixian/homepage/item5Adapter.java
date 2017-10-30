package com.example.shixian.homepage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shixian.R;

import java.util.List;

/**
 * Created by admin on 2017/8/30.
 */

public class item5Adapter extends RecyclerView.Adapter<item5Adapter.ViewHolder>{

    private List<item5> myItem5;

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView homefoodname;

        public TextView homefoodprice;

        public ImageView homefoodimage;

        public ViewHolder (View view){
            super(view);
            homefoodimage = (ImageView) view.findViewById(R.id.home_food_image);
            homefoodprice = (TextView) view.findViewById(R.id.home_food_price);
            homefoodname = (TextView) view.findViewById(R.id.home_food_name);
        }
    }

    public item5Adapter(List<item5> myItem5) {
        this.myItem5 = myItem5;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_5i, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        item5 Item5 = myItem5.get(position);
        holder.homefoodname.setText(Item5.getHomeFoodName());
        holder.homefoodprice.setText(Item5.getHomeFoodPrice());
        holder.homefoodimage.setImageResource(Item5.getHomeFoodImageId());

    }

    @Override
    public int getItemCount() {
        return myItem5.size();
    }

}
