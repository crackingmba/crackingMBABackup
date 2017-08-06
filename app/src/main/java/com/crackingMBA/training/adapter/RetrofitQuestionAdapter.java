package com.crackingMBA.training.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.RetrofitQuestion;

import java.util.List;

/**
 * Created by vijayp on 8/4/17.
 */

public class RetrofitQuestionAdapter extends RecyclerView.Adapter<RetrofitQuestionAdapter.QuestionViewHolder>{
    private List<RetrofitQuestion> questions;
    private int rowLayout;
    private Context context;

    public RetrofitQuestionAdapter(List<RetrofitQuestion> questions, int rowLayout, Context context) {
        this.questions = questions;
        this.rowLayout = rowLayout;
        this.context = context;
    }



    @Override
    public RetrofitQuestionAdapter.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new QuestionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(QuestionViewHolder holder, final int position) {
        holder.questionTitle.setText(questions.get(position).getTitle());
        holder.comments_tv.setText(questions.get(position).getLink());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionTitle;
        TextView comments_tv;

        public QuestionViewHolder(View v) {
            super(v);
            questionTitle = (TextView) v.findViewById(R.id.title);
            comments_tv = (TextView) v.findViewById(R.id.comments_tv);
        }
    }




}

