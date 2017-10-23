package com.crackingMBA.training;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.crackingMBA.training.pojo.MockTestQuestion;

public class MockTestResultActivity extends AppCompatActivity {

    private static String TAG = "MockTestResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocktest_testresult);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        int totalQstns = VideoApplication.allMockQstns.size();
        int attemptedQstns = 0;
        int correctQstns = 0;
        int incorrectQstns = 0;
        double accuracy = 0.0;
        double totalMarks = 0.0;

        for(MockTestQuestion question : VideoApplication.allMockQstns){
            if(null != question.getSelectedOption()){
                attemptedQstns+=1;
                if(question.getSelectedOption().equals(question.getAnswer())){
                    correctQstns+=1;
                    totalMarks = totalMarks+1.0;
                }else{
                    totalMarks = totalMarks-0.25;
                }
            }
        }

        ((TextView) findViewById(R.id.testresult_testTitle)).setText(VideoApplication.selectedMockTestTest.getTestTitle());
        ((TextView) findViewById(R.id.testresult_total_qstns_txt)).setText(""+totalQstns);
        ((TextView) findViewById(R.id.testresult_attempted_qstns_txt)).setText(""+attemptedQstns);
        ((TextView) findViewById(R.id.testresult_correct_qstns_txt)).setText(""+correctQstns);
        ((TextView) findViewById(R.id.testresult_incorrect_qstns_txt)).setText(""+(attemptedQstns-correctQstns));
        ((TextView) findViewById(R.id.testresult_totalmarks_txt)).setText(""+ String.format("%.2f",totalMarks));
    }

    public void attemptAnotherTest(View v){
        Log.d(TAG,"CLicked attemptAnotherTest..");
       /* Intent mainIntent = new Intent(getApplicationContext(),MockTestTopicsActivity.class);
        startActivity(mainIntent);*/
        this.finish();
    }

    public void reviewAnswers(View v){
        Log.d(TAG,"CLicked reviewAnswers..");
        Intent mainIntent = new Intent(getApplicationContext(),MockTestReviewAnswersActivity.class);
        startActivity(mainIntent);
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
