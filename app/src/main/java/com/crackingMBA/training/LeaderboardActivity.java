package com.crackingMBA.training;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crackingMBA.training.adapter.LeaderboardAdapter;
import com.crackingMBA.training.pojo.RetrofitLeaderboardContent;
import com.crackingMBA.training.pojo.RetrofitLeaderboardList;
import com.crackingMBA.training.pojo.RetrofitMockTest;
import com.crackingMBA.training.pojo.RetrofitMockTestList;
import com.crackingMBA.training.restAPI.LeaderboardAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LeaderboardAdapter adapter;
    List<RetrofitLeaderboardContent> questions = new ArrayList<>();
    Call<RetrofitLeaderboardList> call;
    LeaderboardAPIService apiService;
    String exam_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        exam_id= getIntent().getStringExtra("MINI_TEST_EXAM_ID");
        Log.d("Leaderboard",exam_id);
        Toast.makeText(getApplicationContext(), "just started for id= "+exam_id, Toast.LENGTH_SHORT).show();

        apiService = RestClient.getClient().create(LeaderboardAPIService.class);
        recyclerView = (RecyclerView)findViewById(R.id.leaderboardRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(LeaderboardActivity.this));

        adapter = new LeaderboardAdapter(questions, R.layout.retrofit_leaderboard_layout, LeaderboardActivity.this);
        recyclerView.setAdapter(adapter);

/*        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(LeaderboardActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //Toast.makeText(PreparationContentActivity.this, "Hey There!", Toast.LENGTH_SHORT).show();

                        if(questions.get(position).getType().toString().equals("support")){
                            Intent intent = new Intent(LeaderboardActivity.this, SupportGuidanceActivity.class);
                            startActivity(intent);
                        }else if(questions.get(position).getType().toString().equals("study"))
                        {
                            Intent intent = new Intent(LeaderboardActivity.this, PreparationHLContentActivity.class);
                            startActivity(intent);
                        }

                    }
                })
        );*/

        call = apiService.fetchLeaderboard(exam_id);
        fetchPrepContentList();
/*        if(MyUtil.checkConnectivity(getApplicationContext())) {

            MyUtil.showProgressDialog(this);
            fetchPrepContentList();
        }
        else{
            Toast.makeText(this, "Sorry. There is no internet connection!", Toast.LENGTH_SHORT).show();
        }*/

    }

    private void fetchPrepContentList() {
        call.enqueue(new Callback<RetrofitLeaderboardList>() {
            @Override
            public void onResponse(Call<RetrofitLeaderboardList> call, Response<RetrofitLeaderboardList> response) {
                //MyUtil.hideProgressDialog();

                if(response.body() == null){
                    Log.d("Leaderboard","no response");
                    Toast.makeText(getApplicationContext(), "response not received", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Log.d("Leaderboard","response received");
                    Toast.makeText(getApplicationContext(), "response received", Toast.LENGTH_SHORT).show();
                    questions.addAll(response.body().getQuestions());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RetrofitLeaderboardList> call, Throwable t) {
                Log.d("Leaderboard","some issue here. need to fix");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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
}
