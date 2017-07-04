package com.crackingMBA.training.adapter;

import android.graphics.Color;
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
import com.crackingMBA.training.pojo.MockTestTopic;

import java.util.List;

import static com.crackingMBA.training.R.drawable.mock_test_quant_bg;

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
        LinearLayout linlayout;

        public DataObjectHolder(View itemView) {
            super(itemView);
            mockTestTopicThumbnail = (ImageView) itemView.findViewById(R.id.mocktest_topic_thumbnail);
            mocktestTopicId = (TextView) itemView.findViewById(R.id.mocktest_topic_id);
            mocktestTopicTxt = (TextView) itemView.findViewById(R.id.mocktest_topic_txt);
            linlayout=(LinearLayout)itemView.findViewById(R.id.mock_test_subcategories_list);
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
        holder.mocktestTopicId.setText(mDataset.get(position).getId());
       //String mocktestTopicThumbnailURL = mDataset.get(position).getThumbnail();
        holder.mocktestTopicTxt.setText(mDataset.get(position).getName());
        holder.mockTestTopicThumbnail.setImageResource(R.drawable.applogo);

        String category_name = mDataset.get(position).getCategory_name();

        switch(category_name){
            case "quant":{
                holder.linlayout.setBackgroundResource(R.drawable.mock_test_quant_bg);
                break;
            }
            case "dilr":{
                holder.linlayout.setBackgroundResource(R.drawable.mock_test_dilr_bg);
                break;
            }
            case "verbal":{
                holder.linlayout.setBackgroundResource(R.drawable.mock_test_verbal_bg);
                break;
            }

        }

        Typeface custom_font=Typeface.createFromAsset(holder.mocktestTopicTxt.getContext().getAssets(),"fonts/Roboto-Regular.ttf");
        holder.mocktestTopicTxt.setTypeface(custom_font);


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
