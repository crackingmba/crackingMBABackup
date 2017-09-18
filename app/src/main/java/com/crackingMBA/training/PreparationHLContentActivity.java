package com.crackingMBA.training;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
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
import com.crackingMBA.training.adapter.PreparationHLContentAdapter;
import com.crackingMBA.training.interfaces.ClickListener;
import com.crackingMBA.training.pojo.MockTestTest;
import com.crackingMBA.training.pojo.RetrofitPrepHLContent;
import com.crackingMBA.training.pojo.RetrofitPrepHLContentList;
import com.crackingMBA.training.restAPI.PrepContentAPIService;
import com.crackingMBA.training.restAPI.PrepHLContentAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreparationHLContentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PreparationHLContentAdapter adapter;
    List<RetrofitPrepHLContent> questions = new ArrayList<>();
    Call<RetrofitPrepHLContentList> call;
    PrepHLContentAPIService apiService;
    String course_category;String str;int index;
    TextView prep_content_header,prep_study1_tv, prep_study2_tv, prep_study3_tv, prep_study4_tv, prep_study5_tv,prep_study6_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparation_hlcontent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        apiService = RestClient.getClient().create(PrepHLContentAPIService.class);
        recyclerView = (RecyclerView)findViewById(R.id.prepHLcontentRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(PreparationHLContentActivity.this));
        prep_content_header=(TextView)findViewById(R.id.prep_content_header11);

        adapter = new PreparationHLContentAdapter(questions, R.layout.retrofit_prep_study_content_layout, PreparationHLContentActivity.this, new ClickListener() {
            @Override
            public void onPositionClicked(int selected_position) {
                int position=0;

                if(selected_position<9){
                    index=selected_position;
                    position=0;
                }else{
                    index=selected_position%10;
                    position=(selected_position-index)/10;
                }

                Intent intent = new Intent(PreparationHLContentActivity.this, PreparationDetailsActivity.class);
                intent.putExtra("PREP_CATEGORY_ID",questions.get(position).getId());
                intent.putExtra("PREP_CATEGORY_NAME",questions.get(position).getName());

                course_category=questions.get(position).getCategory();

                switch(course_category){
                    case "CAT":{
                        course_category="CAT 2017 Preparation Course";
                        break;
                    }
                    case "IIFT":{
                        course_category="IIFT 2017 Preparation Course";
                        break;
                    }
                    case "SNAP":{
                        course_category="SNAP 2017 Preparation Course";
                        break;
                    }

                }

                switch(index){
                    case 1:{
                        processRows(questions.get(position).getStudy1(), questions.get(position).getStudy1Type());
                        break;
                    }
                    case 2:{
                        processRows(questions.get(position).getStudy2(), questions.get(position).getStudy2Type());
                        break;
                    }
                    case 3:{
                        processRows(questions.get(position).getStudy3(), questions.get(position).getStudy3Type());
                        break;
                    }
                    case 4:{
                        processRows(questions.get(position).getStudy4(), questions.get(position).getStudy4Type());
                        break;
                    }
                    case 5:{
                        processRows(questions.get(position).getStudy5(), questions.get(position).getStudy5Type());
                        break;
                    }
                    case 6:{
                        processRows(questions.get(position).getStudy6(), questions.get(position).getStudy6Type());
                        break;
                    }
                }

                /*switch(index){
                    case 1:{
                        switch(questions.get(position).getStudy1Type()){
                            case "mocktest":{
                                str = questions.get(position).getStudy1();
                                String test_name = str.substring(0,str.indexOf(","));

                                str= questions.get(position).getStudy1();
                                String test_id=str.substring(str.indexOf(",") + 1);

                                MockTestTest mockTestTest = new MockTestTest(test_id, "1000", "numbers1.png",test_name);
                                VideoApplication.selectedMockTestTest= mockTestTest;

                                Intent startIntent = new Intent(getApplicationContext(), StartMockTestActivity.class);
                                startActivity(startIntent);
                                break;
                            }
                            case "video":{
                                str = questions.get(position).getStudy1();
                                String course_name = str.substring(0,str.indexOf(","));

                                str= questions.get(position).getStudy1();
                                String url=str.substring(str.indexOf(",") + 1);

                                intent = new Intent(getApplicationContext(), TargetVideoActivity.class);
                                intent.putExtra("COURSE_NAME",course_category);
                                intent.putExtra("COURSE_SUBJECT",course_name);
                                intent.putExtra("URL",url);
                                startActivity(intent);
                                break;
                            }
                        }
                        break;
                    }


                    case 2:{
                        switch(questions.get(position).getStudy2Type()){
                            case "mocktest":{
                                str = questions.get(position).getStudy2();
                                String test_name = str.substring(0,str.indexOf(","));

                                str= questions.get(position).getStudy2();
                                String test_id=str.substring(str.indexOf(",") + 1);

                                MockTestTest mockTestTest = new MockTestTest(test_id, "1000", "numbers1.png",test_name);
                                VideoApplication.selectedMockTestTest= mockTestTest;

                                Intent startIntent = new Intent(getApplicationContext(), StartMockTestActivity.class);
                                startActivity(startIntent);
                                break;
                            }
                            case "video":{
                                str = questions.get(position).getStudy2();
                                String course_name = str.substring(0,str.indexOf(","));

                                str= questions.get(position).getStudy2();
                                String url=str.substring(str.indexOf(",") + 1);

                                intent = new Intent(getApplicationContext(), TargetVideoActivity.class);
                                intent.putExtra("COURSE_NAME",course_category);
                                intent.putExtra("COURSE_SUBJECT",course_name);
                                intent.putExtra("URL",url);
                                startActivity(intent);
                                break;
                            }
                        }
                        break;
                    }

                    case 3:{
                        switch(questions.get(position).getStudy3Type()){
                            case "mocktest":{
                                str = questions.get(position).getStudy3();
                                String test_name = str.substring(0,str.indexOf(","));

                                str= questions.get(position).getStudy3();
                                String test_id=str.substring(str.indexOf(",") + 1);

                                MockTestTest mockTestTest = new MockTestTest(test_id, "1000", "numbers1.png",test_name);
                                VideoApplication.selectedMockTestTest= mockTestTest;

                                Intent startIntent = new Intent(getApplicationContext(), StartMockTestActivity.class);
                                startActivity(startIntent);
                                break;
                            }
                            case "video":{
                                str = questions.get(position).getStudy3();
                                String course_name = str.substring(0,str.indexOf(","));

                                str= questions.get(position).getStudy3();
                                String url=str.substring(str.indexOf(",") + 1);

                                intent = new Intent(getApplicationContext(), TargetVideoActivity.class);
                                intent.putExtra("COURSE_NAME",course_category);
                                intent.putExtra("COURSE_SUBJECT",course_name);
                                intent.putExtra("URL",url);
                                startActivity(intent);
                                break;
                            }
                        }
                        break;
                    }

                    case 4:{
                        switch(questions.get(position).getStudy4Type()){
                            case "mocktest":{
                                str = questions.get(position).getStudy4();
                                String test_name = str.substring(0,str.indexOf(","));

                                str= questions.get(position).getStudy4();
                                String test_id=str.substring(str.indexOf(",") + 1);

                                MockTestTest mockTestTest = new MockTestTest(test_id, "1000", "numbers1.png",test_name);
                                VideoApplication.selectedMockTestTest= mockTestTest;

                                Intent startIntent = new Intent(getApplicationContext(), StartMockTestActivity.class);
                                startActivity(startIntent);
                                break;
                            }
                            case "video":{
                                str = questions.get(position).getStudy4();
                                String course_name = str.substring(0,str.indexOf(","));

                                str= questions.get(position).getStudy4();
                                String url=str.substring(str.indexOf(",") + 1);

                                intent = new Intent(getApplicationContext(), TargetVideoActivity.class);
                                intent.putExtra("COURSE_NAME",course_category);
                                intent.putExtra("COURSE_SUBJECT",course_name);
                                intent.putExtra("URL",url);
                                startActivity(intent);
                                break;
                            }
                        }
                        break;
                    }

                    case 5:{
                        switch(questions.get(position).getStudy5Type()){
                            case "mocktest":{
                                str = questions.get(position).getStudy5();
                                String test_name = str.substring(0,str.indexOf(","));

                                str= questions.get(position).getStudy5();
                                String test_id=str.substring(str.indexOf(",") + 1);

                                MockTestTest mockTestTest = new MockTestTest(test_id, "1000", "numbers1.png",test_name);
                                VideoApplication.selectedMockTestTest= mockTestTest;

                                Intent startIntent = new Intent(getApplicationContext(), StartMockTestActivity.class);
                                startActivity(startIntent);
                                break;
                            }
                            case "video":{
                                str = questions.get(position).getStudy5();
                                String course_name = str.substring(0,str.indexOf(","));

                                str= questions.get(position).getStudy5();
                                String url=str.substring(str.indexOf(",") + 1);

                                intent = new Intent(getApplicationContext(), TargetVideoActivity.class);
                                intent.putExtra("COURSE_NAME",course_category);
                                intent.putExtra("COURSE_SUBJECT",course_name);
                                intent.putExtra("URL",url);
                                startActivity(intent);
                                break;
                            }
                        }
                        break;
                    }


                    case 6:{
                        switch(questions.get(position).getStudy6Type()) {
                            case "mocktest": {
                                str = questions.get(position).getStudy6();
                                String test_name = str.substring(0, str.indexOf(","));

                                str = questions.get(position).getStudy6();
                                String test_id = str.substring(str.indexOf(",") + 1);

                                MockTestTest mockTestTest = new MockTestTest(test_id, "1000", "numbers1.png", test_name);
                                VideoApplication.selectedMockTestTest = mockTestTest;

                                Intent startIntent = new Intent(getApplicationContext(), StartMockTestActivity.class);
                                startActivity(startIntent);
                                break;
                            }
                            case "video": {
                                str = questions.get(position).getStudy6();
                                String course_name = str.substring(0, str.indexOf(","));

                                str = questions.get(position).getStudy6();
                                String url = str.substring(str.indexOf(",") + 1);

                                intent = new Intent(getApplicationContext(), TargetVideoActivity.class);
                                intent.putExtra("COURSE_NAME", course_category);
                                intent.putExtra("COURSE_SUBJECT", course_name);
                                intent.putExtra("URL", url);
                                startActivity(intent);
                                break;
                            }
                        }
                        break;
                    }

                }*/

            }

            @Override
            public void onLongClicked(int position) {
                // callback performed on click
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(PreparationHLContentActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {
                        //Toast.makeText(PreparationContentActivity.this, "Hey There!", Toast.LENGTH_SHORT).show();
                      

                    }
                })
        );


        String prep_category_code = getIntent().getStringExtra("PREP_CATEGORY_CODE");
        String prep_category_header = getIntent().getStringExtra("PREP_CATEGORY_HEADER");
        //prep_category_code="GK";
        prep_content_header.setText(prep_category_header);
        call = apiService.fetchPrepHLContent(prep_category_code);
        if(MyUtil.checkConnectivity(getApplicationContext())) {

            MyUtil.showProgressDialog(this);
            fetchPrepContentList();
        }
        else{
            Toast.makeText(this, "Sorry. There is no internet connection!", Toast.LENGTH_SHORT).show();
        }
    }



    private void fetchPrepContentList() {
        call.enqueue(new Callback<RetrofitPrepHLContentList>() {
            @Override
            public void onResponse(Call<RetrofitPrepHLContentList> call, Response<RetrofitPrepHLContentList> response) {
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
            public void onFailure(Call<RetrofitPrepHLContentList> call, Throwable t) {
            }
        });
    }


    private void processRows(String study, String studyType){

                switch(studyType){
                    case "mocktest":{
                        str = study;
                        String test_name = str.substring(0,str.indexOf(","));

                        str= study;
                        String test_id=str.substring(str.indexOf(",") + 1);

                        MockTestTest mockTestTest = new MockTestTest(test_id, "1000", "numbers1.png",test_name);
                        VideoApplication.selectedMockTestTest= mockTestTest;

                        Intent startIntent = new Intent(getApplicationContext(), StartMockTestActivity.class);
                        startActivity(startIntent);
                        break;
                    }
                    case "video":{
                        str = study;
                        String course_name = str.substring(0,str.indexOf(","));

                        str= study;
                        String url=str.substring(str.indexOf(",") + 1);

                        Intent intent = new Intent(getApplicationContext(), TargetVideoActivity.class);
                        intent.putExtra("COURSE_NAME",course_category);
                        intent.putExtra("COURSE_SUBJECT",course_name);
                        intent.putExtra("URL",url);
                        startActivity(intent);
                        break;
                    }
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
}
