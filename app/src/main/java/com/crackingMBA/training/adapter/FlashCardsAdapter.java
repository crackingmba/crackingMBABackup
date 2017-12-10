package com.crackingMBA.training.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.RetrofitFlashCardContent;
import com.crackingMBA.training.pojo.RetrofitFlashCardContent;

import java.util.List;

public class FlashCardsAdapter extends RecyclerView.Adapter<FlashCardsAdapter.QuestionViewHolder>{
    private List<RetrofitFlashCardContent> questions;
    private int rowLayout;
    private Context context;

    public FlashCardsAdapter(List<RetrofitFlashCardContent> questions, int rowLayout, Context context) {
        this.questions = questions;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public FlashCardsAdapter.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new QuestionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(QuestionViewHolder holder, final int position) {
        holder.prep_content_category.setText(questions.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView prep_content_category;

        public QuestionViewHolder(View v) {
            super(v);
            prep_content_category = (TextView) v.findViewById(R.id.prep_content_category);
        }
    }




}

