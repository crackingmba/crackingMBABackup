package com.crackingMBA.training.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
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
import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.validator.LocalVideoCheck;

/**
 * Created by Harish on 1/31/2017.
 */
public class WeekVideoViewAdapter extends RecyclerView
        .Adapter<WeekVideoViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "SectionVideoViewAdapter";
    private static ArrayList<VideoList> mDataset;
    private static MyClickListener myClickListener;
    private static Context myContext;
    private static Activity myActivity;
    private static long downloadId;
    private static DownloadManager downloadManager;
    private static DBHelper dbHelper;
    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        Button viewOnlineBtn;
        Button viewOfflineBtn;
        Button deleteOfflineBtn;
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
        TextView videoDownloadURL;
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
            videoDownloadURL = (TextView) itemView.findViewById(R.id.week_videoDownloadURL);

            viewOnlineBtn = (Button) itemView.findViewById(R.id.week_viewonline);
            viewOnlineBtn.setOnClickListener(this);
            viewOfflineBtn = (Button) itemView.findViewById(R.id.week_downloadnow);
            viewOfflineBtn.setOnClickListener(this);
            deleteOfflineBtn = (Button) itemView.findViewById(R.id.week_deletevideo);
            deleteOfflineBtn.setOnClickListener(this);
            final IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            final BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Toast toast = Toast.makeText(myContext, "Video Download Complete", Toast.LENGTH_LONG);
                    deleteOfflineBtn.setText("Delete");
                    viewOfflineBtn.setText("View Offline");
                    deleteOfflineBtn.setEnabled(true);
                    viewOfflineBtn.setEnabled(true);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                }


            };
            myContext.registerReceiver(downloadReceiver, filter);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.week_viewonline) {
                Log.d("Suresh", "Clickd on View online button " + getPosition());



                Log.d("first", "Playing online.."+mDataset.get(getPosition()));

                Intent intent = new Intent(myContext, YoutubeVideoActivity.class);


                VideoApplication.videoList=mDataset.get(getPosition());
                VideoApplication.videoList.setVideoYouTubeURL(mDataset.get(getPosition()).getVideoYouTubeURL());
                boolean internetAvailblity= MyUtil.checkConnectivity(myContext);

                if(internetAvailblity)

                    myContext.startActivity(intent);

                else{
                    Toast toast = Toast.makeText(myContext, "Internet Connectivity Lost. Please connect to internet", Toast.LENGTH_LONG);

                    toast.setGravity(Gravity.BOTTOM, 25, 400);
                    toast.show();
                }

            } else if (v.getId() == R.id.week_downloadnow) {
                Log.d("Suresh", "Clickd on download");
                if (viewOfflineBtn.getText().toString().equalsIgnoreCase("View Offline")) {
                    boolean localavailablity = LocalVideoCheck.verifyLocalStorage(mDataset.get(getPosition()).getVideoID());

                    Log.d("first", "Playing online..");

                    Intent intent = new Intent(myContext, FullscreenActivity.class);
                    intent.putExtra("clickedVideo", mDataset.get(getPosition()).getVideoURL());
                    myContext.startActivity(intent);
                }
                else{
                    boolean internetAvailblity= MyUtil.checkConnectivity(myContext);
                    Log.d("first","internet connnectivity lost");
                    if(internetAvailblity)
                    downloadNow(getPosition(),mDataset.get(getPosition()).getVideoURL());

                    else{
                        Toast toast = Toast.makeText(myContext, "Internet Connectivity Lost. Please connect to internet", Toast.LENGTH_LONG);

                        toast.setGravity(Gravity.BOTTOM, 25, 400);
                        toast.show();
                    }
                }
            }

        else if(v.getId()==R.id.week_deletevideo)

        {
            Log.d("Suresh", "Clickd on remove video");
            deleteVideo(getPosition(),mDataset.get(getPosition()).getVideoURL());
        }

        else

        {
            Log.d("Suresh", "Clickd on other");
            myClickListener.onItemClick(getPosition(), v);
        }


    }

    public void downloadNow(int position,String destURL) {
        //Uri uri = Uri.parse(CrackingConstant.MYPATH+"video.mp4");

        Uri uri = Uri.parse(CrackingConstant.MYPATH+"videos/"+destURL);
        int permissionCheck = ContextCompat.checkSelfPermission(myContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            downloadId = downloadData(uri, position,destURL);
        } else {
            Log.d("week View Adaptor", "Storage Access Permission is mandatory to download video");
            Toast toast = Toast.makeText(myContext, "Storage Permission is mandatory to download video", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 20, 400);
            toast.show();

        }

//        downloadBtn.setText("Cancel Download");
    }

    private long downloadData(Uri uri, int position,String destURL) {
       // String fileName = "video.3gp";
        dbHelper = DBHelper.getInstance(myContext);
        downloadManager = (DownloadManager) myContext.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("crackingMBA.com");
        request.setDescription("Downloading Video..");
        String filePath = CrackingConstant.localstoragepath + CrackingConstant.myFolder + CrackingConstant.noMedia + destURL;
        File file = new File(filePath);
        Uri destUri = Uri.fromFile(file);
        request.setDestinationUri(destUri);
        viewOfflineBtn.setText("Downloading..");
        viewOfflineBtn.setEnabled(false);
        mDataset.get(position).setThumbnailURL(CrackingConstant.MYPATH + "img/" + mDataset.get(position).getThumbnailURL());

        dbHelper.addDownloadVideo(mDataset.get(position));
        return downloadManager.enqueue(request);

    }


    public void deleteVideo(int position,String videourl) {
        // String fileName = "video.3gp";
       // boolean localavailablity = LocalVideoCheck.verifyLocalStorage(videourl);
        boolean localavailablity =  LocalVideoCheck.verifyLocalStorageByVideoID(mDataset.get(position).getVideoID().toString(),myActivity);
        if (localavailablity)

        {

            Log.d("suresh", "Entered into delete video");
            String filePath = CrackingConstant.localstoragepath + CrackingConstant.myFolder + CrackingConstant.noMedia + videourl;
            File file = new File(filePath);


            file.delete();
            dbHelper.deleteVideoRecord(mDataset.get(position));
            viewOfflineBtn.setText("Download");
            deleteOfflineBtn.setEnabled(false);
            viewOfflineBtn.setEnabled(true);
            Log.d("suresh", "Exit into delete video");
            Toast toast = Toast.makeText(myContext, "Video has been deleted", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
    }

}

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public WeekVideoViewAdapter(ArrayList<VideoList> myDataset,Activity activity) {

        mDataset = myDataset;
        Log.d(LOG_TAG, "in WeekVideoViewAdapter.."+mDataset);
        myActivity=activity;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        Log.d(LOG_TAG, "in onCreateViewHolder..");
        String clicked = VideoApplication.videoSelected.getVideoType() == null ? "startup" : VideoApplication.videoSelected.getVideoType();
        Log.d(LOG_TAG, " Clicked is " + clicked);
        myContext = parent.getContext();

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
        holder.videoDownloadURL.setText(mDataset.get(position).getVideoDownloadURL());
        holder.duration.setText(mDataset.get(position).getDuration());
        Boolean videoAvailbllity;

         videoAvailbllity = LocalVideoCheck.verifyLocalStorageByVideoID(mDataset.get(position).getVideoID(),myActivity);
        if (videoAvailbllity) {
            if(mDataset.get(position).isDownloading()){
                holder.viewOfflineBtn.setText("Downloading..");
                holder.viewOfflineBtn.setEnabled(false);
                holder.deleteOfflineBtn.setText("Delete");
                holder.deleteOfflineBtn.setEnabled(false);
            }else {
                holder.deleteOfflineBtn.setText("Delete");
                holder.viewOfflineBtn.setText("View Offline");
                holder.deleteOfflineBtn.setEnabled(true);
                holder.viewOfflineBtn.setEnabled(true);
            }
        } else {
            holder.viewOfflineBtn.setText("Download");
            holder.deleteOfflineBtn.setEnabled(false);
            holder.viewOfflineBtn.setEnabled(true);
        }

        try {
            Log.d("suresh", CrackingConstant.MYPATH + "img/" + mDataset.get(position).getThumbnailURL());
            AsyncTask result = new DownloadImageTask((ImageView) holder.thumbnail)
                    .execute(CrackingConstant.MYPATH + "img/" + mDataset.get(position).getThumbnailURL());
        } catch (Exception e) {
        }
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
