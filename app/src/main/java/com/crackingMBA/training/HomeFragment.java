package com.crackingMBA.training;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.DownloadViewAdapter;
import com.crackingMBA.training.adapter.HomeVideoViewAdapter;
import com.crackingMBA.training.db.DBHelper;
import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;
import com.crackingMBA.training.pojo.VideoListModel;
import com.crackingMBA.training.validator.LocalVideoCheck;
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

    Button btn;
    Button viewonlineButton;
    Button FullVideoBtn;
    Button downloadBtn;
    Button deleteBtn;
    String urlTxt;
    EditText edit;
    MediaController controller;
    //VideoView surfaceView;

    ImageView mImg;
    DownloadManager downloadManager;
    String fullPath = "http://www.crackingmba.com/video.mp4";
    long downloadId;
    RecyclerView recyclerView;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    RecyclerView.Adapter mAdapter;
    RecyclerView.Adapter downloadAdapter;
    LinearLayoutManager mLayoutManager;
    LinearLayoutManager mLayoutManager1;
    boolean isMock;
    private DBHelper dbHelper;

    Button gotoquantBtn;
    Button gotodi;
    Button gotolatest;
    View rootView;
    private static String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home1, container, false);
        gotoquantBtn = (Button) rootView.findViewById(R.id.gotoquant3);
        gotoquantBtn.setOnClickListener(this);
        gotodi = (Button) rootView.findViewById(R.id.gotodi3);
        gotodi.setOnClickListener(this);
        gotolatest = (Button) rootView.findViewById(R.id.gotolatest3);
        gotolatest.setOnClickListener(this);
        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        isMock = pref.getBoolean("isMock", false);
        dbHelper = DBHelper.getInstance(getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.video_recycler_view);
        recyclerView1 = (RecyclerView) rootView.findViewById(R.id.home_recently_recyclerview);
        recyclerView2 = (RecyclerView) rootView.findViewById(R.id.video_recycler_view2);
        recyclerView3 = (RecyclerView) rootView.findViewById(R.id.video_recycler_view3);
        recyclerView.setHasFixedSize(true);
        getDataSet();
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
        recyclerView1.setHasFixedSize(true);
        mLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(mLayoutManager1);
        ArrayList<VideoList> downloadedList = getDataSetDownloadedVideos();
        DownloadViewAdapter downloadAdapter = new DownloadViewAdapter(downloadedList);
        if (downloadedList.size() == 0) {

            rootView.findViewById(R.id.home_download_noVideos).setVisibility(View.VISIBLE);
        } else {
            rootView.findViewById(R.id.home_download_noVideos).setVisibility(View.GONE);
        }
        recyclerView1.setAdapter(downloadAdapter);
        recyclerView1.addItemDecoration(itemDecoration);

        ((DownloadViewAdapter) downloadAdapter).setOnItemClickListener(
                new DownloadViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Log.d(TAG, "Clicked item at position : " + position);
                        VideoList vdo = populateDownloadVideoDataObject(v);//new VideoDataObject();
                        vdo.setVideoSubCategory("video");
                        Log.d(TAG, "set with video..");
                        VideoApplication.videoList = vdo;
                        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
                        Log.d(TAG, "selecting tab 1");
                        //tabLayout.getTabAt(1).select();
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

    private VideoList populateVideoDataObject(View v) {
        Log.d(TAG, "Populating videoDataObject..");
        VideoList vdo = new VideoList();
        TextView duration = (TextView) v.findViewById(R.id.home_duration);
        TextView videoID = (TextView) v.findViewById(R.id.home_videoID);
        TextView videoTitle = (TextView) v.findViewById(R.id.home_videoTitle);
        TextView thumbnailURL = (TextView) v.findViewById(R.id.home_thumbnailURL);
        TextView videoURL = (TextView) v.findViewById(R.id.home_videoURL);
        TextView dateOfUploaded = (TextView) v.findViewById(R.id.home_dateOfUploaded);
        TextView videoDescription = (TextView) v.findViewById(R.id.home_videoDescription);
        TextView videoCategory = (TextView) v.findViewById(R.id.home_videoCategory);
        TextView videoSubCategory = (TextView) v.findViewById(R.id.home_videoSubCategory);
        TextView categoryFullName = (TextView) v.findViewById(R.id.home_categoryFullName);
        TextView subCategoryFullName = (TextView) v.findViewById(R.id.home_subCategoryFullName);

        vdo.setDuration(duration.getText().toString());
        vdo.setVideoID(videoID.getText().toString());
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
            return populateMockGetVideoList();
        } else {
            Log.d(TAG, "In else block");
            final ArrayList<VideoDataObject> results = new ArrayList<VideoDataObject>();
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(CrackingConstant.GETVIDEOLIST_SERVICE_URL, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        VideoListModel videoListModel = gson.fromJson(response, VideoListModel.class);
                        List<VideoList> totallist = videoListModel.getVideoList();
                        List<VideoList> quant = new ArrayList<VideoList>();
                        List<VideoList> dr = new ArrayList<VideoList>();
                        List<VideoList> verbal = new ArrayList<VideoList>();
                        for (VideoList video : totallist) {
                            Log.d("Suresh", "video cateogry" + video.getVideoCategory());
                            if (video.getVideoCategory().equalsIgnoreCase(CrackingConstant.QUAT_CODE)) {
                                quant.add(video);
                               /* quant.add(video);
                                quant.add(video);
                                quant.add(video);
                                quant.add(video);*/
                            } else if (video.getVideoCategory().equalsIgnoreCase(CrackingConstant.DILR_CODE)) {
                                dr.add(video);
                            } else {
                                verbal.add(video);
                              /*  verbal.add(video);
                                verbal.add(video);*/


                            }
                        }
                        try {
                            if (quant.size() <= 1) {
                                gotoquantBtn.setVisibility(View.GONE);
                                if (quant.size() == 0)
                                    ((TextView) getActivity().findViewById(R.id.home_latest_quant_title)).setVisibility(View.GONE);


                            }
                            if (dr.size() <= 0) {
                                gotodi.setVisibility(View.GONE);
                                if (dr.size() == 0)
                                    ((TextView) getActivity().findViewById(R.id.home_latest_dilr)).setVisibility(View.GONE);
                            }
                            if (verbal.size() <= 0) {
                                gotolatest.setVisibility(View.GONE);
                                if (verbal.size() == 0)
                                    ((TextView) getActivity().findViewById(R.id.home_latest_other)).setVisibility(View.GONE);
                            }
                        } catch (Exception e) {

                            Log.e(TAG, "Error occuring " + e.getMessage());
                        }
                        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

                        recyclerView.setLayoutManager(mLayoutManager);
                        mAdapter = new HomeVideoViewAdapter(quant);
                        recyclerView.setAdapter(mAdapter);
                        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
                        recyclerView.addItemDecoration(itemDecoration);


                        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        recyclerView2.setLayoutManager(mLayoutManager2);
                        mAdapter = new HomeVideoViewAdapter(dr);
                        recyclerView2.setAdapter(mAdapter);
                        recyclerView2.addItemDecoration(itemDecoration);

                        LinearLayoutManager mLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        recyclerView3.setLayoutManager(mLayoutManager3);
                        mAdapter = new HomeVideoViewAdapter(verbal);
                        recyclerView3.setAdapter(mAdapter);
                        recyclerView3.addItemDecoration(itemDecoration);
                        Log.d(TAG, "VDO : " + totallist);
                        //  results.add(vdo);


                        ((HomeVideoViewAdapter) mAdapter).setOnItemClickListener(
                                new HomeVideoViewAdapter.MyClickListener() {
                                    @Override
                                    public void onItemClick(int position, View v) {
                                        Log.d(TAG, "Clicked item at position : " + position);
                                        VideoList vdo = populateVideoDataObject(v);//new VideoDataObject();
                                        vdo.setVideoSubCategory("video");
                                        Log.d(TAG, "set with video..");
                                        VideoApplication.videoList = vdo;
                                        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
                                        Log.d(TAG, "selecting tab 1");
                                        //tabLayout.getTabAt(1).select();
                                        Intent weeksIntent = new Intent(getActivity(), TargetVideoActivity.class);
                                        startActivity(weeksIntent);
                                    }
                                }
                        );


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
            } catch (Exception e) {
                e.printStackTrace();
                ;
            }
            return results;
        }

    }

    public ArrayList<VideoList> getDataSetDownloadedVideos() {

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


            mockResults.add(vo);
        }


        return mockResults;
    }

    public ArrayList<VideoDataObject> populateMockGetVideoList() {
        ArrayList<VideoDataObject> mockResults = new ArrayList<VideoDataObject>();
        VideoDataObject vo = null;
        for (int i = 0; i < 10; i++) {
            vo = new VideoDataObject("1", "Ratio & Proportion Day 1", "http://crackingmba.com/video.3gp", "http://crackingmba.com/video.3gp", "video", "02-01-2017", "325", "This is the first day tutorial of Ratio and Proportion");
            mockResults.add(vo);
        }
        return mockResults;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "Inonlicklistener" + view.getId());
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        VideoDataObject vdo = new VideoDataObject();
        Intent subsIntent = new Intent(getActivity(), VideoSubCategoryActivity.class);
        switch (view.getId()) {
            case R.id.gotoquant3:
                Log.d(TAG, "selecting Quant section..");
                vdo.setVideoType("section");
                VideoApplication.videoSelected = vdo;
                VideoApplication.sectionClicked = "quant";
                Log.d(TAG, "selecting tab 1");
                subsIntent.putExtra("sectionSelected", "quant");
                subsIntent.putExtra("headerTitle", "CAT 2017 Preparation Quant Section");

                break;
            case R.id.gotodi3:
                Log.d(TAG, "selecting DI and LR section..");
                vdo.setVideoType("section");
                VideoApplication.videoSelected = vdo;
                VideoApplication.sectionClicked = "dilr";
                Log.d(TAG, "selecting tab 1");
                subsIntent.putExtra("sectionSelected", "dilr");
                subsIntent.putExtra("headerTitle", "CAT 2017 Preparation DI and LR Section");

                break;
            case R.id.gotolatest3:
                Log.d(TAG, "selecting latest articles tab..");
                vdo.setVideoType("section");
                VideoApplication.videoSelected = vdo;
                VideoApplication.sectionClicked = "verbal";
                Log.d(TAG, "selecting tab 1");
                subsIntent.putExtra("sectionSelected", "verbal");
                subsIntent.putExtra("headerTitle", "CAT 2017 Preparation Verbal Section");
                break;
            default:
                Log.d(TAG, "Unknown button clicked..");
                break;
        }
        startActivity(subsIntent);

    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
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



    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime(1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public void deleteVideo(View view) {
        String fileName = "/video.mp4";
        Log.d("suresh", "Entered into delete video");
        String filePath = CrackingConstant.localstoragepath + fileName;
        File file = new File(filePath);


        file.delete();

        Log.d("suresh", "Exit into delete video");
        Toast toast = Toast.makeText(getActivity(), "Video has been deleted", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 25, 400);
        toast.show();
    }

}
