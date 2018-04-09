package com.example.shixian.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.Contants;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_evaluation;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.Evaluation;
import com.example.shixian.R;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by admin on 2017/11/13.
 */

public class EvaluationFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private List<Evaluation> evaluations = new ArrayList<>();

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);
        recyclerView = view.findViewById(R.id.evaluation_recyclerview);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void init() {
        initEvaluation();
    }

    private void showEvaluation() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        RVSimpleAdapter evaluationAdapter = new RVSimpleAdapter();
        evaluationAdapter.OnlyOneItem(new cell_evaluation(evaluations), evaluations.size());
        recyclerView.setAdapter(evaluationAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initEvaluation() {

        SimpleHttpClient.newBuilder()
                .get()
                .url(Contants.API.EVALUATE_GET)
                .addParams("get_all", 1)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<Evaluation>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<Evaluation> evaluation) {

                        if (evaluation.getData().size() != 0) {
                            evaluations = evaluation.getData();
                        }
                        showEvaluation();

                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });

    }

}
