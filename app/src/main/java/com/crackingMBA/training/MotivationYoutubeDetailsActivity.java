package com.crackingMBA.training;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.db.DBHelper;
import com.crackingMBA.training.pojo.VideoList;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MotivationYoutubeDetailsActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener{
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;

    private static String TAG = "TargetVideo Activity";
    private DBHelper dbHelper;
    DownloadManager downloadManager;
    VideoList videoList;
    String motivation_video_url;
    TextView videoName, videoDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation_youtube_details);
        dbHelper = DBHelper.getInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        videoName=(TextView)findViewById(R.id.motivation_youtube_video_name);
        videoDescription=(TextView)findViewById(R.id.motivation_youtube_video_description);

        motivation_video_url = getIntent().getStringExtra("MOTIVATION_VIDEO_URL");

        videoName.setText(getIntent().getStringExtra("MOTIVATION_VIDEO_NAME"));
        videoDescription.setText(getIntent().getStringExtra("MOTIVATION_VIDEO_DESCRIPTION"));

        //youTubeView=(YouTubePlayerView)findViewById(R.id.youtube_view);
        //youTubeView.initialize(MyConfig.YOUTUBE_API_KEY, this);
        int orientation=this.getResources().getConfiguration().orientation;
        YouTubePlayerSupportFragment frag;
       /* if(orientation==Configuration.ORIENTATION_PORTRAIT){
            //code for portrait mode
*/
        frag =
                (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_motivation_video_fragment);

      /*  }
        else{
            //code for landscape
            frag =
                    (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);

        }*/
        frag.initialize(MyConfig.YOUTUBE_API_KEY, this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //videoList = VideoApplication.videoList;
        //Log.d(TAG, "Selected Video Details" + videoList);
        //Log.d(TAG, "video url " + videoList.getVideoURL());

        /*((TextView) findViewById(R.id.target_description)).setText(videoList.getVideoDescription());
        ((TextView) findViewById(R.id.target_videotitle)).setText(videoList.getVideoTitle());
        ((TextView) findViewById(R.id.target_videoCategory)).setText(videoList.getVideoCategory());
        ((TextView) findViewById(R.id.target_subCategory)).setText(videoList.getVideoSubCategory());
        ((TextView) findViewById(R.id.target_categoryFullName)).setText(videoList.getCategoryFullName());
        ((TextView) findViewById(R.id.target_subCategoryFullName)).setText(videoList.getSubCategoryFullName());
        ((TextView) findViewById(R.id.target_thumbnailURL)).setText(videoList.getVideoYouTubeURL());
        ((TextView) findViewById(R.id.target_videoYouTubeURL)).setText(videoList.getThumbnailURL());
        ((TextView) findViewById(R.id.target_description)).setText(videoList.getVideoDescription());*/
    } //the OnCreate code completes here

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            //player.cueVideo("fg9EqZGv9_s"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
            //player.cueVideo(videoList.getVideoYouTubeURL()); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
            player.cueVideo(motivation_video_url); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
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
            //imageView.setImageBitmap(result);
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

  /*  @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Intent intent=getIntent();
        finish();
        startActivity(intent);
     *//*   setContentView(R.layout.activity_target_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //youTubeView=(YouTubePlayerView)findViewById(R.id.youtube_view);
        //youTubeView.initialize(MyConfig.YOUTUBE_API_KEY, this);
        int orientation=this.getResources().getConfiguration().orientation;
        YouTubePlayerSupportFragment frag;
        if(orientation==Configuration.ORIENTATION_PORTRAIT){
            //code for portrait mode

            frag =
                    (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);

        }
        else{
            //code for landscape
            frag =
                    (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment_land);

        }
        frag.initialize(MyConfig.YOUTUBE_API_KEY, this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        videoList = VideoApplication.videoList;
        Log.d(TAG, "Selected Video Details" + videoList);
        Log.d(TAG, "video url " + videoList.getVideoURL());

        ((TextView) findViewById(R.id.target_description)).setText(videoList.getVideoDescription());
        ((TextView) findViewById(R.id.target_videotitle)).setText(videoList.getVideoTitle());
        ((TextView) findViewById(R.id.target_videoCategory)).setText(videoList.getVideoCategory());
        ((TextView) findViewById(R.id.target_subCategory)).setText(videoList.getVideoSubCategory());
        ((TextView) findViewById(R.id.target_categoryFullName)).setText(videoList.getCategoryFullName());
        ((TextView) findViewById(R.id.target_subCategoryFullName)).setText(videoList.getSubCategoryFullName());
        ((TextView) findViewById(R.id.target_thumbnailURL)).setText(videoList.getVideoYouTubeURL());
        ((TextView) findViewById(R.id.target_videoYouTubeURL)).setText(videoList.getThumbnailURL());
        ((TextView) findViewById(R.id.target_videoDownloadURL)).setText(videoList.getVideoDownloadURL());


        ((TextView) findViewById(R.id.target_description)).setText(videoList.getVideoDescription());
*//*



    }*/
}
