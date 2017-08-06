package com.crackingMBA.training;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.crackingMBA.training.adapter.RetrofitForumCommentAdapter;
import com.crackingMBA.training.adapter.RetrofitQuestionAdapter;
import com.crackingMBA.training.pojo.RetrofitForumComment;
import com.crackingMBA.training.pojo.RetrofitForumCommentList;
import com.crackingMBA.training.pojo.RetrofitQuestion;
import com.crackingMBA.training.pojo.RetrofitQuestionList;
import com.crackingMBA.training.restAPI.ForumCommentsAPIService;
import com.crackingMBA.training.restAPI.QuestionAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForumCommentsActivity extends AppCompatActivity {
    ForumCommentsAPIService apiService;
    RecyclerView recyclerView;
    RetrofitForumCommentAdapter adapter;
    List<RetrofitForumComment> questions = new ArrayList<>();
    Call<RetrofitForumCommentList> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_comments);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        apiService = RestClient.getClient().create(ForumCommentsAPIService.class);
        recyclerView = (RecyclerView)findViewById(R.id.forumCommentsRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(ForumCommentsActivity.this));

        adapter = new RetrofitForumCommentAdapter(questions, R.layout.retrofit_forum_post_comment, ForumCommentsActivity.this);
        recyclerView.setAdapter(adapter);

        call = apiService.fetchPostComments("1");

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ForumCommentsActivity.this, "", Toast.LENGTH_SHORT).show();
                // Click action
                //Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
                //startActivity(intent);
            }
        });

        fetchQuestionList();
    }

    private void fetchQuestionList() {
        call.enqueue(new Callback<RetrofitForumCommentList>() {
            @Override
            public void onResponse(Call<RetrofitForumCommentList> call, Response<RetrofitForumCommentList> response) {
                //Log.d(TAG, "Total number of questions fetched : " + response.body().getQuestions().size());
                //int response_size = response.body().getQuestions().size();

                if(response.body() == null){
                    return;
                }else{
                    questions.addAll(response.body().getQuestions());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RetrofitForumCommentList> call, Throwable t) {
                //Log.e(TAG, "Got error : " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
