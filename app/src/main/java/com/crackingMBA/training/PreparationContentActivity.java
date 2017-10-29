package com.crackingMBA.training;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    TextView prep_content_header, enrolled_status_tv;
    String prep_category_header;
    Button prep_content_enroll_now;SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String prep_category_code = getIntent().getStringExtra("PREP_CATEGORY_CODE");
        prep_category_header = getIntent().getStringExtra("PREP_CATEGORY_HEADER");
        setContentView(R.layout.activity_preparation_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prep_content_header=(TextView)findViewById(R.id.prep_content_header);
        enrolled_status_tv=(TextView)findViewById(R.id.enrolled_status_tv);
        //prep_content_enroll_now = (Button)findViewById(R.id.prep_content_enroll_now);

       /* prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);

        if(isUserLoggedIn){
            String whetherSpecificCourseEnrolled="notqueried";
            switch(prep_category_code){
                case "CATPREP1":{
                    whetherSpecificCourseEnrolled = prefs.getString("whetherCATcourseEnrolled", "notqueried");
                    break;
                }
                case "IIFTPREP1":{
                    whetherSpecificCourseEnrolled = prefs.getString("whetherIIFTcourseEnrolled", "notqueried");
                    break;
                }
                case "SNAPPREP1":{
                    whetherSpecificCourseEnrolled = prefs.getString("whetherSNAPcourseEnrolled", "notqueried");
                    break;
                }
                case "XATPREP":{
                    whetherSpecificCourseEnrolled = prefs.getString("whetherXATcourseEnrolled", "notqueried");
                    break;
                }
            }

            if(whetherSpecificCourseEnrolled.equals("queried1")){
                Toast.makeText(this, "HOORRAY! USER ENROLLED FOR THIS COURSE", Toast.LENGTH_SHORT).show();
                //prep_content_enroll_now.setVisibility(View.GONE);
                enrolled_status_tv.setVisibility(View.VISIBLE);
            }else{
                //Toast.makeText(this, "HOORRAY! USER IS NOT ENROLLED FOR THIS COURSE", Toast.LENGTH_SHORT).show();
                //prep_content_enroll_now.setVisibility(View.VISIBLE);
                //enrolled_status_tv.setVisibility(View.GONE);
            }

        }*/

       /* prep_content_enroll_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreparationContentActivity.this, CourseEnrollmentActivity.class);
                switch (prep_category_code){
                    case "CATPREP1":
                    {
                        intent.putExtra("PREP_CATEGORY_CODE","30 Day CAT Challenge");
                        break;
                    }
                    case "IIFTPREP1":
                    {
                        intent.putExtra("PREP_CATEGORY_CODE","40 Day IIFT Challenge");
                        break;
                    }
                    case "SNAPPREP1":
                    {
                        intent.putExtra("PREP_CATEGORY_CODE","50 Day SNAP Challenge");
                        break;
                    }
                    case "XATPREP":
                    {
                        intent.putExtra("PREP_CATEGORY_CODE","60 Day XAT Challenge");
                        break;
                    }
                }

                startActivity(intent);
            }
        });*/


        apiService = RestClient.getClient().create(PrepContentAPIService.class);
        recyclerView = (RecyclerView)findViewById(R.id.prepcontentRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(PreparationContentActivity.this));

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
                            intent.putExtra("PREP_CATEGORY_CODE",prep_category_code);
                            intent.putExtra("PREP_CATEGORY_NAME",questions.get(position).getName());
                            intent.putExtra("PREP_CATEGORY_HEADER",prep_category_header);
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


