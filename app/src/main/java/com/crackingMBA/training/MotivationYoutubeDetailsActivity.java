package com.crackingMBA.training;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.pojo.MockTestTest;
import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.restAPI.UserEnrollmentAPIService;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MotivationYoutubeDetailsActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener{
    private static final int RECOVERY_REQUEST = 1;

    String motivation_video_url;
    Button motivateYT_details_btn;ImageView imgView;
    TextView motivation_tv, motivation_yt_focus_tv;
    //TextView videoName, videoDescription;
    String sectionName, exam_name_text;
    SharedPreferences prefs;
    SharedPreferences.Editor ed;
    UserEnrollmentAPIService enrollment_apiService;
    int isUserEnrolled=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation_youtube_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView motivation_yt_focus_tv = (TextView) findViewById(R.id.motivation_yt_focus_tv);
        ImageView imgView = (ImageView) findViewById(R.id.motivation_yt_focus_img);


        sectionName = getIntent().getStringExtra("EXAM_NAME");
        exam_name_text= getIntent().getStringExtra("EXAM_NAME_TEXT");
        switch (sectionName){
            case "CAT":
            {
                Drawable myDrawable = getResources().getDrawable(R.drawable.focus_cat);
                imgView.setImageDrawable(myDrawable);
                motivation_yt_focus_tv.setText(exam_name_text);
                motivation_video_url = "dwH-dAEYgyM";
                //videoName.setText("'Focus' Quant");
                //videoDescription.setText("The details for Quant Section Go Here!");
                break;
            }
            case "IIFT":
            {
                Drawable myDrawable = getResources().getDrawable(R.drawable.focus_iift1);
                imgView.setImageDrawable(myDrawable);
                motivation_yt_focus_tv.setText(exam_name_text);
                motivation_video_url = "9AzWC0TZDWo";
                //videoName.setText("'Focus' Verbal");
                //videoDescription.setText("The details for Verbal Section Go Here!");
                break;
            }
            case "SNAP":
            {
                Drawable myDrawable = getResources().getDrawable(R.drawable.focus_snap);
                imgView.setImageDrawable(myDrawable);
                motivation_yt_focus_tv.setText(exam_name_text);
                motivation_video_url = "cJG66qbLfHU";
                //videoName.setText("'Focus' DI & LR");
                //videoDescription.setText("The details for DI & LR Section Go Here!");
                break;
            }
            case "XAT":
            {
                Drawable myDrawable = getResources().getDrawable(R.drawable.focus_xat);
                imgView.setImageDrawable(myDrawable);
                motivation_yt_focus_tv.setText(exam_name_text);
                motivation_video_url = "cJG66qbLfHU";
                break;
            }
        }

        //videoName=(TextView)findViewById(R.id.motivation_youtube_video_name);
        //videoDescription=(TextView)findViewById(R.id.motivation_youtube_video_description);

        motivateYT_details_btn = (Button)findViewById(R.id.motivateYT_details_btn);

        View.OnClickListener examOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.motivateYT_details_btn:{
                        Intent intent = new Intent(MotivationYoutubeDetailsActivity.this, PreparationContentActivity.class);

                        switch (sectionName){
                            case "CAT":
                            {
                                intent.putExtra("PREP_CATEGORY_CODE","CATPREP1");
                                intent.putExtra("PREP_CATEGORY_HEADER","Focus RC Preparation Course");
                                break;
                            }
                            case "IIFT":
                            {
                                intent.putExtra("PREP_CATEGORY_CODE","IIFTPREP1");
                                intent.putExtra("PREP_CATEGORY_HEADER","Focus QUANT Preparation Course");
                                break;
                            }
                            case "SNAP":
                            {
                                intent.putExtra("PREP_CATEGORY_CODE","SNAPPREP1");
                                intent.putExtra("PREP_CATEGORY_HEADER","Focus GK Preparation Course");
                                break;
                            }
                            case "XAT":
                            {
                                intent.putExtra("PREP_CATEGORY_CODE","XATPREP");
                                intent.putExtra("PREP_CATEGORY_HEADER","Focus XAT Preparation Course");
                                break;
                            }
                        }


                        loadUserEnrollmentDetails();
                        startActivity(intent);
                        //ViewPager viewPager=(ViewPager)getActivity().findViewById(R.id.container);
                        //viewPager.setCurrentItem(1, true);
                        break;
                    }

                }
            }
        };

        motivateYT_details_btn.setOnClickListener(examOnClickListener);



        YouTubePlayerSupportFragment frag;
        frag = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_motivation_video_fragment);
        frag.initialize(MyConfig.YOUTUBE_API_KEY, this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void loadUserEnrollmentDetails(){
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);

        //Toast.makeText(MotivationYoutubeDetailsActivity.this, "Loading User Enrollment details", Toast.LENGTH_SHORT).show();

        if(isUserLoggedIn){
            final String email = prefs.getString("emailofUser", "");
            String whetherSpecificCourseEnrolled="notqueried";

            switch(sectionName){
                case "CAT":{
                    whetherSpecificCourseEnrolled = prefs.getString("whetherCATcourseEnrolled", "notqueried");
                    break;
                }
                case "IIFT":{
                    whetherSpecificCourseEnrolled = prefs.getString("whetherIIFTcourseEnrolled", "notqueried");
                    break;
                }
                case "SNAP":{
                    whetherSpecificCourseEnrolled = prefs.getString("whetherSNAPcourseEnrolled", "notqueried");
                    break;
                }
                case "XAT":{
                    whetherSpecificCourseEnrolled = prefs.getString("whetherXATcourseEnrolled", "notqueried");
                    break;
                }
             }



            if(whetherSpecificCourseEnrolled.equals("notqueried")){
                final SharedPreferences.Editor ed = prefs.edit();
                //then query it from the server and record the details
                enrollment_apiService = RestClient.getClient().create(UserEnrollmentAPIService.class);
                enrollment_apiService.validateUserEnrollment(email, sectionName).enqueue(new Callback<RetrofitPostResponse>() {
                    @Override
                    public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {
                        //MyUtil.hideProgressDialog();
                        RetrofitPostResponse retrofitPostResponse = response.body();

                        if(retrofitPostResponse.getResponse().equals("0")) {
                            Toast.makeText(MotivationYoutubeDetailsActivity.this, "User is not enrolled for"+sectionName, Toast.LENGTH_SHORT).show();
                            switch(sectionName){
                                case "CAT":{
                                    ed.putString("whetherCATcourseEnrolled","queried0");
                                    ed.commit();
                                    break;
                                }
                                case "IIFT":{
                                    ed.putString("whetherIIFTcourseEnrolled","queried0");
                                    ed.commit();
                                    break;
                                }
                                case "SNAP":{
                                    ed.putString("whetherSNAPcourseEnrolled","queried0");
                                    ed.commit();
                                    break;
                                }
                                case "XAT":{
                                    ed.putString("whetherXATcourseEnrolled","queried0");
                                    ed.commit();
                                    break;
                                }
                            }

                        }else{
                            Toast.makeText(MotivationYoutubeDetailsActivity.this, "User is enrolled for"+sectionName, Toast.LENGTH_SHORT).show();
                            switch(sectionName){
                                case "CAT":{
                                    ed.putString("whetherCATcourseEnrolled","queried1");
                                    ed.commit();
                                    break;
                                }
                                case "IIFT":{
                                    ed.putString("whetherIIFTcourseEnrolled","queried1");
                                    ed.commit();
                                    break;
                                }
                                case "SNAP":{
                                    ed.putString("whetherSNAPcourseEnrolled","queried1");
                                    ed.commit();
                                    break;
                                }
                                case "XAT":{
                                    ed.putString("whetherXATcourseEnrolled","queried1");
                                    ed.commit();
                                    break;
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<RetrofitPostResponse> call, Throwable t) {
                    }
                });


            }

        }else{
            //do nothing much for now
        }

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(motivation_video_url); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
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
