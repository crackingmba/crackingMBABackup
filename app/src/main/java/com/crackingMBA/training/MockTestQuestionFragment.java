package com.crackingMBA.training;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.crackingMBA.training.pojo.MockTestQuestion;
import com.squareup.picasso.Picasso;

public class MockTestQuestionFragment extends Fragment{
    View rootView;

    TextView qstnTxt;
    RadioButton option1, option2,option3,option4;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MockTestQuestion selectedQstn = VideoApplication.selectedMockTestQuestion;

        rootView = inflater.inflate(R.layout.fragment_mocktest_question, container, false);
        qstnTxt = ((TextView) rootView.findViewById(R.id.mocktest_qstn_txt));
        qstnTxt.setText(selectedQstn.getQstnNo()+". "+selectedQstn.getQstnTxt());
        imageView=(ImageView)rootView.findViewById(R.id.mock_test_img) ;
        String mocktestImg=selectedQstn.getQstnImage();

        if(mocktestImg.isEmpty()){
            //the image is not displayed here
        }else{
            try {
                Picasso.with(getContext())
                        .load(CrackingConstant.MYPATH +"img/mock_tests/"+mocktestImg)
                        .into(imageView);
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

}