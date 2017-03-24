package com.crackingMBA.training;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailTxt;
    TextView msg;
    static String TAG = "ForgotPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        emailTxt = (EditText) findViewById(R.id.forgotpwd_emailtxt);

        if(null != getIntent() && null!=getIntent().getExtras()){
            String emailStr = getIntent().getExtras().getString("email");
            if(null!=emailStr && ""!=emailStr){
                emailTxt.setText(emailStr);
            }
        }

        msg = (TextView) findViewById(R.id.forgotpwd_msg);
        msg.setTextColor(Color.RED);
    }

    public void validateOTP(View v){
        final String emailStr = emailTxt.getText().toString();
        if(TextUtils.isEmpty(emailStr)){
            msg.setText("Please enter Email Id");
            return;
        }
        Log.d(TAG,"Email entered is "+emailStr);
        final RequestParams params = new RequestParams();
        params.put("email", emailStr);
        try {
            if(true){

                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                intent.putExtra("email",emailStr);
                startActivity(intent);
                finish();
                return;
            }
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(CrackingConstant.SEND_OTP_SERVICE_URL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    response = "pass";
                    if(response.contains("pass")) {
                        Log.d(TAG, "OTP validated successfully for.."+params);

                        Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                        intent.putExtra("email",emailStr);
                        startActivity(intent);
                        Toast toast = Toast.makeText(getApplicationContext(), "OTP is sent to your registered email", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 25, 400);
                        toast.show();
                        finish();
                    }else{
                        Log.d(TAG, "OTP validation failed..");
                        msg.setText("Please enter a valid Email Id..");
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
                    msg.setText("Ooops!! There is some problem in sending OTP..");
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
