package com.crackingMBA.training;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.crackingMBA.training.pojo.LoginResponseObject;
import com.crackingMBA.training.pojo.RegisrationResponseObject;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * A login screen that offers login via email/password.
 */
public class RegistrationActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    // UI references.
    private EditText mFnameView;
    private EditText mLnameView;
    private EditText mEmailView;
    private EditText mPwdView;
    private EditText mConfirmPwdView;
    private View mProgressView;
    private View mRegView;

    private static String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Set up the login form.
        mFnameView = (EditText) findViewById(R.id.reg_fname);
        mLnameView = (EditText) findViewById(R.id.reg_lname);
        mEmailView = (EditText) findViewById(R.id.reg_email);
        mPwdView = (EditText) findViewById(R.id.reg_pwd);
        mConfirmPwdView = (EditText) findViewById(R.id.reg_confirmpwd);

        Button regButton = (Button) findViewById(R.id.reg_button);
        regButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        mRegView = findViewById(R.id.reg_form);
        mProgressView = findViewById(R.id.reg_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void registerUser() {

        // Reset errors.
        mEmailView.setError(null);
        mPwdView.setError(null);
        mConfirmPwdView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String pwd = mPwdView.getText().toString();
        String confPwd = mConfirmPwdView.getText().toString();
        String fname = mFnameView.getText().toString();
        String lname = mLnameView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(pwd) && !isPasswordValid(pwd)) {
            mPwdView.setError(getString(R.string.error_invalid_password));
            focusView = mPwdView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check if confirm password matches with password
        if (!(pwd.equals(confPwd))) {
            mConfirmPwdView.setError("Confirm Password doesn't match entered password");
            focusView = mConfirmPwdView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            // Registration logic
            registrationServiceCall(fname,lname,email,pwd);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void registrationServiceCall(final String firstName, final String lastName, final String email, final String password)
    {

        RequestParams params = new RequestParams();
        params.put("firstname", firstName);
        params.put("lastname", lastName);
        params.put("email", email);
        params.put("password", password);
        Log.d(TAG, "registrationServiceCall");
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(CrackingConstant.REGISTRATION_SERVICE_URL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    Log.d(TAG, " Registration Response is : " + response);
                    Gson gson = new Gson();
                    RegisrationResponseObject regisrationResponseObject = gson.fromJson(response, RegisrationResponseObject.class);
                    if (regisrationResponseObject != null) {
                        String regisrationStatus = regisrationResponseObject.getStatus();

                        if (regisrationStatus.equalsIgnoreCase("pass")) {

                            VideoApplication.registeringUserName = firstName+" "+lastName;
                            VideoApplication.registeringUserEmail = email;
                            VideoApplication.registeringUserPwd = password;
                            Intent otpIntent = new Intent(getApplicationContext(), OTPValidationActivity.class);
                            startActivity(otpIntent);
                            finish();
                        } else {
                            mEmailView.setError("Email Already registered");
                            showProgress(false);
                            View focusView = null;
                            focusView=mEmailView;
                            focusView.requestFocus();
                        }
                    }
                }
                @Override
                public void onFailure(int statusCode, Throwable error,
                                      String content) {
                    Log.d(TAG, "Status is " + statusCode + " and " + content);
                    if (statusCode == 404) {
                        Log.d(TAG, "Requested resource not found");
                    } else if (statusCode == 500) {
                        Log.d(TAG, "Something went wrong at server end");
                    } else {
                        Log.d(TAG, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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

