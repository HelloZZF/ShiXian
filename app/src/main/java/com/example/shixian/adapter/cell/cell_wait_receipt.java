package com.example.shixian.adapter.cell;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.Response;

/**
 * Created by zzf on 2018/4/12.
 */
public class cell_wait_receipt extends RVBaseCell<List<Wares>> {

    private Context mContext;
    private List<Wares> mWaresList;
    private RVSimpleAdapter mAdapter;
    private AlertDialog.Builder mDialog;

    public cell_wait_receipt(List<Wares> wares, RVSimpleAdapter adapter, Context context) {
        super(wares);
        mWaresList = wares;
        mAdapter = adapter;
        mContext = context;
    }

    @Override
    public int getItemType() {
        return 1;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mDialog = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.template_wait_receipt, parent, false);


        return new RVBaseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, final int position) {

        final Wares ware = mWaresList.get(position);
        Picasso.with(mContext).load(ware.getImageurl()).into(holder.getImageView(R.id.foodimage));
        holder.getTextView(R.id.name).setText(ware.getName());
        holder.getTextView(R.id.introduction).setText(ware.getIntroduction());

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CommodityDetailsAcitivity.class);
                intent.putExtra("ware", ware);
                mContext.startActivity(intent);
            }
        });

        holder.getButton(R.id.wr_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog.setTitle("确认菜品已到达");
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
                .url(Contants.API.ORDER_DELETE)
                .addParams("user_id", ShiXianApplication.getInstance().getUser().getId())
                .addParams("ware_id", wareId)
                .get()
                .build()
                .enqueue(new SimpleCallBack<BaseMsg>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg msg) {
                        if (msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS){
                            ToastUtils.show(mContext,"已收货");
                        }
                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }
}
