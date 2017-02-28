package com.crackingMBA.training;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.crackingMBA.training.pojo.MockTestQuestion;

/**
 * Created by MSK on 24-01-2017.
 */
public class MockTestQuestionFragment extends Fragment implements View.OnClickListener {

    private static String TAG = "MockTestQuestionFragment";
    View rootView;
    boolean isMock;

    TextView qstnTxt;
    RadioButton option1;
    RadioButton option2;
    RadioButton option3;
    RadioButton option4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MockTestQuestion selectedQstn = VideoApplication.selectedMockTestQuestion;
        Log.d(TAG, selectedQstn + " question is selected");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        isMock = pref.getBoolean("isMock", false);

        rootView = inflater.inflate(R.layout.fragment_mocktest_question, container, false);

        qstnTxt = ((TextView) rootView.findViewById(R.id.mocktest_qstn_txt));
        qstnTxt.setText(selectedQstn.getQstnNo()+". "+selectedQstn.getQstnTxt());
        option1 = ((RadioButton) rootView.findViewById(R.id.option1));
        option1.setText(selectedQstn.getOption1());
        option2 = ((RadioButton) rootView.findViewById(R.id.option2));
        option2.setText(selectedQstn.getOption2());
        option3 = ((RadioButton) rootView.findViewById(R.id.option3));
        option3.setText(selectedQstn.getOption3());
        option4 = ((RadioButton) rootView.findViewById(R.id.option4));
        option4.setText(selectedQstn.getOption4());

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG,"clicked view "+rootView.findViewById(v.getId()));
    }

}