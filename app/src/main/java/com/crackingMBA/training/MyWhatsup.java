package com.crackingMBA.training;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crackingMBA.training.adapter.RetrofitQuestionAdapter;
import com.crackingMBA.training.pojo.RetrofitQuestion;
import com.crackingMBA.training.pojo.RetrofitQuestionList;
import com.crackingMBA.training.restAPI.QuestionAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MSK on 24-01-2017.
 */
public class MyWhatsup extends Fragment implements View.OnClickListener {

    View rootView;
    TextView textView;

    private String TAG = MyWhatsup.class.getSimpleName();
    ArrayList<HashMap<String, String>> contactList;
    QuestionAPIService apiService;
    RecyclerView recyclerView;
    RetrofitQuestionAdapter adapter;
    List<RetrofitQuestion> questions = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_whatsup, container, false);

        contactList = new ArrayList<>();

        apiService = RestClient.getClient().create(QuestionAPIService.class);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.questionListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RetrofitQuestionAdapter(questions, R.layout.question_item, getContext());
        recyclerView.setAdapter(adapter);

        fetchQuestionList();

        return rootView;
    }

    private void fetchQuestionList() {
        Call<RetrofitQuestionList> call = apiService.fetchQuestions("android");
        call.enqueue(new Callback<RetrofitQuestionList>() {
            @Override
            public void onResponse(Call<RetrofitQuestionList> call, Response<RetrofitQuestionList> response) {
                Log.d(TAG, "Total number of questions fetched : " + response.body().getQuestions().size());
                questions.addAll(response.body().getQuestions());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<RetrofitQuestionList> call, Throwable t) {
                Log.e(TAG, "Got error : " + t.getLocalizedMessage());
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
