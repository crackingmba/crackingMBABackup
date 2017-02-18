package com.crackingMBA.training.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import com.crackingMBA.training.CrackingConstant;
import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;

/**
 * Created by Harish on 1/31/2017.
 */
public class DownloadViewAdapter extends RecyclerView
        .Adapter<DownloadViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "VideoViewAdapter";
    private ArrayList<VideoList> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        ImageView thumbnailUrl;
        TextView videoID;
        TextView videoTitle;
        TextView thumbnailURL;
        TextView videoURL;
        TextView dateOfUploaded;
        TextView videoDescription;
        ImageView thumbnail;
        TextView duration;
        TextView videoSubCategory;
        TextView categoryFullName;
        TextView subCategoryFullName;
        TextView videoCategory;
        public DataObjectHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.download_thumbnail);
            duration = (TextView) itemView.findViewById(R.id.download_duration);
            videoID = (TextView) itemView.findViewById(R.id.download_videoID);
            videoTitle = (TextView) itemView.findViewById(R.id.download_title);
            thumbnailURL = (TextView) itemView.findViewById(R.id.download_thumbnailURL);
            videoURL = (TextView) itemView.findViewById(R.id.download_videoURL);
            videoCategory = (TextView) itemView.findViewById(R.id.download_videoCategory);
            dateOfUploaded = (TextView) itemView.findViewById(R.id.download_dateOfUploaded);
            videoDescription = (TextView) itemView.findViewById(R.id.download_videoDescription);
            videoSubCategory = (TextView) itemView.findViewById(R.id.download_videoSubCategory);
            categoryFullName = (TextView) itemView.findViewById(R.id.download_categoryFullName);
            subCategoryFullName = (TextView) itemView.findViewById(R.id.download_subCategoryFullName);
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

    public DownloadViewAdapter(ArrayList<VideoList> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        Log.d("VideoViewAdapter","in onCreateViewHolder..");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.download_videoview_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.thumbnailURL.setText(mDataset.get(position).getThumbnailURL());
        Bitmap myBitmap = BitmapFactory.decodeFile(mDataset.get(position).getThumbnailURL());
        try {
            Log.d("DownloadviewAdapater", mDataset.get(position).getThumbnailURL());
            AsyncTask result = new DownloadImageTask((ImageView) holder.thumbnail)
                    .execute(/*CrackingConstant.MYPATH+"img/"+*/mDataset.get(position).getThumbnailURL());
        }
        catch (Exception e){
        }
       // holder.thumbnailUrl.setImageBitmap(myBitmap);
        holder.videoID.setText(mDataset.get(position).getVideoID());
        holder.duration.setText(mDataset.get(position).getDuration()+"m");
        holder.videoDescription.setText(mDataset.get(position).getVideoDescription());
        holder.videoCategory.setText(mDataset.get(position).getVideoCategory());
        holder.videoURL.setText(mDataset.get(position).getVideoURL());
        holder.videoSubCategory.setText(mDataset.get(position).getVideoSubCategory());
        holder.categoryFullName.setText(mDataset.get(position).getCategoryFullName());
        holder.subCategoryFullName.setText(mDataset.get(position).getSubCategoryFullName());
    //    holder.subCategoryFullName.setText(mDataset.get(position).getSubCategoryFullName());
        holder.dateOfUploaded.setText(mDataset.get(position).getUploadDate());
        holder.videoTitle.setText(mDataset.get(position).getVideoTitle());

    }

    public void addItem(VideoList dataObj, int index) {
        Log.d("VideoViewAdapter","in addItem..");
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        Log.d("VideoViewAdapter","in deleteView..");
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
