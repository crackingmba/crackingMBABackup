package com.crackingMBA.training.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.Exam;
import com.squareup.picasso.Picasso;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crackingMBA.training.CrackingConstant;
import com.crackingMBA.training.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Harish on 1/31/2017.
 */
public class ExamAdapter extends RecyclerView
        .Adapter<ExamAdapter.DataObjectHolder> {
    private List<Exam> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        ImageView exam_thumbnail;
        TextView exam_name, exam_description, exam_sections, exam_date;

        public DataObjectHolder(View itemView) {
            super(itemView);
            exam_thumbnail = (ImageView) itemView.findViewById(R.id.exam_thumbnail);
            exam_name = (TextView) itemView.findViewById(R.id.exam_name);
            exam_description = (TextView) itemView.findViewById(R.id.exam_description);
            //exam_sections = (TextView) itemView.findViewById(R.id.exam_sections);
            exam_date = (TextView) itemView.findViewById(R.id.exam_date);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public ExamAdapter(List<Exam> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_row_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.exam_name.setText(mDataset.get(position).getExam_name());
        holder.exam_description.setText(mDataset.get(position).getExam_description());
        //holder.exam_sections.setText(mDataset.get(position).getExam_sections());
        holder.exam_date.setText(mDataset.get(position).getExam_date());
        //holder.exam_thumbnail.setText(mDataset.get(position).getExam_name());

        Context context= holder.exam_thumbnail.getContext();

        String uri = "@drawable/"+mDataset.get(position).getExam_img();
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());

        Drawable res = context.getResources().getDrawable(imageResource);
        holder.exam_thumbnail.setImageDrawable(res);


        //holder.exam_thumbnail.set
        //int id=context.getResources().getIdentifier(mDataset.get(position).getThumbnailURL(), "drawable", context.getPackageName());
        //int id=context.getResources().getIdentifier(mDataset.get(position).getThumbnailURL(), "drawable", context.getPackageName());
        //holder.exam_thumbnail.setImageDrawable(mDataset.get(position).getExam_img());
        //Picasso.with(context.getApplicationContext()).load("cat2017").into(holder.exam_thumbnail);
        //Picasso.with(context.getApplicationContext()).load("http://www.crackingmba.com/wp-content/uploads/2017/04/badge_new.png").into(holder.thumbnail);

    }

    public void addItem(Exam dataObj, int index) {
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
