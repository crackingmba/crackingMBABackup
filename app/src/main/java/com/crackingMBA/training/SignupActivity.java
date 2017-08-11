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
import android.widget.Button;import android.widget.EditText;import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.restAPI.UserAPIService;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private EditText signup_name, signup_email, signup_password;
    private Button signup_btn;
    private ProgressBar signup_progressBar;
    private FirebaseAuth auth;
    private TextView signup_link_login;
    UserAPIService apiService;

    String name;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        signup_name = (EditText) findViewById(R.id.signup_name);
        signup_email = (EditText) findViewById(R.id.signup_email);
        signup_password = (EditText) findViewById(R.id.signup_password);
        signup_btn = (Button)findViewById(R.id.signup_btn);
        signup_progressBar = (ProgressBar) findViewById(R.id.signup_progressBar);
        signup_link_login=(TextView)findViewById(R.id.signup_link_login);



        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = signup_name.getText().toString().trim();
                email = signup_email.getText().toString().trim();
                password = signup_password.getText().toString().trim();

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
                //create user
               /* auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                signup_progressBar.setVisibility(View.GONE);

                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SignupActivity.this);
                                SharedPreferences.Editor ed = prefs.edit();
                                ed.putBoolean("UserLoggedIn", true);
                                ed.commit();

*//*                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                                String username = prefs.getString(KEY_USERNAME, "Default Value if not found");
                                String password = prefs.getString(KEY_PASSWORD, "");*//*

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //Intent intent = new Intent(SignupActivity.this, NewPostActivity.class);
                                    //startActivity(intent);
                                    Toast.makeText(SignupActivity.this, "The user is registered!", Toast.LENGTH_SHORT).show();

                                    sendPost(name,email,password);

                                    //intent.putExtra("USER_ID",questions.get(position).getPostedBy());
                                    //intent.putExtra("USER_NAME",questions.get(position).getPostID());
                                    //finish();
                                }
                            }
                        });*/

            }
        });

        signup_link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);

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

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SignupActivity.this);
                SharedPreferences.Editor ed = prefs.edit();
                ed.putBoolean("isUserLoggedIn", true);
                ed.putString("nameofUser", name);
                ed.putString("emailofUser",email);
                ed.commit();

                RetrofitPostResponse retrofitPostResponse = response.body();

                if(retrofitPostResponse.getResponse().equals("0")) {
                    //showResponse(response.body().toString());
                    Toast.makeText(SignupActivity.this, "The data is not saved to the server", Toast.LENGTH_SHORT).show();

                    //Log.i(TAG, "post submitted to API." + response.body().toString());
                }else{
                    Toast.makeText(SignupActivity.this, "The data is saved to the server", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, NewPostActivity.class);
                    startActivity(intent);
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
