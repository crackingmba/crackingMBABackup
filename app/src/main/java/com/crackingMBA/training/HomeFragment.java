package com.crackingMBA.training;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.LeaderboardAdapter;
import com.crackingMBA.training.pojo.RetrofitLeaderboardContent;
import com.crackingMBA.training.pojo.RetrofitLeaderboardList;
import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.restAPI.LeaderboardAPIService;
import com.crackingMBA.training.restAPI.LeaderboardAPIService2;
import com.crackingMBA.training.restAPI.NoticeBoardURLAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.MyUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment{
    View rootView;
    LeaderboardAdapter adapter, adapter2;
    List<RetrofitLeaderboardContent> questions = new ArrayList<>();
    List<RetrofitLeaderboardContent> questions2 = new ArrayList<>();
    Call<RetrofitLeaderboardList> call123, call2;
    LeaderboardAPIService apiService;
    String snap_exam_id="215", snap_exam_name="SNAP Challenge Test", snap_exam_analysis_url="", xat_exam_id="169", xat_exam_name="XAT Challenge Test", xat_exam_analysis_url="";
    ImageView imageView;

    private static String TAG = "HomeFragment";
    //LinearLayout home_fragment_cat_layout,home_fragment_iift_layout, home_fragment_snap_layout, home_fragment_xat_layout;
    Button exam_details_1, exam_details_2, exam_analysis_1, exam_analysis_2;
    public static final String apk_version="2.8.21";
    public static String server_apk_version="2.8.21";
    String img_url;
    NoticeBoardURLAPIService enrollment_apiService;
    ImageView home_fragment_img; TextView home_fragment_leaderboard_1_tv, home_fragment_leaderboard_2_tv;
    RecyclerView leaderboardHomeFragmentRecyclerView1, leaderboardHomeFragmentRecyclerView2;
    String video_url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home1, container, false);
        /*home_fragment_cat_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_cat_layout);
        home_fragment_iift_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_iift_layout);
        home_fragment_snap_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_snap_layout);
        home_fragment_snap_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_snap_layout);
        home_fragment_xat_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_xat_layout);*/
        exam_details_1=(Button)rootView.findViewById(R.id.exam_details_1);
        exam_details_2=(Button)rootView.findViewById(R.id.exam_details_2);
        exam_analysis_1=(Button)rootView.findViewById(R.id.exam_analysis_1);
        exam_analysis_2=(Button)rootView.findViewById(R.id.exam_analysis_2);
        imageView=(ImageView)rootView.findViewById(R.id.offline_img);
        //home_fragment_img=(ImageView)rootView.findViewById(R.id.home_fragment_img);
        home_fragment_leaderboard_1_tv=(TextView)rootView.findViewById(R.id.home_fragment_leaderboard_1_tv);
        home_fragment_leaderboard_2_tv=(TextView)rootView.findViewById(R.id.home_fragment_leaderboard_2_tv);


        Typeface custom_font=Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Regular.ttf");
        home_fragment_leaderboard_1_tv.setTypeface(custom_font);
        home_fragment_leaderboard_2_tv.setTypeface(custom_font);

        if(MyUtil.checkConnectivity(getContext())) {
            imageView.setVisibility(View.GONE);
            enrollment_apiService = RestClient.getClient().create(NoticeBoardURLAPIService.class);
            enrollment_apiService.getNoticeBoardVideoURL(apk_version).enqueue(new Callback<RetrofitPostResponse>() {
                @Override
                public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {
                    //MyUtil.hideProgressDialog();
                    RetrofitPostResponse retrofitPostResponse = response.body();

                    if(retrofitPostResponse.getResponse().equals("0")) {

                    }else{

                        String temp_str=retrofitPostResponse.getResponse().toString();
                        String str = temp_str;
                        //Toast.makeText(getContext(), "Got the server repponse = "+str, Toast.LENGTH_SHORT).show();
                        //img_url = str.substring(0,str.indexOf(","));
                        //first chunk
                        String first_chunk= str.substring(0,str.indexOf(","));
                        String temp_str_1 =str.substring(str.indexOf(",") + 1);

                        snap_exam_id = first_chunk.substring(0,first_chunk.indexOf("+"));
                        first_chunk=first_chunk.substring(first_chunk.indexOf("+") + 1);
                        snap_exam_name= first_chunk.substring(0,first_chunk.indexOf("+"));
                        snap_exam_analysis_url=first_chunk.substring(first_chunk.indexOf("+") + 1);
                        //Toast.makeText(getContext(), "Exam id= "+snap_exam_id+" Exam Name="+snap_exam_name+" Exam URL="+snap_exam_analysis_url, Toast.LENGTH_SHORT).show();
                        home_fragment_leaderboard_1_tv.setText(snap_exam_name);

                        String second_chunk=temp_str_1.substring(0,temp_str_1.indexOf(","));
                        String third_chunk =temp_str_1.substring(temp_str_1.indexOf(",") + 1);

                        xat_exam_id =second_chunk.substring(0,second_chunk.indexOf("+"));
                        second_chunk=second_chunk.substring(second_chunk.indexOf("+") + 1);
                        xat_exam_name= second_chunk.substring(0,second_chunk.indexOf("+"));
                        xat_exam_analysis_url=second_chunk.substring(second_chunk.indexOf("+") + 1);
                        home_fragment_leaderboard_2_tv.setText(xat_exam_name);

                        //Toast.makeText(getContext(), "Exam id= "+xat_exam_id+" Exam Name="+xat_exam_name+" Exam URL="+xat_exam_analysis_url, Toast.LENGTH_SHORT).show();

                        server_apk_version =third_chunk;
                        //Toast.makeText(getContext(), "Server APK version= "+server_apk_version, Toast.LENGTH_SHORT).show();

                        if(questions.size()>0){
                            questions.clear();
                            adapter.notifyDataSetChanged();
                            //Toast.makeText(getContext(), "Calling Fetch Question List again", Toast.LENGTH_SHORT).show();
                        }

                        if(questions2.size()>0){
                            questions2.clear();
                            adapter2.notifyDataSetChanged();
                            //Toast.makeText(getContext(), "Calling Fetch Question List again", Toast.LENGTH_SHORT).show();
                        }


                        if(apk_version.equals(server_apk_version)){
                            apiService = RestClient.getClient().create(LeaderboardAPIService.class);
                            leaderboardHomeFragmentRecyclerView1 = (RecyclerView)rootView.findViewById(R.id.leaderboardHomeFragmentRecyclerView1);
                            leaderboardHomeFragmentRecyclerView1 .setNestedScrollingEnabled(false);
                            leaderboardHomeFragmentRecyclerView1 .setLayoutManager(new LinearLayoutManager(getContext()));

                            adapter = new LeaderboardAdapter(questions, R.layout.retrofit_leaderboard_layout, getContext());
                            leaderboardHomeFragmentRecyclerView1 .setAdapter(adapter);

                            call123 = apiService.fetchLeaderboard(snap_exam_id);
                            fetchPrepContentList();

                            leaderboardHomeFragmentRecyclerView2 = (RecyclerView)rootView.findViewById(R.id.leaderboardHomeFragmentRecyclerView2);
                            leaderboardHomeFragmentRecyclerView2 .setNestedScrollingEnabled(false);
                            leaderboardHomeFragmentRecyclerView2 .setLayoutManager(new LinearLayoutManager(getContext()));

                            adapter2 = new LeaderboardAdapter(questions2, R.layout.retrofit_leaderboard_layout, getContext());
                            leaderboardHomeFragmentRecyclerView2.setAdapter(adapter2);

                            call2 = apiService.fetchLeaderboard(xat_exam_id);
                            fetchPrepContentList2();



                            //well and good
                            //Picasso.with(getContext())
                            //      .load(img_url)
                            //    .into(home_fragment_img);
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("There is a new update of this app available on play store. Please update your app. Thanks!")
                                    .setPositiveButton("UPDATE FROM PLAY STORE!", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
                                            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                                            // To count with Play market backstack, After pressing back button,
                                            // to taken back to our application, we need to add following flags to intent.
                                            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                            try {
                                                startActivity(goToMarket);
                                            } catch (ActivityNotFoundException e) {
                                                startActivity(new Intent(Intent.ACTION_VIEW,
                                                        Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
                                            }

                                        }
                                    })
                                    .setCancelable(false);

                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("ALERT! New Update Available");
                            alert.show();
                        }


                    }

                }

                @Override
                public void onFailure(Call<RetrofitPostResponse> call, Throwable t) {
                }
            });
        }
        else{
            Toast.makeText(getContext(), "Sorry. There is no internet connection!", Toast.LENGTH_SHORT).show();
            imageView.setVisibility(View.VISIBLE);
        }


        //if(MyUtil.checkConnectivity(getContext())) {


