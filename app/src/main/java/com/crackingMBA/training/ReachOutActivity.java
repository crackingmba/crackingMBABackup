package com.crackingMBA.training;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReachOutActivity extends AppCompatActivity {
    Button btn; EditText et_name, et_email, et_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reach_out);

        //btn=(Button)findViewById(R.id.ReachOutButton);
        //et_name=(EditText) findViewById(R.id.et_name);
        //et_email=(EditText) findViewById(R.id.et_email);
        //et_message=(EditText) findViewById(R.id.et_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        View.OnClickListener reachoutOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()){
          //          case R.id.ReachOutButton:{
                        //Intent examDetails = new Intent(getActivity(), ExamDetailsActivity.class);
                        //examDetails.putExtra("MBA_EXAM_CODE", "CAT");
                        //startActivity(examDetails);
            //            break;
              //      }

                }


            }
        };

        //btn.setOnClickListener(reachoutOnClickListener);
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


}
