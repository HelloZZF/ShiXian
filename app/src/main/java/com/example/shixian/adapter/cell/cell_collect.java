package com.example.shixian.adapter.cell;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shixian.CommodityDetailsAcitivity;
import com.example.shixian.Contants;
import com.example.shixian.R;
import com.example.shixian.ShiXianApplication;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.Wares;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.utils.ToastUtils;
import com.example.shixian.widget.myToolbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zzf on 2018/4/14.
 */
public class cell_collect extends RVBaseCell <List<Wares>>{

    public static final int Type = 6;
    private List<Wares> wares;
    private Context mContext;
    private RVSimpleAdapter mAdapter;
    private AlertDialog.Builder mDialog;

    public cell_collect(List<Wares> wares, Context context, RVSimpleAdapter adapter) {
        super(wares);
        this.wares = wares;
        this.mContext = context;
        this.mAdapter = adapter;
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mDialog = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.template_collect_layout, parent,false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RVBaseViewHolder holder, final int position) {

        final Wares ware = wares.get(position);
        Picasso.with(mContext).load(ware.getImageurl()).into(holder.getImageView(R.id.foodimage));
        holder.getTextView(R.id.name).setText(ware.getName());
        holder.getTextView(R.id.introduction).setText(ware.getIntroduction());
        holder.getTextView(R.id.thought).setText("好评:"+ware.getThought());
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CommodityDetailsAcitivity.class);
                intent.putExtra("ware", ware);
                mContext.startActivity(intent);
            }
        });

        holder.getButton(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog.setTitle("确认删除菜品");
                mDialog.setCancelable(false);
                mDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAdapter.remove(position);
                        deleteData(ware.getId());
                    }
                });
                mDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                mDialog.show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void deleteData(String wareId) {

        SimpleHttpClient.newBuilder()
                .url(Contants.API.COLLECTION_DELETE)
                .addParams("user_id", ShiXianApplication.getInstance().getUser().getId())
                .addParams("ware_id", wareId)
                .get()
                .build()
                .enqueue(new SimpleCallBack<BaseMsg>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg msg) {
                        if (msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS){
                            ToastUtils.show(mContext,"删除成功");
                        }
                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }

}
