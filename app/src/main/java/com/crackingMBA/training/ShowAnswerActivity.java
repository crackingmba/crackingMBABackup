package com.crackingMBA.training;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.Date;

import com.crackingMBA.training.pojo.Qstns;

/**
 * A login screen that offers login via email/password.
 */
public class ShowAnswerActivity extends AppCompatActivity {

    TextView qstnTxt;
    TextView qstnDate;
    TextView answerTxt;
    String url;
    boolean isMock;
    static String TAG = "ShowAnswerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showanswer);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isMock = pref.getBoolean("isMock",false);

        qstnTxt = (TextView) findViewById(R.id.answer_qstnTxt);
        qstnDate = (TextView) findViewById(R.id.answer_qstnDate);
        answerTxt = (TextView) findViewById(R.id.answer_answerTxt);

        Qstns askedQstn = VideoApplication.selectedQstn;
        qstnTxt.setText(askedQstn.getQstn());
        qstnDate.setText(askedQstn.getDate());
        answerTxt.setText(getAnswerForQstn(askedQstn.getQstnId()));

    }

    private String getAnswerForQstn(String qstnId){
        isMock = true;
        if(isMock){
            return "This is a mock answer for the selected question. Actual answers may vary.";
        }else{
            return "Service call goes here";
        }

    }

}
