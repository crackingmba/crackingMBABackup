package com.crackingMBA.training;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class DailyDoseVideoActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener{



    String daily_dose_video_url, daily_dose_test_name; TextView daily_dose_test_name_tv;
    private static final int DD_RECOVERY_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_dose_video);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView daily_dose_test_name_tv = (TextView) findViewById(R.id.daily_dose_test_name_tv);

        daily_dose_video_url = getIntent().getStringExtra("DAILY_DOSE_VIDEO_URL");
        daily_dose_test_name = getIntent().getStringExtra("DAILY_DOSE_TEST_NAME");
        Typeface custom_font=Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Raleway-Light.ttf");
        daily_dose_test_name_tv.setTypeface(custom_font);

        daily_dose_test_name_tv.setText(daily_dose_test_name + " Analysis Video");

        YouTubePlayerSupportFragment frag;
        frag = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_daily_dose_video_fragment);
        frag.initialize(MyConfig.YOUTUBE_API_KEY, this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(daily_dose_video_url); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, DD_RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
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
