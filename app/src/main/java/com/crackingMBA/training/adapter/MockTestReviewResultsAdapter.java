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

/**
 * Created by Harish on 1/31/2017.
 */
public class MockTestReviewResultsAdapter extends RecyclerView
        .Adapter<MockTestReviewResultsAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MockTestReviewResultsAdapter";
    private List<ReviewResultPojo> mDataset;
    private static MyClickListener myClickListener;
    private static String TAG = "MockTestReviewResultsAdapter";

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
        Log.d(TAG,"in onCreateViewHolder..");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mocktest_reviewresultsview_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Log.d(TAG,"in onBindViewHolder..");
        holder.questionTxt.setText(mDataset.get(position).getQuestionTxt());
        String yourAnsrTxt = mDataset.get(position).getSelectedAnswerTxt()==null ? CrackingConstant.NOT_ATTEMPTED : mDataset.get(position).getSelectedAnswerTxt()+" (Your Answer)";
        holder.selectedAnswerTxt.setText(yourAnsrTxt);
        //#AB0800 #3F9D2F
        holder.selectedAnswerTxt.setTextColor(Color.rgb(171,8,0));
        holder.correctAnswerTxt.setText(mDataset.get(position).getCorrectAnswerTxt()+" (Correct Answer)");
        holder.correctAnswerTxt.setTextColor(Color.rgb(63,157,47));
        holder.answerExplanationTxt.setText(mDataset.get(position).getAnswerExplanation());
    }

    public void addItem(ReviewResultPojo dataObj, int index) {
        Log.d(TAG,"in addItem..");
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        Log.d(TAG,"in deleteView..");
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
