package com.crackingMBA.training;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.ExamAdapter;
import com.crackingMBA.training.pojo.Exam;
import com.crackingMBA.training.pojo.ExamModel;
import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;
import com.crackingMBA.training.pojo.VideoListModel;
import com.crackingMBA.training.util.MyUtil;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

public class ExamDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ExamDetailsActivity";
    TextView exam_header, exam_details, imp_dates_details, notifications_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getDataSet();



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
                    String mba_exam_code = getIntent().getStringExtra("MBA_EXAM_CODE");

                    client.get("http://www.crackingmba.com/getExams.php?exam_name="+mba_exam_code, null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d(TAG, "Response is : " + response);
                            Gson gson = new Gson();
                            ExamModel examModel = gson.fromJson(response, ExamModel.class);
                            if (examModel != null) {
                                ArrayList<Exam> exam = examModel.getExamList();
                                Log.d(TAG, "dingdong: ");
                                if(exam!=null){
                                    Log.d(TAG, "sing a song: ");
                                    Exam e= exam.get(0);
                                    String exam_name= e.getName();
                                    Log.d(TAG, "ExamName: "+exam_name);

                                    //exam_header, exam_details, imp_dates_details, notifications_details;
                                    ((TextView)findViewById(R.id.exam_header)).setText(e.getName());
                                    ((TextView)findViewById(R.id.exam_details)).setText(Html.fromHtml(e.getDescription()));
                                    ((TextView)findViewById(R.id.imp_dates_details)).setText(Html.fromHtml(e.getDates()));
                                    ((TextView)findViewById(R.id.notifications_details)).setText(Html.fromHtml(e.getNotifications()));
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

    }
}
