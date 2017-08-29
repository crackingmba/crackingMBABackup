package com.crackingMBA.training;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.restAPI.FeedbackAPIService;
import com.crackingMBA.training.restAPI.NewPostAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.MyUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {
    Button feedback_button; EditText feedback_et;
    FeedbackAPIService apiService; TextView feedback_successmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedback_button=(Button)findViewById(R.id.feedback_button);
        feedback_et=(EditText)findViewById(R.id.feedback_et);
        feedback_successmsg=(TextView)findViewById(R.id.feedback_successmsg);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        feedback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyConfig.hideKeyboard(FeedbackActivity.this);

                String feedback=feedback_et.getText().toString();
                if(TextUtils.isEmpty(feedback)){
                    Toast.makeText(FeedbackActivity.this, "Please enter the feedback and submit ", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    saveNewFeedback(feedback);
                }


            }
        });
    }

    public void saveNewFeedback(final String feedback) {
        MyUtil.showProgressDialog(FeedbackActivity.this);
        apiService = RestClient.getClient().create(FeedbackAPIService.class);
        apiService.saveFeedback(feedback).enqueue(new Callback<RetrofitPostResponse>() {
            @Override
            public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {

                MyUtil.hideProgressDialog();

                RetrofitPostResponse retrofitPostResponse = response.body();

                if(retrofitPostResponse.getResponse().equals("0")) {
                    Toast.makeText(FeedbackActivity.this, "No response from server", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(FeedbackActivity.this, "Thanks! Your feedback is saved!", Toast.LENGTH_SHORT).show();
                    feedback_successmsg.setVisibility(View.VISIBLE);
                    feedback_button.setEnabled(false);
                    feedback_button.setBackgroundColor(Color.GRAY);
                    //finish();
                }
            }

            @Override
            public void onFailure(Call<RetrofitPostResponse> call, Throwable t) {
                Toast.makeText(FeedbackActivity.this, "Some issue in saving", Toast.LENGTH_SHORT).show();
                MyUtil.hideProgressDialog();
            }
        });
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

}


