package com.crackingMBA.training;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class OTPValidationActivity extends AppCompatActivity {

    EditText otpTxt;
    TextView msg;
    static String TAG = "OTPValidationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpvalidation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        otpTxt = (EditText) findViewById(R.id.otpvalidation_otptxt);
        msg = (TextView) findViewById(R.id.otpvalidation_msg);

    }

    public void validateOTP(View v){
        String otpStr = otpTxt.getText().toString();
        Log.d(TAG,"OTP entered is "+otpStr);
        final RequestParams params = new RequestParams();
        params.put("email", VideoApplication.registeringUserEmail);
        params.put("otpText", otpStr);
        try {
            if(true){
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.putString("loggedInUserName", VideoApplication.registeringUserName);
                editor.putString("loggedInUserEmail", VideoApplication.registeringUserEmail);
                editor.putString("loggedInUserPassword", VideoApplication.registeringUserPwd);
                editor.commit();

                Intent dashboardIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                dashboardIntent.putExtra("gotoTab", "3");
                startActivity(dashboardIntent);
                finish();
                return;
            }
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(CrackingConstant.VALIDATE_OTP_SERVICE_URL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    response = "pass";
                    if(response.contains("pass")) {
                        Log.d(TAG, "OTP validated successfully for.."+params);
                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putString("loggedInUserName", VideoApplication.registeringUserName);
                        editor.putString("loggedInUserEmail", VideoApplication.registeringUserEmail);
                        editor.putString("loggedInUserPassword", VideoApplication.registeringUserPwd);
                        editor.commit();

                        Intent dashboardIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                        dashboardIntent.putExtra("gotoTab", "3");
                        startActivity(dashboardIntent);
                        finish();
                    }else{
                        Log.d(TAG, "OTP validation failed..");
                        msg.setText("Please enter a valid OTP..");
                        msg.setTextColor(Color.RED);
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
                    msg.setText("Ooops!! There is some problem in validating OTP");
                    msg.setTextColor(Color.RED);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ;
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

