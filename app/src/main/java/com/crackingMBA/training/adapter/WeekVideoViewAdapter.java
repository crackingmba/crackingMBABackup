package com.crackingMBA.training.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import com.crackingMBA.training.CrackingConstant;
import com.crackingMBA.training.FullscreenActivity;
import com.crackingMBA.training.R;
import com.crackingMBA.training.VideoApplication;
import com.crackingMBA.training.WeeksActivity;
import com.crackingMBA.training.YoutubeVideoActivity;
import com.crackingMBA.training.db.DBHelper;
import com.crackingMBA.training.pojo.VideoList;

/**
 * Created by Harish on 1/31/2017.
 */
public class WeekVideoViewAdapter extends RecyclerView
        .Adapter<WeekVideoViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "SectionVideoViewAdapter";
    private static ArrayList<VideoList> mDataset;
    private static MyClickListener myClickListener;

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
        TextView videoSubCategory;
        TextView categoryFullName;
        TextView subCategoryFullName;
        TextView videoCategory;
        TextView videoYouTubeURL;
        LinearLayout linlayout;

        public DataObjectHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.week_thumbnail);
            duration = (TextView) itemView.findViewById(R.id.week_duration);
            videoID = (TextView) itemView.findViewById(R.id.week_videoID);
            videoTitle = (TextView) itemView.findViewById(R.id.week_videoTitle);
            thumbnailURL = (TextView) itemView.findViewById(R.id.week_thumbnailURL);
            videoURL = (TextView) itemView.findViewById(R.id.week_videoURL);
            videoCategory = (TextView) itemView.findViewById(R.id.week_videoCategory);
            dateOfUploaded = (TextView) itemView.findViewById(R.id.week_dateOfUploaded);
            videoSubCategory = (TextView) itemView.findViewById(R.id.week_subCategory);
            videoDescription = (TextView) itemView.findViewById(R.id.week_videoDescription);
            categoryFullName = (TextView) itemView.findViewById(R.id.week_categoryFullName);
            subCategoryFullName = (TextView) itemView.findViewById(R.id.week_subCategoryFullName);
            videoYouTubeURL = (TextView) itemView.findViewById(R.id.week_videoYouTubeURL);
            linlayout=(LinearLayout)itemView.findViewById(R.id.subcategory_img_bg_layout1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //if (v.getId() == R.id.week_viewonline) {
            if (1==2) {
                //this functionality is not required here
            }
        else
        {
            Log.d("Suresh", "Clickd on other");
            myClickListener.onItemClick(getPosition(), v);
        }
    }
}

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public WeekVideoViewAdapter(ArrayList<VideoList> myDataset,Activity activity) {

        mDataset = myDataset;
        Log.d(LOG_TAG, "in WeekVideoViewAdapter.."+mDataset);
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        Log.d(LOG_TAG, "in onCreateViewHolder..");
        String clicked = VideoApplication.videoSelected.getVideoType() == null ? "startup" : VideoApplication.videoSelected.getVideoType();
        Log.d(LOG_TAG, " Clicked is " + clicked);

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.week_videoview_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        Log.d(LOG_TAG, "in onBindViewHolder..mDataset.."+mDataset);
        holder.videoID.setText(mDataset.get(position).getVideoID());
        holder.videoTitle.setText(mDataset.get(position).getVideoTitle());
        holder.thumbnailURL.setText(mDataset.get(position).getThumbnailURL());
        holder.videoURL.setText(mDataset.get(position).getVideoURL());
        holder.videoCategory.setText(mDataset.get(position).getVideoCategory());
        holder.dateOfUploaded.setText(mDataset.get(position).getUploadDate());
        holder.videoDescription.setText(mDataset.get(position).getVideoDescription());
        holder.videoSubCategory.setText(mDataset.get(position).getVideoSubCategory());
        holder.categoryFullName.setText(mDataset.get(position).getCategoryFullName());
        holder.subCategoryFullName.setText(mDataset.get(position).getSubCategoryFullName());
        holder.videoYouTubeURL.setText(mDataset.get(position).getVideoYouTubeURL());
        holder.duration.setText(mDataset.get(position).getDuration());

        String img_resource="";

        switch(mDataset.get(position).getVideoCategory()){
            case "quant":{
                img_resource="quant";
                holder.linlayout.setBackgroundColor(Color.parseColor("#82B1FF"));
                break;
            }
            case "dilr":{
                img_resource="dilr";
                holder.linlayout.setBackgroundColor(Color.parseColor("#FFAB40"));
                break;
            }
            case "verbal":{
                img_resource="verbal";
                holder.linlayout.setBackgroundColor(Color.parseColor("#EF9A9A"));
                break;
            }

        }

        Context context= holder.thumbnail.getContext();
        int id=context.getResources().getIdentifier(img_resource, "drawable", context.getPackageName());
        holder.thumbnail.setImageResource(id);
    }

    public void addItem(VideoList dataObj, int index) {
        Log.d(LOG_TAG, "in addItem..");
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        Log.d(LOG_TAG, "in deleteView..");
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