package com.crackingMBA.training.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.MockTestTest;
import com.crackingMBA.training.pojo.MockTestTopic;

/**
 * Created by Harish on 1/31/2017.
 */
public class MockTestTestsAdapter extends RecyclerView
        .Adapter<MockTestTestsAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MockTestTestsAdapter";
    private List<MockTestTest> mDataset;
    private static MyClickListener myClickListener;
    private static String TAG = "MockTestTestsAdapter";

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView mocktestTestId;
        ImageView mockTestTestThumbnail;
        TextView mocktestTestTxt;

        public DataObjectHolder(View itemView) {
            super(itemView);
            mockTestTestThumbnail = (ImageView) itemView.findViewById(R.id.mocktest_test_thumbnail);
            mocktestTestId = (TextView) itemView.findViewById(R.id.mocktest_test_id);
            mocktestTestTxt = (TextView) itemView.findViewById(R.id.mocktest_test_title);
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

    public MockTestTestsAdapter(List<MockTestTest> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        Log.d(TAG,"in onCreateViewHolder..");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mocktest_testsview_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Log.d(TAG,"in onBindViewHolder..");
        holder.mocktestTestId.setText(mDataset.get(position).getMocktestTestId());
        String mocktestTestThumbnailURL = mDataset.get(position).getMocktestTestThumbnailURL();
        holder.mocktestTestTxt.setText(mDataset.get(position).getMocktestTestTitle());

        /*Bitmap mIcon11 = null;
        try {
            Log.d("suresh", CrackingConstant.MYPATH + mocktestTestThumbnailURL);
            AsyncTask result = new DownloadImageTask((ImageView) holder.mockTestTestThumbnail)
                    .execute(CrackingConstant.MYPATH +"img/"+mocktestTestThumbnailURL);
        }
        catch (Exception e){
        }*/
        holder.mockTestTestThumbnail.setImageResource(R.drawable.img1);
    }

    public void addItem(MockTestTest dataObj, int index) {
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
