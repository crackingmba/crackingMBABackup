package com.crackingMBA.training;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaymentSuccessActivity extends AppCompatActivity {
    TextView payment_success_message_tv, ps_course_name_tv, ps_name_tv, ps_email_tv;
    Button payment_success_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        String course_name = getIntent().getStringExtra("COURSE_NAME");
        String name_of_user = getIntent().getStringExtra("NAME_OF_USER");
        String email_of_user = getIntent().getStringExtra("EMAIL_OF_USER");

        payment_success_message_tv=(TextView)findViewById(R.id.payment_success_message_tv);
        ps_course_name_tv=(TextView)findViewById(R.id.ps_course_name_tv);
        ps_name_tv=(TextView)findViewById(R.id.ps_name_tv);
        ps_email_tv=(TextView)findViewById(R.id.ps_email_tv);
        payment_success_btn=(Button)findViewById(R.id.payment_success_btn);

        String success_msg = "Congratulations! You are now enrolled for the "+course_name+" course.";
        payment_success_message_tv.setText(success_msg);
        ps_course_name_tv.setText(course_name);
        ps_name_tv.setText(name_of_user);
        ps_email_tv.setText(email_of_user);

        payment_success_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


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
