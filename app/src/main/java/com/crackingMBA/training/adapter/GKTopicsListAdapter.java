package com.crackingMBA.training.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.GKTopics;

import java.util.List;

/**
 * Created by vijayp on 7/9/17.
 */

public class GKTopicsListAdapter extends RecyclerView
        .Adapter<GKTopicsListAdapter.DataObjectHolder>{


    private static String LOG_TAG = "GKTopicsAdapter";
    private List<GKTopics> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView gkTopicsText;
        LinearLayout linlayout;

        public DataObjectHolder(View itemView) {
            super(itemView);
            gkTopicsText = (TextView) itemView.findViewById(R.id.gkTopicsText);
            //linlayout=(LinearLayout)itemView.findViewById(R.id.mock_test_subcategories_list);
            itemView.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        myClickListener.onItemClick(getPosition(), v);
    }
}

    public void setOnItemClickListener(GKTopicsListAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public GKTopicsListAdapter(List<GKTopics> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.d(TAG,"in onCreateViewHolder..");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout_recyclerview_gktopics, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.gkTopicsText.setText(mDataset.get(position).getName());
    }

    public void addItem(GKTopics dataObj, int index) {
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
