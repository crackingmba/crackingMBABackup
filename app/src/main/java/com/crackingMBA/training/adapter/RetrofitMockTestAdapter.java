package com.crackingMBA.training.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.RetrofitMockTest;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RetrofitMockTestAdapter extends RecyclerView.Adapter<RetrofitMockTestAdapter.QuestionViewHolder>{
    private List<RetrofitMockTest> mocktests;
    private int rowLayout;
    private Context context;
    Drawable myDrawable;

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
        holder.mock_test_sections.setText("* " +mocktests.get(position).getSections());
        holder.mock_test_time.setText("* " +mocktests.get(position).getTime());

        String temp_exam_type="";
        if(mocktests.get(position).getExamType().equals("free")){
            temp_exam_type="FREE Test";
        }else if(mocktests.get(position).getExamType().equals("paid")){
            temp_exam_type="PREMIUM Test";
        }
        holder.mock_test_type.setText("* " + temp_exam_type);
        holder.mock_test_date.setText("* " +mocktests.get(position).getDate());

        String exam_name = mocktests.get(position).getTestName().toString();
        exam_name=exam_name.substring(0, exam_name.indexOf(" "));

        if(exam_name.equals("SNAP")){
            myDrawable = holder.minitest_img.getResources().getDrawable(R.drawable.snap2017);
        }

        if(exam_name.equals("XAT")){
            myDrawable = holder.minitest_img.getResources().getDrawable(R.drawable.xat2018);
        }

        holder.minitest_img.setImageDrawable(myDrawable);
    }

    @Override
    public int getItemCount() {
        return mocktests.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView mock_test_name, mock_test_sections, mock_test_time, mock_test_type, mock_test_date;
        ImageView minitest_img;

        public QuestionViewHolder(View v) {
            super(v);
            mock_test_name= (TextView) v.findViewById(R.id.mock_test_name);
            mock_test_sections= (TextView) v.findViewById(R.id.mock_test_sections);
            mock_test_time= (TextView) v.findViewById(R.id.mock_test_time);
            mock_test_type= (TextView) v.findViewById(R.id.mock_test_type);
            mock_test_date= (TextView) v.findViewById(R.id.mock_test_date);
            minitest_img=(ImageView)v.findViewById(R.id.minitest_img);
        }
    }




}

