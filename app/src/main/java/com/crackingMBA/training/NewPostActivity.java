package com.crackingMBA.training;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.restAPI.NewPostAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.restAPI.UserAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPostActivity extends AppCompatActivity {
    Button newpost_button;
    EditText newpost_postdetails;
    TextView newpost_username, newpost_category, newpost_successmsg;
    NewPostAPIService apiService;
    String nameofUser, emailofUser, selectedCategory;
    private ProgressBar newpost_progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        nameofUser = prefs.getString("nameofUser", "");
        emailofUser = prefs.getString("emailofUser", "");
        selectedCategory = prefs.getString("selectedCategory", "");
        newpost_username=(TextView)findViewById(R.id.newpost_username);
        newpost_username.setText(nameofUser);

        newpost_category=(TextView)findViewById(R.id.newpost_category);
        newpost_successmsg=(TextView)findViewById(R.id.newpost_successmsg);
        newpost_category.setText("Category Selected : "+selectedCategory);

        //Toast.makeText(this, "The selected Category is"+selectedCategory, Toast.LENGTH_SHORT).show();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        newpost_button=(Button)findViewById(R.id.newpost_button);
        newpost_postdetails=(EditText)findViewById(R.id.newpost_postdetails);
        newpost_progressBar = (ProgressBar) findViewById(R.id.newpost_progressBar);

        //hideKeyboard(NewPostActivity.this);


        newpost_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NewPostActivity.this, "Thank you! Your data will be saved!", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(LoginSignupActivity.this, LoginActivity.class);
                //startActivity(intent);
                MyConfig.hideKeyboard(NewPostActivity.this);
                newpost_progressBar.setVisibility(View.VISIBLE);
                saveNewPost(selectedCategory,newpost_postdetails.getText().toString(), emailofUser);

            }
        });


    }

    public void saveNewPost(final String category, final String postdetails, final String useremail) {

        apiService = RestClient.getClient().create(NewPostAPIService.class);
        apiService.saveNewPost(category, postdetails, useremail).enqueue(new Callback<RetrofitPostResponse>() {
            @Override
            public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {

                newpost_progressBar.setVisibility(View.GONE);

                RetrofitPostResponse retrofitPostResponse = response.body();

                if(retrofitPostResponse.getResponse().equals("0")) {
                    //showResponse(response.body().toString());
                    //Toast.makeText(NewPostActivity.this, "The data is not saved to the server", Toast.LENGTH_SHORT).show();

                    //Log.i(TAG, "post submitted to API." + response.body().toString());
                }else{
                    newpost_successmsg.setVisibility(View.VISIBLE);
                    newpost_button.setEnabled(false);
                    newpost_button.setBackgroundColor(Color.GRAY);
                    //Toast.makeText(NewPostActivity.this, "The new post is saved to the server", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(NewPostActivity.this, NewPostActivity.class);
                    //startActivity(intent);
                    //finish();

                }
            }

            @Override
            public void onFailure(Call<RetrofitPostResponse> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");
                newpost_progressBar.setVisibility(View.GONE);
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
