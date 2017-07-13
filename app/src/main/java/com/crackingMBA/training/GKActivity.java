package com.crackingMBA.training;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.GKTopicsListAdapter;
import com.crackingMBA.training.adapter.VocabGamesListAdapter;
import com.crackingMBA.training.pojo.GKTopicDetails;
import com.crackingMBA.training.pojo.GKTopics;
import com.crackingMBA.training.pojo.GKTopicsModel;
import com.crackingMBA.training.pojo.VocabGameDetails;
import com.crackingMBA.training.pojo.VocabGameDetailsModel;
import com.crackingMBA.training.pojo.VocabGames;
import com.crackingMBA.training.pojo.VocabGamesModel;
import com.crackingMBA.training.util.MyUtil;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

public class GKActivity extends AppCompatActivity {

    private static final String TAG = "GKActivity";
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;

    ConstraintLayout constraintLayout;
    String gk_id, gk_name = "";
    int qn_number=0;
    ArrayList<GKTopicDetails> gkTopicDetails;
    GKTopicDetails v;
    EditText vocab_game_letter1, vocab_game_letter2, vocab_game_letter3, vocab_game_letter4, vocab_game_letter5, vocab_game_letter6, vocab_game_letter7;
    EditText vocab_game_letter8, vocab_game_letter9, vocab_game_letter10;
    Button vocab_game_hint1, vocab_game_hint2, vocab_game_hint3, vocab_game_skip, vocab_game_submit, vocab_game_next;
    TextView vocab_game_message_box, vocab_game_hint_textview1, vocab_game_hint_textview2, vocab_game_hint_textview3, vocab_game_score;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        gk_id = getIntent().getStringExtra("GK_ID");
        gk_name = getIntent().getStringExtra("GK_NAME");

        if (gk_id == null) {
            //constraintLayout.setVisibility(View.GONE);
            setContentView(R.layout.activity_gk);
            recyclerView = (RecyclerView) findViewById(R.id.GKActivityRecyclerList);
            recyclerView.setHasFixedSize(true);
            getDataSet();
        } else {
            /*setContentView(R.layout.activity_vocab_game);
            constraintLayout = (ConstraintLayout) findViewById(R.id.VocabGamesConstraintLayout);
            //getVocabGameDetails(vocab_game_code, vocab_game_name);
            getVocabGameDetailsFromServer(vocab_game_code, vocab_game_name);

            vocab_game_letter1 = (EditText) findViewById(R.id.vocab_game_letter1);
            vocab_game_letter2 = (EditText) findViewById(R.id.vocab_game_letter2);
            vocab_game_letter3 = (EditText) findViewById(R.id.vocab_game_letter3);
            vocab_game_letter4 = (EditText) findViewById(R.id.vocab_game_letter4);
            vocab_game_letter5 = (EditText) findViewById(R.id.vocab_game_letter5);
            vocab_game_letter6 = (EditText) findViewById(R.id.vocab_game_letter6);
            vocab_game_letter7 = (EditText) findViewById(R.id.vocab_game_letter7);
            vocab_game_letter8 = (EditText) findViewById(R.id.vocab_game_letter8);
            vocab_game_letter9 = (EditText) findViewById(R.id.vocab_game_letter9);
            vocab_game_letter10 = (EditText) findViewById(R.id.vocab_game_letter10);

            vocab_game_hint1 = (Button) findViewById(R.id.vocab_game_hint1);
            vocab_game_hint2 = (Button) findViewById(R.id.vocab_game_hint2);
            vocab_game_hint3 = (Button) findViewById(R.id.vocab_game_hint3);
            vocab_game_skip = (Button) findViewById(R.id.vocab_game_skip);
            vocab_game_submit = (Button) findViewById(R.id.vocab_game_submit);
            vocab_game_next = (Button) findViewById(R.id.vocab_game_next);

            vocab_game_message_box = (TextView) findViewById(R.id.vocab_game_message_box);
            vocab_game_hint_textview1 = (TextView) findViewById(R.id.vocab_game_hint_textview1);
            vocab_game_hint_textview2 = (TextView) findViewById(R.id.vocab_game_hint_textview2);
            vocab_game_hint_textview3 = (TextView) findViewById(R.id.vocab_game_hint_textview3);
            vocab_game_score = (TextView) findViewById(R.id.vocab_game_score);*/
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

                client.get("http://www.crackingmba.com/getGKTopicList.php", null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        GKTopicsModel gkTopicsModel = gson.fromJson(response, GKTopicsModel.class);

                        if (gkTopicsModel != null) {
                            final ArrayList<GKTopics> gkTopicsList = gkTopicsModel.getGKTopics();


                            if (gkTopicsList != null) {
                                Log.d(TAG, "sing a song: ");

                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(mLayoutManager);

                                mAdapter = new GKTopicsListAdapter(gkTopicsList);
                                recyclerView.setAdapter(mAdapter);
                                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);
                                recyclerView.addItemDecoration(itemDecoration);

                                if (null != mAdapter) {
                                    ((GKTopicsListAdapter) mAdapter).setOnItemClickListener(
                                            new GKTopicsListAdapter.MyClickListener() {
                                                @Override
                                                public void onItemClick(int position, View v) {
                                                    Log.d(TAG, "GKTopicsListAdapter, Clicked item at position : " + position);
                                                    //VideoApplication.selectedMockTestTopic = results.get(position);
                                                    Intent gkIntent = new Intent(getApplicationContext(), GKActivity.class);
                                                    gkIntent.putExtra("GK_ID", gkTopicsList.get(position).getId());
                                                    //vocabIntent.putExtra("VOCAB_GAME_GAMEQN", 0);
                                                    //vocabIntent.putExtra("VOCAB_GAME_GAMENAME", vocabGamesList.get(position).getName());

                                                    startActivity(gkIntent);
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
            //TextView textView=(TextView)rootView.findViewById(R.id.networkstatus);
            //textView.setVisibility(View.VISIBLE);
            //textView.setText(R.string.no_internet);
        }


        // return null;
    }


}
