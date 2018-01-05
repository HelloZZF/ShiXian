package com.example.shixian.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_evaluation;
import com.example.shixian.bean.Evaluation;
import com.example.shixian.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/11/13.
 */

public class EvaluationFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private List<Evaluation> evaluations = new ArrayList<>();

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.evaluation_recyclerview);
        return view;
    }

    @Override
    public void init() {
        initEvaluation();
        showEvaluation();
    }

    private void showEvaluation() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        RVSimpleAdapter evaluationAdapter = new RVSimpleAdapter();
        evaluationAdapter.OnlyOneItem(new cell_evaluation(evaluations), evaluations.size());
        recyclerView.setAdapter(evaluationAdapter);
    }

    private void initEvaluation() {

        for (int i = 0; i < 10; i++){
            Evaluation evaluation = new Evaluation(R.drawable.item3_1, "中华小当家","大家好才是真的好，贼好吃，巨好吃，超好吃，真好吃！","2017/11/16");
            evaluations.add(evaluation);
        }

    }

}
