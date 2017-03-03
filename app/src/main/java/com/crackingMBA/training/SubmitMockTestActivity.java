package com.crackingMBA.training;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.crackingMBA.training.pojo.MockTestQuestion;
import com.crackingMBA.training.pojo.MockTestTest;

import java.util.ArrayList;
import java.util.List;

public class SubmitMockTestActivity extends AppCompatActivity {

    private static String TAG = "SubmitMockTestActivity";
    private TextView msg;
    boolean isMock;

    private static int currentQstnIndex = 0;

    private Button prevBtn;
    private TextView countDownTimerTxt;
    private CountDownTimer countDownTimer;
    private final long countDowmInterval = 1*1000;

    List<MockTestQuestionFragment> qstns = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocktest_submittest);

        msg = (TextView) findViewById(R.id.mocktest_qstn_msg);

        if(VideoApplication.allMockQstns.isEmpty()){
            showNoTestsAvailable();
        }

        prevBtn = ((Button) findViewById(R.id.mocktest_qstn_prevBtn));
        prevBtn.setVisibility(View.GONE);

        currentQstnIndex = 0;
        VideoApplication.selectedMockTestQuestion = VideoApplication.allMockQstns.get(currentQstnIndex);
        populateQstnFragments();
        toggleQstnFragment();

        countDownTimerTxt = (TextView) findViewById(R.id.mocktest_countdown_timer_txt);
        long counterStartTime = Integer.parseInt(CrackingConstant.TOTAL_TEST_DURATION)*60*1000;
        countDownTimer = new CountDownTimer(counterStartTime,countDowmInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

                String minsTxt = formatTimeTxt(String.valueOf(millisUntilFinished/60000));
                String secsTxt = formatTimeTxt(String.valueOf((millisUntilFinished%60000)/1000));

                countDownTimerTxt.setText(minsTxt+":"+secsTxt+"/"+CrackingConstant.TOTAL_TEST_DURATION+":00");
                countDownTimerTxt.setBackgroundColor(Color.rgb(241,132,1));
            }

            @Override
            public void onFinish() {
                Intent mainIntent = new Intent(getApplicationContext(),MockTestResultActivity.class);
                startActivity(mainIntent);
            }
        };
        countDownTimer.start();
    }

    private String formatTimeTxt(String unit){
        if(unit.length()==0){
            return "00";
        }else if(unit.length()==1){
            return "0"+unit;
        }else
            return unit;
    }

    private void showNoTestsAvailable(){
        setMsgText(CrackingConstant.NO_QSTNS_AVAILABLE);
        ((Button) findViewById(R.id.mocktest_qstn_skipBtn)).setVisibility(View.GONE);
        ((Button) findViewById(R.id.mocktest_qstn_submitBtn)).setVisibility(View.GONE);
    }

    private void setMsgText(String str){
        msg.setText(str);
    }
    //@Override
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (checked) {
            String selectedOption = ((RadioButton) findViewById(view.getId())).getText().toString();
            VideoApplication.selectedMockTestQuestion.setSelectedOption(selectedOption);
            Log.d(TAG, "value of option selected is " + selectedOption);
        }
    }

    private void populateQstnFragments(){
        qstns.clear();
        MockTestTest test = VideoApplication.selectedMockTestTest;
        ((TextView) findViewById(R.id.mocktest_qstn_testTitle)).setText(test.getTestTitle());
        for(MockTestQuestion q :VideoApplication.allMockQstns) {
            VideoApplication.selectedMockTestQuestion = q;
            MockTestQuestionFragment frag = new MockTestQuestionFragment();
            qstns.add(frag);
            Log.d(TAG,"Populated "+q);
        }
    }

    private void toggleQstnFragment(){
        VideoApplication.selectedMockTestQuestion = VideoApplication.allMockQstns.get(currentQstnIndex);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.question_fragment, qstns.get(currentQstnIndex));
        transaction.commit();
    }

    public void skipQuestion(View v){
        Log.d(TAG,"CLicked skipQuestion..");
        setMsgText(CrackingConstant.EMPTY_TEXT);
        if(currentQstnIndex == 0){
            prevBtn.setVisibility(View.VISIBLE);
        }
        VideoApplication.selectedMockTestQuestion.setSelectedOption(null);
        ((RadioGroup) findViewById(R.id.mocktest_qstn_radiogroup)).clearCheck();
        if(currentQstnIndex == VideoApplication.allMockQstns.size()-1){
            countDownTimer.cancel();
            Intent mainIntent = new Intent(getApplicationContext(),MockTestResultActivity.class);
            startActivity(mainIntent);
            return;
        }
        currentQstnIndex += 1;
        VideoApplication.selectedMockTestQuestion = VideoApplication.allMockQstns.get(currentQstnIndex);
        toggleQstnFragment();
    }

    public void submitAnswer(View v){
        Log.d(TAG,"CLicked submitTest..");
        setMsgText(CrackingConstant.EMPTY_TEXT);
        if(null == VideoApplication.selectedMockTestQuestion.getSelectedOption()){
            setMsgText(CrackingConstant.PLEASE_CHOOSE_AN_OPTION);
        }else{
            if(currentQstnIndex == 0){
                prevBtn.setVisibility(View.VISIBLE);
            }
            if(currentQstnIndex == VideoApplication.allMockQstns.size()-1){
                countDownTimer.cancel();
                Intent mainIntent = new Intent(getApplicationContext(),MockTestResultActivity.class);
                startActivity(mainIntent);
                return;
            }
            currentQstnIndex += 1;
            VideoApplication.selectedMockTestQuestion = VideoApplication.allMockQstns.get(currentQstnIndex);
            toggleQstnFragment();
        }
    }

    public void previousQstn(View v){
        Log.d(TAG,"CLicked prevQstn..");
        setMsgText(CrackingConstant.EMPTY_TEXT);
        if(currentQstnIndex == 1){
            prevBtn.setVisibility(View.GONE);
        }
        currentQstnIndex -= 1;
        VideoApplication.selectedMockTestQuestion = VideoApplication.allMockQstns.get(currentQstnIndex);
        toggleQstnFragment();
    }

    public void resetSelection(View v){
        Log.d(TAG,"CLicked resetSelection..");
        setMsgText(CrackingConstant.EMPTY_TEXT);
        VideoApplication.selectedMockTestQuestion.setSelectedOption(null);
        ((RadioGroup) findViewById(R.id.mocktest_qstn_radiogroup)).clearCheck();
    }
    @Override
    public void onBackPressed() {
        //Leaving intentionally blank to avoid going back to previous question
    }
}
