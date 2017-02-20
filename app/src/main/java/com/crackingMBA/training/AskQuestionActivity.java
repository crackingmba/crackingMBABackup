package com.crackingMBA.training;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.QuestionsViewAdapter;
import com.crackingMBA.training.pojo.Qstns;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class AskQuestionActivity extends AppCompatActivity {

    EditText qstnTxt;
    TextView msg;
    String url;
    boolean isMock;
    static String TAG = "AskQuestionActivvity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askquestion);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isMock = pref.getBoolean("isMock",false);
        qstnTxt = (EditText) findViewById(R.id.askqstn_qstntxt);
        msg = (TextView) findViewById(R.id.askqstn_msg);

    }

    public void submitQuestion(View v){
        String qstnStr = qstnTxt.getText().toString();
        String dateStr = String.valueOf(DateFormat.format("dd/MM/yyyy",new Date()));
        Log.d(TAG,"Submitting qstn "+qstnStr+" On "+dateStr);
        isMock = true;
        if(isMock){
            Log.d(TAG,"Submitted qstn mocked..");
            msg.setText("Your Question is Submitted Successfully");
            msg.setTextColor(Color.BLUE);
        }else {
            Log.d(TAG, "In else block");
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Qstn submitted successfully..");
                        msg.setText("Your Question is Submitted Successfully");
                        msg.setTextColor(Color.BLUE);
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
    }

}

