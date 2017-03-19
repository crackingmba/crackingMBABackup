package com.crackingMBA.training;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.crackingMBA.training.adapter.DownloadImageTask;
import com.crackingMBA.training.db.DBHelper;
import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;
import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.validator.LocalVideoCheck;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class TargetVideoActivity extends AppCompatActivity {

    private static String TAG = "TargetVideo Activity";
    Button viewOnlineBtn;
    Button viewOfflineBtn;
    Button deleteOfflineBtn;
    private DBHelper dbHelper;
    DownloadManager downloadManager;
    long downloadId;
    VideoList videoList;
    ImageView imageView;

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
        Log.d(TAG, "Selected Video Details" + videoList);
        Log.d(TAG, "video url " + videoList.getVideoURL());
        // boolean localavailablity = LocalVideoCheck.verifyLocalStorage(videoList.getVideoURL().toString());
        boolean localavailablity = LocalVideoCheck.verifyLocalStorageByVideoID(videoList.getVideoID().toString(),this);
        // Log.d(TAG, "localavailablity in Target Video" + videoList.getVideoURL().toString());
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
                if(videoList.isDownloading()){
                    viewOfflineBtn.setText("Downloading..");
                    viewOfflineBtn.setEnabled(false);
                    deleteOfflineBtn.setText("Delete");
                    deleteOfflineBtn.setEnabled(false);
                } else if (viewOfflineBtn.getText().toString().equalsIgnoreCase("View Offline")) {


                    viewOffline();


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
        if(videoList.isDownloading()){
            viewOfflineBtn.setText("Downloading..");
            viewOfflineBtn.setEnabled(false);
            deleteOfflineBtn.setText("Delete");
            deleteOfflineBtn.setEnabled(false);
        }else {
            deleteOfflineBtn.setText("Delete");
            viewOfflineBtn.setText("View Offline");
            deleteOfflineBtn.setEnabled(true);
            viewOfflineBtn.setEnabled(true);
        }
    } else {
        viewOfflineBtn.setText("Download");
        deleteOfflineBtn.setEnabled(false);
        viewOfflineBtn.setEnabled(true);
    }

    ((TextView) findViewById(R.id.target_description)).setText(videoList.getVideoDescription());
    ((TextView) findViewById(R.id.target_videotitle)).setText(videoList.getVideoTitle());
    ((TextView) findViewById(R.id.target_videoCategory)).setText(videoList.getVideoCategory());
    ((TextView) findViewById(R.id.target_subCategory)).setText(videoList.getVideoSubCategory());
    ((TextView) findViewById(R.id.target_categoryFullName)).setText(videoList.getCategoryFullName());
    ((TextView) findViewById(R.id.target_subCategoryFullName)).setText(videoList.getSubCategoryFullName());
    ((TextView) findViewById(R.id.target_thumbnailURL)).setText(videoList.getVideoYouTubeURL());
    ((TextView) findViewById(R.id.target_videoYouTubeURL)).setText(videoList.getThumbnailURL());
    ((TextView) findViewById(R.id.target_videoDownloadURL)).setText(videoList.getVideoDownloadURL());

    imageView = (ImageView)findViewById(R.id.target_detailthumbnail);
    imageView.setImageResource(R.drawable.mocktest);

    // Create an object for subclass of AsyncTask
        String URL =
                "http://theopentutorials.com/totwp331/wp-content/uploads/totlogo.png";
        GetXMLTask task = new GetXMLTask();
        // Execute the task
        task.execute(new String[] { URL });




    try {
        Log.d("suresh", CrackingConstant.MYPATH + "img/" + videoList.getThumbnailURL());
        AsyncTask result = new DownloadImageTask((ImageView) findViewById(R.id.target_detailthumbnail))
                .execute( videoList.getThumbnailURL());

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
            videoList.setDownloading(false);
            VideoApplication.downloadingVideoIds.remove(videoList.getVideoID());
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }


    };
    this.registerReceiver(downloadReceiver, filter);

    //download code ends here

}

    //Creating a new class for downloading the image
    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void viewOnline() {
        String clickedVideo = videoList.getVideoURL();


        Log.d("first", "Playing Video..videoList.getVideoYouTubeURL()" + videoList.getVideoYouTubeURL());


        Intent intent = new Intent(this, YoutubeVideoActivity.class);
        intent.putExtra("clickedVideo", clickedVideo);
        startActivity(intent);

    }

    public void viewOffline() {
        String clickedVideo = videoList.getVideoDownloadURL();
        boolean localavailablity = LocalVideoCheck.verifyLocalStorage(clickedVideo);

        Log.d("first", "Playing Video..videoList.getVideoURL()" + videoList.getVideoDownloadURL());





        Intent intent = new Intent(this, FullscreenActivity.class);
        //  intent.putExtra("localavailblity", localavailablity);
        intent.putExtra("clickedVideo", clickedVideo);
        startActivity(intent);

    }
    public void downloadNow() {

        downloadId = downloadData();
    }

    private long downloadData() {
        VideoList selectedVideo = VideoApplication.videoList;
        String fileName = selectedVideo.getVideoDownloadURL();
        downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(CrackingConstant.MYPATH + "videos/" + videoList.getVideoDownloadURL()));
        request.setTitle("crackingMBA.com");
        request.setDescription("Downloading Video..");
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {


            boolean localavailablity = LocalVideoCheck.verifyLocalStorageByVideoID(videoList.getVideoID(),this);

            if (!localavailablity) {
                Log.d("suresh", "Entered into download video"+selectedVideo);
                String filePath1 = CrackingConstant.localstoragepath + CrackingConstant.myFolder + CrackingConstant.noMedia + fileName;
                File file1 = new File(filePath1);
                file1.delete();
                String filePath = CrackingConstant.localstoragepath + CrackingConstant.myFolder + CrackingConstant.noMedia + fileName;
                File file = new File(filePath);
                Uri destUri = Uri.fromFile(file);
                request.setDestinationUri(destUri);

                selectedVideo.setVideoURL(CrackingConstant.MYPATH + "videos/" + selectedVideo.getVideoURL());
                selectedVideo.setThumbnailURL(CrackingConstant.MYPATH + "img/" + selectedVideo.getThumbnailURL());
                selectedVideo.setVideoURL(fileName);
                Log.d("suresh", "selectedVideo" + selectedVideo);
                dbHelper.addDownloadVideo(selectedVideo);
                viewOfflineBtn.setText("Downloading..");
                viewOfflineBtn.setEnabled(false);
                videoList.setDownloading(true);
                VideoApplication.downloadingVideoIds.add(videoList.getVideoID());
                dbHelper.addDownloadVideo(selectedVideo);
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


        return 12;
    }


    public void deleteVideo() {

        boolean localavailablity = LocalVideoCheck.verifyLocalStorageByVideoID(videoList.getVideoID(),this);
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
