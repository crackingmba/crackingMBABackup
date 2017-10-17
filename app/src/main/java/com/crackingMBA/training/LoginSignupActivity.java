package com.crackingMBA.training;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;import android.widget.EditText;import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginSignupActivity extends AppCompatActivity {
    Button btn_login_signup_signup, btn_login_signup_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btn_login_signup_signup=(Button)findViewById(R.id.btn_login_signup_signup);
        btn_login_signup_login=(Button)findViewById(R.id.btn_login_signup_login);

        btn_login_signup_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginSignupActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btn_login_signup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginSignupActivity.this, LoginActivity.class);
                intent.putExtra("IS_IT_FOR_ENROLLMENT","0");
                intent.putExtra("EXAM_NAME","CAT");
                intent.putExtra("EXAM_NAME_TEXT","Spare");
                startActivity(intent);
                finish();

            }
        });
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
