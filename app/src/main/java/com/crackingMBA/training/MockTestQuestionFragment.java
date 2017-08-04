package com.crackingMBA.training;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.crackingMBA.training.adapter.DownloadImageTask;
import com.crackingMBA.training.pojo.MockTestQuestion;

/**
 * Created by MSK on 24-01-2017.
 */
public class MockTestQuestionFragment extends Fragment implements View.OnClickListener {

    private static String TAG = "MockTestQuestionFragment";
    View rootView;

    TextView qstnTxt;
    //MathView mathView;
    RadioButton option1, option2,option3,option4;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MockTestQuestion selectedQstn = VideoApplication.selectedMockTestQuestion;
        //Log.d(TAG, selectedQstn + " question is selected");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        rootView = inflater.inflate(R.layout.fragment_mocktest_question, container, false);

        //question text here
        qstnTxt = ((TextView) rootView.findViewById(R.id.mocktest_qstn_txt));
        qstnTxt.setText(selectedQstn.getQstnNo()+". "+selectedQstn.getQstnTxt());

        //the mathjax details here
        //mathView = ((MathView) rootView.findViewById(R.id.id_quiz_mathjax));
        imageView=(ImageView)rootView.findViewById(R.id.mock_test_img) ;

        String qn_formula=selectedQstn.getQstnFormula();

        String test="Test type of a number is the sum of the following: $${2+\\sqrt{3}*\\sqrt{8}}$$. After this add some more text in the question here ";
        Log.d("qstn_Formula",test);

        //mathView.setText("What type of a number is the sum of the following: $${2+\\\\sqrt{3}*\\\\sqrt{8}}$$. After this add some more text in the question here");
        //mathView.setText("Test type of a number is the sum of the following: $${2+\\sqrt{3}*\\sqrt{8}}$$. After this add some more text in the question here");
        //mathView.setText(qn_formula.toString());
        String mocktestImg=selectedQstn.getQstnImage();

        if(mocktestImg.isEmpty()){
            //the image is not displayed here
        }else{
            try {
                Log.d("mock_test_img_check", CrackingConstant.MYPATH +"img/mock_tests/"+mocktestImg);
                AsyncTask result = new DownloadImageTask((ImageView) imageView, this.getContext())
                        .execute(CrackingConstant.MYPATH +"img/mock_tests/"+mocktestImg);
            }
            catch (Exception e){
            }
        }


        //options go here
        option1 = ((RadioButton) rootView.findViewById(R.id.option1));
        option1.setText(Html.fromHtml(selectedQstn.getOption1()));
        option2 = ((RadioButton) rootView.findViewById(R.id.option2));
        option2.setText(Html.fromHtml(selectedQstn.getOption2()));
        option3 = ((RadioButton) rootView.findViewById(R.id.option3));
        option3.setText(Html.fromHtml(selectedQstn.getOption3()));
        option4 = ((RadioButton) rootView.findViewById(R.id.option4));
        option4.setText(Html.fromHtml(selectedQstn.getOption4()));

        return rootView;
    }

    @Override
    public void onClick(View v) {
        //Log.d(TAG,"clicked view "+rootView.findViewById(v.getId()));
    }

}