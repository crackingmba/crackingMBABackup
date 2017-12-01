package com.crackingMBA.training;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MiniTestActivity extends AppCompatActivity {
    TextView minitest_tv;
    Button minitest_attempt_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_test);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        minitest_tv=(TextView)findViewById(R.id.minitest_name_tv);
        minitest_attempt_test=(Button)findViewById(R.id.minitest_attempt_test);

        minitest_attempt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp_url= "http://www.crackingmba.com/prep?"+getIntent().getStringExtra("MINI_TEST_URL");
                Toast.makeText(getApplicationContext(), temp_url, Toast.LENGTH_SHORT).show();
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(temp_url));
                startActivity(browserIntent);
            }
        });

        String mini_test_name= getIntent().getStringExtra("MINI_TEST_NAME");
        minitest_tv.setText(mini_test_name);

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
