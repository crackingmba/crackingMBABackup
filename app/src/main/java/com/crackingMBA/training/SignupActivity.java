package com.crackingMBA.training;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.restAPI.UserAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private EditText signup_name, signup_email, signup_password;
    private Button signup_btn;
    private ProgressBar signup_progressBar;
    private TextView signup_link_login;
    UserAPIService apiService;
    SharedPreferences prefs;

    String name;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        prefs = PreferenceManager.getDefaultSharedPreferences(SignupActivity.this);

        signup_name = (EditText) findViewById(R.id.signup_name);
        signup_email = (EditText) findViewById(R.id.signup_email);
        signup_password = (EditText) findViewById(R.id.signup_password);
        signup_btn = (Button)findViewById(R.id.signup_btn);
        signup_progressBar = (ProgressBar) findViewById(R.id.signup_progressBar);


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = signup_name.getText().toString().trim();
                email = signup_email.getText().toString().trim();
                password = signup_password.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                signup_progressBar.setVisibility(View.VISIBLE);
                sendPost(name,email,password);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void sendPost(final String name, final String email, String password) {

        apiService = RestClient.getClient().create(UserAPIService.class);
        apiService.savePost(name, email, password).enqueue(new Callback<RetrofitPostResponse>() {
            @Override
            public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {

                signup_progressBar.setVisibility(View.GONE);

                RetrofitPostResponse retrofitPostResponse = response.body();

                if(retrofitPostResponse.getResponse().equals("0")) {
                }else if(retrofitPostResponse.getResponse().equals("4")) {
                    Toast.makeText(SignupActivity.this, "Sorry! A user with this email already exists!", Toast.LENGTH_SHORT).show();
                }else{
                    String user_id=retrofitPostResponse.getResponse();

                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putBoolean("isUserLoggedIn", true);
                    ed.putString("nameofUser", name);
                    ed.putString("emailofUser",email);
                    ed.putString("userID",user_id);
                    ed.putString("whetherCATcourseEnrolled","notqueried");
                    ed.putString("whetherIIFTcourseEnrolled","notqueried");
                    ed.putString("whetherSNAPcourseEnrolled","notqueried");
                    ed.putString("whetherXATcourseEnrolled","notqueried");
                    ed.putString("whetherComboCourseEnrolled","notqueried");
                    ed.commit();
                    finish();

                }
            }

            @Override
            public void onFailure(Call<RetrofitPostResponse> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");
                signup_progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        signup_progressBar.setVisibility(View.GONE);
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
