package com.crackingMBA.training;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class MotivationYoutubeDetailsActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener{
    private static final int RECOVERY_REQUEST = 1;

    String motivation_video_url;
    Button motivateYT_details_btn;ImageView imgView;
    TextView motivation_tv, motivation_yt_focus_tv;
    //TextView videoName, videoDescription;
    String sectionName, exam_name_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation_youtube_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView motivation_yt_focus_tv = (TextView) findViewById(R.id.motivation_yt_focus_tv);
        ImageView imgView = (ImageView) findViewById(R.id.motivation_yt_focus_img);


        sectionName = getIntent().getStringExtra("EXAM_NAME");
        exam_name_text= getIntent().getStringExtra("EXAM_NAME_TEXT");
        switch (sectionName){
            case "RC":
            {
                Drawable myDrawable = getResources().getDrawable(R.drawable.focus_rc);
                imgView.setImageDrawable(myDrawable);
                motivation_yt_focus_tv.setText(exam_name_text);
                motivation_video_url = "dwH-dAEYgyM";
                //videoName.setText("'Focus' Quant");
                //videoDescription.setText("The details for Quant Section Go Here!");
                break;
            }
            case "QUANT":
            {
                Drawable myDrawable = getResources().getDrawable(R.drawable.focus_quant);
                imgView.setImageDrawable(myDrawable);
                motivation_yt_focus_tv.setText(exam_name_text);
                motivation_video_url = "HeS21JBzmg4";
                //videoName.setText("'Focus' Verbal");
                //videoDescription.setText("The details for Verbal Section Go Here!");
                break;
            }
            case "GK":
            {
                Drawable myDrawable = getResources().getDrawable(R.drawable.focus_gk);
                imgView.setImageDrawable(myDrawable);
                motivation_yt_focus_tv.setText(exam_name_text);
                motivation_video_url = "AaScU2v4Uec";
                //videoName.setText("'Focus' DI & LR");
                //videoDescription.setText("The details for DI & LR Section Go Here!");
                break;
            }
/*            case "XAT":
            {
                Drawable myDrawable = getResources().getDrawable(R.drawable.focus_xat);
                imgView.setImageDrawable(myDrawable);
                motivation_yt_focus_tv.setText(exam_name_text);
                break;
            }*/
        }

        //videoName=(TextView)findViewById(R.id.motivation_youtube_video_name);
        //videoDescription=(TextView)findViewById(R.id.motivation_youtube_video_description);

        motivateYT_details_btn = (Button)findViewById(R.id.motivateYT_details_btn);

        View.OnClickListener examOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.motivateYT_details_btn:{
                        Intent intent = new Intent(MotivationYoutubeDetailsActivity.this, PreparationContentActivity.class);

                        switch (sectionName){
                            case "CAT":
                            {
                                intent.putExtra("PREP_CATEGORY_CODE","CATPREP1");
                                intent.putExtra("PREP_CATEGORY_HEADER","Focus CAT Preparation Course");
                                break;
                            }
                            case "IIFT":
                            {
                                intent.putExtra("PREP_CATEGORY_CODE","IIFTPREP1");
                                intent.putExtra("PREP_CATEGORY_HEADER","Focus IIFT Preparation Course");
                                break;
                            }
                            case "SNAP":
                            {
                                intent.putExtra("PREP_CATEGORY_CODE","SNAPPREP1");
                                intent.putExtra("PREP_CATEGORY_HEADER","Focus SNAP Preparation Course");
                                break;
                            }
/*                            case "XAT":
                            {
                                intent.putExtra("PREP_CATEGORY_CODE","XATPREP");
                                intent.putExtra("PREP_CATEGORY_HEADER","Focus XAT Preparation Course");
                                break;
                            }*/
                        }



                        startActivity(intent);
                        //ViewPager viewPager=(ViewPager)getActivity().findViewById(R.id.container);
                        //viewPager.setCurrentItem(1, true);
                        break;
                    }

                }
            }
        };

        motivateYT_details_btn.setOnClickListener(examOnClickListener);



        YouTubePlayerSupportFragment frag;
        frag = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_motivation_video_fragment);
        frag.initialize(MyConfig.YOUTUBE_API_KEY, this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(motivation_video_url); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
