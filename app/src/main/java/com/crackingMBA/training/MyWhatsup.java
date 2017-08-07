package com.crackingMBA.training;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.RetrofitQuestionAdapter;
import com.crackingMBA.training.pojo.RetrofitQuestion;
import com.crackingMBA.training.pojo.RetrofitQuestionList;
import com.crackingMBA.training.restAPI.QuestionAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWhatsup extends Fragment implements AdapterView.OnItemSelectedListener {

    View rootView;

    private String TAG = MyWhatsup.class.getSimpleName();
    QuestionAPIService apiService;
    RecyclerView recyclerView;
    RetrofitQuestionAdapter adapter;
    List<RetrofitQuestion> questions = new ArrayList<>();
    Call<RetrofitQuestionList> call;
    int spinner_counter=0, callingActivityFlag=0;
    Spinner spinner_appln, spinner_prep;

    @Override
    public void onResume() {
        super.onResume();
        spinner_counter=0;
        if(callingActivityFlag>0){
            spinner_counter=1;
            callingActivityFlag=0;
        }
        //spinner_appln.setSelection(0);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_whatsup, container, false);

        apiService = RestClient.getClient().create(QuestionAPIService.class);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.questionListRecyclerView);

        spinner_appln = (Spinner)rootView.findViewById(R.id.spinner_appln);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                    R.array.exams_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_appln.setAdapter(adapter1);
        //spinner_appln.setSelection(0,false);
        spinner_appln.setOnItemSelectedListener(this);

        spinner_prep = (Spinner)rootView.findViewById(R.id.spinner_prep);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.prep_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_prep.setAdapter(adapter2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RetrofitQuestionAdapter(questions, R.layout.question_item, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //Toast.makeText(getContext(), "Welcome to the Comments ection", Toast.LENGTH_SHORT).show();
                        //Start a new activity to display the comments
                        Intent forumpostIntent = new Intent(getContext(), ForumCommentsActivity.class);
                        forumpostIntent.putExtra("POSTED_BY",questions.get(position).getPostedBy());
                        forumpostIntent.putExtra("POST_DETAILS",questions.get(position).getTitle());
                        forumpostIntent.putExtra("POST_ID",questions.get(position).getPostID());
                        //weeksIntent.putExtra("POSTED_BY",questions.get(position).getLink());
                        callingActivityFlag=1;
                        startActivity(forumpostIntent);

                    }
                })
        );



/*
        View.OnClickListener myClicklistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questions.clear();
                call = apiService.fetchQuestions("xat");
                fetchQuestionList();
                //Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
            }
        };*/


        //comments_tv.setOnClickListener(commentsClickListener);

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Welcome to this FAB", Toast.LENGTH_SHORT).show();
                // Click action
                //Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
                //startActivity(intent);
            }
        });

        if(MyUtil.checkConnectivity(getContext())) {
            if(questions.size()>0){
                questions.clear();
            }
            if(spinner_counter<=0){
                call = apiService.fetchQuestions("CAT");
                MyUtil.showProgressDialog(getActivity());
                fetchQuestionList();
            }
        }
        else{
            //Toast.makeText(getContext(), "Sorry. There is no internet connection!", Toast.LENGTH_SHORT).show();
            ((ImageView)rootView.findViewById(R.id.offline_img)).setVisibility(View.VISIBLE);
            ((TextView)rootView.findViewById(R.id.offline_msg_tv)).setVisibility(View.VISIBLE);
            //TextView textView=(TextView)findViewById(R.id.networkstatus);
            //textView.setVisibility(View.VISIBLE);
        }


        return rootView;
    }


    private void fetchQuestionList() {
        call.enqueue(new Callback<RetrofitQuestionList>() {
            @Override
            public void onResponse(Call<RetrofitQuestionList> call, Response<RetrofitQuestionList> response) {
                //Log.d(TAG, "Total number of questions fetched : " + response.body().getQuestions().size());
                //int response_size = response.body().getQuestions().size();
                MyUtil.hideProgressDialog();

                if(response.body()==null){
                    Toast.makeText(getContext(), "No data for this exam", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    return;
                }else{
                    questions.addAll(response.body().getQuestions());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RetrofitQuestionList> call, Throwable t) {
                Log.e(TAG, "Got error : " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String str= parent.getItemAtPosition(position).toString();
        if(spinner_counter==0){
                                     spinner_counter++;
        }else{
            if (str.equals("Select Exam")) {
                spinner_counter++;
            } else {
                //Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
                if (questions.size() > 0) {
                    questions.clear();
                }
                call = apiService.fetchQuestions(str);
                fetchQuestionList();
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
