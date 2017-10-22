package com.crackingMBA.training;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.RetrofitMockTestAdapter;
import com.crackingMBA.training.adapter.RetrofitQuestionAdapter;
import com.crackingMBA.training.pojo.RetrofitMockTest;
import com.crackingMBA.training.pojo.RetrofitMockTestList;
import com.crackingMBA.training.pojo.RetrofitQuestion;
import com.crackingMBA.training.pojo.RetrofitQuestionList;
import com.crackingMBA.training.restAPI.MockTestAPIService;
import com.crackingMBA.training.restAPI.QuestionAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockTestAnalysisActivity extends AppCompatActivity {
    MockTestAPIService apiService;
    RecyclerView recyclerView;
    RetrofitMockTestAdapter adapter;
    List<RetrofitMockTest> mocktests = new ArrayList<>();
    Call<RetrofitMockTestList> call;
    SharedPreferences prefs; TextView mocktest_user;String userID; Button mocktest_analysis_btn, mocktest_analysis_logout_btn;
    SharedPreferences.Editor ed;
    TextView mock_test_analysis_message_tv;
    Boolean onLoginActivityFlag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test_analysis);

        prefs = PreferenceManager.getDefaultSharedPreferences(MockTestAnalysisActivity.this);

        Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);
        String nameofUser = prefs.getString("nameofUser", "");


        mocktest_user = (TextView)findViewById(R.id.mocktest_user);
        mock_test_analysis_message_tv = (TextView)findViewById(R.id.mock_test_analysis_message_tv);
        mocktest_analysis_btn = (Button)findViewById(R.id.mocktest_analysis_btn);
        mocktest_analysis_logout_btn = (Button)findViewById(R.id.mocktest_analysis_logout_btn);

        if(isUserLoggedIn){
            mocktest_user.setText("Welcome "+nameofUser);
            userID= prefs.getString("userID", "");
            mocktest_analysis_btn.setVisibility(View.GONE);
            mocktest_analysis_logout_btn.setVisibility(View.VISIBLE);
        }else{
            mocktest_user.setText("Welcome Guest");
            userID="1";
            mocktest_analysis_btn.setVisibility(View.VISIBLE);
            mocktest_analysis_logout_btn.setVisibility(View.GONE);
        }

        apiService = RestClient.getClient().create(MockTestAPIService.class);
        recyclerView = (RecyclerView)findViewById(R.id.mocktestListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new RetrofitMockTestAdapter(mocktests, R.layout.mocktest_analysis_layout, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(MockTestAnalysisActivity.this, "Ouch! clicked this!", Toast.LENGTH_SHORT).show();
                        //Intent forumpostIntent = new Intent(getApplicationContext(), ForumCommentsActivity.class);
                        //startActivity(forumpostIntent);
                    }
                })
        );


        mocktest_analysis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MockTestAnalysisActivity.this, LoginActivity.class);
                intent.putExtra("IS_IT_FOR_ENROLLMENT","0");
                intent.putExtra("EXAM_NAME","CAT");
                intent.putExtra("EXAM_NAME_TEXT","Spare");
                onLoginActivityFlag=true;
                startActivity(intent);
            }
        });

        mocktest_analysis_logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed = prefs.edit();
                ed.putBoolean("isUserLoggedIn", false);
                ed.putString("nameofUser","");
                ed.putString("emailofUser","");
                ed.putString("userID","");
                ed.commit();
                mocktest_analysis_logout_btn.setVisibility(View.GONE);
                mocktest_user.setText("Welcome Guest");
                mocktest_analysis_btn.setVisibility(View.VISIBLE);
                if(mocktests.size()>0){
                    mocktests.clear();
                    adapter.notifyDataSetChanged();
                }
                mock_test_analysis_message_tv.setVisibility(View.VISIBLE);
                mock_test_analysis_message_tv.setText("Hi! This section is for users enrolled for any of our courses such as '30 Day Challenge, Focus IIFT, Focus SNAP or Focus XAT'. If you are enrolled, please login to view your performance improvement tips and recommendations from us!");
            }
        });


        if(isUserLoggedIn){
            mock_test_analysis_message_tv.setVisibility(View.GONE);
            call = apiService.fetchMockTests(userID);
            fetchQuestionList();
        }else{
            mock_test_analysis_message_tv.setVisibility(View.VISIBLE);
            mock_test_analysis_message_tv.setText("Hi! This section is for users enrolled for any of our courses such as '30 Day Challenge, Focus IIFT, Focus SNAP or Focus XAT'. If you are enrolled, please login to view your performance improvement tips and recommendations from us!");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void fetchQuestionList() {

        call.enqueue(new Callback<RetrofitMockTestList>() {

            @Override
            public void onResponse(Call<RetrofitMockTestList> call, Response<RetrofitMockTestList> response) {
                MyUtil.hideProgressDialog();

                if(response.body()==null){
                    //Toast.makeText(getApplicationContext(), "There are no posts in this category!", Toast.LENGTH_SHORT).show();

                    if(mocktests.size()>0){
                        mocktests.clear();
                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "There are no posts in this category!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    mocktests.addAll(response.body().getMockTestScores());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RetrofitMockTestList> call, Throwable t) {
                //Log.e(TAG, "Got error : " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mocktests.size()>0){
            mocktests.clear();
            adapter.notifyDataSetChanged();
        }

        Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);
        String nameofUser = prefs.getString("nameofUser", "");

        if(onLoginActivityFlag){
            if(isUserLoggedIn){
                mocktest_user.setText("Welcome "+nameofUser);
                userID= prefs.getString("userID", "");
                mocktest_analysis_btn.setVisibility(View.GONE);
                mocktest_analysis_logout_btn.setVisibility(View.VISIBLE);
                mock_test_analysis_message_tv.setVisibility(View.GONE);
                call = apiService.fetchMockTests(userID);
                fetchQuestionList();
            }else{
                mocktest_user.setText("Welcome Guest");
                userID="1";
                mocktest_analysis_btn.setVisibility(View.VISIBLE);
                mocktest_analysis_logout_btn.setVisibility(View.GONE);
            }

        }


    }
}
