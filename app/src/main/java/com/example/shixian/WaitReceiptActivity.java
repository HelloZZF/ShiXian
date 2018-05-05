package com.example.shixian;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_wait_receipt;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.Wares;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.utils.ToastUtils;
import com.example.shixian.widget.myToolbar;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class WaitReceiptActivity extends BaseActivity {

    private myToolbar mToolbar;
    private RecyclerView mRecyclerView;
    private View mStatus;
    private RVSimpleAdapter mAdapter;
    private List<Wares> mWaresList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_receipt);
        mToolbar = findViewById(R.id.wr_toolbar);
        mStatus = findViewById(R.id.wr_status);
        mRecyclerView = findViewById(R.id.wr_recyclerview);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {

        FitsStatusBar(mStatus);
        setStatusbar();
        initToolbar();
        initData();
        refreshView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initData() {

        SimpleHttpClient.newBuilder()
                .url(Contants.API.ORDER_GET)
                .addParams("user_id", Integer.parseInt(ShiXianApplication.getInstance().getUser().getId()))
                .addParams("ispaid", 1)
                .get()
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<Wares>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<Wares> waresBaseMsg) {

                        if (waresBaseMsg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {

                            mWaresList = waresBaseMsg.getData();
                            initRecyclerView();
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
            mAdapter.OnlyOneItem(new cell_wait_receipt(mWaresList, mAdapter, this), mWaresList.size());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }else {

            mAdapter.clear();
            mAdapter.OnlyOneItem(new cell_wait_receipt(mWaresList, mAdapter, this), mWaresList.size());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void refreshView() {

        if (ShiXianApplication.getInstance().isChangeUser() == true && mAdapter != null) {

            initData();
        }
    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaitReceiptActivity.this.finish();
            }
        });
    }
}
