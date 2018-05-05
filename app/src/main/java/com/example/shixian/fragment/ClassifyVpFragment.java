package com.example.shixian.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.shixian.CommodityDetailsAcitivity;
import com.example.shixian.Contants;
import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_classify_ware;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.Wares;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by admin on 2017/10/20.
 */

public class ClassifyVpFragment extends BaseFragment {

    private List<Wares> waresList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RVSimpleAdapter wareAdapter;
    private MaterialRefreshLayout mRefreshLayout;
    private int category;
    private int pagesize = 5;
    private int curpage = 1;
    private int flag = 1;
    private int BeforeWaresSize;

    private  static final int STATE_NORMAL = 0;
    private  static final int STATE_REFREH = 1;
    private  static final int STATE_MORE = 2;
    private  int state = STATE_NORMAL;

    //Fragment构造方法用SetArguments添加参数
    public static ClassifyVpFragment newInstance(int position){

        ClassifyVpFragment newFragment = new ClassifyVpFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_classify_wares,container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.goods_list);
        mRefreshLayout = view.findViewById(R.id.classify_refresh);
        category = getArguments().getInt("position");

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void init() {

        initWares();
        initRefresh();

    }

    private void initRefresh(){

        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                Refresh();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

                LoadMore();
                if (curpage > 20 && flag == 1){
                    flag = 0;
                    Toast.makeText(getContext(), "我是有底线的", Toast.LENGTH_SHORT).show();
                }
                //在这里也加上停止刷新是因为随着curpage的不断增大，大于2之后数据库返回的数据就为空了
                //在InitWare中有判空操作，所以数据为空时不会执行ShowWares函数自然不会执行case STATE_MORE
                //中的停止刷新
                mRefreshLayout.finishRefreshLoadMore();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void Refresh(){

        curpage = 1;
        state = STATE_REFREH;
        waresList.clear();
        initWares();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void LoadMore(){

        curpage++;
        BeforeWaresSize = waresList.size();
        state = STATE_MORE;
        initWares();
    }

    private void showWares() {

        switch (state){

            case STATE_NORMAL:

                if (wareAdapter == null){

                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    wareAdapter = new RVSimpleAdapter();
                    wareAdapter.OnlyOneItem(new cell_classify_ware(waresList, getContext()), waresList.size());
                    recyclerView.setAdapter(wareAdapter);
                }

                break;

            case STATE_REFREH:

                wareAdapter.clear();
                wareAdapter.OnlyOneItem(new cell_classify_ware(waresList, getContext()), waresList.size());
                //recyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();

                break;

            case STATE_MORE:

                List<cell_classify_ware> cells = new ArrayList<>();
                for (int i = 0; i < waresList.size()-BeforeWaresSize; i++){

                    cells.add(new cell_classify_ware(waresList, getContext()));
                }
                if (cells != null && cells.size() > 0)
                    wareAdapter.addAll(BeforeWaresSize, cells);
                mRefreshLayout.finishRefreshLoadMore();

                break;

            default:
                break;
        }



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initWares() {

        SimpleHttpClient.newBuilder()
                .get()
                .url(Contants.API.WARE_GET)
                .addParams("category", category)
                .addParams("curpage", curpage)
                .addParams("pagesize", pagesize)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<Wares>>() {
                    @Override
                    public void onSuccess(Response response, final BaseMsg<Wares> ware) {

                        if (ware.getData().size() != 0){

                            for (int i = 0; i < ware.getData().size(); i++){

                                waresList.add(ware.getData().get(i));
                            }
                            showWares();
                        }
                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });

    }
}
