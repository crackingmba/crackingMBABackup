package com.crackingMBA.training;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.adapter.PreparationHLContentAdapter;
import com.crackingMBA.training.interfaces.ClickListener;
import com.crackingMBA.training.pojo.MockTestTest;
import com.crackingMBA.training.pojo.RetrofitPrepHLContent;
import com.crackingMBA.training.pojo.RetrofitPrepHLContentList;
import com.crackingMBA.training.restAPI.PrepHLContentAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.util.RecyclerItemClickListener;
import com.instamojo.android.Instamojo;
import com.instamojo.android.activities.PaymentDetailsActivity;
import com.instamojo.android.callbacks.OrderRequestCallBack;
import com.instamojo.android.helpers.Constants;
import com.instamojo.android.models.Errors;
import com.instamojo.android.models.Order;
import com.instamojo.android.network.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreparationHLContentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PreparationHLContentAdapter adapter;
    List<RetrofitPrepHLContent> questions = new ArrayList<>();
    Call<RetrofitPrepHLContentList> call;
    PrepHLContentAPIService apiService;
    String course_category;String str;int index;
    TextView prep_content_header;
    int instamojo_check_1=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparation_hlcontent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Instamojo.initialize(this);


        //Request request = new Request()

        apiService = RestClient.getClient().create(PrepHLContentAPIService.class);
        recyclerView = (RecyclerView)findViewById(R.id.prepHLcontentRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(PreparationHLContentActivity.this));
        prep_content_header=(TextView)findViewById(R.id.prep_content_header889);

        adapter = new PreparationHLContentAdapter(questions, R.layout.retrofit_prep_study_content_layout, PreparationHLContentActivity.this, new ClickListener() {
            @Override
            public void onPositionClicked(int selected_position) {

                int position=0;

                if(selected_position<9){
                    index=selected_position;
                    position=0;
                }else{
                    index=selected_position%10;
                    position=(selected_position-index)/10;
                }

                Intent intent = new Intent(PreparationHLContentActivity.this, PreparationDetailsActivity.class);
                intent.putExtra("PREP_CATEGORY_ID",questions.get(position).getId());
                intent.putExtra("PREP_CATEGORY_NAME",questions.get(position).getName());

                course_category=questions.get(position).getCategory();

                switch(course_category){
                    case "CAT":{
                        course_category="CAT 2017 Preparation Course";
                        break;
                    }
                    case "IIFT":{
                        course_category="IIFT 2017 Preparation Course";
                        break;
                    }
                    case "SNAP":{
                        course_category="SNAP 2017 Preparation Course";
                        break;
                    }

                    case "XAT":{
                        course_category="XAT 2018 Preparation Course";
                        break;
                    }

                }

                switch(index){
                    case 1:{
                        processRows(questions.get(position).getStudy1(), questions.get(position).getStudy1Type());
                        break;
                    }
                    case 2:{
                        processRows(questions.get(position).getStudy2(), questions.get(position).getStudy2Type());
                        break;
                    }
                    case 3:{
                        processRows(questions.get(position).getStudy3(), questions.get(position).getStudy3Type());
                        break;
                    }
                    case 4:{
                        processRows(questions.get(position).getStudy4(), questions.get(position).getStudy4Type());
                        break;
                    }
                    case 5:{
                        processRows(questions.get(position).getStudy5(), questions.get(position).getStudy5Type());
                        break;
                    }
                    case 6:{
                        processRows(questions.get(position).getStudy6(), questions.get(position).getStudy6Type());
                        break;
                    }
                }
            }

            @Override
            public void onLongClicked(int position) {
                // callback performed on click
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(PreparationHLContentActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {
                    }
                })
        );


        String prep_category_code = getIntent().getStringExtra("PREP_CATEGORY_CODE");
        String prep_category_name = getIntent().getStringExtra("PREP_CATEGORY_NAME");
        String prep_category_header = getIntent().getStringExtra("PREP_CATEGORY_HEADER");
        //prep_category_code="GK";
        prep_content_header.setText(prep_category_header);
        call = apiService.fetchPrepHLContent(prep_category_code, prep_category_name);
        if(MyUtil.checkConnectivity(getApplicationContext())) {

            MyUtil.showProgressDialog(this);
            fetchPrepContentList();
        }
        else{
            Toast.makeText(this, "Sorry. There is no internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void startPreCreatedUI(Order order){
        //Using Pre created UI
        Intent intent = new Intent(getBaseContext(), PaymentDetailsActivity.class);
        intent.putExtra(Constants.ORDER, order);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE && data != null) {
            String orderID = data.getStringExtra(Constants.ORDER_ID);
            String transactionID = data.getStringExtra(Constants.TRANSACTION_ID);
            String paymentID = data.getStringExtra(Constants.PAYMENT_ID);

            // Check transactionID, orderID, and orderID for null before using them to check the Payment status.
            if (orderID != null && transactionID != null && paymentID != null) {
                //Check for Payment status with Order ID or Transaction ID
            } else {
                //Oops!! Payment was cancelled
            }
        }
    }

    private void fetchPrepContentList() {
        call.enqueue(new Callback<RetrofitPrepHLContentList>() {
            @Override
            public void onResponse(Call<RetrofitPrepHLContentList> call, Response<RetrofitPrepHLContentList> response) {
                MyUtil.hideProgressDialog();

                if(response.body() == null){
                    //comments_comments_not_added.setVisibility(View.VISIBLE);
                    return;
                }else{
                    questions.addAll(response.body().getQuestions());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RetrofitPrepHLContentList> call, Throwable t) {
            }
        });
    }


    private void processRows(String study, String studyType){

                switch(studyType){
                    case "mocktest":{
                        str = study;
                        String test_name = str.substring(0,str.indexOf(","));

                        str= study;
                        String test_id=str.substring(str.indexOf(",") + 1);

                        MockTestTest mockTestTest = new MockTestTest(test_id, "1000", "numbers1.png",test_name);
                        VideoApplication.selectedMockTestTest= mockTestTest;

                        Intent startIntent = new Intent(getApplicationContext(), StartMockTestActivity.class);
                        startActivity(startIntent);
                        break;
                    }
                    case "video":{
                        str = study;
                        String course_name = str.substring(0,str.indexOf(","));

                        str= study;
                        String url=str.substring(str.indexOf(",") + 1);

                        Intent intent = new Intent(getApplicationContext(), TargetVideoActivity.class);
                        intent.putExtra("COURSE_NAME",course_category);
                        intent.putExtra("COURSE_SUBJECT",course_name);
                        intent.putExtra("URL",url);
                        startActivity(intent);
                        break;
                    }
                    case "text":{
/*                        str = study;
                        String course_name = str.substring(0,str.indexOf(","));*/

                        str= study;
                        String webview_url=str.substring(str.indexOf(",") + 1);
                        Intent intent = new Intent(getApplicationContext(), ViewPDFDetailsActivity.class);
                        //intent.putExtra("COURSE_NAME",course_category);
                        //intent.putExtra("COURSE_SUBJECT",course_name);
                        intent.putExtra("WEBVIEW_URL",webview_url);
                        //startActivity(intent);


  /*                      Intent intent1 = new Intent();
                        intent1.setAction(Intent.ACTION_VIEW);
                        intent1.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent1.setData(Uri.parse("https://imjo.in/ymvnAQ"));
                        startActivity(intent1);*/

                        Random r = new Random();
                        String trxn_id="t"+r.nextInt(1000+1);

                        instamojo_check_1++;
                        Order order = new Order("2M4cdCIiXkzLAOVOxCnt0r7z2PRzmg", trxn_id, "test", "test@gmail.com", "9823498234", "100", "banking");

                        // Good time to show progress dialog to user
                        Request request = new Request(order, new OrderRequestCallBack() {
                            @Override
                            public void onFinish(Order order, Exception error) {
                                //dismiss the dialog if showed

                                // Make sure the follwoing code is called on UI thread to show Toasts or to
                                //update UI elements
                                if (error != null) {
                                    if (error instanceof Errors.ConnectionError) {
                                        Log.e("App", "No internet connection");
                                    } else if (error instanceof Errors.ServerError) {
                                        Log.e("App", "Server Error. Try again");
                                    } else if (error instanceof Errors.AuthenticationError){
                                        Log.e("App", "Access token is invalid or expired");
                                    } else if (error instanceof Errors.ValidationError){
                                        // Cast object to validation to pinpoint the issue
                                        Errors.ValidationError validationError = (Errors.ValidationError) error;
                                        if (!validationError.isValidTransactionID()) {
                                            Log.e("App", "Transaction ID is not Unique");
                                            return;
                                        }
                                        if (!validationError.isValidRedirectURL()) {
                                            Log.e("App", "Redirect url is invalid");
                                            return;
                                        }


                                        if (!validationError.isValidWebhook()) {
                                            Toast.makeText(PreparationHLContentActivity.this, "Webhook url is invalid", Toast.LENGTH_SHORT).show();
//                                    /showToast("Webhook url is invalid");
                                            return;
                                        }

                                        if (!validationError.isValidPhone()) {
                                            Log.e("App", "Buyer's Phone Number is invalid/empty");
                                            return;
                                        }
                                        if (!validationError.isValidEmail()) {
                                            Log.e("App", "Buyer's Email is invalid/empty");
                                            return;
                                        }
                                        if (!validationError.isValidAmount()) {
                                            Log.e("App", "Amount is either less than Rs.9 or has more than two decimal places");
                                            return;
                                        }
                                        if (!validationError.isValidName()) {
                                            Log.e("App", "Buyer's Name is required");
                                            return;
                                        }
                                    } else {
                                        Log.e("App", error.getMessage());
                                    }
                                    return;
                                }

                                startPreCreatedUI(order);
                            }
                        });

                        request.execute();
                        break;
                    }
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
