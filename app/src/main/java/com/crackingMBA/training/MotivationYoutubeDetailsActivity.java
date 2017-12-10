package com.crackingMBA.training;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.pojo.MockTestTest;
import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.restAPI.UserEnrollmentAPIService;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MotivationYoutubeDetailsActivity extends AppCompatActivity{
    private static final int RECOVERY_REQUEST = 1;

    String motivation_video_url;
    Button motivateYT_details_btn, motivateYT_enroll_btn;
    String HTMLString; Spanned sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation_youtube_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView motivation_yt_focus_tv;

        //motivation_video_url = "baC_wGbAzU0";

        motivateYT_details_btn = (Button)findViewById(R.id.motivateYT_details_btn);
        motivateYT_enroll_btn = (Button)findViewById(R.id.motivateYT_enroll_btn);
        motivation_yt_focus_tv = (TextView)findViewById(R.id.motivation_yt_focus_tv);

        HTMLString="<p>1. Welcome to the 'SNAP & XAT 90 %ile Challenge' Course</p>"+
                "<p>2. For Rs 300, you get access to the following learning resources</p>"+
                "<p>3. All Study Videos and practice Mock Tests in this app and crackingMBA portal</p>"+
                "<p>4. 2 Mini Tests and 3 Full Length Tests (FLTs) for SNAP 2017</p>"+
                "<p>5. 2 Mini Tests and 3 Full Length Tests (FLTs) of XAT 2018</p>"+
                "<p>6. Access to all Quant, GK and Verbal Flash Cards</p>";
        sp= Html.fromHtml(HTMLString);
        motivation_yt_focus_tv.setText(sp);

        View.OnClickListener examOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.motivateYT_details_btn:{
                        Intent intent = new Intent(MotivationYoutubeDetailsActivity.this, PreparationContentActivity.class);
                        intent.putExtra("PREP_CATEGORY_CODE","XATPREP");
                        intent.putExtra("PREP_CATEGORY_NAME","testing");
                        intent.putExtra("PREP_CATEGORY_HEADER","SNAP and XAT 90 %ile Challenge");
                        startActivity(intent);
                        }
                        //loadUserEnrollmentDetails();
                        break;


                    case R.id.motivateYT_enroll_btn:{
                        Intent intent = new Intent(MotivationYoutubeDetailsActivity.this, CourseEnrollmentActivity.class);
                        intent.putExtra("PREP_CATEGORY_CODE","XATPREP");
                        startActivity(intent);
                        break;
                    }

                }
            }
        };

        motivateYT_details_btn.setOnClickListener(examOnClickListener);
        motivateYT_enroll_btn.setOnClickListener(examOnClickListener);


        /*YouTubePlayerSupportFragment frag;
        frag = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_motivation_video_fragment);
        frag.initialize(MyConfig.YOUTUBE_API_KEY, this);
*/
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


/*    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(motivation_video_url); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }

    }*/

    /*@Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }*/


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
