package com.crackingMBA.training;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.RetrofitForumCommentAdapter;
import com.crackingMBA.training.adapter.RetrofitQuestionAdapter;
import com.crackingMBA.training.pojo.RetrofitForumComment;
import com.crackingMBA.training.pojo.RetrofitForumCommentList;
import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.pojo.RetrofitQuestion;
import com.crackingMBA.training.pojo.RetrofitQuestionList;
import com.crackingMBA.training.restAPI.ForumCommentsAPIService;
import com.crackingMBA.training.restAPI.NewCommentAPIService;
import com.crackingMBA.training.restAPI.NewPostAPIService;
import com.crackingMBA.training.restAPI.QuestionAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForumCommentsActivity extends AppCompatActivity {
    ForumCommentsAPIService apiService;
    NewCommentAPIService newCommentService;
    RecyclerView recyclerView;
    RetrofitForumCommentAdapter adapter;
    List<RetrofitForumComment> questions = new ArrayList<>();
    Call<RetrofitForumCommentList> call;
    String posted_by, post_details, post_id, posted_by_id;
    TextView comments_posted_by, comments_post_details, comments_comments_not_added, comment_postedby_tv;
    Button newcomment_button;
    EditText newcomment_details;
    private ProgressBar newcomment_progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_comments);

        Intent intent = getIntent();
        posted_by_id = intent.getStringExtra("POSTED_BY_ID");
        posted_by = intent.getStringExtra("POSTED_BY");
        post_details = intent.getStringExtra("POST_DETAILS");
        post_id= intent.getStringExtra("POST_ID");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        comments_posted_by=(TextView)findViewById(R.id.comments_posted_by);
        comments_post_details=(TextView)findViewById(R.id.comments_post_details);
        comments_comments_not_added=(TextView)findViewById(R.id.comments_comments_not_added);
        newcomment_button=(Button)findViewById(R.id.newcomment_button);
        newcomment_details=(EditText)findViewById(R.id.newcomment_details);
        //comment_postedby_tv=(TextView)findViewById(R.id.comment_postedby_tv);

        comments_posted_by.setText(posted_by);
        comments_post_details.setText(post_details);
        //comment_postedby_tv.setText(posted_by);

        apiService = RestClient.getClient().create(ForumCommentsAPIService.class);
        recyclerView = (RecyclerView)findViewById(R.id.forumCommentsRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(ForumCommentsActivity.this));

        adapter = new RetrofitForumCommentAdapter(questions, R.layout.retrofit_forum_post_comment, ForumCommentsActivity.this);
        recyclerView.setAdapter(adapter);

        call = apiService.fetchPostComments(post_id);

        if(MyUtil.checkConnectivity(getApplicationContext())) {

            MyUtil.showProgressDialog(this);
            fetchQuestionList();
        }
        else{
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(this, "Sorry. There is no internet connection!", Toast.LENGTH_SHORT).show();
            //TextView textView=(TextView)findViewById(R.id.networkstatus);
            //textView.setVisibility(View.VISIBLE);
        }

        newcomment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NewPostActivity.this, "Thank you! Your data will be saved!", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                //startActivity(intent);
                MyConfig.hideKeyboard(ForumCommentsActivity.this);
                //newcomment_progressBar.setVisibility(View.VISIBLE);


                if(MyUtil.checkConnectivity(ForumCommentsActivity.this)) {
                    MyUtil.showProgressDialog(ForumCommentsActivity.this);
                    saveNewComment(post_id, newcomment_details.getText().toString(), posted_by_id);
                }
                else{
                    Toast.makeText(ForumCommentsActivity.this, "Sorry. There is no internet connection!", Toast.LENGTH_SHORT).show();
                    //((ImageView)rootView.findViewById(R.id.offline_img)).setVisibility(View.VISIBLE);
                    //((TextView)rootView.findViewById(R.id.offline_msg_tv)).setVisibility(View.VISIBLE);
                    //TextView textView=(TextView)findViewById(R.id.networkstatus);
                    //textView.setVisibility(View.VISIBLE);
                }





            }
        });



    }

    public void saveNewComment(final String post_id, final String newcomment_details, final String posted_by_id) {

        newCommentService = RestClient.getClient().create(NewCommentAPIService.class);
        newCommentService.saveNewComment(post_id, newcomment_details, posted_by_id).enqueue(new Callback<RetrofitPostResponse>() {
            @Override
            public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {

                MyUtil.hideProgressDialog();

                RetrofitPostResponse retrofitPostResponse = response.body();

                if(retrofitPostResponse.getResponse().equals("0")) {
                    //showResponse(response.body().toString());
                    Toast.makeText(ForumCommentsActivity.this, "The new comment is not saved to the server", Toast.LENGTH_SHORT).show();

                    //Log.i(TAG, "post submitted to API." + response.body().toString());
                }else{
                    //newpost_successmsg.setVisibility(View.VISIBLE);
                    //newpost_button.setEnabled(false);
                    //newpost_button.setBackgroundColor(Color.GRAY);
                    Toast.makeText(ForumCommentsActivity.this, "The new comment is saved to the server", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(NewPostActivity.this, NewPostActivity.class);
                    //startActivity(intent);
                    //finish();

                }
            }

            @Override
            public void onFailure(Call<RetrofitPostResponse> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");
                //newpost_progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void fetchQuestionList() {
        call.enqueue(new Callback<RetrofitForumCommentList>() {
            @Override
            public void onResponse(Call<RetrofitForumCommentList> call, Response<RetrofitForumCommentList> response) {
                //Log.d(TAG, "Total number of questions fetched : " + response.body().getQuestions().size());
                //int response_size = response.body().getQuestions().size();
                MyUtil.hideProgressDialog();

                if(response.body() == null){
                    //Toast.makeText(ForumCommentsActivity.this, "There are no comments here", Toast.LENGTH_SHORT).show();
                    comments_comments_not_added.setVisibility(View.VISIBLE);
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
