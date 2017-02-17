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

import com.crackingMBA.training.R;
import com.crackingMBA.training.pojo.VideoDataObject;

/**
 * Created by Harish on 1/31/2017.
 */
public class DownloadViewAdapter extends RecyclerView
        .Adapter<DownloadViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "VideoViewAdapter";
    private ArrayList<VideoDataObject> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView title;
        ImageView thumbnailUrl;
        TextView duration;
        TextView id;
        TextView videoTitle;
        TextView thumbnailURL;
        TextView videoURL;
        TextView videoType;
        TextView dateOfUploaded;
        TextView videoDescription;


        TextView videoCategory;
        public DataObjectHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.download_title);
            thumbnailUrl = (ImageView) itemView.findViewById(R.id.download_thumbnail);
            duration = (TextView) itemView.findViewById(R.id.download_duration);

            id = (TextView) itemView.findViewById(R.id.download_id);
          //  videoTitle = (TextView) itemView.findViewById(R.id.download_videoTitle);
            thumbnailURL = (TextView) itemView.findViewById(R.id.download_thumbnailURL);
            videoURL = (TextView) itemView.findViewById(R.id.download_videoURL);
            videoType = (TextView) itemView.findViewById(R.id.download_videoType);
            dateOfUploaded = (TextView) itemView.findViewById(R.id.download_dateOfUploaded);
            videoDescription = (TextView) itemView.findViewById(R.id.download_videoDescription);
            videoCategory = (TextView) itemView.findViewById(R.id.download_videoCategory);
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

    public DownloadViewAdapter(ArrayList<VideoDataObject> myDataset) {
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
        //holder.title.setText(mDataset.get(position).getVideoTitle());
        //holder.thumbnailUrl.setImageURI(Uri.parse(mDataset.get(position).getThumbnailUrl()));
        /*Log.d("DownloadviewAdapater","in onBindViewHolder..");
        Log.d("DownloadviewAdapater",mDataset.get(position).getThumbnailURL());*/
        File imgFile = new  File(mDataset.get(position).getThumbnailURL());
        Bitmap myBitmap = BitmapFactory.decodeFile(mDataset.get(position).getThumbnailURL());

        try {
            Log.d("DownloadviewAdapater", mDataset.get(position).getThumbnailURL());
            AsyncTask result = new DownloadImageTask((ImageView) holder.thumbnailUrl)
                    .execute(mDataset.get(position).getThumbnailURL());
        }
        catch (Exception e){
        }
       // holder.thumbnailUrl.setImageBitmap(myBitmap);
        holder.title.setText(mDataset.get(position).getVideoTitle());
        holder.duration.setText("Duration : "+mDataset.get(position).getDuration()+"m");


        holder.videoDescription.setText(mDataset.get(position).getVideoDescription());
        holder.videoCategory.setText(mDataset.get(position).getVideoType());
        holder.videoURL.setText(mDataset.get(position).getVideoURL());
        // holder.videoType.setText("Latest Quant Prep Videos");
       holder.thumbnailURL.setText( mDataset.get(position).getThumbnailURL());
        holder.dateOfUploaded.setText( mDataset.get(position).getDateOdUploaded());
        holder.id.setText( mDataset.get(position).getId());
        holder.videoType.setText( mDataset.get(position).getVideoType());
    }

    public void addItem(VideoDataObject dataObj, int index) {
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
