package com.crackingMBA.training;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.util.SectionEnum;

/**
 * Created by MSK on 24-01-2017.
 */
public class MockTestFragment extends Fragment{
View rootView;
    LayoutInflater inflater;
    ViewGroup container; Button mock_test_dashboard_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        rootView = inflater.inflate(R.layout.fragment_mocktest_startup, container, false);

        mock_test_dashboard_btn=(Button)rootView.findViewById(R.id.mock_test_dashboard_btn);

        mock_test_dashboard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MockTestAnalysisActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

}
