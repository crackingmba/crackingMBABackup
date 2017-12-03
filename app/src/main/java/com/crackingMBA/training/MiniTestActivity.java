package com.crackingMBA.training;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MiniTestActivity extends AppCompatActivity {
    TextView minitest_tv, minitest_rules;
    Button minitest_attempt_test, minitest_leaderboard_btn;
    String exam_id; String mini_test_name, exam_type;String htmltext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_test);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        minitest_tv=(TextView)findViewById(R.id.minitest_name_tv);
        minitest_rules=(TextView)findViewById(R.id.minitest_rules);
        minitest_attempt_test=(Button)findViewById(R.id.minitest_attempt_test);
        minitest_leaderboard_btn=(Button)findViewById(R.id.minitest_leaderboard_btn);

        exam_id= getIntent().getStringExtra("MINI_TEST_EXAM_ID");
        mini_test_name= getIntent().getStringExtra("MINI_TEST_NAME");
        exam_type= getIntent().getStringExtra("MINI_TEST_EXAM_TYPE");

        minitest_attempt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MiniTestActivity.this);
                builder.setMessage("The test will be launched in a browser on your phone.")
                        .setCancelable(false)
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Toast.makeText(SupportGuidanceActivity.this, "Going to Login screen", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("LAUNCH NOW", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Toast.makeText(SupportGuidanceActivity.this, "Going to Login screen", Toast.LENGTH_SHORT).show();
                                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.crackingmba.com/prep"));
                                startActivity(browserIntent);
                            }
                        });

                //Creating dialog box
                android.support.v7.app.AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("NOTE!");
                alert.show();







            }
        });

        minitest_leaderboard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LeaderboardActivity.class);
                intent.putExtra("MINI_TEST_EXAM_ID", exam_id);
                startActivity(intent);
            }
        });



        minitest_tv.setText(mini_test_name);

        if(exam_type.equals("free")){

            htmltext="<h4>Instructions</h4>" +
                    "<p>1. This is a <b>FREE</b> test</p>" +
                    "<p>2. On clicking <b>'ATTEMPT TEST'</b>, you will be taken to the 'crackingMBA Prep Zone' portal</p>"+
                    "<p>3. In the Portal, <b>LOGIN</b> with your crackingMBA credentials or <b>REGISTER</b>.</p>"+
                    "<p>4. As SNAP 2017 and XAT 2018 are going online this time, you can also take this test on your desktop at <b>http://www.crackingmba.com/prep</b></p>" +
                    "<p>5. Your rank and score can be viewed in the Leaderboard in this app.</p>"+
                    "<p>6. If you need help with logging in, drop a mail to <b>support@crackingmba.com</b></p>";
        }

        if(exam_type.equals("paid")){

            htmltext="<h4>Instructions</h4>" +
                    "<p>1. This is a <b>PREMIUM</b> test available to all enrolled users.</p>" +
                    "<p>2. Please enroll for SNAP or XAT Challenge Course for Rs 300</p>" +
                    "<p>3. On clicking <b>'ATTEMPT TEST'</b>, you will be taken to the 'crackingMBA Prep Zone' portal</p>"+
                    "<p>4. In the Portal, <b>LOGIN</b> with your crackingMBA credentials or <b>REGISTER</b>.</p>"+
                    "<p>5. Your rank and score can be viewed in the Leaderboard in this app.</p>"+
                    "<p>6. If you need help with logging in, drop a mail to <b>support@crackingmba.com</b></p>";
        }

        Spanned sp= Html.fromHtml(htmltext);
        minitest_rules.setText(sp);

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
