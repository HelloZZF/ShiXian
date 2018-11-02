package com.example.shixian.adapter.cell;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.BaseActivity;
import com.example.shixian.DiscussActivity;
import com.example.shixian.DiyActivity;
import com.example.shixian.MainActivity;
import com.example.shixian.R;
import com.example.shixian.RankActivity;
import com.example.shixian.ShiXianApplication;
import com.example.shixian.TogoActivity;
import com.example.shixian.UserLoginActivity;
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
        view.findViewById(R.id.togo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TogoActivity.class);
                mContext.startActivity(intent);
            }
        });
        view.findViewById(R.id.diy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DiyActivity.class);
                mContext.startActivity(intent);
            }
        });
        view.findViewById(R.id.rank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RankActivity.class);
                mContext.startActivity(intent);
            }
        });
        view.findViewById(R.id.discuss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShiXianApplication.getInstance().getUser() == null){
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra("VPid", 0);
                    ShiXianApplication.getInstance().putIntent(intent);
                    Intent LoginIntent = new Intent(mContext, UserLoginActivity.class);
                    mContext.startActivity(LoginIntent);
                }else{
                    Intent intent = new Intent(mContext, DiscussActivity.class);
                    mContext.startActivity(intent);
                }

            }
        });
        return new RVBaseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
