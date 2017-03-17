package com.crackingMBA.training;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * A login screen that offers login via email/password.
 */
public class AskQuestionActivity extends AppCompatActivity {

    EditText qstnTxt;
    TextView msg;
    String email;
    boolean isMock;
    static String TAG = "AskQuestionActivvity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askquestion);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isMock = pref.getBoolean("isMock",false);
        email = pref.getString("loggedInUserEmail",null);
        qstnTxt = (EditText) findViewById(R.id.askqstn_qstntxt);
        msg = (TextView) findViewById(R.id.askqstn_msg);

    }

    public void submitQuestion(View v){
        String qstnStr = qstnTxt.getText().toString();
        Log.d(TAG,"Submitting qstn "+qstnStr);
        if(isMock){
            Log.d(TAG,"Submitted qstn mocked..");
            msg.setText("Your Question is Submitted Successfully");
            msg.setTextColor(Color.BLUE);
        }else {
            Log.d(TAG, "In else block");
            final RequestParams params = new RequestParams();
            params.put("email", email);
            params.put("qnText", qstnStr);
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(CrackingConstant.ADD_QUESTION_SERVICE_URL, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        if(response.contains("pass")) {
                            Log.d(TAG, "Qstn submitted successfully for.."+params);
                            msg.setText("Your Question is Submitted Successfully");
                            msg.setTextColor(Color.BLUE);
                        }else{
                            Log.d(TAG, "Qstn submission failed..");
                            msg.setText("Problem occured while submitting question");
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
                        msg.setText("Ooops!! There is some problem in submitting your question");
                        msg.setTextColor(Color.RED);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                ;
            }
        }
        Intent dashboardIntent=new Intent(getApplicationContext(),DashboardActivity.class);
        dashboardIntent.putExtra("gotoTab","3");
        startActivity(dashboardIntent);
    }

}

