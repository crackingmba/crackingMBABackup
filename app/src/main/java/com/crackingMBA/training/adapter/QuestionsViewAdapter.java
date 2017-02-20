package com.crackingMBA.training.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.Qstns;

/**
 * Created by Harish on 1/31/2017.
 */
public class QuestionsViewAdapter extends RecyclerView
        .Adapter<QuestionsViewAdapter.DataObjectHolder> {
    private List<Qstns> mDataset;
    private static MyClickListener myClickListener;
    private static String TAG = "QuestionsViewAdapter";

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView qstnId;
        TextView qstn;
        TextView date;

        public DataObjectHolder(View itemView) {
            super(itemView);
            qstnId = (TextView) itemView.findViewById(R.id.qstnId);
            qstn = (TextView) itemView.findViewById(R.id.qstntxt);
            date = (TextView) itemView.findViewById(R.id.datetxt);
            Log.i(TAG, "Adding Listener");
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

    public QuestionsViewAdapter(List<Qstns> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        Log.d(TAG,"in onCreateViewHolder..");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mydashboard_qstns_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Log.d(TAG,"in onBindViewHolder..");
        holder.qstnId.setText(mDataset.get(position).getQstnId());
        holder.qstn.setText(mDataset.get(position).getQstn());
        holder.date.setText(mDataset.get(position).getDate());
        Log.d(TAG, holder.qstn.getText()+" "+holder.date.getText());
    }

    public void addItem(Qstns dataObj, int index) {
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
