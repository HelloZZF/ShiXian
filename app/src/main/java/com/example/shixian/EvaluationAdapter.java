package com.example.shixian;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2017/11/15.
 */

public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.ViewHolder>{

    private List<Evaluations> mEvaluations;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView evaluationIcon;
        TextView evaluationName;
        TextView evaluationTime;
        TextView evaluationContent;

        public ViewHolder(View view) {
            super(view);
            evaluationIcon = (CircleImageView) view.findViewById(R.id.evaluation_icon);
            evaluationName = (TextView) view.findViewById(R.id.evaluation_name);
            evaluationTime = (TextView) view.findViewById(R.id.evaluation_time);
            evaluationContent = (TextView) view.findViewById(R.id.evaluation_content);
    }

    }

    public EvaluationAdapter(List<Evaluations> mEvaluatinos) {
        this.mEvaluations = mEvaluatinos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluation_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Evaluations evaluations = mEvaluations.get(position);
        holder.evaluationIcon.setImageResource(evaluations.getEvaluationIconId());
        holder.evaluationContent.setText(evaluations.getEvaluationContent());
        holder.evaluationName.setText(evaluations.getEvaluationName());
        holder.evaluationTime.setText(evaluations.getEvaluationTime());
    }

    @Override
    public int getItemCount() {
        return mEvaluations.size();
    }
}
