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

public class item1Adapter extends RecyclerView.Adapter<item1Adapter.ViewHolder>{

    private List<item1> myItem1;

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;

        public TextView foodName;

        public ImageView userImage;

        public ImageView foodImage;

        public ViewHolder (View view){
            super(view);
            userName = (TextView) view.findViewById(R.id.user_name);
            foodName = (TextView) view.findViewById(R.id.food_name);
            userImage = (ImageView) view.findViewById(R.id.user_image);
            foodImage = (ImageView) view.findViewById(R.id.food_image);
        }

    }

    public item1Adapter(List<item1> myItem1) {
        this.myItem1 = myItem1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_2i, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        item1 Item1 = myItem1.get(position);
        holder.foodImage.setImageResource(Item1.getFoodImageId());
        holder.foodName.setText(Item1.getFoodName());
        holder.userImage.setImageResource(Item1.getUserImageId());
        holder.userName.setText(Item1.getUserName());
    }

    @Override
    public int getItemCount() {
        return myItem1.size();
    }

}
