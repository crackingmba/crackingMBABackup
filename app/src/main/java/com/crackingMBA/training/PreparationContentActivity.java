package com.crackingMBA.training;

import android.content.Intent;
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
import com.crackingMBA.training.adapter.RetrofitForumCommentAdapter;
import com.crackingMBA.training.pojo.RetrofitForumComment;
import com.crackingMBA.training.pojo.RetrofitForumCommentList;
import com.crackingMBA.training.pojo.RetrofitPrepContent;
import com.crackingMBA.training.pojo.RetrofitPrepContentList;
import com.crackingMBA.training.restAPI.ForumCommentsAPIService;
import com.crackingMBA.training.restAPI.PrepContentAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PreparationContentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PreparationContentAdapter adapter;
    List<RetrofitPrepContent> questions = new ArrayList<>();
    Call<RetrofitPrepContentList> call;
    PrepContentAPIService apiService;
    TextView prep_content_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String prep_category_code = getIntent().getStringExtra("PREP_CATEGORY_CODE");
        String prep_category_header = getIntent().getStringExtra("PREP_CATEGORY_HEADER");
        setContentView(R.layout.activity_preparation_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        apiService = RestClient.getClient().create(PrepContentAPIService.class);
        recyclerView = (RecyclerView)findViewById(R.id.prepcontentRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(PreparationContentActivity.this));
        prep_content_header=(TextView)findViewById(R.id.prep_content_header);

        adapter = new PreparationContentAdapter(questions, R.layout.retrofit_prep_content_layout, PreparationContentActivity.this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(PreparationContentActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //Toast.makeText(PreparationContentActivity.this, "Hey There!", Toast.LENGTH_SHORT).show();

                        if(questions.get(position).getType().toString().equals("support")){
                            Intent intent = new Intent(PreparationContentActivity.this, SupportGuidanceActivity.class);
                            startActivity(intent);
                        }else if(questions.get(position).getType().toString().equals("study"))
                        {
                            Intent intent = new Intent(PreparationContentActivity.this, PreparationHLContentActivity.class);
                            switch(prep_category_code){
                                case "CATPREP":{
                                    intent.putExtra("PREP_CATEGORY_CODE","CAT");
                                    intent.putExtra("PREP_CATEGORY_HEADER","CAT Study Material");
                                    break;
                                }

                                case "IIFTPREP":{
                                    intent.putExtra("PREP_CATEGORY_CODE","IIFT");
                                    intent.putExtra("PREP_CATEGORY_HEADER","IIFT Study Material");
                                    break;
                                }

                                case "SNAPPREP":{
                                    intent.putExtra("PREP_CATEGORY_CODE","SNAP");
                                    intent.putExtra("PREP_CATEGORY_HEADER","SNAP Study Material");
                                    break;
                                }

                            }



                            startActivity(intent);
                        }

                        else{
                            Intent intent = new Intent(PreparationContentActivity.this, PreparationDetailsActivity.class);
                            intent.putExtra("PREP_CATEGORY_ID",questions.get(position).getId());
                            intent.putExtra("PREP_CATEGORY_NAME",questions.get(position).getName());
                            startActivity(intent);
                        }

                    }
                })
        );


        prep_content_header.setText(prep_category_header);
        call = apiService.fetchPrepContent(prep_category_code);
        if(MyUtil.checkConnectivity(getApplicationContext())) {

            MyUtil.showProgressDialog(this);
            fetchPrepContentList();
        }
        else{
            Toast.makeText(this, "Sorry. There is no internet connection!", Toast.LENGTH_SHORT).show();
        }

    }

    private void fetchPrepContentList() {
        call.enqueue(new Callback<RetrofitPrepContentList>() {
            @Override
            public void onResponse(Call<RetrofitPrepContentList> call, Response<RetrofitPrepContentList> response) {
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
            public void onFailure(Call<RetrofitPrepContentList> call, Throwable t) {
            }
        });
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


