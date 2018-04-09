package com.example.shixian;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_togo_ware;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.Wares;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.widget.myToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Response;

public class TogoActivity extends BaseActivity {

    private View mStatus;
    private myToolbar mToolbar;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mActionButton;
    private RVSimpleAdapter adapter;
    private List<Wares> mWares = new ArrayList<>();
    private int category = 0;
    private int curpage = 1;
    private int pagesize = 8;
    private int four = -1;
    private int six = -1;
    private int tow = -1;
    private int zero = -1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_togo);
        mStatus = findViewById(R.id.togo_StatusbarView);
        mToolbar = findViewById(R.id.togo_toolbar);
        mRecyclerView = findViewById(R.id.togo_recyclerview);
        mActionButton = findViewById(R.id.floating);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {

        setStatusbar();
        FitsStatusBar(mStatus);
        //initData();
        initToolbar();
        refreshData();
    }

    private boolean GetIntent() {

        Intent intent = getIntent();
        if (intent != null) {

            four = intent.getIntExtra("four", -1);
            tow = intent.getIntExtra("tow", -1);
            zero = intent.getIntExtra("zero", -1);
            six = intent.getIntExtra("six", -1);
            if (four != -1 || tow != -1 || zero != -1 || six != -1)
                return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initToolbar() {

        if (!GetIntent()) {
            mToolbar.setTitle("去逛逛");
            initData();
        }else{

            if (four != -1) {

                initContent("田园时光", four);

            }else if(six != -1) {

                initContent("新鲜每一天", six);

            }else if (zero != -1) {

                initContent("食鲜精选", zero);

            }else if (tow != -1) {

                initContent("新品上市", tow);

            }
            mActionButton.setVisibility(View.GONE);
        }

        mToolbar.setLeftButtonIcon(R.drawable.back);
        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TogoActivity.this.finish();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initContent(String title, int cate) {

        mToolbar.setTitle(title);
        category = cate;
        mWares.clear();
        initData();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initData() {

        SimpleHttpClient.newBuilder()
                .url(Contants.API.WARE_GET)
                .get()
                .addParams("category", category)
                .addParams("pagesize", pagesize)
                .addParams("curpage", curpage)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<Wares>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<Wares> wares) {

                        if (wares.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {

                            int size = wares.getData().size();
                            for (int i = 0; i < size; i++) {
                                mWares.add(wares.getData().get(i));
                            }
                            initRecycleView();
                        }
                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }

    private void initRecycleView() {

        if (adapter == null) {

            adapter = new RVSimpleAdapter();
            adapter.setOnItemClickListener(new RVBaseAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int position) {
                    Intent intent = new Intent(TogoActivity.this, CommodityDetailsAcitivity.class);
                    intent.putExtra("ware", mWares.get(position));
                    TogoActivity.this.startActivity(intent);
                }
            });
            adapter.OnlyOneItem(new cell_togo_ware(mWares), mWares.size());
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }else {
            adapter.clear();
            adapter.OnlyOneItem(new cell_togo_ware(mWares), mWares.size());
            mRecyclerView.setAdapter(adapter);
        }

    }

    private void refreshData() {

        mActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                category = new Random().nextInt(13);
                mWares.clear();
                initData();

            }
        });
    }
}
