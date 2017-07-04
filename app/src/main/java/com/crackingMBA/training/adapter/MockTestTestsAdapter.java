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
import com.crackingMBA.training.pojo.MockTestTest;

import java.util.List;

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
        //ImageView mockTestTestThumbnail;
        TextView mocktestTestTxt;
        LinearLayout linlayout;

        public DataObjectHolder(View itemView) {
            super(itemView);
            //mockTestTestThumbnail = (ImageView) itemView.findViewById(R.id.mocktest_test_thumbnail);
            mocktestTestId = (TextView) itemView.findViewById(R.id.mocktest_test_id);
            mocktestTestTxt = (TextView) itemView.findViewById(R.id.mocktest_test_title);
            linlayout=(LinearLayout)itemView.findViewById(R.id.mock_test_tests_list);
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
        holder.mocktestTestId.setText(mDataset.get(position).getTestId());
        //String mocktestTestThumbnailURL = mDataset.get(position).getTestThumbnailUrl();
        holder.mocktestTestTxt.setText(mDataset.get(position).getTestTitle());

        String category_name=mDataset.get(position).getMtSubCatId();
        Log.d("MockTest_SubCategory ", category_name);

        String categ_name = mDataset.get(position).getCategory_name();

        switch(categ_name){
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

        Typeface custom_font=Typeface.createFromAsset(holder.mocktestTestTxt.getContext().getAssets(),"fonts/Roboto-Regular.ttf");
        holder.mocktestTestTxt.setTypeface(custom_font);
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
