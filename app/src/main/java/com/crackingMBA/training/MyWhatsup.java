package com.crackingMBA.training;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.crackingMBA.training.pojo.LoginResponseObject;
import com.crackingMBA.training.pojo.Question;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSK on 24-01-2017.
 */
public class MyWhatsup extends Fragment implements View.OnClickListener {

    View rootView;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_whatsup, container, false);
        textView=(TextView)rootView.findViewById(R.id.whatsup_text);
        textView.setText(Html.fromHtml("<h2>What's new on crackingMBA!</h2>" +
                "<h3>15th May</h3>" +"<p>1. Added a new video on Sentence Completion in Verbal Section</p>"+"<p>2. Added a new mock test on Sentence Completion in Verbal section</p>"
        +"<h3>14th May</h3><p>1. Added a new video on Ratio in Quant Section</p><p>2. Added a new video on Bar Charts in DI LR section</p>"));

        return rootView;
    }



    @Override
    public void onClick(View v) {
        //Some click activity will happen here

    }





}
