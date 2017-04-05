package com.crackingMBA.training.adapter;

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
import com.crackingMBA.training.pojo.VideoList;

import java.util.List;

/**
 * Created by Harish on 1/31/2017.
 */
public class VerbalHomeVideoViewAdapter extends RecyclerView
        .Adapter<VerbalHomeVideoViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "HomeVideoViewAdapter";
    private List<VideoList> mDataset;
    private static MyClickListener myClickListener;
    private static String TAG = "HomeVideoViewAdapter";

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView videoID;
        TextView videoTitle;
        TextView thumbnailURL;
        TextView videoURL;
        TextView dateOfUploaded;
        TextView videoDescription;
        ImageView thumbnail;
        TextView duration;
        TextView videoCategory;
        TextView videoSubCategory;
        TextView categoryFullName;
        TextView subCategoryFullName;
        TextView videoYouTubeURL;
        public DataObjectHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.home_thumbnail);
            duration = (TextView) itemView.findViewById(R.id.home_duration);
            videoID = (TextView) itemView.findViewById(R.id.home_videoID);
            videoTitle = (TextView) itemView.findViewById(R.id.home_videoTitle);
            thumbnailURL = (TextView) itemView.findViewById(R.id.home_thumbnailURL);
            videoURL = (TextView) itemView.findViewById(R.id.home_videoURL);
            videoCategory = (TextView) itemView.findViewById(R.id.home_videoCategory);
            dateOfUploaded = (TextView) itemView.findViewById(R.id.home_dateOfUploaded);
            videoDescription = (TextView) itemView.findViewById(R.id.home_videoDescription);
            videoSubCategory = (TextView) itemView.findViewById(R.id.home_videoSubCategory);
            categoryFullName = (TextView) itemView.findViewById(R.id.home_categoryFullName);
            subCategoryFullName = (TextView) itemView.findViewById(R.id.home_subCategoryFullName);
            videoYouTubeURL = (TextView) itemView.findViewById(R.id.home_videoYouTubeURL);
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

    public VerbalHomeVideoViewAdapter(List<VideoList> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        Log.d(TAG,"in onCreateViewHolder..");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.verbal_home_videoview_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Log.d(TAG,"in onBindViewHolder..");
        holder.videoID.setText(mDataset.get(position).getVideoID());
        holder.duration.setText("Duration: "+mDataset.get(position).getDuration()+"m");
        holder.videoDescription.setText(mDataset.get(position).getVideoDescription());
        holder.videoCategory.setText(mDataset.get(position).getVideoCategory());
        holder.videoURL.setText(mDataset.get(position).getVideoURL());
        holder.videoSubCategory.setText(mDataset.get(position).getVideoSubCategory());
        holder.categoryFullName.setText(mDataset.get(position).getCategoryFullName());
        holder.subCategoryFullName.setText(mDataset.get(position).getSubCategoryFullName());
        holder.subCategoryFullName.setText(mDataset.get(position).getSubCategoryFullName());
        holder.dateOfUploaded.setText(mDataset.get(position).getUploadDate());
        holder.videoTitle.setText(mDataset.get(position).getVideoTitle());
        holder.videoYouTubeURL.setText(mDataset.get(position).getVideoYouTubeURL());
        holder.thumbnailURL.setText(mDataset.get(position).getThumbnailURL());
        Context context= holder.thumbnail.getContext();
        //int id=context.getResources().getIdentifier(mDataset.get(position).getThumbnailURL(), "drawable", context.getPackageName());
        int id=context.getResources().getIdentifier(mDataset.get(position).getThumbnailURL(), "drawable", context.getPackageName());
        holder.thumbnail.setImageResource(id);

    }

    public void addItem(VideoList dataObj, int index) {
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
