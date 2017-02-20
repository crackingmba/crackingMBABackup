package com.crackingMBA.training.adapter;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.crackingMBA.training.CrackingConstant;
import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.MockTestTopic;
import com.crackingMBA.training.pojo.VideoList;

/**
 * Created by Harish on 1/31/2017.
 */
public class MockTestTopicsAdapter extends RecyclerView
        .Adapter<MockTestTopicsAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MockTestTopicsAdapter";
    private List<MockTestTopic> mDataset;
    private static MyClickListener myClickListener;
    private static String TAG = "MockTestTopicsAdapter";

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView mocktestTopicId;
        ImageView mockTestTopicThumbnail;
        TextView mocktestTopicTxt;

        public DataObjectHolder(View itemView) {
            super(itemView);
            mockTestTopicThumbnail = (ImageView) itemView.findViewById(R.id.mocktest_topic_thumbnail);
            mocktestTopicId = (TextView) itemView.findViewById(R.id.mocktest_topic_id);
            mocktestTopicTxt = (TextView) itemView.findViewById(R.id.mocktest_topic_txt);
            Log.i(LOG_TAG, "Adding Listener");
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

    public MockTestTopicsAdapter(List<MockTestTopic> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        Log.d(TAG,"in onCreateViewHolder..");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mocktest_topicsview_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Log.d(TAG,"in onBindViewHolder..");
        holder.mocktestTopicId.setText(mDataset.get(position).getMocktestTopicId());
        String mocktestTopicThumbnailURL = mDataset.get(position).getMocktestTopicThumbnailURL();
        holder.mocktestTopicTxt.setText(mDataset.get(position).getMocktestTopicTxt());

        /*Bitmap mIcon11 = null;
        try {
            Log.d("suresh", CrackingConstant.MYPATH + mocktestTopicThumbnailURL);
            AsyncTask result = new DownloadImageTask((ImageView) holder.mockTestTopicThumbnail)
                    .execute(CrackingConstant.MYPATH +"img/"+mocktestTopicThumbnailURL);
        }
        catch (Exception e){
        }*/
        holder.mockTestTopicThumbnail.setImageResource(R.drawable.img1);
    }

    public void addItem(MockTestTopic dataObj, int index) {
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
