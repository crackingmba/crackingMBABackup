package com.crackingMBA.training;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.crackingMBA.training.util.SectionEnum;

/**
 * Created by MSK on 24-01-2017.
 */
public class MockTestFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    LinearLayoutManager mLayoutManager;
    boolean isMock;

    View rootView;
    private static String TAG = "MockTestFragment";
    String url = "mocktest_topics_service_url";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        isMock = pref.getBoolean("isMock",false);
        rootView = inflater.inflate(R.layout.fragment_mocktest_startup, container, false);

        TableRow quantRow = (TableRow) rootView.findViewById(R.id.quantrow);
        TableRow diRow = (TableRow) rootView.findViewById(R.id.dirow);
        TableRow verbalRow = (TableRow) rootView.findViewById(R.id.verbalrow);
        quantRow.setOnClickListener(this);
        diRow.setOnClickListener(this);
        verbalRow.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public void onClick(View v) {
        Log.d(TAG,"Clicked a week.."+v.getId());
        Intent mockTestTopicIntent=new Intent(getActivity(),MockTestTopicsActivity.class);
        switch (v.getId()){
            case R.id.quantrow:
                VideoApplication.sectionClicked = SectionEnum.quant.key;
                break;
            case R.id.dirow:
                VideoApplication.sectionClicked = SectionEnum.dilr.key;
                break;
            case R.id.verbalrow:
                VideoApplication.sectionClicked = SectionEnum.verbal.key;
                break;
            default:
                Log.d(TAG,"Unknown button clicked..");
                break;
        }
        Log.d(TAG,"Section Clicked : "+VideoApplication.sectionClicked);
        startActivity(mockTestTopicIntent);
    }

}
