package com.crackingMBA.training;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.pojo.RetrofitForumComment;
import com.crackingMBA.training.pojo.RetrofitForumCommentList;
import com.crackingMBA.training.pojo.RetrofitNoticeBoard;
import com.crackingMBA.training.pojo.RetrofitNoticeBoardList;
import com.crackingMBA.training.restAPI.ForumCommentsAPIService;
import com.crackingMBA.training.restAPI.NoticeBoardAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.FileDownloader;
import com.crackingMBA.training.util.MyUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.bumptech.glide.Glide;


/**
 * Created by MSK on 24-01-2017.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    RecyclerView quantRecyclerView;
    View rootView;
    private static String TAG = "HomeFragment";
    //LinearLayout online_sessions_layout, study_material_layout, mock_tests_layout, videos_layout, forum_layout, motivationLayout;
    LinearLayout home_fragment_cat_layout,home_fragment_iift_layout, home_fragment_snap_layout, home_fragment_xat_layout;
    NoticeBoardAPIService apiService;
    List<RetrofitNoticeBoard> boards = new ArrayList<>();
    Call<RetrofitNoticeBoardList> call;
    TextView notice_board_tv1, notice_board_tv2, notice_board_tv3;
    Button high_5, share_feedback;
    public static final String apk_version="2.8.16";
    public static String server_apk_version;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home1, container, false);
        quantRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_recycler_view);
        quantRecyclerView.setHasFixedSize(true);
        //online_sessions_layout=(LinearLayout)rootView.findViewById(R.id.online_sessions_layout);
        //study_material_layout=(LinearLayout)rootView.findViewById(R.id.study_material_layout);
        //mock_tests_layout=(LinearLayout)rootView.findViewById(R.id.mock_tests_layout);
        //videos_layout=(LinearLayout)rootView.findViewById(R.id.videos_layout);
        //forum_layout=(LinearLayout)rootView.findViewById(R.id.forum_layout);
        //motivationLayout=(LinearLayout)rootView.findViewById(R.id.motivationLayout);
        home_fragment_cat_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_cat_layout);
        home_fragment_iift_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_iift_layout);
        home_fragment_snap_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_snap_layout);
        home_fragment_snap_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_snap_layout);
        home_fragment_xat_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_xat_layout);
        //home_fragment_xat_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_xat_layout);
        high_5=(Button)rootView.findViewById(R.id.high_5);
        share_feedback=(Button)rootView.findViewById(R.id.share_feedback);

        apiService = RestClient.getClient().create(NoticeBoardAPIService.class);
        call = apiService.fetchBoardList("recent");

        if(MyUtil.checkConnectivity(getContext())) {
            fetchBoardList();
        }
        else{
            Toast.makeText(getContext(), "Sorry. There is no internet connection!", Toast.LENGTH_SHORT).show();
        }

        View.OnClickListener examOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    /*case R.id.online_sessions_layout:{
                        break;
                    }
                    case R.id.study_material_layout:{
                        Intent examDetails = new Intent(getActivity(), ExamDetailsActivity.class);
                        examDetails.putExtra("MBA_EXAM_CODE", "XAT");
                        startActivity(examDetails);
                        break;
                    }
                    case R.id.mock_tests_layout:{
                        ViewPager viewPager=(ViewPager)getActivity().findViewById(R.id.container);
                        viewPager.setCurrentItem(2, true);
                        break;
                    }
                    case R.id.videos_layout:{
                        ViewPager viewPager=(ViewPager)getActivity().findViewById(R.id.container);
                        viewPager.setCurrentItem(1, true);
                        break;
                    }
                    case R.id.forum_layout:{
                        ViewPager viewPager=(ViewPager)getActivity().findViewById(R.id.container);
                        viewPager.setCurrentItem(3, true);
                        break;
                    }

                    case R.id.motivationLayout:{
                        Intent motivationIntent = new Intent(getActivity(), MotivationVideosActivity.class);
                        startActivity(motivationIntent);
                        break;
                    }*/
                    case R.id.home_fragment_iift_layout:{
                        ViewPager viewPager=(ViewPager)getActivity().findViewById(R.id.container);
                        viewPager.setCurrentItem(1, true);
                        break;
                    }

                    case R.id.home_fragment_snap_layout:{
                        ViewPager viewPager=(ViewPager)getActivity().findViewById(R.id.container);
                        viewPager.setCurrentItem(1, true);                        break;
                    }

                    case R.id.home_fragment_cat_layout:{
                        ViewPager viewPager=(ViewPager)getActivity().findViewById(R.id.container);
                        viewPager.setCurrentItem(1, true);
                        break;
                    }

                    case R.id.home_fragment_xat_layout:{
                        ViewPager viewPager=(ViewPager)getActivity().findViewById(R.id.container);
                        viewPager.setCurrentItem(1, true);
                        break;
                    }


                    /*case R.id.home_fragment_xat_layout:{
                        Intent intent = new Intent(getActivity(), PreparationContentActivity.class);
                        intent.putExtra("PREP_CATEGORY_CODE","XAT");
                        intent.putExtra("PREP_CATEGORY_HEADER","XAT 2018 Preparation");
                        startActivity(intent);
                        break;
                    }*/

                    case R.id.high_5:{
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
                    }

                    case R.id.share_feedback:{
                        //Display the share feedback page here
                        Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                        intent.putExtra("FEEDBACK_HEADER", "APP Feedback!");
                        intent.putExtra("FEEDBACK_TEXT", "Before clicking that Uninstall button, give us a chance to hear if you were expecting something and that isnt available on this app! We will try to implement your requirements!");
                        startActivity(intent);
                    }

                }
            }
        };

        //online_sessions_layout.setOnClickListener(examOnClickListener);
        //study_material_layout.setOnClickListener(examOnClickListener);
        //mock_tests_layout.setOnClickListener(examOnClickListener);
        //videos_layout.setOnClickListener(examOnClickListener);
        //forum_layout.setOnClickListener(examOnClickListener);
        //motivationLayout.setOnClickListener(examOnClickListener);
        home_fragment_cat_layout.setOnClickListener(examOnClickListener);
        home_fragment_iift_layout.setOnClickListener(examOnClickListener);
        home_fragment_snap_layout.setOnClickListener(examOnClickListener);
        home_fragment_xat_layout.setOnClickListener(examOnClickListener);
        //home_fragment_xat_layout.setOnClickListener(examOnClickListener);
        high_5.setOnClickListener(examOnClickListener);
        share_feedback.setOnClickListener(examOnClickListener);

        return rootView;
    }


    private void fetchBoardList() {
        MyUtil.showProgressDialog(getActivity());
        call.enqueue(new Callback<RetrofitNoticeBoardList>() {
            @Override
            public void onResponse(Call<RetrofitNoticeBoardList> call, Response<RetrofitNoticeBoardList> response) {
                MyUtil.hideProgressDialog();

                if(response.body() == null){
                    Toast.makeText(getContext(), "Oops! The data couldnt be fetched successfully!", Toast.LENGTH_SHORT).show();
                    //comments_comments_not_added.setVisibility(View.VISIBLE);
                    return;
                }else{
                    boards = response.body().getQuestions();
                    notice_board_tv1=(TextView)rootView.findViewById(R.id.notice_board_tv1);
                    notice_board_tv2=(TextView)rootView.findViewById(R.id.notice_board_tv2);
                    notice_board_tv3=(TextView)rootView.findViewById(R.id.notice_board_tv3);

                    server_apk_version=boards.get(0).toString();

                    if(boards.get(0).getAPK_Version().equals(apk_version)){
                        //thats ok
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("You are running an older version of this app. Download and install the latest update from Play Store")
                                .setCancelable(false)
                                .setPositiveButton("UPDATE NOW!", new DialogInterface.OnClickListener() {
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
                                .setNeutralButton("SOMETIME LATER!", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Toast.makeText(SupportGuidanceActivity.this, "Going to Login screen", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                });

                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("New update available on Play Store!!");
                        alert.show();
                    }

                    notice_board_tv1.setText(boards.get(0).getBoard_name());

                    if(boards.size()>1){
                        notice_board_tv2.setText(boards.get(1).getBoard_name());
                    }

                    if(boards.size()>2){
                        notice_board_tv3.setText(boards.get(2).getBoard_name());
                    }
                }
            }

            @Override
            public void onFailure(Call<RetrofitNoticeBoardList> call, Throwable t) {
                Toast.makeText(getContext(), "Oops! The data couldnt be fetched successfully!", Toast.LENGTH_SHORT).show();
                MyUtil.hideProgressDialog();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
    }

        @Override
        public void onClick (View view){

        }
}
