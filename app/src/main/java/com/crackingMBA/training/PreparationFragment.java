package com.crackingMBA.training;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class PreparationFragment extends Fragment implements View.OnClickListener{

    View rootView;
    LayoutInflater inflater;
    ViewGroup container;
    CardView VocabCardView, GKCardView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;

        String clicked = VideoApplication.videoSelected.getVideoType()==null ? "startup" : VideoApplication.videoSelected.getVideoType();
        if(clicked.equals("startup")){
            VideoApplication.videoSelected.setVideoType("startup");

            rootView = inflater.inflate(R.layout.fragment_preparation_startup, container, false);

            VocabCardView = (CardView)rootView.findViewById(R.id.VocabCardView);
            GKCardView = (CardView)rootView.findViewById(R.id.GKCardView);
            //XATCardView = (CardView)rootView.findViewById(R.id.XATCardView);

            VocabCardView.setOnClickListener(this);
            GKCardView.setOnClickListener(this);
            //XATCardView.setOnClickListener(this);
        }

        return rootView;

    }
    @Override
    public void onResume() {
        super.onResume();
    }

    Intent motivationVideoDetails;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.VocabCardView: {
                if (HomeFragment.apk_version.equals(HomeFragment.server_apk_version)) {
                    Intent intent = new Intent(getContext(), PreparationContentActivity.class);
                    intent.putExtra("PREP_CATEGORY_CODE","VOCAB");
                    intent.putExtra("PREP_CATEGORY_HEADER","VOCAB Flash Cards");
                    startActivity(intent);
                } else {
                    //Launch the dialog to update from server
                    launch_update_dialog();

                }
                break;
            }

            case R.id.GKCardView: {
                    if (HomeFragment.apk_version.equals(HomeFragment.server_apk_version)) {
                        Intent intent = new Intent(getContext(), PreparationContentActivity.class);
                        intent.putExtra("PREP_CATEGORY_CODE","GK");
                        intent.putExtra("PREP_CATEGORY_HEADER","GK Flash Cards");
                        startActivity(intent);
                    }else{
                        launch_update_dialog();
                    }
                }
                break;
            /*case R.id.XATCardView: {
                    if (HomeFragment.apk_version.equals(HomeFragment.server_apk_version)) {
                        motivationVideoDetails = new Intent(getContext(), MotivationYoutubeDetailsActivity.class);
                        motivationVideoDetails.putExtra("EXAM_NAME", "XAT");
                        motivationVideoDetails.putExtra("EXAM_NAME_TEXT", "Wondering how to prepare for XAT 2017 and XAT GK section? Sign up for our '60 Day XAT Challenge' to get a 60 day study plan with study material, mock tests, tips, formulae and previous year solved papers to improve your scores!");
                        startActivity(motivationVideoDetails);
                    }else{
                        launch_update_dialog();
                    }
                }
                break;*/
            default:
                break;
        }


    }

    private void launch_update_dialog(){
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


}
