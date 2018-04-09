package com.example.shixian;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_rank;
import com.example.shixian.adapter.cell.cell_rank_head;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.HomeItem3;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.widget.myToolbar;

import java.util.List;

import okhttp3.Response;

public class RankActivity extends BaseActivity {

    private myToolbar mToolbar;
    private RecyclerView mRecyclerView;
    private View mStatus;
    private RVSimpleAdapter mAdapter;
    private List<HomeItem3> mRanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        mToolbar = findViewById(R.id.rank_toolbar);
        mRecyclerView = findViewById(R.id.rank_recycle);
        mStatus = findViewById(R.id.rank_status);

        init();
    }

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
                RankActivity.this.finish();
            }
        });
    }

    private void initRecycleView() {

        if (mAdapter == null) {

            mAdapter = new RVSimpleAdapter();
            mAdapter.OnlyOneItem(new cell_rank_head(mRanks), 1);
            mAdapter.OnlyOneItem(new cell_rank(mRanks), 7);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void initData() {

        SimpleHttpClient.newBuilder()
                .get()
                .url(Contants.API.DIY_GET)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<HomeItem3>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<HomeItem3> diys) {

                        if (diys.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {

                            mRanks = diys.getData();
                        }
                        initRecycleView();

                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }
}
