package com.example.shixian;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_collect;
import com.example.shixian.adapter.cell.cell_coupon;
import com.example.shixian.adapter.cell.cell_evaluation;
import com.example.shixian.adapter.cell.cell_mydiy;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.Coupon;
import com.example.shixian.bean.Evaluation;
import com.example.shixian.bean.HomeItem3;
import com.example.shixian.bean.User;
import com.example.shixian.bean.Wares;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.widget.myToolbar;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class UserUniversalActivity extends BaseActivity {

    private myToolbar mToolbar;
    private View mStatus;
    private RecyclerView mRecyclerView;
    private String FLAG = "";
    private RVSimpleAdapter mAdapter;
    private List<Evaluation> mEvaluationList = new ArrayList<>();
    private List<Coupon> mCouponList = new ArrayList<>();
    private List<Wares> mCollectList = new ArrayList<>();
    private List<HomeItem3> mDiyWareList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_universal);
        mToolbar = findViewById(R.id.uu_toolbar);
        mStatus = findViewById(R.id.uu_status);
        mRecyclerView = findViewById(R.id.uu_recycler);

        if (getIntent().getStringExtra("UNIVERSAL") != null)
            FLAG = getIntent().getStringExtra("UNIVERSAL");

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {

        FitsStatusBar(mStatus);
        setStatusbar();
        initToolbar();

        ChooserActivity();
        refreshData();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ChooserActivity() {

        switch (FLAG) {

            case "MY_EVALUATION":
                setToolbarTitle("我的评论");
                initEvaluationData();
                break;

            case "MY_COUPON":
                setToolbarTitle("优惠券");
                initCouponData();
                break;

            case "MY_COLLECT":
                setToolbarTitle("我的收藏");
                initCollectData();
                break;

            case "MY_DIY":
                setToolbarTitle("我的创作");
                initDiyData();
                break;

            default:
                break;
        }
    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserUniversalActivity.this.finish();
            }
        });

    }

    private void setToolbarTitle(String title) {

        mToolbar.setTitle(title);
    }


    private void initRecycler(RVBaseCell cell, List list) {

        if (mAdapter == null) {

            mAdapter = new RVSimpleAdapter();
            mAdapter.OnlyOneItem(cell, list.size());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mAdapter);

        } else {

            mAdapter.clear();
            mAdapter.OnlyOneItem(cell, list.size());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initEvaluationData() {

        User mUser = ShiXianApplication.getInstance().getUser();
        SimpleHttpClient.newBuilder()
                .url(Contants.API.EVALUATE_GET)
                .addParams("user_id", Integer.parseInt(mUser.getId()))
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<Evaluation>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<Evaluation> evaluation) {

                        if (evaluation.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {

                            mEvaluationList = evaluation.getData();
                            initRecycler(new cell_evaluation(mEvaluationList), mEvaluationList);
                        }

                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initCollectData() {

        User mUser = ShiXianApplication.getInstance().getUser();
        SimpleHttpClient.newBuilder()
                .url(Contants.API.COLLECTION_GET)
                .addParams("user_id", Integer.parseInt(mUser.getId()))
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<Wares>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<Wares> waresBaseMsg) {

                        if (waresBaseMsg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {

                            mCollectList = waresBaseMsg.getData();
                            if (mAdapter == null) {

                                mAdapter = new RVSimpleAdapter();
                                mAdapter.OnlyOneItem(new cell_collect(mCollectList, UserUniversalActivity.this, mAdapter), mCollectList.size());
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(UserUniversalActivity.this));
                                mRecyclerView.setAdapter(mAdapter);
                            }else{

                                mAdapter.clear();
                                mAdapter.OnlyOneItem(new cell_collect(mCollectList, UserUniversalActivity.this, mAdapter), mCollectList.size());
                            }

                        }

                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initCouponData() {

        User mUser = ShiXianApplication.getInstance().getUser();
        SimpleHttpClient.newBuilder()
                .url(Contants.API.COUPON_GET)
                .addParams("user_id", Integer.parseInt(mUser.getId()))
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<Coupon>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<Coupon> couponBaseMsg) {

                        if (couponBaseMsg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {
                            mCouponList = couponBaseMsg.getData();
                            initRecycler(new cell_coupon(mCouponList), mCouponList);
                        }

                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initDiyData() {

        User mUser = ShiXianApplication.getInstance().getUser();
        SimpleHttpClient.newBuilder()
                .url(Contants.API.DIY_GET)
                .addParams("user_id", Integer.parseInt(mUser.getId()))
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<HomeItem3>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<HomeItem3> msg) {

                        if (msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {
                            mDiyWareList = msg.getData();
                            initRecycler(new cell_mydiy(mDiyWareList), mDiyWareList);
                        }

                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void refreshData() {

        if (mAdapter != null && ShiXianApplication.getInstance().isChangeUser() == true) {

            switch (FLAG) {

                case "MY_EVALUATION":
                    initEvaluationData();
                    break;

                case "MY_COLLECT":
                    initCollectData();
                    break;

                case "MY_COUPON":
                    initCouponData();
                    break;

                case "MY_DIY":
                    initDiyData();
                    break;

                default:
                    break;
            }

        }
    }
}
