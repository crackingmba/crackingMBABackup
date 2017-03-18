package com.crackingMBA.training;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.crackingMBA.training.pojo.Question;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isMock = pref.getBoolean("isMock",false);

        qstnTxt = (TextView) findViewById(R.id.answer_qstnTxt);
        qstnDate = (TextView) findViewById(R.id.answer_qstnDate);
        answerTxt = (TextView) findViewById(R.id.answer_answerTxt);

        Question askedQstn = VideoApplication.selectedQstn;
        qstnTxt.setText(askedQstn.getQnText());
        qstnDate.setText(askedQstn.getQnAnswerDate());
      //  answerTxt.setText(getAnswerForQstn(askedQstn.getQnAnswer()));
        answerTxt.setText(askedQstn.getQnAnswer());
    }

    private String getAnswerForQstn(String qstnId){
        isMock = false;
        if(isMock){
            return "This is a mock answer for the selected question. Actual answers may vary.";
        }else{
            return "Service call goes here";
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

