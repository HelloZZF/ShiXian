package com.example.shixian.adapter.cell;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.shixian.Contants;
import com.example.shixian.EditAddressActivity;
import com.example.shixian.R;
import com.example.shixian.ShiXianApplication;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.bean.Address;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.utils.ToastUtils;

import java.util.List;

import okhttp3.Response;

/**
 * Created by zzf on 2018/4/14.
 */
public class cell_my_address extends RVBaseCell<List<Address>> {

    private Context mContext;
    private List<Address> mAddressList;
    private RVSimpleAdapter mAdapter;
    private AlertDialog.Builder mDialog;
    private int beforePosition = -1;

    public cell_my_address(List<Address> addresses, Context context, RVSimpleAdapter adapter) {
        super(addresses);
        this.mContext = context;
        this.mAddressList = addresses;
        this.mAdapter = adapter;
    }

    @Override
    public int getItemType() {
        return 1;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.template_my_address, parent, false);
        RVBaseViewHolder viewHolder = new RVBaseViewHolder(view, listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, final int position) {

        final Address address = mAddressList.get(position);
        String phone = address.getPhone();
        holder.getTextView(R.id.add_phone).setText(phone.substring(0, 3) + "****" + phone.substring(7,11));
        holder.getTextView(R.id.add_address).setText(address.getAddress());
        holder.getTextView(R.id.add_name).setText(address.getName());
        CheckBox checkBox = (CheckBox) holder.getView(R.id.add_check);
        LinearLayout editLayout = (LinearLayout) holder.getView(R.id.add_edit);
        LinearLayout deleteLayout = (LinearLayout) holder.getView(R.id.add_delete);

        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditAddressActivity.class);
                intent.putExtra("address", address);
                mContext.startActivity(intent);
            }
        });
        mDialog = new AlertDialog.Builder(mContext);
        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                mDialog.setTitle("确认删除地址");
                mDialog.setCancelable(false);
                mDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteData(address.getId(), position);
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

        if (address.getIsdefault().equals("1")) {

            checkBox.setChecked(true);
            checkBox.setText("默认地址");
            beforePosition = position;
        }else if (address.getIsdefault().equals("0")) {

            checkBox.setChecked(false);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                setDefaultData(address.getId(), position);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void deleteData(final String id, final int position) {

        SimpleHttpClient.newBuilder()
                .get()
                .url(Contants.API.ADDRESS_DELETE)
                .addParams("address_id", id)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg msg) {

                        if (msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {

                            ToastUtils.show(mContext, "删除成功");
                            mAdapter.remove(position);

                        }
                    }

                    @Override
                    public void onError(int code, Exception e) {
                        ToastUtils.show(mContext, "删除失败");
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setDefaultData(final String id, final int position) {

        SimpleHttpClient.newBuilder()
                .get()
                .url(Contants.API.ADDRESS_SETDEFAULT)
                .addParams("address_id", id)
                .addParams("user_id", ShiXianApplication.getInstance().getUser().getId())
                .build()
                .enqueue(new SimpleCallBack<BaseMsg>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg msg) {

                        if (msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {

                            mAddressList.get(position).setIsdefault("1");
                            mAddressList.get(beforePosition).setIsdefault("0");
                            sortList();
                            mAdapter.clear();
                            mAdapter.OnlyOneItem(new cell_my_address(mAddressList, mContext, mAdapter), mAddressList.size());
                            ToastUtils.show(mContext, "设置成功");
                        }
                    }

                    @Override
                    public void onError(int code, Exception e) {
                        ToastUtils.show(mContext, "设置失败");
                    }
                });
    }

    private void sortList() {

        Address address = null;

        for ( int i = 0; i < mAddressList.size(); i++) {

            if (mAddressList.get(i).getIsdefault().equals("1")) {

                address = mAddressList.get(i);
                mAddressList.remove(i);
                mAddressList.add(0, address);
            }
        }
    }
}
