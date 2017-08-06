package com.crackingMBA.training.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.RetrofitForumComment;
import com.crackingMBA.training.pojo.RetrofitQuestion;

import java.util.List;

/**
 * Created by vijayp on 8/4/17.
 */

public class RetrofitForumCommentAdapter extends RecyclerView.Adapter<RetrofitForumCommentAdapter.QuestionViewHolder>{
    private List<RetrofitForumComment> questions;
    private int rowLayout;
    private Context context;

    public RetrofitForumCommentAdapter(List<RetrofitForumComment> questions, int rowLayout, Context context) {
        this.questions = questions;
        this.rowLayout = rowLayout;
        this.context = context;
    }



    @Override
    public RetrofitForumCommentAdapter.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new QuestionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(QuestionViewHolder holder, final int position) {
        holder.comment_detail_tv.setText(questions.get(position).getComment_details());
        holder.comment_postedby_tv.setText(questions.get(position).getPosted_by());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView comment_detail_tv;
        TextView comment_postedby_tv;

        public QuestionViewHolder(View v) {
            super(v);
            comment_detail_tv = (TextView) v.findViewById(R.id.comment_detail_tv);
            comment_postedby_tv = (TextView) v.findViewById(R.id.comment_postedby_tv);
        }
    }




}

