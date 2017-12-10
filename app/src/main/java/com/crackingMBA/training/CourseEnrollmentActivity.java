package com.crackingMBA.training;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.crackingMBA.training.restAPI.SaveUserEnrollmentService;
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
    EditText enroll_course_name_et, enroll_email_id_et, enroll_name_et, enroll_phone_id_et;
    Button enroll_payment_btn;
    ProgressDialog mTestProgressDialog;
    String serverKey, email, phone;String trxn_id, user_name;
    ServerKeyAPIService serverKeyAPIService;SaveUserEnrollmentService saveUserEnrollmentService;
    String prep_category_code, course_name; String temp_course_code="";
    SharedPreferences prefs; String temp_course_name="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enrollment);

        Instamojo.initialize(this);

        prep_category_code = getIntent().getStringExtra("PREP_CATEGORY_CODE");
        enroll_course_name_et=(EditText)findViewById(R.id.enroll_course_name_et);
        enroll_email_id_et=(EditText)findViewById(R.id.enroll_email_id_et);
        enroll_name_et=(EditText)findViewById(R.id.enroll_name_et);
        enroll_phone_id_et=(EditText)findViewById(R.id.enroll_phone_id_et);


        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);

        if(isUserLoggedIn){
            email = prefs.getString("emailofUser", "");
            user_name=prefs.getString("nameofUser", "");
        }else{
            email=""; user_name="";
        }


        switch(prep_category_code){
            case "CATPREP1":{
                temp_course_code="CAT";
                temp_course_name="30 DAY CAT CHALLENGE";
                break;
            }

            case "IIFTPREP1":{
                temp_course_code="IIFT";
                temp_course_name="40 DAY IIFT CHALLENGE";
                break;
            }

            case "SNAPPREP1":{
                temp_course_code="SNAP";
                temp_course_name="50 DAY SNAP CHALLENGE";
                break;
            }

            case "XATPREP":{
                temp_course_code="XAT";
                temp_course_name="60 DAY XAT CHALLENGE";
                break;
            }

            case "COMBO":{
                temp_course_code="XAT";
                temp_course_name="SNAP and XAT 90 %ile CHALLENGE";
                break;
            }

        }
        enroll_payment_btn=(Button)findViewById(R.id.enroll_payment_btn);
        enroll_course_name_et.setText(temp_course_name);
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

        enroll_payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_name=enroll_name_et.getText().toString();
                email=enroll_email_id_et.getText().toString();
                phone=enroll_phone_id_et.getText().toString();

                if(user_name.equals("")||email.equals("")||phone.equals("")||phone.length()<10){
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CourseEnrollmentActivity.this);
                    builder.setMessage("Please enter valid values for Name, Email ID and Phone. Phone number should have 10 digits.")
                            .setCancelable(false)
                            .setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //Toast.makeText(SupportGuidanceActivity.this, "Going to Login screen", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            });

                    //Creating dialog box
                    android.support.v7.app.AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("ALERT!");
                    alert.show();
                }else {

                    //Order order = new Order(serverKey, trxn_id, user_name, email, phone, "300", temp_course_name);
                    Order order = new Order(serverKey, trxn_id, user_name, email, phone, "300", temp_course_name);

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
                                } else if (error instanceof Errors.AuthenticationError) {
                                    Log.e("App", "Access token is invalid or expired");
                                } else if (error instanceof Errors.ValidationError) {
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

                    request.execute();
                }


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
                Toast.makeText(this, "Payment is successfully processed!", Toast.LENGTH_LONG).show();
                final Intent intent= new Intent(CourseEnrollmentActivity.this, PaymentSuccessActivity.class);

                intent.putExtra("COURSE_NAME",temp_course_name);
                intent.putExtra("NAME_OF_USER",user_name);
                intent.putExtra("EMAIL_OF_USER",email);

                //MyUtil.showProgressDialog(PreparationHLContentActivity.this);
                saveUserEnrollmentService = RestClient.getClient().create(SaveUserEnrollmentService.class);
                saveUserEnrollmentService.saveUserEnrollment(email, temp_course_code).enqueue(new Callback<RetrofitPostResponse>() {


                    @Override
                    public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {
                        RetrofitPostResponse retrofitPostResponse = response.body();


                        if (retrofitPostResponse.getResponse().equals("0")) {
                            Toast.makeText(CourseEnrollmentActivity.this, "Sorry! The enrollment details are not saved!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CourseEnrollmentActivity.this, "The enrollment details are saved!!", Toast.LENGTH_SHORT).show();

                            //Set the user login credentials
                            SharedPreferences.Editor ed = prefs.edit();
                            ed.putBoolean("isUserLoggedIn", true);
                            ed.putString("nameofUser", user_name);
                            ed.putString("emailofUser",email);

                            //Set the preferences for enrollment
                            switch(prep_category_code){
                                case "CATPREP1":{
                                    ed.putString("whetherCATcourseEnrolled","queried1");
                                    ed.commit();
                                    break;
                                }

                                case "IIFTPREP1":{
                                    ed.putString("whetherIIFTcourseEnrolled","queried1");
                                    ed.commit();
                                    break;
                                }

                                case "SNAPPREP1":{
                                    ed.putString("whetherSNAPcourseEnrolled","queried1");
                                    ed.commit();
                                    break;
                                }

                                case "XATPREP":{
                                    ed.putString("whetherXATcourseEnrolled","queried1");
                                    ed.commit();
                                    break;
                                }
                            }
                        }

                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailure(Call<RetrofitPostResponse> call, Throwable t) {
                    }
                });
                //ed.commit();

                //pass the details of course, name and email to the next page
                //set the preferences here
                //submit the enrollment data to the server

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

