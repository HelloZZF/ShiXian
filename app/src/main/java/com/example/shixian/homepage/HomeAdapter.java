package com.example.shixian.homepage;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shixian.R;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.List;


/**
 * Created by admin on 2017/8/27.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<item1> myItem1;

    private List<item5> myItem5;

    private Context context;

    public enum ITEM_TYPE {
        ITEM_1,
        ITEM_2,
        ITEM_3,
        ITEM_4,
        ITEM_5,
        ITEM_6
    }

    public HomeAdapter(List<item1> myItem1, List<item5> myItem5, Context context){
        this.myItem1 = myItem1;
        this.myItem5 = myItem5;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_2.ordinal()) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_1,  parent, false);
            return new Item2Holder(view);

        }else if (viewType == ITEM_TYPE.ITEM_1.ordinal()) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_2,  parent, false);
            return new Item1Holder(view, myItem1, context);

        } else if (viewType == ITEM_TYPE.ITEM_3.ordinal()) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_3,  parent, false);
            return new Item3Holder(view);

        } else if (viewType == ITEM_TYPE.ITEM_4.ordinal()) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_4,  parent, false);
            return new Item4Holder(view);

        } else if (viewType == ITEM_TYPE.ITEM_5.ordinal()) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_5,  parent, false);
            return new Item5Holder(view, myItem5, context);

        } else if (viewType == ITEM_TYPE.ITEM_6.ordinal()) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_6,  parent, false);
            return new Item6Holder(view, context);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof Item3Holder) {
//
//            ((Item3Holder) holder).foryouimage.setImageResource(R.drawable.item3_1);
//            ((Item3Holder) holder).fashionimage.setImageResource(R.drawable.item3_2);
//
//        }

    }


        @Override
    public int getItemViewType(int position) {

        if (position == 0){
            return ITEM_TYPE.ITEM_6.ordinal();
        }else if (position == 1){
            return ITEM_TYPE.ITEM_2.ordinal();
        } else if (position == 2) {
            return ITEM_TYPE.ITEM_1.ordinal();
        } else if (position == 3) {
            return ITEM_TYPE.ITEM_3.ordinal();
        } else if (position == 4) {
            return ITEM_TYPE.ITEM_4.ordinal();
        } else if (position == 5) {
            return ITEM_TYPE.ITEM_5.ordinal();
        }

        return 0;
    }

    public static class Item1Holder extends RecyclerView.ViewHolder {

        public Item1Holder (View view, List<item1> myItem1, Context context){
            super(view);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.Item1_List);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            item1Adapter Item1adapter = new item1Adapter(myItem1);
            recyclerView.setAdapter(Item1adapter);
        }

    }

    public static class Item2Holder extends RecyclerView.ViewHolder {

//        public Button togo;
//
//        public Button diy;
//
//        public Button discuss;
//
//        public Button rank;

        public Item2Holder (View view) {
            super(view);
//            togo = (Button) view.findViewById(R.id.togo);
//            diy = (Button) view.findViewById(R.id.diy);
//            discuss = (Button) view.findViewById(R.id.discuss);
//            rank = (Button) view.findViewById(R.id.rank);
        }


    }

    public static class Item3Holder extends RecyclerView.ViewHolder {

        public TextView foryoutext;

        public TextView foryoucontent;

//        public CircleImageView foryouimage;

        public Button foryou;

        public TextView fashiontext;

        public TextView fashioncontent;

 //       public CircleImageView fashionimage;

        public Button fashion;

        public Item3Holder (View view){
            super(view);
            fashiontext = (TextView) view.findViewById(R.id.fashiontext);
            fashioncontent = (TextView) view.findViewById(R.id.fashioncontent);
   //         fashionimage = (CircleImageView) view.findViewById(R.id.fashionimage);
            fashion = (Button) view.findViewById(R.id.foryou);
            foryoutext = (TextView) view.findViewById(R.id.foryoutext);
            foryoucontent = (TextView) view.findViewById(R.id.foryoucontent);
    //        foryouimage = (CircleImageView) view.findViewById(R.id.foryouimage);
            foryou = (Button) view.findViewById(R.id.foryou);
        }

    }

    public static class Item4Holder extends RecyclerView.ViewHolder {

//        public TextView like;

        public Item4Holder (View view){
            super(view);
//            like = (TextView) view.findViewById(R.id.item3_text);
        }

    }

    public static class Item5Holder extends RecyclerView.ViewHolder {

        public Item5Holder (View view, List<item5> myItem5, Context context){
            super(view);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.Item5_List);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            item5Adapter Item5adapter = new item5Adapter(myItem5);
            recyclerView.setAdapter(Item5adapter);
        }

    }

    public static class Item6Holder extends RecyclerView.ViewHolder {

        private RollPagerView pagerView;

        public Item6Holder (View view, final Context context){
            super(view);
            pagerView = (RollPagerView) view.findViewById(R.id.rollPagerView);

            pagerView.setAdapter(new MyPagerAdapter());

            pagerView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(context,"111"+position, Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    public static class MyPagerAdapter extends StaticPagerAdapter {

        private int[] image = {R.drawable.item6_1, R.drawable.item6_2, R.drawable.item6_3};

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(image[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        }

        @Override
        public int getCount() {
            return image.length;
        }

    }

    @Override
    public int getItemCount() {
        return 6;
    }

}
