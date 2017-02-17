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
    private static Activity myActivity=new Activity();
    private static long downloadId;
    private static DownloadManager downloadManager;

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
        Button viewOnlineBtn;
        Button viewOfflineBtn;
        Button deleteOfflineBtn;

        public DataObjectHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.week_thumbnail);
            duration = (TextView) itemView.findViewById(R.id.week_duration);
            id = (TextView) itemView.findViewById(R.id.week_id);
            videoTitle = (TextView) itemView.findViewById(R.id.week_videoTitle);
            thumbnailURL = (TextView) itemView.findViewById(R.id.week_thumbnailURL);
            videoURL = (TextView) itemView.findViewById(R.id.week_videoURL);
            videoType = (TextView) itemView.findViewById(R.id.week_videoType);
            dateOfUploaded = (TextView) itemView.findViewById(R.id.week_dateOfUploaded);
            videoDescription = (TextView) itemView.findViewById(R.id.week_videoDescription);
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
                String clickedVideo = "video.mp4";
                boolean localavailablity = LocalVideoCheck.verifyLocalStorage(clickedVideo);

                Log.d("first", "Playing online..");

                Intent intent = new Intent(myContext, FullscreenActivity.class);
                intent.putExtra("clickedVideo", mDataset.get(getPosition()).getVideoURL());
                boolean internetAvailblity= MyUtil.checkConnectivity(myContext);
                Log.d("first","internet connnectivity lost");
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
                    boolean localavailablity = LocalVideoCheck.verifyLocalStorage(mDataset.get(getPosition()).getVideoURL());

                    Log.d("first", "Playing online..");

                    Intent intent = new Intent(myContext, FullscreenActivity.class);
                    intent.putExtra("clickedVideo", mDataset.get(getPosition()).getVideoURL());
                    myContext.startActivity(intent);
                }
                else{
                    boolean internetAvailblity= MyUtil.checkConnectivity(myContext);
                    Log.d("first","internet connnectivity lost");
                    if(internetAvailblity)
                    downloadNow(getPosition());

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
            deleteVideo(mDataset.get(getPosition()).getVideoURL());
        }

        else

        {
            Log.d("Suresh", "Clickd on other");
            myClickListener.onItemClick(getPosition(), v);
        }


    }

    public void downloadNow(int position) {
        //Uri uri = Uri.parse(CrackingConstant.MYPATH+"video.mp4");

        Uri uri = Uri.parse("http://3gp.telugump4.org/med/Chikki_Chikki_Bam_Bam_-_Aadhi_(HD_DTH_Rip).3gp");
        int permissionCheck = ContextCompat.checkSelfPermission(myContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            downloadId = downloadData(uri, position);
        } else {
            Log.d("week View Adaptor", "Storage Access Permission is mandatory to download video");
            Toast toast = Toast.makeText(myContext, "Storage Permission is mandatory to download video", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 20, 400);
            toast.show();

        }

//        downloadBtn.setText("Cancel Download");
    }

    private long downloadData(Uri uri, int position) {
        String fileName = "video.3gp";
        downloadManager = (DownloadManager) myContext.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("crackingMBA.com");
        request.setDescription("Downloading Video..");
        String filePath = CrackingConstant.localstoragepath + CrackingConstant.myFolder + CrackingConstant.noMedia + fileName;
        File file = new File(filePath);
        Uri destUri = Uri.fromFile(file);
        request.setDestinationUri(destUri);
        viewOfflineBtn.setText("Downloading..");
        viewOfflineBtn.setEnabled(false);
        return downloadManager.enqueue(request);

    }


    public void deleteVideo(String videourl) {
        // String fileName = "video.3gp";
        boolean localavailablity = LocalVideoCheck.verifyLocalStorage(videourl);
        if (localavailablity)

        {

            Log.d("suresh", "Entered into delete video");
            String filePath = CrackingConstant.localstoragepath + CrackingConstant.myFolder + CrackingConstant.noMedia + videourl;
            File file = new File(filePath);


            file.delete();
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

    public WeekVideoViewAdapter(ArrayList<VideoList> myDataset) {
        mDataset = myDataset;
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
        //holder.title.setText(mDataset.get(position).getVideoTitle());
        //holder.thumbnailUrl.setImageURI(Uri.parse(mDataset.get(position).getThumbnailUrl()));
        Log.d(LOG_TAG, "in onBindViewHolder..");
        //   holder.thumbnail.setImageResource(R.drawable.img1);


        //holder.duration.setText("Duration : "+mDataset.get(position).getDuration()+"m");
        holder.id.setText(mDataset.get(position).getVideoID());

        holder.videoTitle.setText(mDataset.get(position).getVideoTitle());
        holder.thumbnailURL.setText(mDataset.get(position).getThumbnailURL());
       /* if(position==1)
            holder.videoURL.setText("myvideo.mp4");
        else*/
        holder.videoURL.setText(mDataset.get(position).getVideoURL());
        holder.videoType.setText(mDataset.get(position).getVideoSubCategory());
        holder.dateOfUploaded.setText(mDataset.get(position).getUploadDate());
        holder.videoDescription.setText(mDataset.get(position).getVideoDescription());
        holder.duration.setText(mDataset.get(position).getDuration());
        Boolean videoAvailbllity;
      /*  if(position==1)
             videoAvailbllity = LocalVideoCheck.verifyLocalStorage("myvideo.mp4");
        else*/
         videoAvailbllity = LocalVideoCheck.verifyLocalStorage(mDataset.get(position).getVideoURL());
        if (videoAvailbllity) {
            holder.deleteOfflineBtn.setText("Delete");
            holder.viewOfflineBtn.setText("View Offline");
            holder.deleteOfflineBtn.setEnabled(true);
            holder.viewOfflineBtn.setEnabled(true);
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
