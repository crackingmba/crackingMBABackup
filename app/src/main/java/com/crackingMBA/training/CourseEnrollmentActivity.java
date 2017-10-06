package com.crackingMBA.training;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.instamojo.android.Instamojo;
import com.instamojo.android.activities.PaymentDetailsActivity;
import com.instamojo.android.callbacks.OrderRequestCallBack;
import com.instamojo.android.helpers.Constants;
import com.instamojo.android.models.Errors;
import com.instamojo.android.models.Order;
import com.instamojo.android.network.Request;

import java.util.Random;

public class CourseEnrollmentActivity extends AppCompatActivity {
    EditText enroll_transaction_id_et, enroll_course_name_et, enroll_email_id_et, enroll_name_et;
    Button enroll_payment_btn;
    ProgressDialog mTestProgressDialog;
    String key, email, course_name;String trxn_id, user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enrollment);

        Instamojo.initialize(this);

        enroll_transaction_id_et=(EditText)findViewById(R.id.enroll_transaction_id_et);
        enroll_course_name_et=(EditText)findViewById(R.id.enroll_course_name_et);
        enroll_email_id_et=(EditText)findViewById(R.id.enroll_email_id_et);
        enroll_name_et=(EditText)findViewById(R.id.enroll_name_et);

        enroll_payment_btn=(Button)findViewById(R.id.enroll_payment_btn);
        enroll_transaction_id_et.setText("1234");
        enroll_course_name_et.setText("SNAP");
        enroll_email_id_et.setText("");
        key="5I6WbB8NaT3FqnPDhNQ44XLj4j3Iem";
        email="test@gmail.com";
        course_name="CAT";
        user_name="test";

        Random r = new Random();
        trxn_id="trxn"+ r.nextInt(1000+1);

        enroll_transaction_id_et.setText(trxn_id);
        enroll_course_name_et.setText("TEST");

        enroll_payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    user_name=enroll_name_et.getText().toString();
                    email=enroll_email_id_et.getText().toString();

                    Order order = new Order(key, trxn_id, user_name, email, "9823498234", "300", course_name);

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

                    request.execute();
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
                //Toast.makeText(this, "Payment is successfully processed!", Toast.LENGTH_SHORT).show();
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
