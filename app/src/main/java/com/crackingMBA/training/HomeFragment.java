package com.crackingMBA.training;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.DILRHomeVideoViewAdapter;
import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.DownloadViewAdapter;
import com.crackingMBA.training.adapter.QuantHomeVideoViewAdapter;
import com.crackingMBA.training.adapter.VerbalHomeVideoViewAdapter;
import com.crackingMBA.training.db.DBHelper;
import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;
import com.crackingMBA.training.pojo.VideoListModel;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MSK on 24-01-2017.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    RecyclerView recentRecyclerView;
    RecyclerView quantRecyclerView;
    RecyclerView dilrRecyclerView;
    RecyclerView verbalRecyclerView;
    RecyclerView.Adapter recentAdapter;
    RecyclerView.Adapter quantAdapter;
    RecyclerView.Adapter dilrAdapter;
    RecyclerView.Adapter verbalAdapter;
    boolean isMock;
    private DBHelper dbHelper;

    Button gotoquantBtn;
    Button gotodi;
    Button gotolatest;
    Button homeRefreshBtn;
    View rootView;
    private static String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
// Suresh Added comments
        //Vijender comment added
        //added one more comments by Suresh
        //added one new comment by Vijendert
        //third comments addedby suresh

        rootView = inflater.inflate(R.layout.fragment_home1, container, false);
        gotoquantBtn = (Button) rootView.findViewById(R.id.gotoquant3);
        gotoquantBtn.setOnClickListener(this);
        gotodi = (Button) rootView.findViewById(R.id.gotodi3);
        gotodi.setOnClickListener(this);
        gotolatest = (Button) rootView.findViewById(R.id.gotolatest3);
        gotolatest.setOnClickListener(this);
        homeRefreshBtn = (Button) rootView.findViewById(R.id.home_refresh);
        homeRefreshBtn.setOnClickListener(this);
        dbHelper = DBHelper.getInstance(getContext());
        recentRecyclerView = (RecyclerView) rootView.findViewById(R.id.home_recently_recyclerview); //abc
        quantRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_recycler_view);
        dilrRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_recycler_view2);
        verbalRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_recycler_view3);
        recentRecyclerView.setHasFixedSize(true);
        quantRecyclerView.setHasFixedSize(true);
        dilrRecyclerView.setHasFixedSize(true);
        verbalRecyclerView.setHasFixedSize(true);

        getDataSet();

        RecyclerView.ItemDecoration recentItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager recentLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        ArrayList<VideoList> downloadedList = getDataSetDownloadedVideos();
        recentAdapter = new DownloadViewAdapter(downloadedList);
        if (downloadedList.size() == 0) {

            rootView.findViewById(R.id.home_download_noVideos).setVisibility(View.VISIBLE);
        } else {
            VideoApplication.allDownloadedVideos = downloadedList;
            rootView.findViewById(R.id.home_download_noVideos).setVisibility(View.GONE);
        }
        recentRecyclerView.setAdapter(recentAdapter);
        recentRecyclerView.setLayoutManager(recentLayoutManager);
        recentRecyclerView.addItemDecoration(recentItemDecoration);

        ((DownloadViewAdapter) recentAdapter).setOnItemClickListener(
                new DownloadViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Log.d(TAG, "Clicked item at position : " + position);
                        VideoList vdo = VideoApplication.allDownloadedVideos.get(position);//populateDownloadVideoDataObject(v);//new VideoDataObject();
                        vdo.setVideoSubCategory("video");
                        if(VideoApplication.downloadingVideoIds.contains(vdo.getVideoID())){
                            vdo.setDownloading(true);
                        }else{
                            vdo.setDownloading(false);
                        }
                        Log.d(TAG, "set with video.."+vdo);
                        VideoApplication.videoList = vdo;
                        Intent targetIntent = new Intent(getActivity(), TargetVideoActivity.class);
                        startActivity(targetIntent);
                    }
                }
        );

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private VideoList populateDownloadVideoDataObject(View v) {
        Log.d(TAG, "Populating videoDataObject..");
        VideoList vdo = new VideoList();
        TextView duration = (TextView) v.findViewById(R.id.download_duration);
        TextView id = (TextView) v.findViewById(R.id.download_videoID);
        TextView videoTitle = (TextView) v.findViewById(R.id.download_title);
        TextView thumbnailURL = (TextView) v.findViewById(R.id.download_thumbnailURL);
        TextView videoURL = (TextView) v.findViewById(R.id.download_videoURL);
        TextView videoCategory = (TextView) v.findViewById(R.id.download_videoCategory);
        TextView dateOfUploaded = (TextView) v.findViewById(R.id.download_dateOfUploaded);
        TextView videoDescription = (TextView) v.findViewById(R.id.download_videoDescription);
        TextView videoSubCategory = (TextView) v.findViewById(R.id.download_videoSubCategory);
        TextView categoryFullName = (TextView) v.findViewById(R.id.download_categoryFullName);
        TextView subCategoryFullName = (TextView) v.findViewById(R.id.download_subCategoryFullName);
        vdo.setDuration(duration.getText().toString());
        vdo.setVideoID(id.getText().toString());
        vdo.setVideoTitle(videoTitle.getText().toString());
        vdo.setThumbnailURL(thumbnailURL.getText().toString());
        vdo.setVideoURL(videoURL.getText().toString());
        vdo.setVideoSubCategory(videoSubCategory.getText().toString());
        vdo.setUploadDate(dateOfUploaded.getText().toString());
        vdo.setVideoDescription(videoDescription.getText().toString());
        vdo.setVideoCategory(videoCategory.getText().toString());
        vdo.setCategoryFullName(categoryFullName.getText().toString());
        vdo.setSubCategoryFullName(subCategoryFullName.getText().toString());
        return vdo;
    }


    private ArrayList<VideoDataObject> getDataSet() {

        Log.d(TAG, "isMock?" + isMock);
        isMock = false;
        if (isMock) {
            //  return populateMockGetVideoList();
        } else {
            Log.d(TAG, "In else block");
            final ArrayList<VideoDataObject> results = new ArrayList<VideoDataObject>();
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(CrackingConstant.GETVIDEOLIST_SERVICE_URL__QUANT_SERVICE_URL, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        VideoListModel videoListModel = gson.fromJson(response, VideoListModel.class);
                        if (videoListModel != null) {
                            List<VideoList> quant = videoListModel.getVideoList();
                            try {
                                if (null != quant) {
                                    if (quant.size() == 0)
                                        rootView.findViewById(R.id.home_latest_quant_noVideos).setVisibility(View.VISIBLE);
                                    else {
                                        rootView.findViewById(R.id.home_latest_quant_noVideos).setVisibility(View.GONE);

                                        VideoApplication.allQuantVideos = quant;
                                        RecyclerView.ItemDecoration quantItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
                                        LinearLayoutManager quantLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                        quantRecyclerView.setLayoutManager(quantLayoutManager);
                                        quantAdapter = new QuantHomeVideoViewAdapter(quant);
                                        quantRecyclerView.setAdapter(quantAdapter);
                                        quantRecyclerView.addItemDecoration(quantItemDecoration);
                                        ((QuantHomeVideoViewAdapter) quantAdapter).setOnItemClickListener(
                                                new QuantHomeVideoViewAdapter.MyClickListener() {
                                                    @Override
                                                    public void onItemClick(int position, View v) {
                                                        Log.d(TAG, "Clicked item at position : " + position);
                                                        VideoList vdo = VideoApplication.allQuantVideos.get(position);
                                                        vdo.setVideoSubCategory("video");
                                                        Log.d(TAG, "set with video..");
                                                        VideoApplication.videoList = vdo;
                                                        Intent weeksIntent = new Intent(getActivity(), TargetVideoActivity.class);
                                                        startActivity(weeksIntent);
                                                    }
                                                }
                                        );
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

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
                    }
                });
                //dilr section
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(CrackingConstant.HOME_TAB_GETVIDEOLIST__DILR_SERVICE_URL, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        VideoListModel videoListModel = gson.fromJson(response, VideoListModel.class);
                        if (videoListModel != null) {
                            List<VideoList> dr = videoListModel.getVideoList();

                            try {

                                if (null != dr) {
                                   // gotodi.setVisibility(View.GONE);
                                    if (dr.size() == 0)
                                        rootView.findViewById(R.id.home_latest_dilr_noVideos).setVisibility(View.VISIBLE);
                                    else {
                                        rootView.findViewById(R.id.home_latest_dilr_noVideos).setVisibility(View.GONE);

                                        VideoApplication.allDilrVideos = dr;
                                        RecyclerView.ItemDecoration dilrItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
                                        LinearLayoutManager dilrLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                        dilrRecyclerView.setLayoutManager(dilrLayoutManager);
                                        dilrAdapter = new DILRHomeVideoViewAdapter(dr);
                                        dilrRecyclerView.setAdapter(dilrAdapter);
                                        dilrRecyclerView.addItemDecoration(dilrItemDecoration);
                                        ((DILRHomeVideoViewAdapter) dilrAdapter).setOnItemClickListener(
                                                new DILRHomeVideoViewAdapter.MyClickListener() {
                                                    @Override
                                                    public void onItemClick(int position, View v) {
                                                        Log.d(TAG, "Clicked item at position : " + position);
                                                        VideoList vdo = VideoApplication.allDilrVideos.get(position);
                                                        vdo.setVideoSubCategory("video");
                                                        Log.d(TAG, "set with video..");
                                                        VideoApplication.videoList = vdo;
                                                        Intent weeksIntent = new Intent(getActivity(), TargetVideoActivity.class);
                                                        startActivity(weeksIntent);
                                                    }
                                                }
                                        );
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            //verbal section
            try{
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(CrackingConstant.HOME_TAB_GETVIDEOLIST__VERBAL_SERVICE_URL, null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    Log.d(TAG, "Response is : " + response);
                    Gson gson = new Gson();
                    VideoListModel videoListModel = gson.fromJson(response, VideoListModel.class);
                    if (videoListModel != null) {
                        List<VideoList> verbal = videoListModel.getVideoList();

                        try {

                            if (null != verbal) {
                              //  gotolatest.setVisibility(View.GONE);
                                if (verbal.size() == 0)
                                    rootView.findViewById(R.id.home_latest_other_noVideos).setVisibility(View.VISIBLE);
                                else {
                                    rootView.findViewById(R.id.home_latest_other_noVideos).setVisibility(View.GONE);

                                    VideoApplication.allVerbalVideos = verbal;
                                    RecyclerView.ItemDecoration verbalItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
                                    LinearLayoutManager verbalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                    verbalRecyclerView.setLayoutManager(verbalLayoutManager);
                                    verbalAdapter = new VerbalHomeVideoViewAdapter(verbal);
                                    verbalRecyclerView.setAdapter(verbalAdapter);
                                    verbalRecyclerView.addItemDecoration(verbalItemDecoration);
                                    ((VerbalHomeVideoViewAdapter) verbalAdapter).setOnItemClickListener(
                                            new VerbalHomeVideoViewAdapter.MyClickListener() {
                                                @Override
                                                public void onItemClick(int position, View v) {
                                                    Log.d(TAG, "Clicked item at position : " + position);
                                                    VideoList vdo = VideoApplication.allVerbalVideos.get(position);
                                                    vdo.setVideoSubCategory("video");
                                                    Log.d(TAG, "set with video..");
                                                    VideoApplication.videoList = vdo;
                                                    Intent weeksIntent = new Intent(getActivity(), TargetVideoActivity.class);
                                                    startActivity(weeksIntent);
                                                }
                                            }
                                    );
                                }
                            }
                        } catch (Exception e) {

                            Log.e(TAG, "Error occuring " + e.getMessage());
                        }
                    }
                }
            });
}
                    catch(Exception
                             e){

                    }




                    }
        return null;
                }


        public ArrayList<VideoList> getDataSetDownloadedVideos () {

            ArrayList<VideoList> mockResults = new ArrayList<VideoList>();
            VideoList vo = null;

            List<VideoList> videoDataObjects = dbHelper.getAllDownloadedVideos();
            Log.d(TAG, "get Downloaded videos" + videoDataObjects);
            for (VideoList videoDataObject : videoDataObjects) {
                vo = new VideoList();
                vo.setVideoURL(videoDataObject.getVideoURL());
                vo.setThumbnailURL(videoDataObject.getThumbnailURL());
                vo.setDuration(videoDataObject.getDuration());
                vo.setVideoTitle(videoDataObject.getVideoTitle());
                vo.setVideoDescription(videoDataObject.getVideoDescription());
                vo.setUploadDate(videoDataObject.getUploadDate());
                vo.setVideoCategory(videoDataObject.getVideoCategory());
                vo.setVideoID(videoDataObject.getVideoID());
                vo.setVideoSubCategory(videoDataObject.getVideoSubCategory());
                vo.setCategoryFullName(videoDataObject.getCategoryFullName());
                vo.setSubCategoryFullName(videoDataObject.getSubCategoryFullName());
                vo.setDownloading(videoDataObject.isDownloading());
                vo.setVideoDownloadURL(videoDataObject.getVideoDownloadURL());
                vo.setVideoYouTubeURL(videoDataObject.getVideoYouTubeURL());
                mockResults.add(vo);
            }


            return mockResults;
        }

        public ArrayList<VideoDataObject> populateMockGetVideoList () {
            ArrayList<VideoDataObject> mockResults = new ArrayList<VideoDataObject>();
            VideoDataObject vo = null;
            for (int i = 0; i < 10; i++) {
                vo = new VideoDataObject("1", "Ratio & Proportion Day 1", "http://crackingmba.com/video.3gp", "http://crackingmba.com/video.3gp", "video", "02-01-2017", "325", "This is the first day tutorial of Ratio and Proportion");
                mockResults.add(vo);
            }
            return mockResults;
        }

        @Override
        public void onClick (View view){
            Log.d(TAG, "Inonlicklistener" + view.getId());
            TabLayout tabLayout;
            Intent subsIntent;
            switch (view.getId()) {
                case R.id.gotoquant3:
                    Log.d(TAG, "selecting Quant section..");
                    subsIntent = new Intent(getActivity(), VideoSubCategoryActivity.class);
                    VideoApplication.sectionClicked = "quant";
                    subsIntent.putExtra("sectionSelected", "quant");
                    subsIntent.putExtra("headerTitle", "CAT 2017 Preparation Quant Section");
                    startActivity(subsIntent);
                    break;
                case R.id.gotodi3:
                    subsIntent = new Intent(getActivity(), VideoSubCategoryActivity.class);
                    Log.d(TAG, "selecting DI and LR section..");
                    VideoApplication.sectionClicked = "dilr";
                    subsIntent.putExtra("sectionSelected", "dilr");
                    subsIntent.putExtra("headerTitle", "CAT 2017 Preparation DI and LR Section");
                    startActivity(subsIntent);
                    break;
                case R.id.gotolatest3:
                    subsIntent = new Intent(getActivity(), VideoSubCategoryActivity.class);
                    Log.d(TAG, "selecting latest articles tab..");
                    VideoApplication.sectionClicked = "verbal";
                    subsIntent.putExtra("sectionSelected", "verbal");
                    subsIntent.putExtra("headerTitle", "CAT 2017 Preparation Verbal Section");
                    startActivity(subsIntent);
                    break;
                case R.id.home_refresh:
                    Log.d(TAG, "refresh clicked..");
                    final ArrayList<VideoList> downloadedList = getDataSetDownloadedVideos();
                    VideoApplication.allDownloadedVideos = downloadedList;
                    RecyclerView.Adapter downloadAdapter = new DownloadViewAdapter(downloadedList);
                    RecyclerView.ItemDecoration downloadItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
                    LinearLayoutManager downloadLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    recentRecyclerView.setAdapter(downloadAdapter);
                    recentRecyclerView.setLayoutManager(downloadLayoutManager);
                    recentRecyclerView.addItemDecoration(downloadItemDecoration);

                    ((DownloadViewAdapter) downloadAdapter).setOnItemClickListener(
                            new DownloadViewAdapter.MyClickListener() {
                                @Override
                                public void onItemClick(int position, View v) {
                                    Log.d(TAG, "Clicked item at position : " + position);
                                    VideoList vdo = VideoApplication.allDownloadedVideos.get(position);
                                    if(VideoApplication.downloadingVideoIds.contains(vdo.getVideoID())){
                                        vdo.setDownloading(true);
                                    }else{
                                        vdo.setDownloading(false);
                                    }
                                    vdo.setVideoSubCategory("video");
                                    Log.d(TAG, "set with video..");
                                    VideoApplication.videoList = vdo;
                                    Intent targetIntent = new Intent(getActivity(), TargetVideoActivity.class);
                                    startActivity(targetIntent);
                                }
                            }
                    );
                default:
                    Log.d(TAG, "Unknown button clicked..");

                    break;
            }


        }


        @Override
        public void onRequestPermissionsResult ( int requestCode,
        String permissions[], int[] grantResults){
            switch (requestCode) {
                case 1: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.


                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                    }
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request
            }
        }
}
