package com.crackingMBA.training;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class SupportGuidanceActivity extends AppCompatActivity {

    Button support_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_guidance);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        support_btn=(Button)findViewById(R.id.support_btn);


        View.OnClickListener examOnClickListener = new View.OnClickListener() {
            Intent go;
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SupportGuidanceActivity.this);
                builder.setMessage("You are leaving this section. Do you want to go to the forum?")
                        .setCancelable(false)
                        .setPositiveButton("LET's GO!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(SupportGuidanceActivity.this, DashboardActivity.class);
                                intent.putExtra("SELECTED_TAB", "3");
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNeutralButton("SOMETIME LATER!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Toast.makeText(SupportGuidanceActivity.this, "Going to Login screen", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Alert!!");
                alert.show();



            }
        };

        support_btn.setOnClickListener(examOnClickListener);
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
