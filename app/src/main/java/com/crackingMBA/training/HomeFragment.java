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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.restAPI.NoticeBoardURLAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.MyUtil;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment{
    View rootView;
    private static String TAG = "HomeFragment";
    LinearLayout home_fragment_cat_layout,home_fragment_iift_layout, home_fragment_snap_layout, home_fragment_xat_layout;
    Button high_5, share_feedback;
    public static final String apk_version="2.8.21";
    public static String server_apk_version="2.8.21";
    String img_url;
    NoticeBoardURLAPIService enrollment_apiService;
    ImageView home_fragment_img; TextView home_fragment_daily_dose_tv;
    String video_url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home1, container, false);
        home_fragment_cat_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_cat_layout);
        home_fragment_iift_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_iift_layout);
        home_fragment_snap_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_snap_layout);
        home_fragment_snap_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_snap_layout);
        home_fragment_xat_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_xat_layout);
        high_5=(Button)rootView.findViewById(R.id.high_5);
        share_feedback=(Button)rootView.findViewById(R.id.share_feedback);
        home_fragment_img=(ImageView)rootView.findViewById(R.id.home_fragment_img);
        home_fragment_daily_dose_tv=(TextView)rootView.findViewById(R.id.home_fragment_daily_dose_tv);

        Typeface custom_font=Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Regular.ttf");
        home_fragment_daily_dose_tv.setTypeface(custom_font);



        if(MyUtil.checkConnectivity(getContext())) {
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
                        img_url = str.substring(0,str.indexOf(","));
                        String sub_str =str.substring(str.indexOf(",") + 1);
                        video_url =sub_str.substring(0,sub_str.indexOf(","));
                        server_apk_version =sub_str.substring(sub_str.indexOf(",") + 1);


                        if(apk_version.equals(server_apk_version)){
                            //well and good
                            Picasso.with(getContext())
                                    .load(img_url)
                                    .into(home_fragment_img);
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
        }

        home_fragment_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DailyDoseVideoActivity.class);
                intent.putExtra("DAILY_DOSE_VIDEO_URL", video_url);
                startActivity(intent);
            }
        });

        View.OnClickListener examOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
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

        home_fragment_cat_layout.setOnClickListener(examOnClickListener);
        home_fragment_iift_layout.setOnClickListener(examOnClickListener);
        home_fragment_snap_layout.setOnClickListener(examOnClickListener);
        home_fragment_xat_layout.setOnClickListener(examOnClickListener);
        //home_fragment_xat_layout.setOnClickListener(examOnClickListener);
        high_5.setOnClickListener(examOnClickListener);
        share_feedback.setOnClickListener(examOnClickListener);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
