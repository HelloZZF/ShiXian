package com.example.shixian;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_all_order;
import com.example.shixian.adapter.cell.cell_cart_ware;
import com.example.shixian.adapter.cell.cell_classify_ware;
import com.example.shixian.adapter.cell.cell_home_item3i;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.Wares;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.widget.myToolbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class AllOrderActivity extends BaseActivity {

    private myToolbar mToolbar;
    private View mStatus;
    private RecyclerView mRecyclerView;
    private RVSimpleAdapter mAdapter;
    private TabLayout mTabLayout;
    private String Ispaid = "1";
    private List<Wares> mWaresList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order);
        mToolbar = findViewById(R.id.ao_toolbar);
        mStatus = findViewById(R.id.ao_status);
        mRecyclerView = findViewById(R.id.ao_recyclerview);
        mTabLayout = findViewById(R.id.ao_tablayout);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {

        FitsStatusBar(mStatus);
        setStatusbar();
        initToobar();
        initTab();
        initData();
        refreshView();
    }

    private void initToobar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllOrderActivity.this.finish();
            }
        });
    }

    private void initTab() {

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    Ispaid = "1";
                    mWaresList.clear();
                    initData();
                }
                else {
                    mWaresList.clear();
                    Ispaid = "0";
                    initData();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initData() {

        SimpleHttpClient.newBuilder()
                .url(Contants.API.ORDER_GET)
                .addParams("user_id", Integer.parseInt(ShiXianApplication.getInstance().getUser().getId()))
                .addParams("ispaid", Ispaid)
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void refreshView() {

        if (ShiXianApplication.getInstance().isChangeUser() == true && mAdapter != null) {

            initData();
        }

    }

    private void initRecyclerView() {

        if (mAdapter == null) {

            mAdapter = new RVSimpleAdapter();
            mAdapter.OnlyOneItem(new cell_all_order(mWaresList, Ispaid, this), mWaresList.size());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mAdapter);
        } else {

            mAdapter.clear();
            mAdapter.OnlyOneItem(new cell_all_order(mWaresList, Ispaid, this), mWaresList.size());
        }
    }
}
