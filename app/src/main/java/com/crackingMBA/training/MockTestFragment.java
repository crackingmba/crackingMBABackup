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

import com.crackingMBA.training.adapter.RetrofitMockTestAdapter;
import com.crackingMBA.training.pojo.RetrofitMockTest;
import com.crackingMBA.training.pojo.RetrofitMockTestList;
import com.crackingMBA.training.restAPI.MockTestAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.util.RecyclerItemClickListener;
import com.crackingMBA.training.util.SectionEnum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockTestFragment extends Fragment{
View rootView;
    LayoutInflater inflater;
    ViewGroup container; Button mock_test_dashboard_btn;
    MockTestAPIService apiService;
    RecyclerView recyclerView;
    RetrofitMockTestAdapter adapter;
    List<RetrofitMockTest> mocktests = new ArrayList<>();
    Call<RetrofitMockTestList> call;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        rootView = inflater.inflate(R.layout.fragment_mocktest_startup, container, false);

        apiService = RestClient.getClient().create(MockTestAPIService.class);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.mocktestFragmentRecyclerView123);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RetrofitMockTestAdapter(mocktests, R.layout.mocktest_analysis_layout, getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //Toast.makeText(getActivity(), "Ouch! clicked this!", Toast.LENGTH_SHORT).show();
                        //Intent forumpostIntent = new Intent(getApplicationContext(), ForumCommentsActivity.class);
                        //startActivity(forumpostIntent);
                    }
                })
        );
        call = apiService.fetchMockTests("1");
        fetchQuestionList();

/*        mock_test_dashboard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MockTestAnalysisActivity.class);
                startActivity(intent);
            }
        });*/

        return rootView;
    }

    private void fetchQuestionList() {
        MyUtil.showProgressDialog(getActivity());
        call.enqueue(new Callback<RetrofitMockTestList>() {

            @Override
            public void onResponse(Call<RetrofitMockTestList> call, Response<RetrofitMockTestList> response) {
                MyUtil.hideProgressDialog();

                if(response.body()==null){
                    //Toast.makeText(getApplicationContext(), "There are no posts in this category!", Toast.LENGTH_SHORT).show();

                    if(mocktests.size()>0){
                        mocktests.clear();
                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "There are no posts in this category!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    mocktests.addAll(response.body().getMockTestScores());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RetrofitMockTestList> call, Throwable t) {
                //Log.e(TAG, "Got error : " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mocktests.size()>0){
            mocktests.clear();
            adapter.notifyDataSetChanged();
        }

    }

}