/*        home_fragment_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DailyDoseVideoActivity.class);
                intent.putExtra("DAILY_DOSE_VIDEO_URL", video_url);
                startActivity(intent);
            }
        });*/

        View.OnClickListener examOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.exam_details_1:{
                        //ViewPager viewPager=(ViewPager)getActivity().findViewById(R.id.container);
                        //viewPager.setCurrentItem(1, true);
                        Intent intent= new Intent(getContext(), MiniTestActivity.class);
                        intent.putExtra("MINI_TEST_EXAM_ID",snap_exam_id);
                        intent.putExtra("MINI_TEST_NAME",snap_exam_name);
                        startActivity(intent);
                        break;
                    }

                    case R.id.exam_details_2:{
                        Intent intent= new Intent(getContext(), MiniTestActivity.class);
                        intent.putExtra("MINI_TEST_EXAM_ID",xat_exam_id);
                        intent.putExtra("MINI_TEST_NAME",xat_exam_name);
                        startActivity(intent);
                        break;
                    }

                    case R.id.exam_analysis_1:{
                        Intent intent= new Intent(getContext(), DailyDoseVideoActivity.class);
                        intent.putExtra("DAILY_DOSE_TEST_NAME", snap_exam_name);
                        intent.putExtra("DAILY_DOSE_VIDEO_URL", snap_exam_analysis_url);
                        startActivity(intent);
                        break;
                    }

                    case R.id.exam_analysis_2:{
                        Intent intent= new Intent(getContext(), DailyDoseVideoActivity.class);
                        intent.putExtra("DAILY_DOSE_TEST_NAME", xat_exam_name);
                        intent.putExtra("DAILY_DOSE_VIDEO_URL",xat_exam_analysis_url);
                        startActivity(intent);
                        break;
                    }

