package com.crackingMBA.training;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.MockTestTestsAdapter;
import com.crackingMBA.training.pojo.MockTestTest;
import com.crackingMBA.training.pojo.MockTestTestsModel;
import com.crackingMBA.training.pojo.VideoListModel;

public class MockTestTestsActivity extends AppCompatActivity {
    private static String TAG = "MockTestTestsActivity";
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    boolean isMock;
    private static List<MockTestTest> tests;
    public static Map<String,MockTestTest> mockTestsMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocktest_tests);
        ((TextView) findViewById(R.id.mocktest_topic_title)).setText("CAT2017 Mock Test for "+VideoApplication.selectedMockTestTopic.getMocktestTopicTxt());
        recyclerView=(RecyclerView)findViewById(R.id.mocktest_test_recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        getMockTestTestsData(VideoApplication.selectedMockTestTopic.getMocktestTopicId());
    }

    private void getMockTestTestsData(String topicId) {

        isMock = true;
        if(isMock){
            populateMockMockTestsData();
        }else {
            String url = "http://crackingmba.com/getVideoList.php?subcategory_id=ratio";
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        MockTestTestsModel model = gson.fromJson(response, MockTestTestsModel.class);
                        tests = model.getTests();
                        Log.d(TAG, "MockTestTests : " + tests);
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

        populateMockTestsMap();

        mAdapter = new MockTestTestsAdapter(tests);
        recyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        if (null != mAdapter) {
            ((MockTestTestsAdapter) mAdapter).setOnItemClickListener(
                    new MockTestTestsAdapter.MyClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            Log.d(TAG, "MockTestTestsAdapter, Clicked item at position : " + position);
                            Log.d(TAG,mockTestsMap.toString());
                            String testId = ((TextView)v.findViewById(R.id.mocktest_test_id)).getText().toString();
                            Log.d(TAG,"Test Id = "+testId);
                            VideoApplication.selectedMockTestTest = mockTestsMap.get(testId);
                            Intent startIntent = new Intent(getApplicationContext(), StartMockTestActivity.class);
                            startActivity(startIntent);
                            Log.d(TAG, "MockTestTest.." + VideoApplication.selectedMockTestTest);
                        }
                    }
            );
        }
    }

    private void populateMockMockTestsData(){
        tests = new ArrayList<>();
        MockTestTest test1 = new MockTestTest("test1","","Mock Test1","Ratio & Proportion","10","10");
        MockTestTest test2 = new MockTestTest("test2","","Mock Test2","Simple Interest","10","10");
        tests.add(test1);
        tests.add(test2);
    }

    private void populateMockTestsMap(){
        mockTestsMap = new HashMap<>();
        for(MockTestTest test : tests){
            mockTestsMap.put(test.getMocktestTestId(),test);
        }
    }
}
