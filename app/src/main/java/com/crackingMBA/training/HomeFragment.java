package com.crackingMBA.training;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

//import com.bumptech.glide.Glide;
import com.crackingMBA.training.pojo.VideoDataObject;

import java.util.ArrayList;

/**
 * Created by MSK on 24-01-2017.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    RecyclerView quantRecyclerView;
    boolean isMock;

    View rootView;
    private static String TAG = "HomeFragment";
    LinearLayout catLayout, xatLayout, snapLayout, iiftLayout, matLayout, motivationLayout;
    LinearLayout home_fragment_iift_gk_layout,home_fragment_snap_gk_layout, home_fragment_gk_layout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home1, container, false);
        quantRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_recycler_view);
        quantRecyclerView.setHasFixedSize(true);
        catLayout=(LinearLayout)rootView.findViewById(R.id.catLayout);
        xatLayout=(LinearLayout)rootView.findViewById(R.id.xatLayout);
        snapLayout=(LinearLayout)rootView.findViewById(R.id.snapLayout);
        iiftLayout=(LinearLayout)rootView.findViewById(R.id.iiftLayout);
        matLayout=(LinearLayout)rootView.findViewById(R.id.matLayout);
        motivationLayout=(LinearLayout)rootView.findViewById(R.id.motivationLayout);
        home_fragment_iift_gk_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_iift_gk_layout);
        home_fragment_snap_gk_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_snap_gk_layout);
        home_fragment_gk_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_general_gk_layout);


        View.OnClickListener examOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.catLayout:{
                        Intent examDetails = new Intent(getActivity(), ExamDetailsActivity.class);
                        examDetails.putExtra("MBA_EXAM_CODE", "CAT");
                        startActivity(examDetails);
                        break;
                    }
                    case R.id.xatLayout:{
                        Intent examDetails = new Intent(getActivity(), ExamDetailsActivity.class);
                        examDetails.putExtra("MBA_EXAM_CODE", "XAT");
                        startActivity(examDetails);
                        break;
                    }
                    case R.id.snapLayout:{
                        Intent examDetails = new Intent(getActivity(), ExamDetailsActivity.class);
                        examDetails.putExtra("MBA_EXAM_CODE", "SNAP");
                        startActivity(examDetails);
                        break;
                    }
                    case R.id.iiftLayout:{
                        Intent examDetails = new Intent(getActivity(), ExamDetailsActivity.class);
                        examDetails.putExtra("MBA_EXAM_CODE", "IIFT");
                        startActivity(examDetails);
                        break;
                    }
                    case R.id.matLayout:{
                        Intent examDetails = new Intent(getActivity(), ExamDetailsActivity.class);
                        examDetails.putExtra("MBA_EXAM_CODE", "NMAT");
                        startActivity(examDetails);
                        break;
                    }

     /*               case R.id.homeVocabGame:{
                        Intent vocabGame = new Intent(getActivity(), VocabGameActivity.class);
                        vocabGame.putExtra("VOCAB_GAME", "NMAT");
                        startActivity(vocabGame);
                        break;
                    }*/

                    case R.id.motivationLayout:{
                        Intent motivationIntent = new Intent(getActivity(), MotivationVideosActivity.class);
                        startActivity(motivationIntent);
                        break;
                    }

                    case R.id.home_fragment_iift_gk_layout:{
                        Intent intent = new Intent(getActivity(), PreparationContentActivity.class);
                        intent.putExtra("PREP_CATEGORY_CODE","IIFTGK");
                        intent.putExtra("PREP_CATEGORY_HEADER","IIFT Previous Year GK");
                        startActivity(intent);
                        break;
                    }

                    case R.id.home_fragment_snap_gk_layout:{
                        Intent intent = new Intent(getActivity(), PreparationContentActivity.class);
                        intent.putExtra("PREP_CATEGORY_CODE","SNAPGK");
                        intent.putExtra("PREP_CATEGORY_HEADER","SNAP Previous Year GK");
                        startActivity(intent);
                        break;
                    }

                    case R.id.home_fragment_general_gk_layout:{
                        Intent intent = new Intent(getActivity(), PreparationContentActivity.class);
                        intent.putExtra("PREP_CATEGORY_CODE","GK");
                        intent.putExtra("PREP_CATEGORY_HEADER","GK Study Material");
                        startActivity(intent);
                        break;
                    }

                }
            }
        };

        catLayout.setOnClickListener(examOnClickListener);
        xatLayout.setOnClickListener(examOnClickListener);
        snapLayout.setOnClickListener(examOnClickListener);
        iiftLayout.setOnClickListener(examOnClickListener);
        matLayout.setOnClickListener(examOnClickListener);
        //vocabgameLayout.setOnClickListener(examOnClickListener);
        motivationLayout.setOnClickListener(examOnClickListener);
        home_fragment_iift_gk_layout.setOnClickListener(examOnClickListener);
        home_fragment_snap_gk_layout.setOnClickListener(examOnClickListener);
        home_fragment_gk_layout.setOnClickListener(examOnClickListener);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    private ArrayList<VideoDataObject> getDataSet() {
           return null;
    }


    private ArrayList<VideoDataObject> getDataSet1() {


       return null;
                }


        @Override
        public void onClick (View view){



        }
}
