package com.crackingMBA.training;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * A login screen that offers login via email/password.
 */
public class ChangePasswordActivity extends AppCompatActivity {

    private static String TAG = "ChangePasswordActivity";

    // UI references.
    private EditText mOTPView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private TextView msg;
    private View mProgressView;
    private View mFormView;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd);
        // Set up the login form.
        mOTPView = (EditText) findViewById(R.id.changepwd_otptxt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mPasswordView = (EditText) findViewById(R.id.changepwd_pwd);
        mConfirmPasswordView = (EditText) findViewById(R.id.changepwd_confirmpwd);

        Button changePwdBtn = (Button) findViewById(R.id.changepwd_button);
        changePwdBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

        mFormView = findViewById(R.id.changepwd_form);
        mProgressView = findViewById(R.id.changepwd_progress);

        if(null != getIntent() && null!=getIntent().getExtras()){
            email = getIntent().getExtras().getString("email");
        }
    }

    private void changePassword() {

        // Reset errors.
        mOTPView.setError(null);
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);

        // Store values at the time of the change pwd attempt.
        String otp = mOTPView.getText().toString();
        String pwd = mPasswordView.getText().toString();
        String confPwd = mConfirmPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid otp.
        if (TextUtils.isEmpty(otp)) {
            mOTPView.setError(getString(R.string.error_field_required));
            focusView = mOTPView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(pwd) && !isPasswordValid(pwd)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check if confirm password matches with password
        if (!(pwd.equals(confPwd))) {
            mConfirmPasswordView.setError("Confirm Password doesn't match entered password");
            focusView = mConfirmPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt change pwd and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            changePwdServiceCall(otp,email,pwd);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
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

            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    private void changePwdServiceCall(final String otp,final String email, final String password)
        {

            RequestParams params = new RequestParams();
            params.put("otp", otp);
            params.put("email", email);
            params.put("password", password);
            Log.d(TAG, "changePwdServiceCall");
            try {
                if(true){
                    Log.d(TAG, "Password Changed successfully for.."+email);
                    showProgress(false);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    Toast toast = Toast.makeText(getApplicationContext(), "Password changed successfully. Please Login to proceed..", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();
                    finish();
                    return;
                }
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(CrackingConstant.CHANGE_PASSWORD_SERVICE_URL, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, " Change Pwd Response is : " + response);
                        if(response.contains("pass")) {
                            Log.d(TAG, "Password Changed successfully for.."+email);
                            showProgress(false);
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            Toast toast = Toast.makeText(getApplicationContext(), "Password changed successfully. Please Login to proceed..", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP, 25, 400);
                            toast.show();
                            finish();
                        }else{
                            Log.d(TAG, "Change pwd failed..");
                            msg.setText("Please enter valid details..");
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
                //dilr section
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

