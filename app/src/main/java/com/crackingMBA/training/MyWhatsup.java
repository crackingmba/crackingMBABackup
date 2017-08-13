package com.crackingMBA.training;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    int spinner_counter=0, spinner_prep_counter=0;
    Spinner spinner_appln, spinner_prep;
    TextView forum_header, forum_user_details; Button forum_logout_btn;
    int spinner_appln_selected_item_position=1;
    int spinner_prep_selected_item_position=0, onResumeStateChanged=0; String name_of_user;
    SharedPreferences prefs;
    SharedPreferences.Editor ed;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_whatsup, container, false);

        apiService = RestClient.getClient().create(QuestionAPIService.class);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.questionListRecyclerView);
        forum_header=(TextView)rootView.findViewById(R.id.forum_header);
        forum_user_details=(TextView)rootView.findViewById(R.id.forum_user_details);
        spinner_counter=0;spinner_prep_counter=0;

        spinner_appln = (Spinner)rootView.findViewById(R.id.spinner_appln);
        forum_logout_btn = (Button)rootView.findViewById(R.id.forum_logout_btn);
        forum_logout_btn = (Button)rootView.findViewById(R.id.forum_logout_btn);

        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        ed = prefs.edit();

        Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);
        name_of_user = prefs.getString("nameofUser", "");


        if(isUserLoggedIn){
            forum_user_details.setText("Hello "+name_of_user+"!");
            forum_logout_btn.setVisibility(View.VISIBLE);
        }else{
            forum_user_details.setText("Hello Guest!");
            forum_logout_btn.setVisibility(View.GONE);
        }

        forum_logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed.putBoolean("isUserLoggedIn", false);
                ed.putString("nameofUser","");
                ed.putString("emailofUser","");
                ed.putString("userID","");
                ed.commit();
                forum_user_details.setText("Hello Guest!");
                forum_logout_btn.setVisibility(View.GONE);
            }
        });

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                    R.array.exams_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_appln.setAdapter(adapter1);
        spinner_appln.setOnItemSelectedListener(this);

        spinner_prep = (Spinner)rootView.findViewById(R.id.spinner_prep);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.prep_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_prep.setAdapter(adapter2);
        spinner_prep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Toast.makeText(getContext(), "We are in Prep Spinner", Toast.LENGTH_SHORT).show();
                String str= parentView.getItemAtPosition(position).toString();

                if(spinner_prep_counter>0) {
                    if (position > 0) {
                        ed.putString("selectedSpinner", "spinner_prep");
                        ed.putInt("selectedSpinnerPosition", position);
                        ed.commit();

                        if (questions.size() > 0) {
                            questions.clear();
                        }

                        if (spinner_appln.getSelectedItemPosition() > 0) {
                            spinner_appln.setSelection(0);
                        }

                        forum_header.setText("Preparation Forum : " + str);
                        call = apiService.fetchQuestions(str);
                        //MyUtil.showProgressDialog(getActivity());
                        fetchQuestionList();
                    } else {
                        //do nothing for now
                    }
                }else{
                    spinner_prep_counter++;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RetrofitQuestionAdapter(questions, R.layout.question_item, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent forumpostIntent = new Intent(getContext(), ForumCommentsActivity.class);
                        forumpostIntent.putExtra("POSTED_BY_ID",questions.get(position).getPostedById());
                        forumpostIntent.putExtra("POSTED_BY",questions.get(position).getPostedBy());
                        forumpostIntent.putExtra("POST_DETAILS",questions.get(position).getTitle());
                        forumpostIntent.putExtra("POST_ID",questions.get(position).getPostID());

                        ed.putString("POST_ID", questions.get(position).getPostID());
                        ed.putString("POST_DETAILS", questions.get(position).getTitle());
                        ed.putString("POSTED_BY", questions.get(position).getPostedBy());
                        ed.putString("POSTED_BY_ID", questions.get(position).getPostedById());
                        ed.commit();





                        startActivity(forumpostIntent);

                    }
                })
        );

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);

                spinner_appln_selected_item_position=spinner_appln.getSelectedItemPosition();
                spinner_prep_selected_item_position=spinner_prep.getSelectedItemPosition();

                if(isUserLoggedIn){
                    forum_logout_btn.setVisibility(View.VISIBLE);
                }

                if(spinner_appln_selected_item_position==0 && spinner_prep_selected_item_position==0){
                    Toast.makeText(getContext(), "Select a category from drop down list to create a new post", Toast.LENGTH_SHORT).show();
                }else{
                    if (isUserLoggedIn) {
                        Intent intent = new Intent(getContext(), NewPostActivity.class);
                        startActivity(intent);

                        if(spinner_appln_selected_item_position>0){
                            ed.putString("selectedCategory", spinner_appln.getSelectedItem().toString());
                        }else if(spinner_prep_selected_item_position>0)
                        {
                            ed.putString("selectedCategory", spinner_prep.getSelectedItem().toString());
                        }

                        ed.commit();

                    }else{
                        Intent intent = new Intent(getContext(), LoginSignupActivity.class);
                        startActivity(intent);

                    }
                }
            }
        });

        if(MyUtil.checkConnectivity(getContext())) {
            if(questions.size()>0){
                questions.clear();
            }
            if(spinner_counter<=0 && spinner_prep_counter<=0 && onResumeStateChanged<1){
                //Toast.makeText(getContext(), "We are in seeded list "+onResumeStateChanged, Toast.LENGTH_SHORT).show();
                call = apiService.fetchQuestions("CAT");
                fetchQuestionList();
            }
        }
        else{
            ((ImageView)rootView.findViewById(R.id.offline_img)).setVisibility(View.VISIBLE);
            ((TextView)rootView.findViewById(R.id.offline_msg_tv)).setVisibility(View.VISIBLE);
        }


        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String str= parent.getItemAtPosition(position).toString();
        if(spinner_counter>0){
            if(position>0){

                ed.putString("selectedSpinner", "spinner_appln");
                ed.putInt("selectedSpinnerPosition", position);
                ed.commit();

                if (questions.size() > 0) {
                    questions.clear();
                }

                if(spinner_prep.getSelectedItemPosition()>0){
                    spinner_prep.setSelection(0);
                }

                forum_header.setText("Admissions Forum : "+str);
                call = apiService.fetchQuestions(str);
                fetchQuestionList();
            }else{
                //do nothing for now
            }
        }else{
            spinner_counter++;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onResumeStateChanged++;

        String selectedSpinner = prefs.getString("selectedSpinner", "");
        int selectedSpinnerPosition = prefs.getInt("selectedSpinnerPosition", 0);


        if(selectedSpinnerPosition==0){
            spinner_counter=0;
            spinner_prep_counter=0;
        }

        Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);
        String name_of_user = prefs.getString("nameofUser", "");

        if(isUserLoggedIn){
            forum_user_details.setText("Hello "+name_of_user+"!");
            forum_logout_btn.setVisibility(View.VISIBLE);
        }else{
            forum_user_details.setText("Hello Guest!");
            forum_logout_btn.setVisibility(View.GONE);
        }



        if(selectedSpinner.equals("spinner_appln")){
            if(spinner_appln.getSelectedItemPosition()==selectedSpinnerPosition){
                if(questions.size()>0){
                    questions.clear();
                }
                call = apiService.fetchQuestions(spinner_appln.getSelectedItem().toString());
                fetchQuestionList();
            }

            spinner_appln.setSelection(selectedSpinnerPosition);
            spinner_prep.setSelection(0);

        }else if(selectedSpinner.equals("spinner_prep")){
            if(spinner_prep.getSelectedItemPosition()==selectedSpinnerPosition){
                if(questions.size()>0){
                    questions.clear();
                }
                call = apiService.fetchQuestions(spinner_prep.getSelectedItem().toString());
                fetchQuestionList();
            }

            spinner_prep.setSelection(selectedSpinnerPosition);
            spinner_appln.setSelection(0);
        }


    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private void fetchQuestionList() {
        call.enqueue(new Callback<RetrofitQuestionList>() {

            @Override
            public void onResponse(Call<RetrofitQuestionList> call, Response<RetrofitQuestionList> response) {
                MyUtil.hideProgressDialog();

                if(response.body()==null){
                    Toast.makeText(getContext(), "There are no posts in this category!", Toast.LENGTH_SHORT).show();
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
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
