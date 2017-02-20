package com.crackingMBA.training;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.crackingMBA.training.pojo.MockTestTest;

public class StartMockTestActivity extends AppCompatActivity {

    private static String TAG = "StartMockTestActivity";
    boolean isMock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocktest_starttest);

        MockTestTest test = VideoApplication.selectedMockTestTest;
        ((TextView) findViewById(R.id.mocktest_test_title)).setText(test.getMocktestTestTitle());
        ((TextView) findViewById(R.id.mocktest_test_topic)).setText(test.getMockTestTopicTxt());
        ((TextView) findViewById(R.id.mocktest_test_noofqstns)).setText("1. There are "+test.getMockTestNoOfQstns()+" number of questions in this test");
        ((TextView) findViewById(R.id.mocktest_test_duration)).setText("4. This is a timed test of "+test.getMockTestDuration()+" minutes. After this the test will stop");

    }

    public void startTest(View v){
        Log.d(TAG,"CLicked startTest..");
    }
}
