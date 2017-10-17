package com.crackingMBA.training;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.RetrofitForumCommentAdapter;
import com.crackingMBA.training.pojo.RetrofitForumComment;
import com.crackingMBA.training.pojo.RetrofitForumCommentList;
import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.restAPI.ForumCommentsAPIService;
import com.crackingMBA.training.restAPI.NewCommentAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.MyUtil;

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
    TextView comments_posted_by, comments_post_details, comments_comments_not_added;
    Button newcomment_button, forum_comments_signup_btn, forum_comments_login_btn;
    EditText newcomment_details;
    SharedPreferences prefs;String userID;String nameofUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_comments);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        prefs = PreferenceManager.getDefaultSharedPreferences(ForumCommentsActivity.this);

        post_id=prefs.getString("POST_ID", "");
        post_details=prefs.getString("POST_DETAILS", "");
        posted_by_id=prefs.getString("POSTED_BY_ID", "");
        posted_by=prefs.getString("POSTED_BY", "");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);
        nameofUser = prefs.getString("nameofUser", "");
        userID = prefs.getString("userID", "");

        comments_posted_by=(TextView)findViewById(R.id.comments_posted_by);
        comments_post_details=(TextView)findViewById(R.id.comments_post_details);
        comments_comments_not_added=(TextView)findViewById(R.id.comments_comments_not_added);
        newcomment_button=(Button)findViewById(R.id.newcomment_button);
        forum_comments_signup_btn=(Button)findViewById(R.id.forum_comments_signup_btn);
        forum_comments_login_btn=(Button)findViewById(R.id.forum_comments_login_btn);
        newcomment_details=(EditText)findViewById(R.id.newcomment_details);
        //comment_postedby_tv=(TextView)findViewById(R.id.comment_postedby_tv);

        comments_posted_by.setText(posted_by);
        comments_post_details.setText(post_details);

        if(isUserLoggedIn){
            newcomment_button.setEnabled(true);
            newcomment_button.setVisibility(View.VISIBLE);
        }else{
            newcomment_button.setEnabled(false);
        }

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
            Toast.makeText(this, "Sorry. There is no internet connection!", Toast.LENGTH_SHORT).show();
        }

        newcomment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyConfig.hideKeyboard(ForumCommentsActivity.this);
                userID = prefs.getString("userID", "");

                if(MyUtil.checkConnectivity(ForumCommentsActivity.this)) {

                    String newcomment = newcomment_details.getText().toString();

                    if(TextUtils.isEmpty(newcomment)){
                        Toast.makeText(ForumCommentsActivity.this, "Please enter a comment and submit ", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        newcomment=newcomment.replaceAll("'","\\\\'");
                        //Toast.makeText(ForumCommentsActivity.this, newcomment, Toast.LENGTH_SHORT).show();
                        saveNewComment(post_id, newcomment, userID);
                    }
                }
                else{
                    Toast.makeText(ForumCommentsActivity.this, "Sorry. There is no internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        newcomment_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);

                if(isUserLoggedIn){
                }else{
                    MyConfig.hideKeyboard(ForumCommentsActivity.this);
                    newcomment_details.setText("Oops! Looks like you are not logged in!\n\n Please Sign Up or Login to continue.");
                    newcomment_details.setTextColor(Color.RED);
                    newcomment_details.setEnabled(false);
                    newcomment_button.setVisibility(View.GONE);
                    forum_comments_signup_btn.setVisibility(View.VISIBLE);
                    forum_comments_login_btn.setVisibility(View.VISIBLE);
                }
            }
        });

        forum_comments_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumCommentsActivity.this, LoginSignupActivity.class);
                startActivity(intent);
            }
        });

        forum_comments_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumCommentsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void saveNewComment(final String post_id, final String newcomment_details, final String posted_by_id) {
        MyUtil.showProgressDialog(ForumCommentsActivity.this);

        newCommentService = RestClient.getClient().create(NewCommentAPIService.class);
        newCommentService.saveNewComment(post_id, newcomment_details, posted_by_id).enqueue(new Callback<RetrofitPostResponse>() {
            @Override
            public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {

                MyUtil.hideProgressDialog();

                RetrofitPostResponse retrofitPostResponse = response.body();

                if(retrofitPostResponse.getResponse().equals("0")) {
                    Toast.makeText(ForumCommentsActivity.this, "Sorry! Your comment is not saved", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForumCommentsActivity.this, "Thanks! Your comment is saved!", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }

            @Override
            public void onFailure(Call<RetrofitPostResponse> call, Throwable t) {
            }
        });
    }

    private void fetchQuestionList() {
        call.enqueue(new Callback<RetrofitForumCommentList>() {
            @Override
            public void onResponse(Call<RetrofitForumCommentList> call, Response<RetrofitForumCommentList> response) {
                MyUtil.hideProgressDialog();

                if(response.body() == null){
                    comments_comments_not_added.setVisibility(View.VISIBLE);
                    return;
                }else{
                    questions.addAll(response.body().getQuestions());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RetrofitForumCommentList> call, Throwable t) {
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();

        Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);

        if(isUserLoggedIn){
            newcomment_button.setEnabled(true);
            newcomment_button.setVisibility(View.VISIBLE);
            forum_comments_signup_btn.setVisibility(View.GONE);
            forum_comments_login_btn.setVisibility(View.GONE);
            newcomment_details.setText("");
            newcomment_details.setEnabled(true);
            newcomment_details.setTextColor(Color.BLACK);
        }else{
        }
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
