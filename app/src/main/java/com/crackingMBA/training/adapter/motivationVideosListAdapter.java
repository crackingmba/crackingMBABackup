package com.crackingMBA.training.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.MotivationVideos;

import java.util.List;

/**
 * Created by vijayp on 7/20/17.
 */

public class motivationVideosListAdapter extends RecyclerView
        .Adapter<motivationVideosListAdapter.DataObjectHolder> {

    private static String LOG_TAG = "motivationVideosListAdapter";
    private List<MotivationVideos> mDataset;
    private static motivationVideosListAdapter.MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView motivationVideosText;
        LinearLayout linlayout;

        public DataObjectHolder(View itemView) {
            super(itemView);
            motivationVideosText = (TextView) itemView.findViewById(R.id.motivationText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(motivationVideosListAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public motivationVideosListAdapter(List<MotivationVideos> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public motivationVideosListAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.d(TAG,"in onCreateViewHolder..");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout_recyclerview_motivation, parent, false);

        motivationVideosListAdapter.DataObjectHolder dataObjectHolder = new motivationVideosListAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(motivationVideosListAdapter.DataObjectHolder holder, int position) {
        holder.motivationVideosText.setText(mDataset.get(position).getName());
    }

    public void addItem(MotivationVideos dataObj, int index) {
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
