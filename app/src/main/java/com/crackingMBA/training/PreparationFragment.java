package com.crackingMBA.training;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.MediaController;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.HashMap;

import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.util.MyUtil;

/**
 * Created by MSK on 24-01-2017.
 */
public class PreparationFragment extends Fragment implements View.OnClickListener{

    private static String TAG = "PreparationFragment";
    View rootView;
    LayoutInflater inflater;
    ViewGroup container;
    boolean isMock;

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

            TableRow quantRow = (TableRow) rootView.findViewById(R.id.quantrow1);
            TableRow diRow = (TableRow) rootView.findViewById(R.id.dirow);
            TableRow verbalRow = (TableRow) rootView.findViewById(R.id.verbalrow);
            quantRow.setOnClickListener(this);
            diRow.setOnClickListener(this);
            verbalRow.setOnClickListener(this);
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

        Intent dashboardIntent=new Intent(getActivity(),VideoSubCategoryActivity.class);
        switch (v.getId()){
            case R.id.quantrow1:
                VideoApplication.sectionClicked="quant";
                dashboardIntent.putExtra("sectionaName","quant");
                break;
            case R.id.dirow:
                VideoApplication.sectionClicked="dilr";
                dashboardIntent.putExtra("sectionaName","dilr");
                break;
            case R.id.verbalrow:
                VideoApplication.sectionClicked="verbal";
                dashboardIntent.putExtra("sectionaName","verbal");
                break;
            default:
                Log.d(TAG,"Unknown button clicked..");
                break;
        }
        startActivity(dashboardIntent);

    }


}
