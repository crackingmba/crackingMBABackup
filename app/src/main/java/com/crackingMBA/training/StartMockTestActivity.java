package com.crackingMBA.training;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.pojo.MockTestQuestion;
import com.crackingMBA.training.pojo.MockTestQuestionsModel;
import com.crackingMBA.training.pojo.MockTestTest;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class StartMockTestActivity extends AppCompatActivity {

    private static String TAG = "StartMockTestActivity";
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    boolean isMock;
    private static List<MockTestQuestion> qstns = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocktest_starttest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        MockTestTest test = VideoApplication.selectedMockTestTest;
        ((TextView) findViewById(R.id.mocktest_test_title)).setText(test.getTestTitle());
        ((TextView) findViewById(R.id.mocktest_test_topic)).setText(VideoApplication.selectedMockTestTopic.getName());
        ((TextView) findViewById(R.id.mocktest_test_noofqstns)).setText("1. There are "+CrackingConstant.TOTAL_NO_OF_QSTNS+" number of questions in this test");
        ((TextView) findViewById(R.id.mocktest_test_duration)).setText("4. This is a timed test of "+CrackingConstant.TOTAL_TEST_DURATION+" minutes. After this the test will stop");
        ((Button) findViewById(R.id.mocktest_starttest_button)).setEnabled(true);

        getMockTestQstnsData(test.getTestId());
    }

    private void getMockTestQstnsData(String testId) {

        if(isMock){
            qstns.add(new MockTestQuestion("1", "1", "What is the ratio of x:y if x is 4 and y is 32?", "1:2", "1:4", "1:7","1:8",null,"4"));
            qstns.add(new MockTestQuestion("1", "2", "What is the ratio of x:y if x is 4 and y is 32?", "1:2", "1:4", "1:7","1:8",null,"4"));
            qstns.add(new MockTestQuestion("1", "3", "What is the ratio of x:y if x is 4 and y is 32?", "1:2", "1:4", "1:7","1:8",null,"4"));
            VideoApplication.allMockQstns = qstns;
        }else {//Populate request parameters
            RequestParams params = new RequestParams();
            params.add("testId",testId);
            try {
                Log.d(TAG,"serviceUrl = "+CrackingConstant.GET_MOCKTEST_QSTNS_SERVICE_URL);
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(CrackingConstant.GET_MOCKTEST_QSTNS_SERVICE_URL, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        MockTestQuestionsModel model = gson.fromJson(response, MockTestQuestionsModel.class);
                        Log.d(TAG,"model retrieved.."+model);
                        if(null == model || model.getMockTestQns().size()==0){
                            Toast noQstnsMsg= Toast.makeText(getApplicationContext(),"No Questions are available under this test..", Toast.LENGTH_SHORT);
                            noQstnsMsg.show();
                            ((Button) findViewById(R.id.mocktest_starttest_button)).setEnabled(false);
                            return;
                        }else{
                            ((Button) findViewById(R.id.mocktest_starttest_button)).setEnabled(true);
                        }
                        qstns = model.getMockTestQns();
                        Log.d(TAG, "MockTestQstns : " + qstns);
                        VideoApplication.allMockQstns = qstns;
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


    public void startTest(View v){
        Log.d(TAG,"CLicked startTest..");
        Intent submitIntent=new Intent(getApplicationContext(),SubmitMockTestActivity.class);
        startActivity(submitIntent);
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
