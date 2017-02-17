package com.crackingMBA.training.adapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.crackingMBA.training.CrackingConstant;
import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;

/**
 * Created by Harish on 1/31/2017.
 */
public class HomeVideoViewAdapter extends RecyclerView
        .Adapter<HomeVideoViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "HomeVideoViewAdapter";
    private List<VideoList> mDataset;
    private static MyClickListener myClickListener;
    private static String TAG = "HomeVideoViewAdapter";

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView id;
        TextView videoTitle;
        TextView thumbnailURL;
        TextView videoURL;
        TextView videoType;
        TextView dateOfUploaded;
        TextView videoDescription;
        ImageView thumbnail;
        TextView duration;
        TextView videoCategory;
        public DataObjectHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.home_thumbnail);
            duration = (TextView) itemView.findViewById(R.id.home_duration);
            id = (TextView) itemView.findViewById(R.id.home_id);
            videoTitle = (TextView) itemView.findViewById(R.id.home_videoTitle);
            thumbnailURL = (TextView) itemView.findViewById(R.id.home_thumbnailURL);
            videoURL = (TextView) itemView.findViewById(R.id.home_videoURL);
            videoType = (TextView) itemView.findViewById(R.id.home_videoType);
            dateOfUploaded = (TextView) itemView.findViewById(R.id.home_dateOfUploaded);
            videoDescription = (TextView) itemView.findViewById(R.id.home_videoDescription);
            videoCategory = (TextView) itemView.findViewById(R.id.home_videoCategory);
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

    public HomeVideoViewAdapter(List<VideoList> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        Log.d(TAG,"in onCreateViewHolder..");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_videoview_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        //holder.title.setText(mDataset.get(position).getVideoTitle());
        //holder.thumbnailUrl.setImageURI(Uri.parse(mDataset.get(position).getThumbnailUrl()));
        Log.d(TAG,"in onBindViewHolder..");
       // holder.thumbnail.setImageResource(R.drawable.img1);
        holder.id.setText(mDataset.get(position).getVideoID());
        holder.duration.setText("Duration: "+mDataset.get(position).getDuration()+"m");
        holder.videoDescription.setText(mDataset.get(position).getVideoDescription());
        holder.videoCategory.setText(mDataset.get(position).getVideoCategory());
        holder.videoURL.setText(mDataset.get(position).getVideoURL());
       // holder.videoType.setText("Latest Quant Prep Videos");
        String thumbnailURL= mDataset.get(position).getThumbnailURL();
        Bitmap mIcon11 = null;
        // holder.thumbnail.setImageResource(R.drawable.img1);
        try {
            Log.d("suresh", CrackingConstant.MYPATH + thumbnailURL);
            AsyncTask result = new DownloadImageTask((ImageView) holder.thumbnail)
                    .execute(CrackingConstant.MYPATH +"img/"+thumbnailURL);
        }
        catch (Exception e){
        }
        holder.videoTitle.setText(mDataset.get(position).getVideoTitle());

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
