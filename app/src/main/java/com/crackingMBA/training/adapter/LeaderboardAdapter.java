package com.crackingMBA.training.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.RetrofitLeaderboardContent;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.QuestionViewHolder>{
    private List<RetrofitLeaderboardContent> questions;
    private int rowLayout;
    private Context context;

    public LeaderboardAdapter(List<RetrofitLeaderboardContent> questions, int rowLayout, Context context) {
        this.questions = questions;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public LeaderboardAdapter.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new QuestionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(QuestionViewHolder holder, final int position) {
        holder.leaderboard_sl.setText(questions.get(position).getSlno());
        holder.leaderboard_name.setText(questions.get(position).getName());
        holder.leaderboard_score.setText(questions.get(position).getTotalScore());
        holder.leaderboard_accuracy.setText(questions.get(position).getAccuracy());

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView leaderboard_sl, leaderboard_name, leaderboard_score, leaderboard_accuracy;

        public QuestionViewHolder(View v) {
            super(v);
            leaderboard_sl = (TextView) v.findViewById(R.id.leaderboard_sl);
            leaderboard_name = (TextView) v.findViewById(R.id.leaderboard_name);
            leaderboard_score = (TextView) v.findViewById(R.id.leaderboard_score);
            leaderboard_accuracy = (TextView) v.findViewById(R.id.leaderboard_accuracy);
        }
    }




}

