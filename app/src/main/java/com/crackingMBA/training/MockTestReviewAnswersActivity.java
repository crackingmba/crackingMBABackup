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
import android.widget.TextView;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.MockTestReviewResultsAdapter;
import com.crackingMBA.training.pojo.MockTestQuestion;
import com.crackingMBA.training.pojo.ReviewResultPojo;

import java.util.ArrayList;

public class MockTestReviewAnswersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocktest_reviewanswers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.mocktest_reviewanswers_recycler_view);
        recyclerView.setHasFixedSize(true);

        ((TextView) findViewById(R.id.reviewanswers_test_title)).setText(VideoApplication.selectedMockTestTest.getTestTitle());
        getDataSet();
    }

    private void getDataSet() {

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MockTestReviewResultsAdapter(populateReviewResults());
        recyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        if (null != mAdapter) {
            ((MockTestReviewResultsAdapter) mAdapter).setOnItemClickListener(
                    new MockTestReviewResultsAdapter.MyClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            //Log.d(TAG, "MockTestReviewResultsAdapter, Clicked item at position : " + position);
                        }
                    }
            );
        }
    }

    private ArrayList<ReviewResultPojo> populateReviewResults() {
        ArrayList<ReviewResultPojo> results = new ArrayList<>();
        ReviewResultPojo vo1 = null; int count=1;
        for(MockTestQuestion question : VideoApplication.allMockQstns) {
            vo1 = new ReviewResultPojo(count+". "+question.getQstnTxt(),question.getSelectedOption(),question.getAnswer(), question.getAnswerExplanation());
            results.add(vo1);
            count++;
            //Log.d(TAG,"Added : "+vo1);
        }
        //Log.d(TAG,"Returning Review results:"+results);
        return results;
    }

    public void viewScoreCard(View v){
        //Log.d(TAG,"Clicked viewScoreCard..");
//        Intent intent = new Intent(getApplicationContext(),MockTestResultActivity.class);
//        startActivity(intent);
        finish();
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
