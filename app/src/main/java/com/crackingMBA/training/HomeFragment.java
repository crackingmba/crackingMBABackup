package com.crackingMBA.training;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

//import com.bumptech.glide.Glide;


/**
 * Created by MSK on 24-01-2017.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    RecyclerView quantRecyclerView;
    View rootView;
    private static String TAG = "HomeFragment";
    LinearLayout online_sessions_layout, study_material_layout, mock_tests_layout, videos_layout, forum_layout, motivationLayout;
    LinearLayout home_fragment_iift_gk_layout,home_fragment_snap_gk_layout, home_fragment_gk_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home1, container, false);
        quantRecyclerView = (RecyclerView) rootView.findViewById(R.id.video_recycler_view);
        quantRecyclerView.setHasFixedSize(true);
        online_sessions_layout=(LinearLayout)rootView.findViewById(R.id.online_sessions_layout);
        study_material_layout=(LinearLayout)rootView.findViewById(R.id.study_material_layout);
        mock_tests_layout=(LinearLayout)rootView.findViewById(R.id.mock_tests_layout);
        videos_layout=(LinearLayout)rootView.findViewById(R.id.videos_layout);
        forum_layout=(LinearLayout)rootView.findViewById(R.id.forum_layout);
        motivationLayout=(LinearLayout)rootView.findViewById(R.id.motivationLayout);
        home_fragment_iift_gk_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_iift_gk_layout);
        home_fragment_snap_gk_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_snap_gk_layout);
        home_fragment_gk_layout=(LinearLayout)rootView.findViewById(R.id.home_fragment_general_gk_layout);

        View.OnClickListener examOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.online_sessions_layout:{
                        Intent examDetails = new Intent(getActivity(), ExamDetailsActivity.class);
                        examDetails.putExtra("MBA_EXAM_CODE", "SESSIONS");
                        startActivity(examDetails);
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

        online_sessions_layout.setOnClickListener(examOnClickListener);
        study_material_layout.setOnClickListener(examOnClickListener);
        mock_tests_layout.setOnClickListener(examOnClickListener);
        videos_layout.setOnClickListener(examOnClickListener);
        forum_layout.setOnClickListener(examOnClickListener);
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


        @Override
        public void onClick (View view){



        }
}
