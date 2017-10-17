package com.crackingMBA.training.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.RetrofitMockTest;

import java.util.List;

public class RetrofitMockTestAdapter extends RecyclerView.Adapter<RetrofitMockTestAdapter.QuestionViewHolder>{
    private List<RetrofitMockTest> mocktests;
    private int rowLayout;
    private Context context;

    public RetrofitMockTestAdapter(List<RetrofitMockTest> questions, int rowLayout, Context context) {
        this.mocktests = questions;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public RetrofitMockTestAdapter.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new QuestionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(QuestionViewHolder holder, final int position) {
        holder.mock_test_name.setText(mocktests.get(position).getTestName());
    }

    @Override
    public int getItemCount() {
        return mocktests.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView mock_test_name;

        public QuestionViewHolder(View v) {
            super(v);
            mock_test_name= (TextView) v.findViewById(R.id.mock_test_name);
        }
    }




}

