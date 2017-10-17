package com.crackingMBA.training;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.restAPI.LoginAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.restAPI.UserAPIService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText login_email, login_password;
    private Button login_btn;
    private ProgressBar login_progressBar;
    private FirebaseAuth auth;
    LoginAPIService apiService;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);
        login_btn = (Button)findViewById(R.id.login_btn);
        login_progressBar = (ProgressBar) findViewById(R.id.login_progressBar);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyConfig.hideKeyboard(LoginActivity.this);
                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                validateLogin(email, password);
            }
        });
    }

    public void validateLogin(final String email, final String password) {

        apiService = RestClient.getClient().create(LoginAPIService.class);
        apiService.validateLogin(email, password).enqueue(new Callback<RetrofitPostResponse>() {
            @Override
            public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {


                RetrofitPostResponse retrofitPostResponse = response.body();

                if(retrofitPostResponse.getResponse().equals("0")) {
                    Toast.makeText(LoginActivity.this, "Sorry! Please check your login credentials", Toast.LENGTH_SHORT).show();
                }else{
                    String name_of_user=retrofitPostResponse.getResponse();
                    String user_id = name_of_user.substring( 0, name_of_user.indexOf(","));
                    String name_of_the_user = name_of_user.substring(name_of_user.indexOf(",")+1, name_of_user.length());
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putBoolean("isUserLoggedIn", true);
                    ed.putString("nameofUser",name_of_the_user);
                    ed.putString("emailofUser",email);
                    ed.putString("userID",user_id);
                    ed.commit();

                    String reason_code = getIntent().getStringExtra("IS_IT_FOR_ENROLLMENT");
                    String exam_name = getIntent().getStringExtra("EXAM_NAME");
                    String exam_name_text = getIntent().getStringExtra("EXAM_NAME_TEXT");
                    if(reason_code.equals("1")){
                        SharedPreferences.Editor ed1 = prefs.edit();
                        ed1.putString("whetherCATcourseEnrolled","notqueried");
                        ed1.putString("whetherIIFTcourseEnrolled","notqueried");
                        ed1.putString("whetherSNAPcourseEnrolled","notqueried");
                        ed1.putString("whetherXATcourseEnrolled","notqueried");
                        ed1.commit();
                        Intent motivationVideoDetails = new Intent(getApplicationContext(), MotivationYoutubeDetailsActivity.class);
                        motivationVideoDetails.putExtra("EXAM_NAME", exam_name);
                        motivationVideoDetails.putExtra("EXAM_NAME_TEXT", exam_name_text);

                        startActivity(motivationVideoDetails);
                        finish();
                    }

                    //ed.commit();
                    finish();

                }
            }

            @Override
            public void onFailure(Call<RetrofitPostResponse> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        login_progressBar.setVisibility(View.GONE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
