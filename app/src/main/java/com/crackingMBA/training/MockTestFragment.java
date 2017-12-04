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
    ViewGroup container;
    MockTestAPIService apiService;
    RecyclerView recyclerView;
    RetrofitMockTestAdapter adapter;
    List<RetrofitMockTest> mocktests = new ArrayList<>();
    Call<RetrofitMockTestList> call;
    static int k=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        rootView = inflater.inflate(R.layout.fragment_mocktest_startup, container, false);

        //Toast.makeText(getContext(), "We are in OnCreate", Toast.LENGTH_SHORT).show();
        apiService = RestClient.getClient().create(MockTestAPIService.class);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.mocktestFragmentRecyclerView123);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RetrofitMockTestAdapter(mocktests, R.layout.mocktest_analysis_layout, getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        String temp_str=mocktests.get(position).getTestName().toString();
                        //String exam_name = temp_str.substring(0, temp_str.indexOf(" "));

                        String temp_exam_id=mocktests.get(position).getExamID().toString();
                        String temp_exam_type=mocktests.get(position).getExamType().toString();
                        String temp_analysis_url=mocktests.get(position).getURL().toString();

                        //Toast.makeText(getActivity(), exam_name, Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(getContext(), MiniTestActivity.class);
                        intent.putExtra("MINI_TEST_EXAM_ID",temp_exam_id);
                        intent.putExtra("MINI_TEST_NAME",temp_str);
                        intent.putExtra("MINI_TEST_EXAM_TYPE",temp_exam_type);
                        intent.putExtra("MINI_TEST_ANALYSIS_URL",temp_analysis_url);
                        startActivity(intent);
                    }
                })
        );
        call = apiService.fetchMockTests("1");
        k++;
        if(k<=1){
            if(mocktests.size()>0){
                mocktests.clear();
                adapter.notifyDataSetChanged();
                //Toast.makeText(getContext(), "Flushing list and creating list again", Toast.LENGTH_SHORT).show();
                fetchQuestionList(); //calling the list again
            }else{
                //Toast.makeText(getContext(), "Calling Fetch Question List first time", Toast.LENGTH_SHORT).show();
                fetchQuestionList(); //calling the list again
            }
        }else{
            k=0;
            if(mocktests.size()>0){
                mocktests.clear();
                adapter.notifyDataSetChanged();
                //Toast.makeText(getContext(), "Calling Fetch Question List again", Toast.LENGTH_SHORT).show();
                fetchQuestionList(); //calling the list again
            }else{
                //Toast.makeText(getContext(), "Calling Fetch Question List again", Toast.LENGTH_SHORT).show();
                fetchQuestionList(); //calling the list again
            }
        }

        return rootView;
    }

    private void fetchQuestionList() {
        MyUtil.showProgressDialog(getActivity());
        call.enqueue(new Callback<RetrofitMockTestList>() {

            @Override
            public void onResponse(Call<RetrofitMockTestList> call, Response<RetrofitMockTestList> response) {
                MyUtil.hideProgressDialog();

                if(response.body()==null){
                    if(mocktests.size()>0){
                        mocktests.clear();
                    }
                    adapter.notifyDataSetChanged();
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

    }

}
