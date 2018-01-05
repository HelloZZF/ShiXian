package com.example.shixian.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.Evaluation;

import java.util.List;

/**
 * Created by admin on 2017/12/31.
 */

public class cell_evaluation extends RVBaseCell<List<Evaluation>>{

    public static final int Type = 7;
    private Context mContext;
    private List<Evaluation> mEvaluations;

    public cell_evaluation(List<Evaluation> evaluations) {
        super(evaluations);
        this.mEvaluations = evaluations;
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.template_evaluation_layout, parent,false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view, listener);

        return holder;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        Evaluation evaluations = mEvaluations.get(position);
        holder.getImageView(R.id.evaluation_icon).setImageResource(evaluations.getEvaluationIconId());
        holder.getTextView(R.id.evaluation_content).setText(evaluations.getEvaluationContent());
        holder.getTextView(R.id.evaluation_name).setText(evaluations.getEvaluationName());
        holder.getTextView(R.id.evaluation_time).setText(evaluations.getEvaluationTime());
    }
}
