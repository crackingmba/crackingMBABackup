package com.crackingMBA.training;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.util.MyUtil;
import com.crackingMBA.training.util.SectionEnum;

/**
 * Created by MSK on 24-01-2017.
 */
public class MockTestFragment extends Fragment implements View.OnClickListener {
View rootView;
    private static String TAG = "MockTestFragment";
    LayoutInflater inflater;
    ViewGroup container;
    CardView quantRow, personal_help_cv, app_feedback;
    CardView dilrRow;
    CardView verbalRow;
    TextView quant_textView, dilr_textView, verbal_textView;
    Button play_n_win_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        rootView = inflater.inflate(R.layout.fragment_mocktest_startup, container, false);

        quantRow=(CardView)rootView.findViewById(R.id.mock_test_quantrow);
        dilrRow=(CardView)rootView.findViewById(R.id.mock_test_dilrrow);
        verbalRow=(CardView)rootView.findViewById(R.id.mock_test_verbalrow);
        personal_help_cv=(CardView)rootView.findViewById(R.id.personal_help_cv);
        app_feedback=(CardView)rootView.findViewById(R.id.app_feedback);

        quant_textView=(TextView)rootView.findViewById(R.id.mock_test_quant_textview);
        dilr_textView=(TextView)rootView.findViewById(R.id.mock_test_dilr_textview);
        verbal_textView=(TextView)rootView.findViewById(R.id.mock_test_verbal_textview);
        play_n_win_btn=(Button)rootView.findViewById(R.id.play_n_win_btn);



        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
        quant_textView.setTypeface(custom_font);
        dilr_textView.setTypeface(custom_font);
        verbal_textView.setTypeface(custom_font);


        quantRow.setOnClickListener(this);
        dilrRow.setOnClickListener(this);
        verbalRow.setOnClickListener(this);
        personal_help_cv.setOnClickListener(this);
        app_feedback.setOnClickListener(this);
        play_n_win_btn.setOnClickListener(this);

        //String   myAndroidDeviceId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        //Toast.makeText(getContext(), "The Android Device ID is "+myAndroidDeviceId, Toast.LENGTH_SHORT).show();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public void onClick(View v) {
        if(MyUtil.checkConnectivity(getContext())) {
            Log.d(TAG, "Clicked a week.." + v.getId());
            Intent mockTestTopicIntent = new Intent(getActivity(), MockTestTopicsActivity.class);
            switch (v.getId()) {
                case R.id.mock_test_quantrow:
                    //VideoApplication.sectionClicked = SectionEnum.quant.key;
                    VideoApplication.sectionClicked = "quant";
                    break;
                case R.id.mock_test_dilrrow:
                    //VideoApplication.sectionClicked = SectionEnum.dilr.key;
                    VideoApplication.sectionClicked = "dilr";
                    break;
                case R.id.mock_test_verbalrow:
                    //VideoApplication.sectionClicked = SectionEnum.verbal.key;
                    VideoApplication.sectionClicked = "verbal";
                    break;
                case R.id.personal_help_cv:
                    //Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                    //intent.putExtra("FEEDBACK_HEADER", "Reach OUT!");
                    //intent.putExtra("FEEDBACK_TEXT", "You know you could score that perfect percentile. If something is impacting your performance and you need some help, reach out! This is free for all our awesome users!");
                    //startActivity(intent);
                    //VideoApplication.sectionClicked = SectionEnum.verbal.key;
                    //VideoApplication.sectionClicked = "verbal";
                    break;
                case R.id.app_feedback:

                    //intent = new Intent(getActivity(), FeedbackActivity.class);
                    //intent.putExtra("FEEDBACK_HEADER", "APP Feedback!");
                    //intent.putExtra("FEEDBACK_TEXT", "Before clicking that Uninstall button, give us a chance to hear if you were expecting something and that isnt available on this app! We will try to implement your requirements!");
                    //startActivity(intent);
                    //VideoApplication.sectionClicked = SectionEnum.verbal.key;
                    //VideoApplication.sectionClicked = "verbal";
                    break;
                case R.id.play_n_win_btn:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Tai Lang has deadly arsenal up his sleeve this time. Rev up your Vocabulary and Grammar skills. The game activates on 16th October 2017.")
                            .setPositiveButton("SURE! AM ALL GAME! ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            })
                            .setCancelable(false);

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Hold the Horses!");
                    alert.show();
                    break;
                default:
                    Log.d(TAG, "Unknown button clicked..");
                    break;
            }
            //Log.d(TAG, "Section Clicked : " + VideoApplication.sectionClicked);
            //startActivity(mockTestTopicIntent);
        }else{
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getContext(), R.string.no_internet, duration);
            toast.show();
            //TextView textView=(TextView)rootView.findViewById(R.id.networkstatus);
            //textView.setVisibility(View.VISIBLE);
            //textView.setText(R.string.no_internet);
        }


    }

}
