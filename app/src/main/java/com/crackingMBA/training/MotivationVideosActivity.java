package com.crackingMBA.training;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.motivationVideosListAdapter;
import com.crackingMBA.training.pojo.MotivationVideos;
import com.crackingMBA.training.pojo.MotivationVideosModel;
import com.crackingMBA.training.util.MyUtil;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

public class MotivationVideosActivity extends AppCompatActivity {

    private static final String TAG = "MotivationActivity";
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    LinearLayout lin;

    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String motivation_video_id = getIntent().getStringExtra("MOTIVATION_VIDEO_VIDEOID");

        if (motivation_video_id == null) {
            setContentView(R.layout.activity_motivation_recyclerview);
            lin=(LinearLayout)findViewById(R.id.motivation_video_slog_mba);
            Typeface tf = Typeface.createFromAsset(getAssets(),
                    "fonts/Raleway-Light.ttf");
            TextView tv = (TextView)findViewById(R.id.slogMBA_TextView);
            tv.setTypeface(tf);

            View.OnClickListener motivationalOnClickListener = new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    switch (view.getId()){
                        case R.id.motivation_video_slog_mba:{
                            Log.d(TAG, "onClick: dingdongsingasong");
                            Intent motivationVideoDetails = new Intent(getApplicationContext(), MotivationYoutubeDetailsActivity.class);
                            motivationVideoDetails.putExtra("MOTIVATION_VIDEO_URL", "ueLbSYiSWCs");
                            motivationVideoDetails.putExtra("MOTIVATION_VIDEO_NAME", "Why you need to slog your *** off and crack MBA this year!");
                            motivationVideoDetails.putExtra("MOTIVATION_VIDEO_DESCRIPTION", "This is a brief video to help you pull up your socks and get going. If you see a dip in your performance in the mock tests or are just not getting the right kind " +
                                    "of motivation, this video should help you pull up your sagging spirits and enable you to keep your tempo high!");
                            startActivity(motivationVideoDetails);
                            break;
                        }

                    }

                }
            };

            lin.setOnClickListener(motivationalOnClickListener);
            //recyclerView = (RecyclerView) findViewById(R.id.MotivationRecyclerList);
            //recyclerView.setHasFixedSize(true);
            //getDataSet();
        } else {
            //setContentView(R.layout.activity_motivation_youtube_video_layout);
            //constraintLayout = (ConstraintLayout) findViewById(R.id.MotivationVideosConstraintLayout);
            Log.d(TAG, "onClick: heythere");
            //getVocabGameDetailsFromServer(motivation_video_id);

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

        if (MyUtil.checkConnectivity(getApplicationContext())) {
            try {
                //showProgress(true, quantProgressView);

                AsyncHttpClient client = new AsyncHttpClient();
                //String mba_exam_code = getIntent().getStringExtra("VOCAB_GAME");

                client.get("http://www.crackingmba.com/getMotivationVideos.php", null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        MotivationVideosModel motivationVideosModel = gson.fromJson(response, MotivationVideosModel.class);

                        if (motivationVideosModel != null) {
                            final ArrayList<MotivationVideos> motivationVideosList = motivationVideosModel.getMotivationVideos();


                            if (motivationVideosList != null) {
                                Log.d(TAG, "sing a song: ");

                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(mLayoutManager);

                                mAdapter = new motivationVideosListAdapter(motivationVideosList);
                                recyclerView.setAdapter(mAdapter);
                                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);
                                recyclerView.addItemDecoration(itemDecoration);

                                if (null != mAdapter) {
                                    ((motivationVideosListAdapter) mAdapter).setOnItemClickListener(
                                            new motivationVideosListAdapter.MyClickListener() {
                                                @Override
                                                public void onItemClick(int position, View v) {
                                                    Log.d(TAG, "motivationVideosListAdapter, Clicked item at position : " + position);
                                                    //VideoApplication.selectedMockTestTopic = results.get(position);
                                                    //Log.d(TAG, "DingDongVocabGames: " + vocabGamesList.get(position).getName());

                                                    Intent motivationVideosIntent = new Intent(getApplicationContext(), MotivationVideosActivity.class);
                                                    //vocabIntent.putExtra("VOCAB_GAME_GAMEID", vocabGamesList.get(position).getId());
                                                    //vocabIntent.putExtra("VOCAB_GAME_GAMEQN", 0);
                                                    motivationVideosIntent.putExtra("MOTIVATION_VIDEO_VIDEOID", motivationVideosList.get(position).getId());

                                                    startActivity(motivationVideosIntent);
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

        } else {
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), R.string.no_internet, duration);
            toast.show();
        }


        // return null;
    }

    public String processUserAnswerEditBox(EditText t) {
        String tempText = t.getText().toString();
        if (tempText == null || tempText.isEmpty()) {
            tempText = " ";
        }

        return tempText;
    }

    private void getVocabGameDetails() {

       /* if (this.vocabGamesDetailsList != null) {

            v = this.vocabGamesDetailsList.get(qn_number);
            Log.d(TAG, "sing a song with a qn_number of " +qn_number);
            //TextView vocab_game_name, vocab_game_qn, vocab_game_score;
            ((TextView) findViewById(R.id.vocab_game_name)).setText(vocab_game_name);
            ((TextView) findViewById(R.id.vocab_game_qn)).setText(v.getQn());
            ((TextView) findViewById(R.id.vocab_game_score)).setText("Total Points: 10");

            View.OnClickListener vocabOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (view.getId()) {
                        case R.id.vocab_game_hint1: {
                            vocab_game_hint_textview1.setText(v.getHint1());
                            vocab_game_score.setText("Total Points: 6");

                            vocab_game_hint2.setEnabled(true);
                            vocab_game_hint2.setBackgroundResource(android.R.color.holo_orange_dark);
                            //vocab_game_hint1.setEnabled(false);
                            break;
                        }

                        case R.id.vocab_game_hint2: {
                            vocab_game_hint_textview2.setText(v.getHint2());
                            vocab_game_score.setText("Total Points: 4");

                            vocab_game_hint3.setEnabled(true);
                            vocab_game_hint3.setBackgroundResource(android.R.color.holo_green_dark);
                            //vocab_game_hint2.setEnabled(false);
                            break;
                        }

                        case R.id.vocab_game_hint3: {
                            vocab_game_hint_textview3.setText(v.getHint3());
                            vocab_game_score.setText("Total Points: 2");
                            break;
                        }

                        case R.id.vocab_game_skip: {
                            //Display the answer
                            //Enable the Next Button
                            vocab_game_message_box.setVisibility(View.VISIBLE);
                            vocab_game_message_box.setBackgroundResource(android.R.color.holo_purple);
                            vocab_game_message_box.setText("You can move to next question!");

                            vocab_game_next.setEnabled(true);
                            vocab_game_next.setBackgroundResource(android.R.color.holo_purple);
                            break;
                        }

                        case R.id.vocab_game_next: {
                            qn_number++;
                            if(qn_number<vocabGamesDetailsList.size()){
                                resetVocabGameFields();
                                getVocabGameDetails();
                            }else{
                                resetVocabGameFields();
                                vocab_game_message_box.setVisibility(View.VISIBLE);
                                vocab_game_message_box.setBackgroundResource(android.R.color.holo_orange_light);
                                vocab_game_message_box.setText("You have reached the end of this Vocab Game!");
                                vocab_game_skip.setBackgroundResource(R.color.com_facebook_button_background_color_disabled);
                                vocab_game_next.setBackgroundResource(R.color.com_facebook_button_background_color_disabled);
                                vocab_game_skip.setEnabled(false);
                                vocab_game_next.setEnabled(false);
                            }

                            break;
                        }

                        case R.id.vocab_game_submit: {
                            //Check if all the hints are displayed or else suggest to take a hint
                            //If all the hints are taken, then compare the user ans with correct ans and display result
                            //Enable the Next Button
                            String correct_answer = v.getAnswer();
                            int correct_answer_length = correct_answer.length();
                            if (correct_answer_length < 10) {
                                int shortfall = 10 - correct_answer_length;

                                for (int i = 1; i <= shortfall; i++) {
                                    correct_answer += " ";
                                }
                            }


                            String user_answer = processUserAnswerEditBox(vocab_game_letter1);

                            user_answer += processUserAnswerEditBox(vocab_game_letter2);
                            user_answer += processUserAnswerEditBox(vocab_game_letter3);
                            user_answer += processUserAnswerEditBox(vocab_game_letter4);
                            user_answer += processUserAnswerEditBox(vocab_game_letter5);
                            user_answer += processUserAnswerEditBox(vocab_game_letter6);
                            user_answer += processUserAnswerEditBox(vocab_game_letter7);
                            user_answer += processUserAnswerEditBox(vocab_game_letter8);
                            user_answer += processUserAnswerEditBox(vocab_game_letter9);
                            user_answer += processUserAnswerEditBox(vocab_game_letter10);

                            vocab_game_message_box.setVisibility(View.VISIBLE);

                            if(user_answer.equals(correct_answer)){
                                vocab_game_message_box.setBackgroundResource(android.R.color.holo_green_light);
                                vocab_game_message_box.setText("Awesome! \n\n You are a vocab rockstar!");
                            }else{
                                vocab_game_message_box.setBackgroundResource(android.R.color.holo_red_light);
                                vocab_game_message_box.setText("Oops!" + "\n\n" + "Correct Answer:\n\n" +correct_answer);
                            }

                            vocab_game_next.setEnabled(true);
                            vocab_game_next.setBackgroundResource(android.R.color.holo_purple);

                            break;
                        }
                    }
                    //((TextView)findViewById(R.id.vocab_game_hint_textview1)).setText(v.getQn());
                }
            };

            vocab_game_hint1.setOnClickListener(vocabOnClickListener);
            vocab_game_hint2.setOnClickListener(vocabOnClickListener);
            vocab_game_hint3.setOnClickListener(vocabOnClickListener);
            vocab_game_submit.setOnClickListener(vocabOnClickListener);
            vocab_game_skip.setOnClickListener(vocabOnClickListener);
            vocab_game_next.setOnClickListener(vocabOnClickListener);
        }*/

    }


    private void getVocabGameDetailsFromServer(String motivation_video_id) {

      /*  if (MyUtil.checkConnectivity(getApplicationContext())) {
            try {
                //showProgress(true, quantProgressView);

                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://www.crackingmba.com/getVocabGames.php?vocab_game_code=" + vocab_game_code, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response from server is : " + response);
                        Gson gson = new Gson();
                        VocabGameDetailsModel vocabGameDetailsModel = gson.fromJson(response, VocabGameDetailsModel.class);

                        if (vocabGameDetailsModel != null) {
                            vocabGamesDetailsList = vocabGameDetailsModel.getVocabGameDetails();

                            if (vocabGamesDetailsList != null) {
                                getVocabGameDetails();
                                //final VocabGameDetails v = vocabGamesDetailsList.get(0);
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
        } else {
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), R.string.no_internet, duration);
            toast.show();
        }*/

    }

}
