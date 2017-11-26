package com.example.shixian;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/11/13.
 */

public class EvaluationFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Evaluations> evaluations = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.evaluation_recyclerview);
        initEvaluation();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        EvaluationAdapter evaluationAdapter = new EvaluationAdapter(evaluations);
        recyclerView.setAdapter(evaluationAdapter);

        return view;
    }

    private void initEvaluation() {

        for (int i = 0; i < 10; i++){
            Evaluations evaluation = new Evaluations(R.drawable.item3_1, "中华小当家","大家好才是真的好，贼好吃，巨好吃，超好吃，真好吃！","2017/11/16");
            evaluations.add(evaluation);
        }

    }

}
