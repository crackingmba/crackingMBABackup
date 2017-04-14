package com.crackingMBA.training;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    CardView quantRow;
    CardView dilrRow;
    CardView verbalRow;
    TextView quant_textView, dilr_textView, verbal_textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        rootView = inflater.inflate(R.layout.fragment_mocktest_startup, container, false);
        //TableRow quantRow = (TableRow) rootView.findViewById(R.id.quantrow);
        //TableRow diRow = (TableRow) rootView.findViewById(R.id.dirow);
        //TableRow verbalRow = (TableRow) rootView.findViewById(R.id.verbalrow);

        quantRow=(CardView)rootView.findViewById(R.id.mock_test_quantrow);
        dilrRow=(CardView)rootView.findViewById(R.id.mock_test_dilrrow);
        verbalRow=(CardView)rootView.findViewById(R.id.mock_test_verbalrow);

        quant_textView=(TextView)rootView.findViewById(R.id.mock_test_quant_textview);
        dilr_textView=(TextView)rootView.findViewById(R.id.mock_test_dilr_textview);
        verbal_textView=(TextView)rootView.findViewById(R.id.mock_test_verbal_textview);



        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Pacifico-Regular.ttf");
        quant_textView.setTypeface(custom_font);
        dilr_textView.setTypeface(custom_font);
        verbal_textView.setTypeface(custom_font);


        quantRow.setOnClickListener(this);
        dilrRow.setOnClickListener(this);
        verbalRow.setOnClickListener(this);
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
                default:
                    Log.d(TAG, "Unknown button clicked..");
                    break;
            }
            Log.d(TAG, "Section Clicked : " + VideoApplication.sectionClicked);
            startActivity(mockTestTopicIntent);
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
