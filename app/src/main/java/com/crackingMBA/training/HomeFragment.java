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
    RecyclerView quantRecyclerView;
    RecyclerView dilrRecyclerView;
    RecyclerView verbalRecyclerView;
    RecyclerView.Adapter quantAdapter;
    RecyclerView.Adapter dilrAdapter;
    RecyclerView.Adapter verbalAdapter;
    boolean isMock;

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
        quantRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_recycler_view);
        dilrRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_recycler_view2);
        verbalRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_recycler_view3);
        quantRecyclerView.setHasFixedSize(true);
        dilrRecyclerView.setHasFixedSize(true);
        verbalRecyclerView.setHasFixedSize(true);

        getDataSet();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
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
                        // contacts-related task you need to do


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
