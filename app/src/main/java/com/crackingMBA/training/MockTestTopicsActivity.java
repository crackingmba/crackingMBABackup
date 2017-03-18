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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.MockTestTopicsAdapter;
import com.crackingMBA.training.pojo.MockTestTopic;
import com.crackingMBA.training.pojo.MockTestTopicsModel;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class MockTestTopicsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    LinearLayoutManager mLayoutManager;
    private TextView msg;
    boolean isMock;

    private static String TAG = "MockTestTopicsActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocktest_topics);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isMock = pref.getBoolean("isMock", false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        msg = (TextView)findViewById(R.id.mocktest_topic_msg);
        recyclerView = (RecyclerView) findViewById(R.id.mocktest_topics_recycler_view);
        recyclerView.setHasFixedSize(true);

        getDataSet(VideoApplication.sectionClicked);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getDataSet(String sectionClicked) {

        Log.d(TAG, "isMock?" + isMock);
        if (isMock) {
            List<MockTestTopic> results = populateMockTestTopics();
            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MockTestTopicsAdapter(results);
            recyclerView.setAdapter(mAdapter);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);

            if (null != mAdapter) {
                ((MockTestTopicsAdapter) mAdapter).setOnItemClickListener(
                        new MockTestTopicsAdapter.MyClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {
                                Log.d(TAG, "Section adapter, Clicked item at position : " + position);
                                MockTestTopic topic = populateMockTestTopic(v);
                                VideoApplication.selectedMockTestTopic = topic;
                                Intent topicsIntent=new Intent(getApplicationContext(),MockTestTestsActivity.class);
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
                //Populate request parameters
                RequestParams params = new RequestParams();
                params.add("category",VideoApplication.sectionClicked);
                Log.d(TAG,"serviceUrl = "+CrackingConstant.GET_MOCKTEST_TOPICS_SERVICE_URL);
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(CrackingConstant.GET_MOCKTEST_TOPICS_SERVICE_URL, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        MockTestTopicsModel model = gson.fromJson(response, MockTestTopicsModel.class);
                        Log.d(TAG,"model retrieved.."+model);
                        if(null == model || model.getmTSubCatList().size()==0){
                            msg.setText("No Topics are available under this section..");
                            return;
                        }
                        ((TextView) findViewById(R.id.mocktest_topic_sectiontitle)).setText(model.getmTSubCatTitle());
                        VideoApplication.selectedMockTestTopicTitle = model.getmTSubCatTitle();
                        results.addAll(model.getmTSubCatList());
                        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

                        recyclerView.setLayoutManager(mLayoutManager);
                        mAdapter = new MockTestTopicsAdapter(results);
                        recyclerView.setAdapter(mAdapter);
                        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);
                        recyclerView.addItemDecoration(itemDecoration);

                        if (null != mAdapter) {
                            ((MockTestTopicsAdapter) mAdapter).setOnItemClickListener(
                                    new MockTestTopicsAdapter.MyClickListener() {
                                        @Override
                                        public void onItemClick(int position, View v) {
                                            Log.d(TAG, "MockTestTopicsAdapter, Clicked item at position : " + position);
                                            VideoApplication.selectedMockTestTopic = results.get(position);
                                            Intent topicsIntent=new Intent(getApplicationContext(),MockTestTestsActivity.class);
                                            startActivity(topicsIntent);
                                            Log.d(TAG, "set with topic.."+VideoApplication.selectedMockTestTopic);
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
        MockTestTopic vo1 = new MockTestTopic("topic1",VideoApplication.sectionClicked, "Ratio & Proportion", "");
        MockTestTopic vo2 = new MockTestTopic("topic2",VideoApplication.sectionClicked, "Simple Interest","");
        mockResults.add(vo1);
        mockResults.add(vo2);
        Log.d(TAG,"Returning mocked objects:"+mockResults);
        return mockResults;
    }

    private MockTestTopic populateMockTestTopic(View v){
        MockTestTopic mockTestTopic = new MockTestTopic();
        mockTestTopic.setId(((TextView)v.findViewById(R.id.mocktest_topic_id)).getText().toString());
        mockTestTopic.setName(((TextView)v.findViewById(R.id.mocktest_topic_txt)).getText().toString());
        return mockTestTopic;
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
