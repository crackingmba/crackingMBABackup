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
import android.preference.PreferenceManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.HomeVideoViewAdapter;
import com.crackingMBA.training.adapter.MockTestTopicsAdapter;
import com.crackingMBA.training.adapter.SectionVideoViewAdapter;
import com.crackingMBA.training.pojo.MockTestTopic;
import com.crackingMBA.training.pojo.MockTestTopicsModel;
import com.crackingMBA.training.pojo.SubCatList;
import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;
import com.crackingMBA.training.pojo.VideoListModel;
import com.crackingMBA.training.validator.LocalVideoCheck;

/**
 * Created by MSK on 24-01-2017.
 */
public class MockTestFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    LinearLayoutManager mLayoutManager;
    boolean isMock;

    View rootView;
    private static String TAG = "MockTestFragment";
    String url = "mocktest_topics_service_url";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_mocktest_startup, container, false);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        isMock = pref.getBoolean("isMock", false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.mocktest_startup_recycler_view);
        recyclerView.setHasFixedSize(true);

        getDataSet();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getDataSet() {

        Log.d(TAG, "isMock?" + isMock);
        isMock=true;
        if (isMock) {
            List<MockTestTopic> results = populateMockTestTopics();
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MockTestTopicsAdapter(results);
            recyclerView.setAdapter(mAdapter);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);

            if (null != mAdapter) {
                ((MockTestTopicsAdapter) mAdapter).setOnItemClickListener(
                        new MockTestTopicsAdapter.MyClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {
                                Log.d(TAG, "Section adapter, Clicked item at position : " + position);
                                MockTestTopic topic = populateMockTestTopic(v);
                                VideoApplication.selectedMockTestTopic = topic;
                                Intent topicsIntent=new Intent(getActivity().getApplicationContext(),MockTestTestsActivity.class);
                                startActivity(topicsIntent);
                                Log.d(TAG, "set with topic.."+topic);
                            }
                        }
                );
            }
        } else {
            Log.d(TAG, "In else block");
            final ArrayList<MockTestTopic> results = new ArrayList<MockTestTopic>();
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        MockTestTopicsModel model = gson.fromJson(response, MockTestTopicsModel.class);
                        results.addAll(model.getTopics());
                        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

                        recyclerView.setLayoutManager(mLayoutManager);
                        mAdapter = new MockTestTopicsAdapter(results);
                        recyclerView.setAdapter(mAdapter);
                        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
                        recyclerView.addItemDecoration(itemDecoration);

                        if (null != mAdapter) {
                            ((MockTestTopicsAdapter) mAdapter).setOnItemClickListener(
                                    new MockTestTopicsAdapter.MyClickListener() {
                                        @Override
                                        public void onItemClick(int position, View v) {
                                            Log.d(TAG, "Section adapter, Clicked item at position : " + position);
                                            MockTestTopic topic = populateMockTestTopic(v);
                                            Intent topicsIntent=new Intent(getActivity().getApplicationContext(),MockTestTestsActivity.class);
                                            startActivity(topicsIntent);
                                            Log.d(TAG, "set with topic.."+topic);
                                        }
                                    }
                            );
                        }
                        Log.d(TAG, "VDO : " + results);
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
            }
        }

    }

    private ArrayList<MockTestTopic> populateMockTestTopics() {
        ArrayList<MockTestTopic> mockResults = new ArrayList<MockTestTopic>();
        MockTestTopic vo1 = new MockTestTopic("topic1","", "Ratio & Proportion");
        MockTestTopic vo2 = new MockTestTopic("topic2","", "Simple Interest");
        mockResults.add(vo1);
        mockResults.add(vo2);
        Log.d(TAG,"Returning mocked objects:"+mockResults);
        return mockResults;
    }

    private MockTestTopic populateMockTestTopic(View v){
        MockTestTopic mockTestTopic = new MockTestTopic();
        mockTestTopic.setMocktestTopicId(((TextView)v.findViewById(R.id.mocktest_topic_id)).getText().toString());
        mockTestTopic.setMocktestTopicTxt(((TextView)v.findViewById(R.id.mocktest_topic_txt)).getText().toString());
        return mockTestTopic;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "in onCLick.." + view.getId());
    }

}
