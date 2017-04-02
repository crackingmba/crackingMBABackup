package com.crackingMBA.training;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.util.MyUtil;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.WeekVideoViewAdapter;
import com.crackingMBA.training.pojo.VideoList;
import com.crackingMBA.training.pojo.VideoListModel;

public class WeeksActivity extends AppCompatActivity {
    private static String TAG = "Weeks Activitiy";
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerView.Adapter weekAdapter;
    boolean isMock;
    String sectionSelected;
    String headerTitle;
    String subcategoryid;
    Activity myWeeksActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeks);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sectionSelected = extras.getString("sectionSelected");
            headerTitle = extras.getString("headerTitle");
            subcategoryid=extras.getString("subcategoryid");
        } else {
            sectionSelected = "ratio";
            headerTitle = "CAT 2017 Preparation Quant Section";
        }
        myWeeksActivity=this;
        Log.d(TAG, "headerTitle" + headerTitle);
        ((TextView) findViewById(R.id.weeksTitleHeader)).setText(headerTitle.toString());
        ((TextView) findViewById(R.id.weeksSubCategoryName)).setText(sectionSelected.toString());
        //((TextView) findViewById(R.id.Weeks_noVideo)).setVisibility(View.INVISIBLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.week_recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        getWeeksData();

}

    private void getWeeksData() {

        String category=VideoApplication.sectionClicked;
        String subcategory=VideoApplication.subcategorySelected;
        final ArrayList<VideoListModel> results = new ArrayList<VideoListModel>();
        String url = "http://crackingmba.com/getVideoList.php?category="+category+"&subcategory=" + subcategoryid;
        Log.d(TAG,"Get Video List for Subcategory url "+ url);


        if(MyUtil.checkConnectivity(getApplicationContext())) {
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                MyUtil.showProgressDialog(this);
                client.get(url, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "selected subcategories Response is : " + response);
                        Gson gson = new Gson();
                        VideoListModel videoListModel = gson.fromJson(response, VideoListModel.class);
                        //  Log.d(TAG,"converted to object of selected subcategories Response is : : "+videoListModel);
                        if (videoListModel != null) {

                            weekAdapter = new WeekVideoViewAdapter(videoListModel.getVideoList(), myWeeksActivity);
                            recyclerView.setAdapter(weekAdapter);
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);
                            recyclerView.addItemDecoration(itemDecoration);

                            if (null != weekAdapter) {
                                ((WeekVideoViewAdapter) weekAdapter).setOnItemClickListener(
                                        new WeekVideoViewAdapter.MyClickListener() {
                                            @Override
                                            public void onItemClick(int position, View v) {
                                                Log.d(TAG, "week adapter, Clicked item at position : " + position);
                                                //VideoDataObject vdo = populateVideoDataObject(v);//new VideoDataObject();
                                                VideoApplication.videoList = populateVideoList(v);
                                                Intent weeksIntent = new Intent(getApplicationContext(), TargetVideoActivity.class);
                                                startActivity(weeksIntent);
                                                Log.d(TAG, "VideoList.." + VideoApplication.videoList);

                                            }
                                        }
                                );
                            }
                        } else {
                            Log.d(TAG, "No videos available in this category  ");
                        }
                        MyUtil.hideProgressDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        Log.d(TAG, "Status is " + statusCode + " and " + content);
                        if (statusCode == 404) {
                            Log.d(TAG, "Requested resource not found");
                        } else if (statusCode == 500) {
                            Log.d(TAG, "Something went wrong at server end");
                        } else {
                            Log.d(TAG, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]");
                        }
                        MyUtil.hideProgressDialog();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), R.string.no_internet, duration);
            toast.show();
            TextView textView=(TextView)findViewById(R.id.networkstatus);
            textView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private VideoList populateVideoList(View v) {
        Log.d(TAG, "Populating videoDataObject in Preparation Fragment..");
        VideoList vdo = new VideoList();
        TextView videourl = (TextView) v.findViewById(R.id.week_videoURL);
        TextView title = (TextView) v.findViewById(R.id.week_videoTitle);
        TextView description = (TextView) v.findViewById(R.id.week_videoDescription);
        TextView thumbnail = (TextView) v.findViewById(R.id.week_thumbnailURL);
        TextView videoID = (TextView) v.findViewById(R.id.week_videoID);
        TextView duration = (TextView) v.findViewById(R.id.week_duration);
        TextView subcategory = (TextView) v.findViewById(R.id.week_subCategory);
        TextView uploadDate = (TextView) v.findViewById(R.id.week_dateOfUploaded);
        TextView videoCategory = (TextView) v.findViewById(R.id.week_videoCategory);
        TextView subCategoryFullName = (TextView) v.findViewById(R.id.week_subCategoryFullName);
        TextView categoryFullName = (TextView) v.findViewById(R.id.week_categoryFullName);
        TextView youtubeURL = (TextView) v.findViewById(R.id.week_videoYouTubeURL);
        TextView downloadURL = (TextView) v.findViewById(R.id.week_videoDownloadURL);
        vdo.setVideoURL(videourl.getText().toString());
        vdo.setVideoTitle(title.getText().toString());
        vdo.setThumbnailURL(thumbnail.getText().toString());
        vdo.setVideoDescription(description.getText().toString());
        vdo.setVideoCategory(headerTitle);
        vdo.setVideoID(videoID.getText().toString());
        vdo.setDuration(duration.getText().toString());
        vdo.setVideoSubCategory(subcategory.getText().toString());
        vdo.setVideoCategory(videoCategory.getText().toString());
        vdo.setCategoryFullName(categoryFullName.getText().toString());
        vdo.setSubCategoryFullName(subCategoryFullName.getText().toString());
        vdo.setUploadDate(uploadDate.getText().toString());
        vdo.setVideoYouTubeURL(youtubeURL.getText().toString());
        vdo.setVideoDownloadURL(downloadURL.getText().toString());
        return vdo;
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
