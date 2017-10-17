package com.crackingMBA.training;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
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
                emailofUser = prefs.getString("emailofUser", "");
                MyConfig.hideKeyboard(NewPostActivity.this);

                String newcomment=newpost_postdetails.getText().toString();
                if(TextUtils.isEmpty(newcomment)){
                    Toast.makeText(NewPostActivity.this, "Please enter the details and submit ", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    newcomment=newcomment.replaceAll("'","\\\\'");
                    saveNewPost(selectedCategory,newcomment, emailofUser);
                }


            }
        });


    }

    public void saveNewPost(final String category, final String postdetails, final String useremail) {
        newpost_progressBar.setVisibility(View.VISIBLE);
        apiService = RestClient.getClient().create(NewPostAPIService.class);
        apiService.saveNewPost(category, postdetails, useremail).enqueue(new Callback<RetrofitPostResponse>() {
            @Override
            public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {

                newpost_progressBar.setVisibility(View.GONE);

                RetrofitPostResponse retrofitPostResponse = response.body();

                if(retrofitPostResponse.getResponse().equals("0")) {
                }else{
                    newpost_successmsg.setVisibility(View.VISIBLE);
                    newpost_button.setEnabled(false);
                    newpost_button.setBackgroundColor(Color.GRAY);
                    finish();
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