/*                    case R.id.high_5:{
                        Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
                        }
                        break;
                    }*/

/*                    case R.id.share_feedback:{
                        //Display the share feedback page here
                        Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                        intent.putExtra("FEEDBACK_HEADER", "APP Feedback!");
                        intent.putExtra("FEEDBACK_TEXT", "Before clicking that Uninstall button, give us a chance to hear if you were expecting something and that isnt available on this app! We will try to implement your requirements!");
                        startActivity(intent);
                    }*/

                }
            }
        };

        exam_details_1.setOnClickListener(examOnClickListener);
        exam_details_2.setOnClickListener(examOnClickListener);
        exam_analysis_1.setOnClickListener(examOnClickListener);
        exam_analysis_2.setOnClickListener(examOnClickListener);
        //home_fragment_xat_layout.setOnClickListener(examOnClickListener);
        //high_5.setOnClickListener(examOnClickListener);
        //share_feedback.setOnClickListener(examOnClickListener);

        return rootView;
    }

    private void fetchPrepContentList() {
        call123.enqueue(new Callback<RetrofitLeaderboardList>() {
            @Override
            public void onResponse(Call<RetrofitLeaderboardList> call, Response<RetrofitLeaderboardList> response) {
                //MyUtil.hideProgressDialog();

                if(response.body() == null){
                    return;
                }else{
                    questions.addAll(response.body().getQuestions());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RetrofitLeaderboardList> call, Throwable t) {

            }
        });
    }

    private void fetchPrepContentList2() {
        call2.enqueue(new Callback<RetrofitLeaderboardList>() {
            @Override
            public void onResponse(Call<RetrofitLeaderboardList> call, Response<RetrofitLeaderboardList> response) {
                //MyUtil.hideProgressDialog();

                if(response.body() == null){
                    return;
                }else{
                    questions2.addAll(response.body().getQuestions());
                    adapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RetrofitLeaderboardList> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
