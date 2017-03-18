package com.crackingMBA.training;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.MockTestTestsAdapter;
import com.crackingMBA.training.pojo.MockTestTest;
import com.crackingMBA.training.pojo.MockTestTestsModel;
import com.crackingMBA.training.pojo.MockTestTopic;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class MockTestTestsActivity extends AppCompatActivity {
    private static String TAG = "MockTestTestsActivity";
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    private TextView msg;
    boolean isMock;
    private static List<MockTestTest> tests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocktest_tests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isMock = pref.getBoolean("isMock", false);

        ((TextView) findViewById(R.id.mocktest_topic_title)).setText(VideoApplication.selectedMockTestTopicTitle);
        msg = (TextView) findViewById(R.id.mocktest_test_msg);
        recyclerView=(RecyclerView)findViewById(R.id.mocktest_test_recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        getMockTestTestsData(VideoApplication.selectedMockTestTopic.getId());
    }

    private void getMockTestTestsData(String topicId) {

        Log.d(TAG,"isMock="+isMock);
        if(isMock){
            populateMockMockTestsData();
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
                                VideoApplication.selectedMockTestTest = tests.get(position);
                                //Populate allQuestions for this test -- code to be changes according to service
                                Intent startIntent = new Intent(getApplicationContext(), StartMockTestActivity.class);
                                startActivity(startIntent);
                                Log.d(TAG, "MockTestTest.." + VideoApplication.selectedMockTestTest);
                            }
                        }
                );
            }
        }else {
            //Populate request parameters
            RequestParams params = new RequestParams();
            params.add("subcatID",topicId);
            try {
                Log.d(TAG,"serviceUrl="+CrackingConstant.GET_MOCKTEST_TESTS_SERVICE_URL);
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(CrackingConstant.GET_MOCKTEST_TESTS_SERVICE_URL, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        MockTestTestsModel model = gson.fromJson(response, MockTestTestsModel.class);
                        Log.d(TAG,"model retrieved is "+model);
                        if(null == model || model.getMockTestList().size()==0){
                            msg.setText("No Tests are available under this topic..");
                            return;
                        }
                        tests = model.getMockTestList();
                        Log.d(TAG, "MockTestTests : " + tests);
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
                                            VideoApplication.selectedMockTestTest = tests.get(position);
                                            //Populate allQuestions for this test -- code to be changes according to service
                                            Intent startIntent = new Intent(getApplicationContext(), StartMockTestActivity.class);
                                            startActivity(startIntent);
                                            Log.d(TAG, "MockTestTest.." + VideoApplication.selectedMockTestTest);
                                        }
                                    }
                            );
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void populateMockMockTestsData(){
        tests = new ArrayList<>();
        MockTestTest test1 = new MockTestTest("test1","","Mock Test1","Ratio & Proportion");
        MockTestTest test2 = new MockTestTest("test2","","Mock Test2","Simple Interest");
        tests.add(test1);
        tests.add(test2);
    }

    private MockTestTopic populateMockTestTopic(View v){
        MockTestTopic mockTestTopic = new MockTestTopic();
        mockTestTopic.setId(((TextView)v.findViewById(R.id.mocktest_topic_id)).getText().toString());
        mockTestTopic.setName(((TextView)v.findViewById(R.id.mocktest_topic_txt)).getText().toString());
        return mockTestTopic;
    }


}
