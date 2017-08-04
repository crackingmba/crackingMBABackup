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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class MyWhatsup extends Fragment implements View.OnClickListener {

    View rootView; Button btn, original;

    private String TAG = MyWhatsup.class.getSimpleName();
    QuestionAPIService apiService;
    RecyclerView recyclerView;
    RetrofitQuestionAdapter adapter;
    List<RetrofitQuestion> questions = new ArrayList<>();
    Call<RetrofitQuestionList> call;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_whatsup, container, false);

        apiService = RestClient.getClient().create(QuestionAPIService.class);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.questionListRecyclerView);
        //btn=(Button)rootView.findViewById(R.id.whatsup_refresh_btn);
        //original=(Button)rootView.findViewById(R.id.whatsup_original);

        Spinner spinner = (Spinner)rootView.findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                    R.array.exams_array, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
            spinner.setAdapter(adapter1);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RetrofitQuestionAdapter(questions, R.layout.question_item, getContext());
        recyclerView.setAdapter(adapter);

        call = apiService.fetchQuestions("cat");

        View.OnClickListener myClicklistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questions.clear();
                call = apiService.fetchQuestions("xat");
                fetchQuestionList();
                //Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
            }
        };

        View.OnClickListener originalClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questions.clear();
                call = apiService.fetchQuestions("cat");
                fetchQuestionList();
            }
        };

        //btn.setOnClickListener(myClicklistener);
        //original.setOnClickListener(originalClickListener);

        fetchQuestionList();

        return rootView;
    }

    private void fetchQuestionList() {
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
