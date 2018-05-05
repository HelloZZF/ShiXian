package com.example.shixian;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.example.shixian.BaseActivity;
import com.example.shixian.Contants;
import com.example.shixian.R;
import com.example.shixian.ShiXianApplication;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_my_address;
import com.example.shixian.bean.Address;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.widget.myToolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Response;

public class MyAddressActivity extends BaseActivity {

    private myToolbar mToolbar;
    private View mStatus;
    private RecyclerView mRecyclerView;
    private RVSimpleAdapter mAdapter;
    private List<Address> mAddressList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_adrress);
        mToolbar = findViewById(R.id.md_toolbar);
        mStatus = findViewById(R.id.md_status);
        mRecyclerView = findViewById(R.id.md_recycler);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {

        FitsStatusBar(mStatus);
        setStatusbar();
        initToolbar();
        initData();
    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyAddressActivity.this.finish();
            }
        });

        mToolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyAddressActivity.this, EditAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initData() {

        SimpleHttpClient.newBuilder()
                .get()
                .url(Contants.API.ADDRESS_GET)
                .addParams("user_id", ShiXianApplication.getInstance().getUser().getId())
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<Address>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<Address> msg) {

                        if (msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {

                            mAddressList = msg.getData();
                            Collections.sort(mAddressList);
                            initRecyclerView();
                            setResult(RESULT_OK);
                        }
                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }

    private void initRecyclerView() {

        if (mAdapter == null) {

            mAdapter = new RVSimpleAdapter();
            mAdapter.OnlyOneItem(new cell_my_address(mAddressList, this, mAdapter), mAddressList.size());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {

            mAdapter.clear();
            mAdapter.OnlyOneItem(new cell_my_address(mAddressList, this, mAdapter), mAddressList.size());
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            initData();
//        }
//    }
//  用上下文启动Activi的时候不能用StartActivityForResult所以只能在onResume里刷新数据了
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

}
