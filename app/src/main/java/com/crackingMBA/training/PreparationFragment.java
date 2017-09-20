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


/**
 * Created by MSK on 24-01-2017.
 */
public class PreparationFragment extends Fragment implements View.OnClickListener{

    private static String TAG = "PreparationFragment";
    View rootView;
    LayoutInflater inflater;
    ViewGroup container;
    CardView CATCardView, IIFTCardView, SNAPCardView, XATCardView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;

        Log.d(TAG, VideoApplication.videoSelected.getVideoType()+" is selected");
        String clicked = VideoApplication.videoSelected.getVideoType()==null ? "startup" : VideoApplication.videoSelected.getVideoType();
        if(clicked.equals("startup")){
            VideoApplication.videoSelected.setVideoType("startup");

            rootView = inflater.inflate(R.layout.fragment_preparation_startup, container, false);

            CATCardView = (CardView)rootView.findViewById(R.id.CATCardView);
            IIFTCardView = (CardView)rootView.findViewById(R.id.IIFTCardView);
            SNAPCardView = (CardView)rootView.findViewById(R.id.SNAPCardView);
            XATCardView = (CardView)rootView.findViewById(R.id.XATCardView);

            CATCardView.setOnClickListener(this);
            IIFTCardView.setOnClickListener(this);
            SNAPCardView.setOnClickListener(this);
            XATCardView.setOnClickListener(this);
        }

        return rootView;

    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG,"Clicked a week.."+v.getId());

        //Intent dashboardIntent=new Intent(getActivity(),VideoSubCategoryActivity.class);
        //Intent intent=new Intent(getActivity(),MotivationYoutubeDetailsActivity.class);
        switch (v.getId()){
            case R.id.CATCardView:
                //VideoApplication.sectionClicked="quant";
                //intent.putExtra("sectionName","quant");
                //Intent intent = new Intent(getActivity(), PreparationHLContentActivity.class);
                Intent motivationVideoDetails = new Intent(getContext(), MotivationYoutubeDetailsActivity.class);
                motivationVideoDetails.putExtra("EXAM_NAME", "CAT");
                motivationVideoDetails.putExtra("EXAM_NAME_TEXT", "'Spare your pizza craving today for a bigger one later!'. At 499, for the price of a medium pan pizza, you can now crack CAT!");
                //motivationVideoDetails.putExtra("MOTIVATION_VIDEO_URL", "ueLbSYiSWCs");
                //motivationVideoDetails.putExtra("MOTIVATION_VIDEO_NAME", "Why you need to slog your *** off and crack MBA this year!");
                //motivationVideoDetails.putExtra("MOTIVATION_VIDEO_DESCRIPTION", "This is a brief video to help you pull up your socks and get going. If you see a dip in your performance in the mock tests or are just not getting the right kind of motivation, this video should help you pull up your sagging spirits and enable you to keep your tempo high!");
                startActivity(motivationVideoDetails);
                break;
                case R.id.IIFTCardView:
                    motivationVideoDetails = new Intent(getContext(), MotivationYoutubeDetailsActivity.class);
                    motivationVideoDetails.putExtra("EXAM_NAME", "IIFT");
                    motivationVideoDetails.putExtra("EXAM_NAME_TEXT", "'Let your hard work, blood, toil and sweat earn you a pizza treat!'. At 499, for the price of a medium pan pizza, you can now crack IIFT!");
                    //motivationVideoDetails.putExtra("MOTIVATION_VIDEO_URL", "ueLbSYiSWCs");
                    //motivationVideoDetails.putExtra("MOTIVATION_VIDEO_NAME", "Why you need to slog your *** off and crack MBA this year!");
                    //motivationVideoDetails.putExtra("MOTIVATION_VIDEO_DESCRIPTION", "This is a brief video to help you pull up your socks and get going. If you see a dip in your performance in the mock tests or are just not getting the right kind " +
                      //      "of motivation, this video should help you pull up your sagging spirits and enable you to keep your tempo high!");
                    startActivity(motivationVideoDetails);
                //VideoApplication.sectionClicked="dilr";
                //intent.putExtra("sectionName","dilr");
                break;
            case R.id.SNAPCardView:
                motivationVideoDetails = new Intent(getContext(), MotivationYoutubeDetailsActivity.class);
                motivationVideoDetails.putExtra("EXAM_NAME", "SNAP");
                motivationVideoDetails.putExtra("EXAM_NAME_TEXT", "'Invest in a medium pizza to yield a jumbo size pizza in a few months!'. At 499, for the price of a medium pan pizza, you can now crack SNAP!");
                startActivity(motivationVideoDetails);
                //VideoApplication.sectionClicked="verbal";
                //intent.putExtra("sectionName","verbal");
                break;
            case R.id.XATCardView:
                motivationVideoDetails = new Intent(getContext(), MotivationYoutubeDetailsActivity.class);
                motivationVideoDetails.putExtra("EXAM_NAME", "XAT");
                motivationVideoDetails.putExtra("EXAM_NAME_TEXT", "'Sow this single pizza slice today to reap a big fat pizza in a few months!'. At 499, for the price of a medium pan pizza, you can now crack XAT!");
                startActivity(motivationVideoDetails);
                break;
            default:
                Log.d(TAG,"Unknown button clicked..");
                break;
        }


    }


}
