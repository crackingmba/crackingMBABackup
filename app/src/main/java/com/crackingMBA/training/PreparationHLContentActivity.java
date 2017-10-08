package com.crackingMBA.training;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.PreparationHLContentAdapter;
import com.crackingMBA.training.interfaces.ClickListener;
import com.crackingMBA.training.pojo.MockTestTest;
import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.pojo.RetrofitPrepHLContent;
import com.crackingMBA.training.pojo.RetrofitPrepHLContentList;
import com.crackingMBA.training.restAPI.LoginAPIService;
import com.crackingMBA.training.restAPI.PrepHLContentAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.restAPI.UserEnrollmentAPIService;
import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.util.RecyclerItemClickListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
    TextView prep_content_header;
    SharedPreferences prefs;
    SharedPreferences.Editor ed;
    UserEnrollmentAPIService enrollment_apiService;
    String temp_message="";
    String dialog_header="";


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
        prep_content_header=(TextView)findViewById(R.id.prep_content_header889);

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
                    case "XAT":{
                        course_category="SNAP 2017 Preparation Course";
                        break;
                    }

                }

                switch(course_category){
                    case "CATPREP1":{
                        temp_message="To access all premium content in this section, please enroll for the Focus 'CAT' course for Rs 300 only";
                        dialog_header="ENROLL for Focus 'CAT' course";

                        break;
                    }

                    case "IIFTPREP1":{
                        temp_message="To access all content in this section, please enroll for the Focus 'IIFT' course for Rs 300 only";
                        dialog_header="ENROLL for Focus 'IIFT' course";
                        break;
                    }

                    case "SNAPPREP1":{
                        temp_message="To access all content in this section, please enroll for the Focus 'SNAP' course for Rs 300 only";
                        dialog_header="ENROLL for Focus 'SNAP' course";
                        break;
                    }

                    case "XATPREP":{
                        temp_message="To access all content in this section, please enroll for the Focus 'XAT' course for Rs 300 only";
                        dialog_header="ENROLL for Focus 'XAT' course";
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
                    }
                })
        );


        String prep_category_code = getIntent().getStringExtra("PREP_CATEGORY_CODE");
        String prep_category_name = getIntent().getStringExtra("PREP_CATEGORY_NAME");
        String prep_category_header = getIntent().getStringExtra("PREP_CATEGORY_HEADER");
        //prep_category_code="GK";
        prep_content_header.setText(prep_category_header);
        call = apiService.fetchPrepHLContent(prep_category_code, prep_category_name);
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
                    case "text":{
                        str = study;
                        String course_name = str.substring(0,str.indexOf(","));

                        str= study;
                        String webview_url=str.substring(str.indexOf(",") + 1);
                        Intent intent = new Intent(getApplicationContext(), ViewPDFDetailsActivity.class);
                        intent.putExtra("COURSE_NAME",course_category);
                        intent.putExtra("COURSE_SUBJECT",course_name);
                        intent.putExtra("WEBVIEW_URL",webview_url);
                        startActivity(intent);
                        break;
                    }

                    case "pmocktest":{
                        displayPaymentOptions(study, studyType, course_category);
                        break;
                    }
                    case "pvideo":{
                        displayPaymentOptions(study, studyType, course_category);
                        break;
                    }
                    case "ptext":{
/*                        str = study;
                        String course_name = str.substring(0,str.indexOf(","));

                        str= study;
                        String webview_url=str.substring(str.indexOf(",") + 1);
                        Intent intent = new Intent(getApplicationContext(), ViewPDFDetailsActivity.class);
                        intent.putExtra("COURSE_NAME",course_category);
                        intent.putExtra("COURSE_SUBJECT",course_name);
                        intent.putExtra("WEBVIEW_URL",webview_url);
                        startActivity(intent);*/


                        displayPaymentOptions(study, studyType, course_category);//testing some text

                        break;
                    }
                }

    }

    public int displayPaymentOptions(final String study, final String studyType, final String course_name){
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed = prefs.edit();

        Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);
        final String email = prefs.getString("emailofUser", "");
        //name_of_user = prefs.getString("nameofUser", "");


        if(isUserLoggedIn){
            Toast.makeText(this, "User is logged in", Toast.LENGTH_SHORT).show();

            //Now that the user is logged in, check if the user has enrolled
            //make a call to the remote server to check this

            //MyUtil.showProgressDialog(PreparationHLContentActivity.this);
            enrollment_apiService = RestClient.getClient().create(UserEnrollmentAPIService.class);
            enrollment_apiService.validateUserEnrollment(email, course_name).enqueue(new Callback<RetrofitPostResponse>() {
                @Override
                public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {
                    //MyUtil.hideProgressDialog();
                    RetrofitPostResponse retrofitPostResponse = response.body();

                    if(retrofitPostResponse.getResponse().equals("0")) {
                        //Toast.makeText(PreparationHLContentActivity.this, "Sorry! You are not enrolled", Toast.LENGTH_SHORT).show();
                        //display the form
                        //display the dialog box to Enroll or Cancel
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(PreparationHLContentActivity.this);
                        builder.setMessage(temp_message)
                                .setCancelable(false)
                                .setPositiveButton("ENROLL NOW", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Intent intent = new Intent(PreparationHLContentActivity.this, LoginActivity.class);
                                        //startActivity(intent);

                                        Intent intent = new Intent(PreparationHLContentActivity.this, CourseEnrollmentActivity.class);
                                        intent.putExtra("PREP_CATEGORY_CODE",course_category);
                                        startActivity(intent);



                                    }
                                })
                                .setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Toast.makeText(SupportGuidanceActivity.this, "Going to Login screen", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                });

                        //Creating dialog box
                        android.support.v7.app.AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle(dialog_header);
                        alert.show();

                    }else{
                        //String name_of_user=retrofitPostResponse.getResponse();
                        //String user_id = name_of_user.substring( 0, name_of_user.indexOf(","));
                        //String name_of_the_user = name_of_user.substring(name_of_user.indexOf(",")+1, name_of_user.length());
                        //Toast.makeText(PreparationHLContentActivity.this, "You are enrolled!", Toast.LENGTH_SHORT).show();
                        //display the actual content
                        switch(studyType){
                            case "pmocktest":{
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

                            case "pvideo":{
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

                            case "ptext":{
                                str = study;
                                String course_name = str.substring(0,str.indexOf(","));

                                str= study;
                                String webview_url=str.substring(str.indexOf(",") + 1);
                                Intent intent = new Intent(getApplicationContext(), ViewPDFDetailsActivity.class);
                                intent.putExtra("COURSE_NAME",course_category);
                                intent.putExtra("COURSE_SUBJECT",course_name);
                                intent.putExtra("WEBVIEW_URL",webview_url);
                                startActivity(intent);
                                break;
                            }

                        }
                        /*

                            SharedPreferences.Editor ed = prefs.edit();
                            ed.putBoolean("isUserLoggedIn", true);
                            ed.putString("nameofUser",name_of_the_user);
                            ed.putString("emailofUser",email);
                            ed.putString("userID",user_id);
                            ed.commit();
                            finish();
*/

                    }
                }

                @Override
                public void onFailure(Call<RetrofitPostResponse> call, Throwable t) {
                }
            });


        }else{
            //Toast.makeText(this, "User is not logged in", Toast.LENGTH_SHORT).show();





            //Display the Dialog Here
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setMessage(temp_message)
                    .setCancelable(false)
                    .setPositiveButton("ALREADY ENROLLED?  LOGIN NOW", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(PreparationHLContentActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("NOT ENROLLED YET? ENROLL NOW", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Toast.makeText(SupportGuidanceActivity.this, "Going to Login screen", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PreparationHLContentActivity.this, CourseEnrollmentActivity.class);
                            intent.putExtra("PREP_CATEGORY_CODE",course_category);
                            startActivity(intent);

                        }
                    })
                    .setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Toast.makeText(SupportGuidanceActivity.this, "Going to Login screen", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });

            //Creating dialog box
            android.support.v7.app.AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle(dialog_header);
            alert.show();


        }
        return 0;
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
