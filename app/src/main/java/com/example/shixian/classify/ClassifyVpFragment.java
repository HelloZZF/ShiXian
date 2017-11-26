package com.example.shixian.classify;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.shixian.CommodityDetailsAcitivity;
import com.example.shixian.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/10/20.
 */

public class ClassifyVpFragment extends Fragment {

    private List<Goods> goodsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.classify_goods,container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.goods_list);
        initGoods();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        GoodsAdapter goodsAdapter = new GoodsAdapter(goodsList);
        goodsAdapter.setOnGoodsItemClickListener(new GoodsAdapter.OnGoodsItemClilckListener() {
            @Override
            public void OnClick(View view, int position) {
                Intent intent = new Intent(getActivity(), CommodityDetailsAcitivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(goodsAdapter);

        return view;

    }

    private void initGoods() {
        for (int i = 0; i < 10 ; i++){
            Goods goods = new Goods("20￥", "100好评", "糖醋排骨", R.drawable.item5_3, "精选上等排骨");
            goodsList.add(goods);
        }
    }
}
