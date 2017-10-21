package com.crackingMBA.training.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crackingMBA.training.CrackingConstant;
import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.ReviewResultPojo;

import java.util.List;

public class MockTestReviewResultsAdapter extends RecyclerView
        .Adapter<MockTestReviewResultsAdapter.DataObjectHolder> {
    private List<ReviewResultPojo> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView questionTxt, selectedAnswerTxt, correctAnswerTxt, answerExplanationTxt;

        public DataObjectHolder(View itemView) {
            super(itemView);
            questionTxt = (TextView) itemView.findViewById(R.id.reviewanswers_questionTxt);
            selectedAnswerTxt = (TextView) itemView.findViewById(R.id.reviewanswers_selectedAnswerTxt);
            correctAnswerTxt = (TextView) itemView.findViewById(R.id.reviewanswers_correctAnswerTxt);
            answerExplanationTxt = (TextView) itemView.findViewById(R.id.reviewanswers_AnswerExplanation);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MockTestReviewResultsAdapter(List<ReviewResultPojo> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mocktest_reviewresultsview_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.questionTxt.setText(mDataset.get(position).getQuestionTxt());
        String yourAnsrTxt = mDataset.get(position).getSelectedAnswerTxt()==null ? CrackingConstant.NOT_ATTEMPTED : "Your Answer : "+mDataset.get(position).getSelectedAnswerTxt();
        holder.selectedAnswerTxt.setText(yourAnsrTxt);
        //#AB0800 #3F9D2F
        //holder.selectedAnswerTxt.setTextColor(Color.rgb(171,8,0));
        holder.selectedAnswerTxt.setTextColor(Color.BLUE);
        holder.correctAnswerTxt.setText("Correct Answer : "+mDataset.get(position).getCorrectAnswerTxt());
        //holder.correctAnswerTxt.setTextColor(Color.rgb(63,157,47));
        holder.correctAnswerTxt.setTextColor(Color.GREEN);
        holder.answerExplanationTxt.setText("ANSWER EXPLANATION : "+mDataset.get(position).getAnswerExplanation());
    }

    public void addItem(ReviewResultPojo dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
