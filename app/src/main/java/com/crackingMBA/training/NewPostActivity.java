package com.crackingMBA.training;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.crackingMBA.training.R.id.signup_link_login;

public class NewPostActivity extends AppCompatActivity {
    Button newpost_button;
    EditText newpost_postdetails;
    TextView newpost_username, newpost_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String nameofUser = prefs.getString("nameofUser", "");
        String selectedCategory = prefs.getString("selectedCategory", "");
        newpost_username=(TextView)findViewById(R.id.newpost_username);
        newpost_username.setText(nameofUser);

        newpost_category=(TextView)findViewById(R.id.newpost_category);
        newpost_category.setText("Category Selected : "+selectedCategory);

        Toast.makeText(this, "The selected Category is"+selectedCategory, Toast.LENGTH_SHORT).show();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        newpost_button=(Button)findViewById(R.id.newpost_button);
        newpost_postdetails=(EditText)findViewById(R.id.newpost_postdetails);
        hideKeyboard(NewPostActivity.this);


        newpost_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewPostActivity.this, "Thank you! Your data will be saved!", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                //startActivity(intent);

            }
        });
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
