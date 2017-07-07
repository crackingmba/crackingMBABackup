package com.crackingMBA.training.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.VocabGames;

import java.util.List;

/**
 * Created by vijayp on 7/7/17.
 */
public class VocabGamesListAdapter extends RecyclerView
        .Adapter<VocabGamesListAdapter.DataObjectHolder> {
    private static String LOG_TAG = "VocabGamesListAdapter";
    private List<VocabGames> mDataset;
    private static com.crackingMBA.training.adapter.VocabGamesListAdapter.MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView vocabgamesText;
        LinearLayout linlayout;

        public DataObjectHolder(View itemView) {
            super(itemView);
            vocabgamesText = (TextView) itemView.findViewById(R.id.vocabgamesText);
            //linlayout=(LinearLayout)itemView.findViewById(R.id.mock_test_subcategories_list);
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

    public VocabGamesListAdapter(List<VocabGames> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.d(TAG,"in onCreateViewHolder..");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout_recyclerview_vocabgames, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.vocabgamesText.setText(mDataset.get(position).getName());
    }

    public void addItem(VocabGames dataObj, int index) {
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

