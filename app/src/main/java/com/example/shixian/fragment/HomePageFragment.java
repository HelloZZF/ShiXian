package com.example.shixian.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.shixian.Contants;
import com.example.shixian.FoodClassActivity;
import com.example.shixian.LookUsActivity;
import com.example.shixian.NewsActivity;
import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_home_item1;
import com.example.shixian.adapter.cell.cell_home_item2;
import com.example.shixian.adapter.cell.cell_home_item3;
import com.example.shixian.adapter.cell.cell_home_item5;
import com.example.shixian.adapter.cell.cell_home_item4;
import com.example.shixian.adapter.cell.cell_home_item6;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.HomeItem1;
import com.example.shixian.bean.HomeItem3;

import com.example.shixian.bean.HomeItem6;
import com.example.shixian.bean.Wares;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.utils.ToastUtils;
import com.example.shixian.widget.myToolbar;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by admin on 2017/9/2.
 */

public class HomePageFragment extends BaseFragment {

    private List<HomeItem1> myItem1 = new ArrayList<>();

    private List<HomeItem3> myItem3 = new ArrayList<>();

    private List<HomeItem1> myItem5 = new ArrayList<>();

    private List<Wares> myItem6 = new ArrayList<>();

    private View statusBar;

    private RecyclerView recyclerView;
    private MaterialRefreshLayout refreshLayout;
    private myToolbar toolbar;
    private int mdistanceY = 0;

    private RVSimpleAdapter homeAdapter;

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        statusBar = view.findViewById(R.id.home_StatusbarView);
        recyclerView = view.findViewById(R.id.home_item);
        toolbar = view.findViewById(R.id.home_toolbar);
        refreshLayout = view.findViewById(R.id.home_refresh);
        homeAdapter = new RVSimpleAdapter();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void init() {

        initMyItem1();
        initMyItem3();
        initMyItem5();
        initMyItem6();
        initRecyclerView();
        initToolbar();
        FitsStatusBar(statusBar);
        initRefresh();

    }

    private void initToolbar() {

        toolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FoodClassActivity.class);
                getContext().startActivity(intent);
            }
        });

        toolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LookUsActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    private void initRefresh() {

        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                    }
                }, 2000);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshLoadMore();
                    }
                }, 1000);
            }
        });

    }

    private void initRecyclerView() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mdistanceY += dy;
                int toolbarHeight = toolbar.getHeight();
                if (mdistanceY <= toolbarHeight) {
                    float scale = (float) mdistanceY / toolbarHeight;
                    float alpha = scale * 255;
                    //11,215,18
                    toolbar.setBackgroundColor(Color.argb((int) alpha, 86,209,118));
                    statusBar.setBackgroundColor(Color.argb((int) alpha, 86,209,118));
                } else{
                    //0xff0bd712
                    toolbar.setBackgroundColor(0xff56d176);
                    statusBar.setBackgroundColor(0xff56d176);
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(homeAdapter);

    }

    private void showData(){

        if (myItem1.size() > 0 && myItem3.size() > 0 && myItem5.size() > 0 && myItem6.size() > 0){

            homeAdapter.add(new cell_home_item1(myItem1));
            homeAdapter.add(new cell_home_item2(null));
            homeAdapter.add(new cell_home_item3(myItem3));
            homeAdapter.add(new cell_home_item4(null));
            homeAdapter.add(new cell_home_item5(myItem5));
            homeAdapter.add(new cell_home_item6(myItem6));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initMyItem1(){

        SimpleHttpClient.newBuilder()
                .get()
                .addParams("item", "item1")
                .url(Contants.API.HOME_GET)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<HomeItem1>>() {

                    @Override
                    public void onSuccess(Response response, BaseMsg<HomeItem1> Item1s) {

                        if (Item1s.getData().size() != 0){

                            for (int i = 0; i < Item1s.getData().size(); i++){

                                myItem1.add(Item1s.getData().get(i));
                            }
                            showData();
                        }

                    }

                    @Override
                    public void onError(int code, Exception e) {
                    }
                });
    }

    private void initMyItem3() {

        SimpleHttpClient.newBuilder()
                .get()
                .url(Contants.API.DIY_GET)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<HomeItem3>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<HomeItem3> item3s) {

                        if (item3s.getData().size() != 0){

                            for (int i = 0; i < item3s.getData().size(); i++){

                                myItem3.add(item3s.getData().get(i));
                            }
                            showData();
                        }

                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initMyItem5(){

        SimpleHttpClient.newBuilder()
                .get()
                .addParams("item", "item5")
                .url(Contants.API.HOME_GET)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<HomeItem1>>() {

                    @Override
                    public void onSuccess(Response response, BaseMsg<HomeItem1> Item5s) {

                        if (Item5s.getData().size() != 0){

                            for (int i = 0; i < Item5s.getData().size(); i++){

                                myItem5.add(Item5s.getData().get(i));
                            }
                            showData();
                        }

                    }

                    @Override
                    public void onError(int code, Exception e) {
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initMyItem6() {

        SimpleHttpClient.newBuilder()
                .get()
                .url(Contants.API.WARE_GET)
                .addParams("category", 2)
                .addParams("pagesize", 8)
                .addParams("curpage", 1)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<Wares>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<Wares> ware) {

                        if (ware.getData().size() != 0){

                            for (int i = 0; i < ware.getData().size(); i++){

                                myItem6.add(ware.getData().get(i));
                            }
                            showData();
                        }

                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }

}
