package com.crackingMBA.training;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.pojo.RetrofitFlashCardContent;
import com.crackingMBA.training.pojo.RetrofitFlashCardContentList;
import com.crackingMBA.training.pojo.RetrofitPostResponse;
import com.crackingMBA.training.restAPI.ComboCourseEnrollmentAPIService;
import com.crackingMBA.training.restAPI.FlashcardsAPIService;
import com.crackingMBA.training.restAPI.RestClient;
import com.crackingMBA.training.restAPI.UserEnrollmentAPIService;
import com.crackingMBA.training.util.MyUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlashCardsActivity extends AppCompatActivity {
    List<RetrofitFlashCardContent> questions = new ArrayList<>();
    Call<RetrofitFlashCardContentList> call;
    FlashcardsAPIService apiService;
    TextView flash_card_content_tv, flash_card_details_tv, flash_card_user_message, flash_card_header_message;
    Button flash_card_show_answer_btn, flash_card_prev_btn, flash_card_next_btn;
    static int card_number=0;
    Spanned sp;
    SharedPreferences prefs;
    SharedPreferences.Editor ed;
    ComboCourseEnrollmentAPIService comboCourseEnrollmentAPIService;
    String whetherComboCourseEnrolled="notqueried", HTMLString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_cards);

        final String prep_category_code = getIntent().getStringExtra("CATEGORY_CODE");
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);

        flash_card_user_message=(TextView)findViewById(R.id.flash_card_user_message);
        flash_card_header_message=(TextView)findViewById(R.id.flash_card_header_message);

        if(isUserLoggedIn) {
            final String nameofUser1 = prefs.getString("nameofUser", "");

            if (whetherComboCourseEnrolled.equals("notqueried")) {
                loadUserEnrollmentDetails(); //test
            }else if(whetherComboCourseEnrolled.equals("queried0")){
                HTMLString="<p>Users enrolled for the 'SNAP & XAT 90 %ile Challenge' Course can access all the cards. You will be prompted to login or enroll after 10 cards.</p>";
                sp= Html.fromHtml(HTMLString);
                flash_card_header_message.setText(sp);

            }else if(whetherComboCourseEnrolled.equals("queried1")){
                flash_card_header_message.setText("As an enrolled user of the 'SNAP & XAT 90 %ile Challenge' course, you can access all the flash cards!");
            }


        }else{
            flash_card_user_message.setText("Welcome Guest User");
            HTMLString="Users enrolled for the 'SNAP & XAT 90 %ile Challenge' Course can access all the cards. You will be prompted to login or enroll after 10 cards.</p>";
            sp= Html.fromHtml(HTMLString);
            flash_card_header_message.setText(sp);
        }

        flash_card_content_tv=(TextView)findViewById(R.id.flash_card_content_tv);
        flash_card_show_answer_btn=(Button)findViewById(R.id.flash_card_show_answer_btn);
        flash_card_prev_btn=(Button)findViewById(R.id.flash_card_prev_btn);
        flash_card_next_btn=(Button)findViewById(R.id.flash_card_next_btn);
        flash_card_details_tv=(TextView)findViewById(R.id.flash_card_details_tv);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        apiService = RestClient.getClient().create(FlashcardsAPIService.class);

        call = apiService.fetchFlashCards(prep_category_code);
        if(MyUtil.checkConnectivity(getApplicationContext())) {

            MyUtil.showProgressDialog(this);
            fetchPrepContentList();
        }
        else{
            Toast.makeText(this, "Sorry. There is no internet connection!", Toast.LENGTH_SHORT).show();
        }

        flash_card_show_answer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flash_card_details_tv.getVisibility() == View.VISIBLE){
                    flash_card_details_tv.setVisibility(View.INVISIBLE);
                }else if (flash_card_details_tv.getVisibility() == View.INVISIBLE){
                    flash_card_details_tv.setVisibility(View.VISIBLE);
                    flash_card_details_tv.setText(questions.get(card_number).getDetail1());
                }

                //card_number++;
                //flash_card_content.setText(questions.get(card_number).getName());

            }
        });

        flash_card_prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_number--;
                if(card_number<0){
                    card_number=0;
                    Toast.makeText(FlashCardsActivity.this, "There are no other cards this side!", Toast.LENGTH_SHORT).show();
                }else{
                    flash_card_details_tv.setVisibility(View.INVISIBLE);
                    flash_card_content_tv.setText(questions.get(card_number).getName());
                }

            }
        });

        flash_card_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_number++;

                if (card_number > 9) {
                    Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);

                    if(isUserLoggedIn){
                        whetherComboCourseEnrolled = prefs.getString("whetherComboCourseEnrolled", "notqueried");
                        if (whetherComboCourseEnrolled.equals("notqueried")) {
                            loadUserEnrollmentDetails();
                        }
                            whetherComboCourseEnrolled = prefs.getString("whetherComboCourseEnrolled", "notqueried");

                            if (whetherComboCourseEnrolled.equals("queried0")) {
                                //Toast.makeText(FlashCardsActivity.this, "user needs to enroll", Toast.LENGTH_SHORT).show();
                                displayDialog();
                            } else if(whetherComboCourseEnrolled.equals("queried1")){
                                //Toast.makeText(FlashCardsActivity.this, "user is enrolled", Toast.LENGTH_SHORT).show();
                                if (card_number > (questions.size() - 1)) {
                                    card_number--;
                                    Toast.makeText(FlashCardsActivity.this, "There are no other cards this side!", Toast.LENGTH_SHORT).show();
                                } else {
                                    flash_card_details_tv.setVisibility(View.INVISIBLE);
                                    flash_card_content_tv.setText(questions.get(card_number).getName());
                                }
                            }

                        }else{

                        displayDialog();
                    }
                    }else{
                        if (card_number > (questions.size() - 1)) {
                            card_number--;
                            Toast.makeText(FlashCardsActivity.this, "There are no other cards this side!", Toast.LENGTH_SHORT).show();
                        } else {
                            flash_card_details_tv.setVisibility(View.INVISIBLE);
                            flash_card_content_tv.setText(questions.get(card_number).getName());
                        }
                    }
            }

        });
    }



    private void fetchPrepContentList() {
        call.enqueue(new Callback<RetrofitFlashCardContentList>() {
            @Override
            public void onResponse(Call<RetrofitFlashCardContentList> call, Response<RetrofitFlashCardContentList> response) {
                MyUtil.hideProgressDialog();

                if(response.body() == null){
                    //comments_comments_not_added.setVisibility(View.VISIBLE);
                    return;
                }else{
                    questions.addAll(response.body().getQuestions());
                    displayDetails();
                }
            }

            @Override
            public void onFailure(Call<RetrofitFlashCardContentList> call, Throwable t) {
            }
        });
    }

    private void loadUserEnrollmentDetails(){


            final String email = prefs.getString("emailofUser", "");
            final String nameofUser = prefs.getString("nameofUser", "");
            //Toast.makeText(this, "User emailid= "+email, Toast.LENGTH_SHORT).show();


            //checking if the user is an enrolled user
            comboCourseEnrollmentAPIService = RestClient.getClient().create(ComboCourseEnrollmentAPIService.class);
            comboCourseEnrollmentAPIService .validateComboCourseEnrollment(email).enqueue(new Callback<RetrofitPostResponse>() {
                @Override
                public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {
                    //MyUtil.hideProgressDialog();
                    RetrofitPostResponse retrofitPostResponse = response.body();

                    if(retrofitPostResponse.getResponse().equals("1")) {
                        //Toast.makeText(FlashCardsActivity.this, "The response is 1", Toast.LENGTH_SHORT).show();
                        ed = prefs.edit();
                        SharedPreferences.Editor ed1 = prefs.edit();
                        ed.putString("whetherComboCourseEnrolled","queried1");
                        ed.commit();
                        flash_card_user_message.setText("Welcome "+nameofUser);
                        flash_card_header_message.setText("As an enrolled user of the 'SNAP & XAT Prep Combo' course, you can access all the flash cards!");
                    }else if(retrofitPostResponse.getResponse().equals("0")){
                        //Toast.makeText(FlashCardsActivity.this, "The response is 0", Toast.LENGTH_SHORT).show();
                        ed = prefs.edit();
                        ed.putString("whetherComboCourseEnrolled","queried0");
                        ed.commit();

                        flash_card_user_message.setText("Welcome "+nameofUser);
                        HTMLString="<p>Users enrolled for the 'SNAP & XAT 90 %ile Challenge' Course can access all the cards. You will be prompted to login or enroll after 10 cards.</p>";
                        sp= Html.fromHtml(HTMLString);
                        flash_card_header_message.setText(sp);
                    }else{
                        //Toast.makeText(FlashCardsActivity.this, "The response is neither 0 nor 1", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RetrofitPostResponse> call, Throwable t) {
                }
            });
    }


    private void displayDetails() {
        //Toast.makeText(this, "the word is " +questions.get(0).getName(), Toast.LENGTH_SHORT).show();
        card_number=0;
        flash_card_details_tv.setVisibility(View.INVISIBLE);
        flash_card_content_tv.setText(questions.get(0).getName());

    }

    private void displayDialog(){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("You can view only 10 cards. ENROLL for the 'SNAP & XAT 90 %ile Challenge' course for Rs 300 only and access all GK, Quant and Verbal Flash Cards")
                .setCancelable(false)
                .setPositiveButton("ALREADY ENROLLED?  LOGIN NOW", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(FlashCardsActivity.this, LoginActivity.class);
                        intent.putExtra("IS_IT_FOR_ENROLLMENT","1");
                        intent.putExtra("EXAM_NAME","CAT");
                        intent.putExtra("EXAM_NAME_TEXT","Get access to all flash cards for Rs 300 only.");
                        startActivity(intent);
                    }
                })
                .setNegativeButton("NOT ENROLLED YET? ENROLL NOW", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), MotivationYoutubeDetailsActivity.class);
                        startActivity(intent);

                    }
                })
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

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(this, "in OnResume", Toast.LENGTH_SHORT).show();

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);

        if(isUserLoggedIn){
            flash_card_user_message.setText(prefs.getString("nameofUser", ""));

            whetherComboCourseEnrolled = prefs.getString("whetherComboCourseEnrolled", "notqueried");

            if(whetherComboCourseEnrolled.equals("notqueried")){
                //Toast.makeText(this, "Not queried for this user", Toast.LENGTH_SHORT).show();
                loadUserEnrollmentDetails();
                //whetherComboCourseEnrolled = prefs.getString("whetherComboCourseEnrolled", "notqueried");

            }
        }



    }


}
