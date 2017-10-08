package com.crackingMBA.training;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crackingMBA.training.pojo.MockTestTest;
import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.restAPI.ServerKeyAPIService;
import com.crackingMBA.training.restAPI.UserEnrollmentAPIService;
import com.instamojo.android.Instamojo;
import com.instamojo.android.activities.PaymentDetailsActivity;
import com.instamojo.android.callbacks.OrderRequestCallBack;
import com.instamojo.android.helpers.Constants;
import com.instamojo.android.models.Errors;
import com.instamojo.android.models.Order;
import com.instamojo.android.network.Request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseEnrollmentActivity extends AppCompatActivity {
    EditText enroll_transaction_id_et, enroll_course_name_et, enroll_email_id_et, enroll_name_et;
    Button enroll_payment_btn;
    ProgressDialog mTestProgressDialog;
    String serverKey, email;String trxn_id, user_name;
    ServerKeyAPIService serverKeyAPIService;String prep_category_code;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enrollment);

        Instamojo.initialize(this);

        prep_category_code = getIntent().getStringExtra("PREP_CATEGORY_CODE");

        switch(prep_category_code){
            case "CATPREP1":{
                prep_category_code="Focus CAT";
                break;
            }

            case "IIFTPREP1":{
                prep_category_code="Focus IIFT";
                break;
            }

            case "SNAPPREP1":{
                prep_category_code="Focus SNAP";
                break;
            }

            case "XATPREP":{
                prep_category_code="Focus XAT";
                break;
            }
        }

        enroll_transaction_id_et=(EditText)findViewById(R.id.enroll_transaction_id_et);
        enroll_course_name_et=(EditText)findViewById(R.id.enroll_course_name_et);
        enroll_email_id_et=(EditText)findViewById(R.id.enroll_email_id_et);
        enroll_name_et=(EditText)findViewById(R.id.enroll_name_et);


        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);

        if(isUserLoggedIn){
            email = prefs.getString("emailofUser", "");
            user_name=prefs.getString("nameofUser", "");
        }else{
            email=""; user_name="";
        }


        enroll_payment_btn=(Button)findViewById(R.id.enroll_payment_btn);
        enroll_transaction_id_et.setText("1234");
        enroll_course_name_et.setText("SNAP");
        enroll_email_id_et.setText(email);
        enroll_name_et.setText(user_name);
        //key="5I6WbB8NaT3FqnPDhNQ44XLj4j3Iem";


        //MyUtil.showProgressDialog(PreparationHLContentActivity.this);
        serverKeyAPIService = RestClient.getClient().create(ServerKeyAPIService.class);
        serverKeyAPIService.getKeyFromServer("key").enqueue(new Callback<RetrofitPostResponse>() {


                @Override
                public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {
                    RetrofitPostResponse retrofitPostResponse = response.body();
                    serverKey= retrofitPostResponse.getResponse();
                }

                @Override
                public void onFailure(Call<RetrofitPostResponse> call, Throwable t) {
                }
            });


        Random r = new Random();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        trxn_id = formatter.format(date)+ r.nextInt(100000+1);


        enroll_transaction_id_et.setText(trxn_id);
        enroll_course_name_et.setText(prep_category_code);

        enroll_payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    user_name=enroll_name_et.getText().toString();
                    email=enroll_email_id_et.getText().toString();

                    Order order = new Order(serverKey, trxn_id, user_name, email, "9823498234", "10", prep_category_code);

                    // Good time to show progress dialog to user
                    //MyUtil.showProgressDialog();
                    mTestProgressDialog = new ProgressDialog(CourseEnrollmentActivity.this);
                    mTestProgressDialog.setMessage("Loading Payment Options");
                    mTestProgressDialog.setIndeterminate(true);
                    mTestProgressDialog.setCanceledOnTouchOutside(false);

                    mTestProgressDialog.show();
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
                                        Toast.makeText(CourseEnrollmentActivity.this, "Webhook url is invalid", Toast.LENGTH_SHORT).show();
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

                    //request.execute();
                    Intent intent= new Intent(CourseEnrollmentActivity.this, PaymentSuccessActivity.class);

                    intent.putExtra("COURSE_NAME",prep_category_code);
                    intent.putExtra("NAME_OF_USER",user_name);
                    intent.putExtra("EMAIL_OF_USER",email);
                    //ed.commit();

                    //pass the details of course, name and email to the next page
                    //set the preferences here
                    //submit the enrollment data to the server

                    startActivity(intent);
                    finish();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void startPreCreatedUI(Order order){
        //Using Pre created UI
        mTestProgressDialog.dismiss();
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
                Toast.makeText(this, "Payment is successfully processed!", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(CourseEnrollmentActivity.this, PaymentSuccessActivity.class);

                intent.putExtra("COURSE_NAME",prep_category_code);
                intent.putExtra("NAME_OF_USER",user_name);
                intent.putExtra("EMAIL_OF_USER",email);
                //ed.commit();

                //pass the details of course, name and email to the next page
                //set the preferences here
                //submit the enrollment data to the server

                startActivity(intent);
                finish();
            } else {
                //Oops!! Payment was cancelled
                //Toast.makeText(this, "Oops! There was some issue in Payment!", Toast.LENGTH_SHORT).show();
            }
        }
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
