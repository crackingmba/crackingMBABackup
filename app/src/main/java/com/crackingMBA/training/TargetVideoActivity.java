package com.crackingMBA.training;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import com.crackingMBA.training.adapter.DownloadImageTask;
import com.crackingMBA.training.db.DBHelper;
import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;
import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.validator.LocalVideoCheck;

public class TargetVideoActivity extends AppCompatActivity {

    private static String TAG = "TargetVideo Activity";
    Button viewOnlineBtn;
    Button viewOfflineBtn;
    Button deleteOfflineBtn;
    private DBHelper dbHelper;
    DownloadManager downloadManager;
    long downloadId;
    VideoList videoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_video);
        dbHelper = DBHelper.getInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        videoList = VideoApplication.videoList;
        Log.d(TAG, "video url " + videoList.getVideoURL());
        boolean localavailablity = LocalVideoCheck.verifyLocalStorage(videoList.getVideoURL().toString());
        Log.d(TAG, "localavailablity in Target Video" + videoList.getVideoURL().toString());
        viewOnlineBtn = (Button) findViewById(R.id.target_viewinline);
        viewOnlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean internetAvailblity = MyUtil.checkConnectivity(getApplicationContext());
                Log.d(TAG, "internet connnectivity lost");
                if (internetAvailblity)
                    viewOnline();
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Internet Connectivity Lost. Please connect to internet", Toast.LENGTH_LONG);

                    toast.setGravity(Gravity.BOTTOM, 25, 400);
                    toast.show();
                }
            }
        });
        viewOfflineBtn = (Button) findViewById(R.id.target_downloadnow);
        viewOfflineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewOfflineBtn.getText().toString().equalsIgnoreCase("View Offline")) {


                    viewOnline();


                } else {
                    boolean internetAvailblity = MyUtil.checkConnectivity(getApplicationContext());
                    Log.d(TAG, "internet connnectivity lost");
                    if (internetAvailblity)
                        downloadNow();
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Internet Connectivity Lost. Please connect to internet", Toast.LENGTH_LONG);

                        toast.setGravity(Gravity.BOTTOM, 25, 400);
                        toast.show();
                    }
                }

            }
        });
        deleteOfflineBtn = (Button) findViewById(R.id.target_deletevideo);
        deleteOfflineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteVideo();

            }
        });
        if (localavailablity) {
            deleteOfflineBtn.setText("Delete");
            viewOfflineBtn.setText("View Offline");
            deleteOfflineBtn.setEnabled(true);
            viewOfflineBtn.setEnabled(true);
        } else {
            viewOfflineBtn.setText("Download");
            deleteOfflineBtn.setEnabled(false);
            viewOfflineBtn.setEnabled(true);
        }

        ((TextView) findViewById(R.id.target_description)).setText(videoList.getVideoDescription());
        ((TextView) findViewById(R.id.target_videotitle)).setText(videoList.getVideoTitle());
        ((TextView) findViewById(R.id.target_screentitle)).setText(videoList.getVideoCategory());

        try {
            Log.d("suresh", CrackingConstant.MYPATH + "img/" + videoList.getThumbnailURL());
            AsyncTask result = new DownloadImageTask((ImageView) findViewById(R.id.target_detailthumbnail))
                    .execute(CrackingConstant.MYPATH + "img/" + videoList.getThumbnailURL());
        } catch (Exception e) {
        }

        ((TextView) findViewById(R.id.target_description)).setText(videoList.getVideoDescription());
        final IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        final BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast toast = Toast.makeText(getApplicationContext(), "Video Download Complete", Toast.LENGTH_LONG);
                deleteOfflineBtn.setText("Delete");
                viewOfflineBtn.setText("View Offline");
                deleteOfflineBtn.setEnabled(true);
                viewOfflineBtn.setEnabled(true);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            }


        };
        this.registerReceiver(downloadReceiver, filter);

        //download code ends here

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void viewOnline() {
        String clickedVideo = videoList.getVideoURL();
        // boolean localavailablity = LocalVideoCheck.verifyLocalStorage(clickedVideo);

        Log.d("first", "Playing Video..videoList.getVideoURL()" + videoList.getVideoURL());

        Intent intent = new Intent(this, FullscreenActivity.class);
        //  intent.putExtra("localavailblity", localavailablity);
        intent.putExtra("clickedVideo", clickedVideo);
        startActivity(intent);

/*

        String fileName = "/Ice.mkv";
        String filePath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES) + fileName;
        File file = new File(filePath);
        Log.d("first", file + "");
        Uri fullUri = Uri.parse("http://crackingmba.com/video.mp4");
        surfaceView.setVideoURI(fullUri);
        surfaceView.requestFocus();
        surfaceView.start();
        Log.d("first", fullUri + "");*/
    }


    public void downloadNow() {
        //Uri uri = Uri.parse(CrackingConstant.MYPATH+"video.mp4");


        downloadId = downloadData();
//        downloadBtn.setText("Cancel Download");
    }

    private long downloadData() {
        String fileName = videoList.getVideoURL();
        downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(CrackingConstant.MYPATH + "videos/" + videoList.getVideoURL()));
        request.setTitle("crackingMBA.com");
        request.setDescription("Downloading Video..");
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {


            boolean localavailablity = LocalVideoCheck.verifyLocalStorage(fileName);

            if (!localavailablity) {
                // String fileName = "/video.mp4";
                Log.d("suresh", "Entered into delete video");
                String filePath1 = CrackingConstant.localstoragepath + CrackingConstant.myFolder + CrackingConstant.noMedia + fileName;
                File file1 = new File(filePath1);
                file1.delete();
                String filePath = CrackingConstant.localstoragepath + CrackingConstant.myFolder + CrackingConstant.noMedia + fileName;
                File file = new File(filePath);
                Uri destUri = Uri.fromFile(file);
                request.setDestinationUri(destUri);
                VideoList selectedVideo = VideoApplication.videoList;
                VideoDataObject videoDataObject = new VideoDataObject();
                selectedVideo.setVideoURL(CrackingConstant.MYPATH + "videos/" + selectedVideo.getThumbnailURL());

                selectedVideo.setThumbnailURL(CrackingConstant.MYPATH + "img/" + selectedVideo.getThumbnailURL());
                selectedVideo.setVideoURL(fileName);
                selectedVideo.setVideoID("2");
                Log.d("suresh", "selectedVideo" + selectedVideo);
                dbHelper.addDownloadVideo(selectedVideo);
                viewOfflineBtn.setText("Downloading..");

                return downloadManager.enqueue(request);
            }
        } else {
            Log.d(TAG, "Storage Permission required");
            Toast toast = Toast.makeText(this, "Storage Permission required", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 25, 400);
            toast.show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest
                            .permission.WRITE_EXTERNAL_STORAGE},
                    1);
           /* Intent intent=new Intent(this,WeeksActivity.class);
            startActivity(intent);*/

        }
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d("suresh", "Entered into delete video");
            String filePath1 = CrackingConstant.localstoragepath + CrackingConstant.myFolder + CrackingConstant.noMedia + fileName;
            File file1 = new File(filePath1);
            file1.delete();
            String filePath = CrackingConstant.localstoragepath + CrackingConstant.myFolder + CrackingConstant.noMedia + fileName;
            File file = new File(filePath);
            Uri destUri = Uri.fromFile(file);
            request.setDestinationUri(destUri);
            VideoList selectedVideo = VideoApplication.videoList;
            VideoDataObject videoDataObject = new VideoDataObject();
            selectedVideo.setVideoURL(CrackingConstant.MYPATH + "videos/" + selectedVideo.getThumbnailURL());

            selectedVideo.setThumbnailURL(CrackingConstant.MYPATH + "img/" + selectedVideo.getThumbnailURL());
            selectedVideo.setVideoURL(file.toString());
            viewOfflineBtn.setText("Downloading..");
            viewOfflineBtn.setEnabled(false);
            return downloadManager.enqueue(request);
        }

        return 12;
    }


    public void deleteVideo() {
        //  String fileName = "video.3gp";
        boolean localavailablity = LocalVideoCheck.verifyLocalStorage(videoList.getVideoURL());
        if (localavailablity)

        {

            Log.d("suresh", "Entered into delete video");
            String filePath = CrackingConstant.localstoragepath + CrackingConstant.myFolder + CrackingConstant.noMedia + (videoList.getVideoURL());
            File file = new File(filePath);


            file.delete();
            viewOfflineBtn.setText("Download");
            deleteOfflineBtn.setEnabled(false);
            viewOfflineBtn.setEnabled(true);
            dbHelper.deleteVideoRecord(videoList);

            Log.d("suresh", "Exit into delete video");
            Toast toast = Toast.makeText(this, "Video has been deleted", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
