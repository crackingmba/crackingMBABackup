package com.crackingMBA.training;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.PreparationContentAdapter;
import com.crackingMBA.training.adapter.PreparationDetailAdapter;
import com.crackingMBA.training.pojo.RetrofitPrepDetail;
import com.crackingMBA.training.pojo.RetrofitPrepDetailList;
import com.crackingMBA.training.restAPI.PrepContentAPIService;
import com.crackingMBA.training.restAPI.PrepDetailAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreparationDetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PreparationDetailAdapter adapter;
    List<RetrofitPrepDetail> questions = new ArrayList<>();
    Call<RetrofitPrepDetailList> call;
    PrepDetailAPIService apiService;
    TextView prep_content_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparation_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prep_content_header=(TextView)findViewById(R.id.prep_content_header);

        apiService = RestClient.getClient().create(PrepDetailAPIService.class);
        recyclerView = (RecyclerView)findViewById(R.id.prepdetailRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(PreparationDetailsActivity.this));

        adapter = new PreparationDetailAdapter(questions, R.layout.retrofit_prep_detail_layout, PreparationDetailsActivity.this);
        recyclerView.setAdapter(adapter);

        String exam_category_id = getIntent().getStringExtra("PREP_CATEGORY_ID");
        String header = getIntent().getStringExtra("PREP_CATEGORY_NAME");

        prep_content_header.setText(header);

        call = apiService.fetchPrepDetail(exam_category_id);

        if(MyUtil.checkConnectivity(getApplicationContext())) {

            MyUtil.showProgressDialog(this);
            fetchPrepContentList();
        }
        else{
            Toast.makeText(this, "Sorry. There is no internet connection!", Toast.LENGTH_SHORT).show();
        }
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

    private void fetchPrepContentList() {
        call.enqueue(new Callback<RetrofitPrepDetailList>() {
            @Override
            public void onResponse(Call<RetrofitPrepDetailList> call, Response<RetrofitPrepDetailList> response) {
                MyUtil.hideProgressDialog();

                if(response.body() == null){
                    //comments_comments_not_added.setVisibility(View.VISIBLE);
                    return;
                }else{
                    questions.addAll(response.body().getQuestions());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RetrofitPrepDetailList> call, Throwable t) {
            }
        });
    }

}
