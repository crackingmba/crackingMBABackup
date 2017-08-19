package com.crackingMBA.training.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.RetrofitPrepDetail;
import com.crackingMBA.training.pojo.RetrofitPrepDetail;

import java.util.List;

public class PreparationDetailAdapter extends RecyclerView.Adapter<PreparationDetailAdapter.QuestionViewHolder>{
    private List<RetrofitPrepDetail> questions;
    private int rowLayout;
    private Context context;
    int count=1;

    public PreparationDetailAdapter(List<RetrofitPrepDetail> questions, int rowLayout, Context context) {
        this.questions = questions;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public PreparationDetailAdapter.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new QuestionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(QuestionViewHolder holder, final int position) {
        holder.prep_detail_qn_tv.setText(questions.get(position).getQn());
        if(questions.get(position).getOpt1().isEmpty()){
            holder.prep_detail_opt1_tv.setVisibility(View.GONE);
        }else{
            holder.prep_detail_opt1_tv.setVisibility(View.VISIBLE);
            holder.prep_detail_opt1_tv.setText(questions.get(position).getOpt1());
        }

        if(questions.get(position).getOpt2().isEmpty()){
            holder.prep_detail_opt2_tv.setVisibility(View.GONE);
        }else{
            holder.prep_detail_opt2_tv.setVisibility(View.VISIBLE);
            holder.prep_detail_opt2_tv.setText(questions.get(position).getOpt2());
        }

        if(questions.get(position).getOpt3().isEmpty()){
            holder.prep_detail_opt3_tv.setVisibility(View.GONE);
        }else{
            holder.prep_detail_opt3_tv.setVisibility(View.VISIBLE);
            holder.prep_detail_opt3_tv.setText(questions.get(position).getOpt3());
        }

        if(questions.get(position).getOpt4().isEmpty()){
            holder.prep_detail_opt4_tv.setVisibility(View.GONE);
        }else{
            holder.prep_detail_opt4_tv.setVisibility(View.VISIBLE);
            holder.prep_detail_opt4_tv.setText(questions.get(position).getOpt4());
        }

        count++;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView prep_detail_qn_tv, prep_detail_opt1_tv, prep_detail_opt2_tv, prep_detail_opt3_tv, prep_detail_opt4_tv;

        public QuestionViewHolder(View v) {
            super(v);
            prep_detail_qn_tv = (TextView) v.findViewById(R.id.prep_detail_qn_tv);
            prep_detail_opt1_tv = (TextView) v.findViewById(R.id.prep_detail_opt1_tv);
            prep_detail_opt2_tv = (TextView) v.findViewById(R.id.prep_detail_opt2_tv);
            prep_detail_opt3_tv = (TextView) v.findViewById(R.id.prep_detail_opt3_tv);
            prep_detail_opt4_tv = (TextView) v.findViewById(R.id.prep_detail_opt4_tv);
        }
    }
}

