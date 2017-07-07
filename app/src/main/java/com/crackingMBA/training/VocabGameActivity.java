package com.crackingMBA.training;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.MockTestTopicsAdapter;
import com.crackingMBA.training.adapter.VocabGamesListAdapter;
import com.crackingMBA.training.pojo.Exam;
import com.crackingMBA.training.pojo.ExamModel;
import com.crackingMBA.training.pojo.VocabGameDetails;
import com.crackingMBA.training.pojo.VocabGameDetailsModel;
import com.crackingMBA.training.pojo.VocabGames;
import com.crackingMBA.training.pojo.VocabGamesModel;
import com.crackingMBA.training.util.MyUtil;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

public class VocabGameActivity extends AppCompatActivity {

    private static final String TAG = "VocabGameActivity";
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;

    ConstraintLayout constraintLayout;
    String vocab_game_code, vocab_game_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        vocab_game_code = getIntent().getStringExtra("VOCAB_GAME_GAMEID");
        vocab_game_name = getIntent().getStringExtra("VOCAB_GAME_GAMENAME");

        if(vocab_game_code==null){
            //constraintLayout.setVisibility(View.GONE);
            setContentView(R.layout.activity_vocab_game_recyclerview);
            recyclerView = (RecyclerView) findViewById(R.id.VocabGameRecyclerList);
            recyclerView.setHasFixedSize(true);
            getDataSet();
        }else{
            setContentView(R.layout.activity_vocab_game);
            constraintLayout = (ConstraintLayout) findViewById(R.id.VocabGamesConstraintLayout);
            getVocabGameDetails(vocab_game_code);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }





    @Override
    public void onResume() {
        super.onResume();
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


    private void getDataSet() {

        if(MyUtil.checkConnectivity(getApplicationContext())) {
            try {
                //showProgress(true, quantProgressView);

                AsyncHttpClient client = new AsyncHttpClient();
                //String mba_exam_code = getIntent().getStringExtra("VOCAB_GAME");

                client.get("http://www.crackingmba.com/getVocabGames.php", null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        VocabGamesModel vocabGamesModel = gson.fromJson(response, VocabGamesModel.class);

                        if (vocabGamesModel != null) {
                            final ArrayList<VocabGames> vocabGamesList = vocabGamesModel.getVocabGamesArrayList();


                            if(vocabGamesList!=null){
                                Log.d(TAG, "sing a song: ");

                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(mLayoutManager);

                                mAdapter = new VocabGamesListAdapter(vocabGamesList);
                                recyclerView.setAdapter(mAdapter);
                                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);
                                recyclerView.addItemDecoration(itemDecoration);

                                if (null != mAdapter) {
                                    ((VocabGamesListAdapter) mAdapter).setOnItemClickListener(
                                            new VocabGamesListAdapter.MyClickListener() {
                                                @Override
                                                public void onItemClick(int position, View v) {
                                                    Log.d(TAG, "VocabGamesListAdapter, Clicked item at position : " + position);
                                                    //VideoApplication.selectedMockTestTopic = results.get(position);
                                                    Log.d(TAG, "DingDongVocabGames: "+ vocabGamesList.get(position).getName());

                                                    Intent vocabIntent = new Intent(getApplicationContext(), VocabGameActivity.class);
                                                    vocabIntent.putExtra("VOCAB_GAME_GAMEID", vocabGamesList.get(position).getId());
                                                    vocabIntent.putExtra("VOCAB_GAME_GAMENAME", vocabGamesList.get(position).getName());

                                                    startActivity(vocabIntent);
                                                    //Log.d(TAG, "set with topic.." + VideoApplication.selectedMockTestTopic);
                                                }
                                            }
                                    );
                                }
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
                        //showProgress(false, quantProgressView);
                    }

                });

                //dilr section
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), R.string.no_internet, duration);
            toast.show();
            //TextView textView=(TextView)rootView.findViewById(R.id.networkstatus);
            //textView.setVisibility(View.VISIBLE);
            //textView.setText(R.string.no_internet);
        }


