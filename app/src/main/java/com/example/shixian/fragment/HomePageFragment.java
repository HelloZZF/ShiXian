package com.example.shixian.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.shixian.R;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_home_item1;
import com.example.shixian.adapter.cell.cell_home_item2;
import com.example.shixian.adapter.cell.cell_home_item3;
import com.example.shixian.adapter.cell.cell_home_item5;
import com.example.shixian.adapter.cell.cell_home_item4;
import com.example.shixian.adapter.cell.cell_home_item6;
import com.example.shixian.bean.HomeItem3;

import com.example.shixian.bean.HomeItem6;
import com.example.shixian.widget.myToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/9/2.
 */

public class HomePageFragment extends BaseFragment {

    private List<HomeItem3> myItem3 = new ArrayList<>();

    private List<HomeItem6> myItem5 = new ArrayList<>();

    private View statusBar;

    private RecyclerView recyclerView;
    private MaterialRefreshLayout refreshLayout;
    private myToolbar toolbar;
    private int mdistanceY = 0;

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        statusBar = view.findViewById(R.id.home_StatusbarView);
        recyclerView = view.findViewById(R.id.home_item);
        toolbar = view.findViewById(R.id.home_toolbar);
        refreshLayout = view.findViewById(R.id.home_refresh);

        return view;
    }

    @Override
    public void init() {

        initMyItem3();
        initMyItem5();
        FitsStatusBar(statusBar);
        initRecyclerView();
        initRefresh();

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
                    toolbar.setBackgroundColor(Color.argb((int) alpha, 11,215,18));
                    statusBar.setBackgroundColor(Color.argb((int) alpha, 11,215,18));
                } else{
                    toolbar.setBackgroundColor(0xff0bd712);
                    statusBar.setBackgroundColor(0xff0bd712);
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RVSimpleAdapter homeAdapter = new RVSimpleAdapter();

        homeAdapter.add(new cell_home_item1(null));
        homeAdapter.add(new cell_home_item2(null));
        homeAdapter.add(new cell_home_item3(myItem3));
        homeAdapter.add(new cell_home_item4(null));
        homeAdapter.add(new cell_home_item5(null));
        homeAdapter.add(new cell_home_item6(myItem5));

        recyclerView.setAdapter(homeAdapter);

    }

    private void initMyItem3() {
        HomeItem3 Item1 = new HomeItem3("全职爸爸","混炒肉丝",R.drawable.item1_1,R.drawable.item1_4);
        HomeItem3 Item2 = new HomeItem3("Holle Word","秘制炒蛋",R.drawable.item1_2,R.drawable.item1_5);
        HomeItem3 Item3 = new HomeItem3("宝宝","养生鱼汤",R.drawable.item1_3,R.drawable.item1_6);
        HomeItem3 Item4 = new HomeItem3("全职爸爸","混炒肉丝",R.drawable.item1_1,R.drawable.item1_4);
        HomeItem3 Item5 = new HomeItem3("Holle Word","秘制炒蛋",R.drawable.item1_2,R.drawable.item1_5);
        HomeItem3 Item6 = new HomeItem3("宝宝","养生鱼汤",R.drawable.item1_3,R.drawable.item1_6);
        myItem3.add(Item1);
        myItem3.add(Item2);
        myItem3.add(Item3);
        myItem3.add(Item4);
        myItem3.add(Item5);
        myItem3.add(Item6);
    }

    private void initMyItem5() {
        HomeItem6 Item1 = new HomeItem6(R.drawable.item5_1,"蒜炒五花肉","21¥");
        HomeItem6 Item2 = new HomeItem6(R.drawable.item5_2,"笋干鸭胗","27¥");
        HomeItem6 Item3 = new HomeItem6(R.drawable.item5_3,"干菇牛肉","25¥");
        HomeItem6 Item4 = new HomeItem6(R.drawable.item5_4,"清蒸黄金丝","19¥");
        HomeItem6 Item5 = new HomeItem6(R.drawable.item5_5,"栗子鸡肉","23¥");
        HomeItem6 Item6 = new HomeItem6(R.drawable.item5_6,"麻辣滑鸡","30¥");
        HomeItem6 Item7 = new HomeItem6(R.drawable.item5_7,"红烧鸡块","26¥");
        HomeItem6 Item8 = new HomeItem6(R.drawable.item5_8,"培根包菜","18¥");
        myItem5.add(Item1);
        myItem5.add(Item2);
        myItem5.add(Item3);
        myItem5.add(Item4);
        myItem5.add(Item5);
        myItem5.add(Item6);
        myItem5.add(Item7);
        myItem5.add(Item8);
    }

}
