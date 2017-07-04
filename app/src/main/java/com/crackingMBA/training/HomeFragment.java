package com.crackingMBA.training;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.DILRHomeVideoViewAdapter;
import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.DownloadViewAdapter;
import com.crackingMBA.training.adapter.ExamAdapter;
import com.crackingMBA.training.adapter.QuantHomeVideoViewAdapter;
import com.crackingMBA.training.adapter.VerbalHomeVideoViewAdapter;
import com.crackingMBA.training.db.DBHelper;
import com.crackingMBA.training.pojo.Exam;
import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;
import com.crackingMBA.training.pojo.VideoListModel;
import com.crackingMBA.training.util.MyUtil;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MSK on 24-01-2017.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    RecyclerView quantRecyclerView;
    RecyclerView dilrRecyclerView;
    RecyclerView verbalRecyclerView;
    RecyclerView.Adapter quantAdapter;
    RecyclerView.Adapter dilrAdapter;
    RecyclerView.Adapter verbalAdapter;
    boolean isMock;

    Button gotoquantBtn;
    Button gotodi;
    Button gotolatest;
    View rootView;
    private static String TAG = "HomeFragment";
    private View quantProgressView;
    private View dilrProgressView;
    private View verbalProgressView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home1, container, false);
        //gotoquantBtn = (Button) rootView.findViewById(R.id.gotoquant3);
        //gotoquantBtn.setOnClickListener(this);
        //gotodi = (Button) rootView.findViewById(R.id.gotodi3);
        //gotodi.setOnClickListener(this);
        //gotolatest = (Button) rootView.findViewById(R.id.gotolatest3);
        //gotolatest.setOnClickListener(this);
        quantRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_recycler_view);
        //dilrRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_recycler_view2);
        //verbalRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_recycler_view3);
        quantRecyclerView.setHasFixedSize(true);
        //dilrRecyclerView.setHasFixedSize(true);
        //verbalRecyclerView.setHasFixedSize(true);
        //quantProgressView = rootView.findViewById(R.id.recentquatvideos);
        //dilrProgressView = rootView.findViewById(R.id.recentdiandlrvideos);
        //verbalProgressView = rootView.findViewById(R.id.recentverbalvideos);
        getDataSet();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    private ArrayList<VideoDataObject> getDataSet() {

            List<Exam> myVidList= new ArrayList<Exam>();
            Exam tempVid=new Exam();
            tempVid.setExam_name("CAT 2017");
            tempVid.setExam_description("Entrance test for admission to PGDM programmes at IIMs and other institutes");
            tempVid.setExam_sections("Quant, DI&LR, Verbal");
            tempVid.setExam_notification_date("In July");
            tempVid.setExam_date("Exam Date: November or December 2017");
            tempVid.setExam_img("cat2017");
            myVidList.add(tempVid);

            tempVid=new Exam();
            tempVid.setExam_name("IIFT 2017");
            tempVid.setExam_description("Entrance test for admission to post graduate management programmes at IIFT institutes");
            tempVid.setExam_sections("Quant, DI&LR, Verbal");
            tempVid.setExam_notification_date("In July");
            tempVid.setExam_date("Exam Date: November 2017");
            tempVid.setExam_img("iift2017");
            myVidList.add(tempVid);

            tempVid=new Exam();
            tempVid.setExam_name("MAT 2017");
            tempVid.setExam_description("Entrance test for admission to post graduate management programmes at various BSchools in India");
            tempVid.setExam_sections("Quant, DI&LR, Verbal");
            tempVid.setExam_notification_date("In July");
            tempVid.setExam_date("Exam Date: Paper Based Test on 3rd September 2017. Computer Based from 9th September 2017 onwards");
            tempVid.setExam_img("mat2017");
            myVidList.add(tempVid);

            tempVid=new Exam();
            tempVid.setExam_name("SNAP 2017");
            tempVid.setExam_description("Entrance test for admission to MBA programmes at Symbiosis institutes");
            tempVid.setExam_sections("Quant, DI&LR, Verbal");
            tempVid.setExam_notification_date("In July");
            tempVid.setExam_date("Exam Date: December 2017");
            tempVid.setExam_img("snap2017");
            myVidList.add(tempVid);

            tempVid=new Exam();
            tempVid.setExam_name("XAT 2018");
            tempVid.setExam_description("Entrance test for admission to post graduate management programmes at Xavier institutes such as XLRI");
            tempVid.setExam_sections("Quant, DI&LR, Verbal");
            tempVid.setExam_notification_date("In July");
            tempVid.setExam_date("Exam Date: January 2018");
            tempVid.setExam_img("xat2018");
            myVidList.add(tempVid);

            try {
                    if (myVidList.size() == 0)
                        rootView.findViewById(R.id.home_latest_quant_noVideos).setVisibility(View.VISIBLE);
                    else {
                        rootView.findViewById(R.id.home_latest_quant_noVideos).setVisibility(View.GONE);

                        RecyclerView.ItemDecoration quantItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
                        LinearLayoutManager quantLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        quantRecyclerView.setLayoutManager(quantLayoutManager);
                        quantAdapter = new ExamAdapter(myVidList);

                        quantRecyclerView.setAdapter(quantAdapter);
                        quantRecyclerView.addItemDecoration(quantItemDecoration);
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }


        return null;
    }


    private ArrayList<VideoDataObject> getDataSet1() {

        Log.d(TAG, "isMock?" + isMock);
        isMock = false;
        if (isMock) {
            //  return populateMockGetVideoList();
        } else {
            Log.d(TAG, "In else block");
            final ArrayList<VideoDataObject> results = new ArrayList<VideoDataObject>();

            if(MyUtil.checkConnectivity(getContext())) {
                try {
                    //showProgress(true, quantProgressView);

                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get(CrackingConstant.GETVIDEOLIST_SERVICE_URL__QUANT_SERVICE_URL, null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d(TAG, "Response is : " + response);
                            Gson gson = new Gson();
                            VideoListModel videoListModel = gson.fromJson(response, VideoListModel.class);
                            if (videoListModel != null) {
                                List<VideoList> quant = videoListModel.getVideoList();


                                List<Exam> myVidList= new ArrayList<Exam>();
                                Exam tempVid=new Exam();
                                tempVid.setExam_name("CAT 2017");
                                tempVid.setExam_description("Entrance test for admission to PGDM programmes at IIMs and other institutes");
                                tempVid.setExam_sections("Quant, DI&LR, Verbal");
                                tempVid.setExam_notification_date("In July");
                                tempVid.setExam_date("Exam Date: November or December 2017");
                                tempVid.setExam_img("cat2017");
                                myVidList.add(tempVid);

                                tempVid=new Exam();
                                tempVid.setExam_name("IIFT 2017");
                                tempVid.setExam_description("Entrance test for admission to post graduate management programmes at IIFT institutes");
                                tempVid.setExam_sections("Quant, DI&LR, Verbal");
                                tempVid.setExam_notification_date("In July");
                                tempVid.setExam_date("Exam Date: November 2017");
                                tempVid.setExam_img("iift2017");
                                myVidList.add(tempVid);

                                tempVid=new Exam();
                                tempVid.setExam_name("MAT 2017");
                                tempVid.setExam_description("Entrance test for admission to post graduate management programmes at various BSchools in India");
                                tempVid.setExam_sections("Quant, DI&LR, Verbal");
                                tempVid.setExam_notification_date("In July");
                                tempVid.setExam_date("Exam Date: Paper Based Test on 3rd September 2017. Computer Based from 9th September 2017 onwards");
                                tempVid.setExam_img("mat2017");
                                myVidList.add(tempVid);

                                tempVid=new Exam();
                                tempVid.setExam_name("SNAP 2017");
                                tempVid.setExam_description("Entrance test for admission to MBA programmes at Symbiosis institutes");
                                tempVid.setExam_sections("Quant, DI&LR, Verbal");
                                tempVid.setExam_notification_date("In July");
                                tempVid.setExam_date("Exam Date: December 2017");
                                tempVid.setExam_img("snap2017");
                                myVidList.add(tempVid);
                                tempVid=new Exam();
                                tempVid.setExam_name("XAT 2018");
                                tempVid.setExam_description("Entrance test for admission to post graduate management programmes at Xavier institutes such as XLRI");
                                tempVid.setExam_sections("Quant, DI&LR, Verbal");
                                tempVid.setExam_notification_date("In July");
                                tempVid.setExam_date("Exam Date: January 2018");
                                tempVid.setExam_img("xat2018");
                                myVidList.add(tempVid);

                                try {
                                    if (null != quant) {
                                        if (quant.size() == 0)
                                            rootView.findViewById(R.id.home_latest_quant_noVideos).setVisibility(View.VISIBLE);
                                        else {
                                            rootView.findViewById(R.id.home_latest_quant_noVideos).setVisibility(View.GONE);

                                            //VideoApplication.allQuantVideos = quant;
                                            //VideoApplication.allQuantVideos = myVidList;
                                            RecyclerView.ItemDecoration quantItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
                                            LinearLayoutManager quantLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                            quantRecyclerView.setLayoutManager(quantLayoutManager);
                                            //quantAdapter = new QuantHomeVideoViewAdapter(quant);
                                            quantAdapter = new ExamAdapter(myVidList);

                                            quantRecyclerView.setAdapter(quantAdapter);
                                            quantRecyclerView.addItemDecoration(quantItemDecoration);
         /*                                   ((QuantHomeVideoViewAdapter) quantAdapter).setOnItemClickListener(
                                                    new QuantHomeVideoViewAdapter.MyClickListener() {
                                                        @Override
                                                        public void onItemClick(int position, View v) {
                                                            Log.d(TAG, "Clicked item at position : " + position);
                                                            VideoList vdo = VideoApplication.allQuantVideos.get(position);
                                                            vdo.setVideoSubCategory("video");
                                                            Log.d(TAG, "set with video..");
                                                            VideoApplication.videoList = vdo;
                                                            Intent weeksIntent = new Intent(getActivity(), TargetVideoActivity.class);
                                                            startActivity(weeksIntent);
                                                        }
                                                    }
                                            );*/
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            //showProgress(false, quantProgressView);

                        }

                        @Override
                        public void onFailure(int statusCode, Throwable error,
                                              String content) {
                            Log.d(TAG, "Status is " + statusCode + " and " + content);
                            if (statusCode == 404) {
                                Log.d(TAG, "Requested resource not found");
                            } else if (statusCode == 500) {
                                Log.d(TAG, "Something went wrong at server end");
                            } else {
                                Log.d(TAG, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]");
                            }
                            //showProgress(false, quantProgressView);
                        }

                    });

                    //dilr section
                } catch (Exception e) {
                    e.printStackTrace();
                }


                /*try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    showProgress(true, dilrProgressView);
                    client.get(CrackingConstant.HOME_TAB_GETVIDEOLIST__DILR_SERVICE_URL, null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d(TAG, "Response is : " + response);
                            Gson gson = new Gson();
                            VideoListModel videoListModel = gson.fromJson(response, VideoListModel.class);
                            if (videoListModel != null) {
                                List<VideoList> dr = videoListModel.getVideoList();

                                try {

                                    if (null != dr) {
                                        // gotodi.setVisibility(View.GONE);
                                        if (dr.size() == 0)
                                            rootView.findViewById(R.id.home_latest_dilr_noVideos).setVisibility(View.VISIBLE);
                                        else {
                                            rootView.findViewById(R.id.home_latest_dilr_noVideos).setVisibility(View.GONE);

                                            VideoApplication.allDilrVideos = dr;
                                            RecyclerView.ItemDecoration dilrItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
                                            LinearLayoutManager dilrLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                            dilrRecyclerView.setLayoutManager(dilrLayoutManager);
                                            dilrAdapter = new DILRHomeVideoViewAdapter(dr);
                                            dilrRecyclerView.setAdapter(dilrAdapter);
                                            dilrRecyclerView.addItemDecoration(dilrItemDecoration);
                                            ((DILRHomeVideoViewAdapter) dilrAdapter).setOnItemClickListener(
                                                    new DILRHomeVideoViewAdapter.MyClickListener() {
                                                        @Override
                                                        public void onItemClick(int position, View v) {
                                                            Log.d(TAG, "Clicked item at position : " + position);
                                                            VideoList vdo = VideoApplication.allDilrVideos.get(position);
                                                            vdo.setVideoSubCategory("video");
                                                            Log.d(TAG, "set with video..");
                                                            VideoApplication.videoList = vdo;
                                                            Intent weeksIntent = new Intent(getActivity(), TargetVideoActivity.class);
                                                            startActivity(weeksIntent);
                                                        }
                                                    }
                                            );
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();

                                }
                                showProgress(false, dilrProgressView);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    showProgress(false, dilrProgressView);
                }*/
                //verbal section
                /*try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    showProgress(true, verbalProgressView);
                    client.get(CrackingConstant.HOME_TAB_GETVIDEOLIST__VERBAL_SERVICE_URL, null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d(TAG, "Response is : " + response);
                            Gson gson = new Gson();
                            VideoListModel videoListModel = gson.fromJson(response, VideoListModel.class);
                            if (videoListModel != null) {
                                List<VideoList> verbal = videoListModel.getVideoList();

                                try {

                                    if (null != verbal) {
                                        //  gotolatest.setVisibility(View.GONE);
                                        if (verbal.size() == 0)
                                            rootView.findViewById(R.id.home_latest_other_noVideos).setVisibility(View.VISIBLE);
                                        else {
                                            rootView.findViewById(R.id.home_latest_other_noVideos).setVisibility(View.GONE);

                                            VideoApplication.allVerbalVideos = verbal;
                                            RecyclerView.ItemDecoration verbalItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
                                            LinearLayoutManager verbalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                            verbalRecyclerView.setLayoutManager(verbalLayoutManager);
                                            verbalAdapter = new VerbalHomeVideoViewAdapter(verbal);
                                            verbalRecyclerView.setAdapter(verbalAdapter);
                                            verbalRecyclerView.addItemDecoration(verbalItemDecoration);
                                            ((VerbalHomeVideoViewAdapter) verbalAdapter).setOnItemClickListener(
                                                    new VerbalHomeVideoViewAdapter.MyClickListener() {
                                                        @Override
                                                        public void onItemClick(int position, View v) {
                                                            Log.d(TAG, "Clicked item at position : " + position);
                                                            VideoList vdo = VideoApplication.allVerbalVideos.get(position);
                                                            vdo.setVideoSubCategory("video");
                                                            Log.d(TAG, "set with video..");
                                                            VideoApplication.videoList = vdo;
                                                            Intent weeksIntent = new Intent(getActivity(), TargetVideoActivity.class);
                                                            startActivity(weeksIntent);
                                                        }
                                                    }
                                            );
                                        }
                                    }
                                    showProgress(false, verbalProgressView);
                                } catch (Exception e) {

                                    Log.e(TAG, "Error occuring " + e.getMessage());
                                    showProgress(false, verbalProgressView);
                                }
                            }
                        }
                    });
                } catch (Exception
                        e) {
                    Log.e(TAG, "Error occuring " + e.getMessage());
                    showProgress(false, verbalProgressView);
                }*/

            }else{
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getContext(), R.string.no_internet, duration);
                toast.show();
               TextView textView=(TextView)rootView.findViewById(R.id.networkstatus);
                textView.setVisibility(View.VISIBLE);
                textView.setText(R.string.no_internet);
            }


                    }
        return null;
                }


        @Override
        public void onClick (View view){
           // Log.d(TAG, "Inonlicklistener" + view.getId());
            //TabLayout tabLayout;
            //Intent subsIntent;
            /*switch (view.getId()) {
                case R.id.gotoquant3:
                    Log.d(TAG, "selecting Quant section..");
                    subsIntent = new Intent(getActivity(), VideoSubCategoryActivity.class);
                    VideoApplication.sectionClicked = "quant";
                    subsIntent.putExtra("sectionSelected", "quant");
                    subsIntent.putExtra("headerTitle", "CAT 2017 Preparation Quant Section");
                    startActivity(subsIntent);
                    break;
                case R.id.gotodi3:
                    subsIntent = new Intent(getActivity(), VideoSubCategoryActivity.class);
                    Log.d(TAG, "selecting DI and LR section..");
                    VideoApplication.sectionClicked = "dilr";
                    subsIntent.putExtra("sectionSelected", "dilr");
                    subsIntent.putExtra("headerTitle", "CAT 2017 Preparation DI and LR Section");
                    startActivity(subsIntent);
                    break;
                case R.id.gotolatest3:
                    subsIntent = new Intent(getActivity(), VideoSubCategoryActivity.class);
                    Log.d(TAG, "selecting latest articles tab..");
                    VideoApplication.sectionClicked = "verbal";
                    subsIntent.putExtra("sectionSelected", "verbal");
                    subsIntent.putExtra("headerTitle", "CAT 2017 Preparation Verbal Section");
                    startActivity(subsIntent);
                    break;

                default:
                    Log.d(TAG, "Unknown button clicked..");

                    break;
            }*/


        }
    /**
     * Shows the progress UI and hides the login form.
     */
   /* @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show,final View showProgressSection) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);



            showProgressSection.setVisibility(show ? View.VISIBLE : View.GONE);
            showProgressSection.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    showProgressSection.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            showProgressSection.setVisibility(show ? View.VISIBLE : View.GONE);

        }
    }*/
}