        // return null;
    }

    public String processUserAnswerEditBox(EditText t){
        String tempText=t.getText().toString();
        if(tempText==null||tempText.isEmpty()){
            tempText=" ";
        }

        return tempText;
    }

    private void getVocabGameDetails(final String vocab_game_code) {

        if(MyUtil.checkConnectivity(getApplicationContext())) {
            try {
                //showProgress(true, quantProgressView);

                AsyncHttpClient client = new AsyncHttpClient();
                //String mba_exam_code = getIntent().getStringExtra("VOCAB_GAME");

                client.get("http://www.crackingmba.com/getVocabGames.php?vocab_game_code="+vocab_game_code, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        VocabGameDetailsModel vocabGameDetailsModel = gson.fromJson(response, VocabGameDetailsModel.class);

                        if (vocabGameDetailsModel != null) {
                            final ArrayList<VocabGameDetails> vocabGamesDetailsList = vocabGameDetailsModel.getVocabGameDetails();


                            if(vocabGamesDetailsList!=null){
                                Log.d(TAG, "sing a song: ");
                                final VocabGameDetails v = vocabGamesDetailsList.get(0);
                                //TextView vocab_game_name, vocab_game_qn, vocab_game_score;
                                ((TextView)findViewById(R.id.vocab_game_name)).setText(vocab_game_name);
                                ((TextView)findViewById(R.id.vocab_game_qn)).setText(v.getQn());
                                ((TextView)findViewById(R.id.vocab_game_score)).setText("Total Points: 10");

                                final EditText vocab_game_letter1= (EditText)findViewById(R.id.vocab_game_letter1);
                                final EditText vocab_game_letter2= (EditText)findViewById(R.id.vocab_game_letter2);
                                final EditText vocab_game_letter3= (EditText)findViewById(R.id.vocab_game_letter3);
                                final EditText vocab_game_letter4= (EditText)findViewById(R.id.vocab_game_letter4);
                                final EditText vocab_game_letter5= (EditText)findViewById(R.id.vocab_game_letter5);
                                final EditText vocab_game_letter6= (EditText)findViewById(R.id.vocab_game_letter6);
                                final EditText vocab_game_letter7= (EditText)findViewById(R.id.vocab_game_letter7);
                                final EditText vocab_game_letter8= (EditText)findViewById(R.id.vocab_game_letter8);
                                final EditText vocab_game_letter9= (EditText)findViewById(R.id.vocab_game_letter9);
                                final EditText vocab_game_letter10= (EditText)findViewById(R.id.vocab_game_letter10);
                                final Button vocab_game_hint1 = (Button)findViewById(R.id.vocab_game_hint1);
                                final Button vocab_game_hint2 = (Button)findViewById(R.id.vocab_game_hint2);
                                final Button vocab_game_hint3 = (Button)findViewById(R.id.vocab_game_hint3);

                                vocab_game_letter1.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(1)});
                                vocab_game_letter2.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(1)});
                                vocab_game_letter3.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(1)});
                                vocab_game_letter4.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(1)});
                                vocab_game_letter5.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(1)});
                                vocab_game_letter6.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(1)});
                                vocab_game_letter7.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(1)});
                                vocab_game_letter8.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(1)});
                                vocab_game_letter9.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(1)});
                                vocab_game_letter10.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(1)});

                                //vocab_game_letter1.setFilters({new InputFilter.LengthFilter(1)});


                                ((Button)findViewById(R.id.vocab_game_hint2)).setEnabled(false);
                                ((Button)findViewById(R.id.vocab_game_hint3)).setEnabled(false);


                                View.OnClickListener vocabOnClickListener = new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        switch (view.getId()){
                                            case R.id.vocab_game_hint1:{
                                                ((TextView)findViewById(R.id.vocab_game_hint_textview1)).setText(v.getHint1());
                                                ((TextView)findViewById(R.id.vocab_game_score)).setText("Total Points: 6");
                                                vocab_game_hint2.setEnabled(true);
                                                vocab_game_hint2.setBackgroundResource(android.R.color.holo_orange_dark);
                                                vocab_game_hint1.setEnabled(false);
                                                break;
                                            }

                                            case R.id.vocab_game_hint2:{
                                                ((TextView)findViewById(R.id.vocab_game_hint_textview2)).setText(v.getHint2());
                                                ((TextView)findViewById(R.id.vocab_game_score)).setText("Total Points: 4");

                                                vocab_game_hint3.setEnabled(true);
                                                vocab_game_hint3.setBackgroundResource(android.R.color.holo_green_dark);
                                                vocab_game_hint2.setEnabled(false);
                                                break;
                                            }

                                            case R.id.vocab_game_hint3:{
                                                ((TextView)findViewById(R.id.vocab_game_hint_textview3)).setText(v.getHint3());
                                                ((TextView)findViewById(R.id.vocab_game_score)).setText("Total Points: 2");
                                                break;
                                            }

                                            case R.id.vocab_game_submit:{

                                                String correct_answer = v.getAnswer();
                                                int correct_answer_length=correct_answer.length();
                                                if(correct_answer_length<10){
                                                    int shortfall = 10 - correct_answer_length;

                                                    for(int i = 1 ;i <= shortfall; i++){
                                                        correct_answer+=" ";
                                                    }
                                                }


                                                String user_answer= processUserAnswerEditBox(vocab_game_letter1);

                                                user_answer+=processUserAnswerEditBox(vocab_game_letter2);
                                                user_answer+=processUserAnswerEditBox(vocab_game_letter3);
                                                user_answer+=processUserAnswerEditBox(vocab_game_letter4);
                                                user_answer+=processUserAnswerEditBox(vocab_game_letter5);
                                                user_answer+=processUserAnswerEditBox(vocab_game_letter6);
                                                user_answer+=processUserAnswerEditBox(vocab_game_letter7);
                                                user_answer+=processUserAnswerEditBox(vocab_game_letter8);
                                                user_answer+=processUserAnswerEditBox(vocab_game_letter9);
                                                user_answer+=processUserAnswerEditBox(vocab_game_letter10);

                                                ((TextView)findViewById(R.id.vocab_game_message_box)).setText("Your Answer:"
                                                        +"\n\n"+user_answer+","+"\n\n"+"Correct Answer:"+correct_answer+",");



                                                break;
                                            }
                                        }
                                        //((TextView)findViewById(R.id.vocab_game_hint_textview1)).setText(v.getQn());
                                    }
                                };

                                ((Button)findViewById(R.id.vocab_game_hint1)).setOnClickListener(vocabOnClickListener);
                                ((Button)findViewById(R.id.vocab_game_hint2)).setOnClickListener(vocabOnClickListener);
                                ((Button)findViewById(R.id.vocab_game_hint3)).setOnClickListener(vocabOnClickListener);
                                ((Button)findViewById(R.id.vocab_game_submit)).setOnClickListener(vocabOnClickListener);











                            }
                        }
                        //showProgress(false, quantProgressView);

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
                        //showProgress(false, quantProgressView);
                    }

                });

                //dilr section
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), R.string.no_internet, duration);
            toast.show();
            //TextView textView=(TextView)rootView.findViewById(R.id.networkstatus);
            //textView.setVisibility(View.VISIBLE);
            //textView.setText(R.string.no_internet);
        }


        // return null;
    }
}