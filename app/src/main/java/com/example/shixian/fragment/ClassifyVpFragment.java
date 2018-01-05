package com.example.shixian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.CommodityDetailsAcitivity;
import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_classify_ware;
import com.example.shixian.bean.Ware;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/10/20.
 */

public class ClassifyVpFragment extends BaseFragment {

    private List<Ware> waresList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RVSimpleAdapter wareAdapter;

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_classify_wares,container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.goods_list);

        return view;
    }

    @Override
    public void init() {

        initWares();
        showWares();

    }

    private void showWares() {

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        wareAdapter = new RVSimpleAdapter();
        wareAdapter.setOnItemClickListener(new RVBaseAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                startActivity(new Intent(getContext(), CommodityDetailsAcitivity.class));
            }
        });
        wareAdapter.OnlyOneItem(new cell_classify_ware(waresList), waresList.size());
        recyclerView.setAdapter(wareAdapter);
    }

    private void initWares() {
        for (int i = 0; i < 10 ; i++){
            Ware goods = new Ware("20￥", "100好评", "糖醋排骨", R.drawable.item5_3, "精选上等排骨");
            waresList.add(goods);
        }
    }
}
