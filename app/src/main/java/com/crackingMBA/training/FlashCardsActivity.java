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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    TextView flash_card_content, flash_card_details_tv, flash_card_user_message, flash_card_header_message;
    Button flash_card_show_answer_btn, flash_card_prev_btn, flash_card_next_btn;
    static int card_number=0;
    SharedPreferences prefs;
    ComboCourseEnrollmentAPIService comboCourseEnrollmentAPIService;
    String whetherComboCourseEnrolled="notqueried";

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
            final String nameofUser = prefs.getString("nameofUser", "");
            flash_card_user_message.setText("Welcome "+nameofUser);


            if (whetherComboCourseEnrolled.equals("notqueried")) {
                loadUserEnrollmentDetails();
            }

            whetherComboCourseEnrolled = prefs.getString("whetherComboCourseEnrolled", "notqueried");
            if (whetherComboCourseEnrolled.equals("queried0")) {
                flash_card_header_message.setText("Looks like you are not enrolled as yet!");
            } else if(whetherComboCourseEnrolled.equals("queried1")){
                flash_card_header_message.setText("Welcome enrolled user!");
            }else{
                flash_card_header_message.setText("Hi There!");
            }


        }else{
            flash_card_user_message.setText("Welcome Guest User");
        }
        //Toast.makeText(this, "Category selected is "+prep_category_code, Toast.LENGTH_SHORT).show();

        flash_card_content=(TextView)findViewById(R.id.flash_card_content);
        flash_card_show_answer_btn=(Button)findViewById(R.id.flash_card_show_answer_btn);
        flash_card_prev_btn=(Button)findViewById(R.id.flash_card_prev_btn);
        flash_card_next_btn=(Button)findViewById(R.id.flash_card_next_btn);
        flash_card_details_tv=(TextView)findViewById(R.id.flash_card_details_tv);
        flash_card_content=(TextView)findViewById(R.id.flash_card_content);


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
                    flash_card_content.setText(questions.get(card_number).getName());
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
                                Toast.makeText(FlashCardsActivity.this, "user needs to enroll", Toast.LENGTH_SHORT).show();
                                displayDialog();
                            } else if(whetherComboCourseEnrolled.equals("queried1")){
                                //Toast.makeText(FlashCardsActivity.this, "user is enrolled", Toast.LENGTH_SHORT).show();
                                if (card_number > (questions.size() - 1)) {
                                    card_number--;
                                    Toast.makeText(FlashCardsActivity.this, "There are no other cards this side!", Toast.LENGTH_SHORT).show();
                                } else {
                                    flash_card_details_tv.setVisibility(View.INVISIBLE);
                                    flash_card_content.setText(questions.get(card_number).getName());
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
                            flash_card_content.setText(questions.get(card_number).getName());
                        }
                    }



/*                    if (whetherComboCourseEnrolled.equals("notqueried")) {
                        loadUserEnrollmentDetails();
                        whetherComboCourseEnrolled = prefs.getString("whetherComboCourseEnrolled", "notqueried");
                        if (whetherComboCourseEnrolled.equals("queried0")) {
                            Toast.makeText(FlashCardsActivity.this, "user needs to enroll", Toast.LENGTH_SHORT).show();
                            displayDialog();
                        } else {
                            //Toast.makeText(FlashCardsActivity.this, "user is enrolled", Toast.LENGTH_SHORT).show();
                            if (card_number > (questions.size() - 1)) {
                                card_number--;
                                Toast.makeText(FlashCardsActivity.this, "There are no other cards this side!", Toast.LENGTH_SHORT).show();
                            } else {
                                flash_card_details_tv.setVisibility(View.INVISIBLE);
                                flash_card_content.setText(questions.get(card_number).getName());
                            }
                        }
                    } else if (whetherComboCourseEnrolled.equals("queried0")) {
                        Toast.makeText(FlashCardsActivity.this, "user needs to enroll", Toast.LENGTH_SHORT).show();
                        displayDialog();
                    } else {
                        //Toast.makeText(FlashCardsActivity.this, "user is enrolled", Toast.LENGTH_SHORT).show();
                        if (card_number > (questions.size() - 1)) {
                            card_number--;
                            Toast.makeText(FlashCardsActivity.this, "There are no other cards this side!", Toast.LENGTH_SHORT).show();
                        } else {
                            flash_card_details_tv.setVisibility(View.INVISIBLE);
                            flash_card_content.setText(questions.get(card_number).getName());
                        }
                    }

                }else{
                    if (card_number > (questions.size() - 1)) {
                        card_number--;
                        Toast.makeText(FlashCardsActivity.this, "There are no other cards this side!", Toast.LENGTH_SHORT).show();
                    } else {
                        flash_card_details_tv.setVisibility(View.INVISIBLE);
                        flash_card_content.setText(questions.get(card_number).getName());
                    }
                }*/

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
            //Toast.makeText(this, "User emailid= "+email, Toast.LENGTH_SHORT).show();
            final SharedPreferences.Editor ed = prefs.edit();

            //checking if the user is an enrolled user
            comboCourseEnrollmentAPIService = RestClient.getClient().create(ComboCourseEnrollmentAPIService.class);
            comboCourseEnrollmentAPIService .validateComboCourseEnrollment(email).enqueue(new Callback<RetrofitPostResponse>() {
                @Override
                public void onResponse(Call<RetrofitPostResponse> call, Response<RetrofitPostResponse> response) {
                    //MyUtil.hideProgressDialog();
                    RetrofitPostResponse retrofitPostResponse = response.body();

                    if(retrofitPostResponse.getResponse().equals("1")) {
                        //Toast.makeText(FlashCardsActivity.this, "User is enrolled", Toast.LENGTH_SHORT).show();
                        ed.putString("whetherComboCourseEnrolled","queried1");
                        ed.commit();
                    }else if(retrofitPostResponse.getResponse().equals("0")){
                            //Toast.makeText(FlashCardsActivity.this, "User is not enrolled", Toast.LENGTH_SHORT).show();
                            ed.putString("whetherComboCourseEnrolled","queried0");
                            ed.commit();
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
        flash_card_content.setText(questions.get(0).getName());

    }

    private void displayDialog(){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Further cards are available if you ENROLL for our SNAP & XAT Combo course for Rs 300 only. You get all Flash Cards in Quant, Verbal and GK and our entire study material in this course.")
                .setCancelable(false)
                .setPositiveButton("ALREADY ENROLLED?  LOGIN NOW", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(FlashCardsActivity.this, LoginActivity.class);
                        intent.putExtra("IS_IT_FOR_ENROLLMENT","1");
                        intent.putExtra("EXAM_NAME","CAT");
                        intent.putExtra("EXAM_NAME_TEXT","Spare your pizza craving today for a bigger one later!'. At 300, for the price of a medium pan pizza, you can now crack CAT 2017!");
                        startActivity(intent);
                    }
                })
                .setNegativeButton("NOT ENROLLED YET? ENROLL NOW", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(SupportGuidanceActivity.this, "Going to Login screen", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FlashCardsActivity.this, CourseEnrollmentActivity.class);
                        intent.putExtra("PREP_CATEGORY_CODE","COMBO COURSE");
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


}
