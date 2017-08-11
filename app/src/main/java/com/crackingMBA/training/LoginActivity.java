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

                //login_progressBar.setVisibility(View.VISIBLE);

                //authenticate user
               /* auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                login_progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        login_password.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login is successfully completed!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, NewPostActivity.class);
                                    startActivity(intent);
                                    //finish();
                                }
                            }
                        });*/
            }
        });
    }

    public void validateLogin(final String email, final String password) {

        apiService = RestClient.getClient().create(LoginAPIService.class);
        apiService.validateLogin(email, password).enqueue(new Callback<RetrofitPostResponse>() {
            @Override
            public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {

                //signup_progressBar.setVisibility(View.GONE);


/*                SharedPreferences.Editor ed = prefs.edit();
                ed.putBoolean("isUserLoggedIn", true);
                ed.putString("nameofUser", name);
                ed.putString("emailofUser",email);
                ed.commit();*/

                RetrofitPostResponse retrofitPostResponse = response.body();

                if(retrofitPostResponse.getResponse().equals("0")) {
                    //showResponse(response.body().toString());
                    Toast.makeText(LoginActivity.this, "The user cannot be logged in", Toast.LENGTH_SHORT).show();
                    //the user is not Signed Up
                    //Log.i(TAG, "post submitted to API." + response.body().toString());
                }else{
                    Toast.makeText(LoginActivity.this, "The user is valid", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putBoolean("isUserLoggedIn", true);
                    ed.putString("emailofUser",email);
                    ed.commit();
                    //Intent intent = new Intent(LoginSignupActivity.this, NewPostActivity.class);
                    //startActivity(intent);
                    //the user is Signed Up
                    finish();

                }
            }

            @Override
            public void onFailure(Call<RetrofitPostResponse> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");
                //signup_progressBar.setVisibility(View.GONE);
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
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
