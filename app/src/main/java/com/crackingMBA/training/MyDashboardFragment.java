package com.crackingMBA.training;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.QuestionsViewAdapter;
import com.crackingMBA.training.pojo.Qstns;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

/**
 * Created by MSK on 24-01-2017.
 */
public class MyDashboardFragment extends Fragment implements View.OnClickListener{

    private static String TAG = "MyDashboardFragment";
    boolean isMock;
    View rootView;

    RecyclerView qstnsRecyclerView;
    RecyclerView.Adapter mAdapter;
    LinearLayoutManager mLayoutManager;
    String url = "";

    SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView of MyDashboardFragment");
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        isMock = pref.getBoolean("isMock",false);
        boolean isLoggedIn = pref.getBoolean("isLoggedIn",true);
        if(isLoggedIn){
            rootView = inflater.inflate(R.layout.fragment_mydashboard_loggedin, container, false);

            mayRequestReadAccess();

            String loggedInUserName = pref.getString("loggedInUserName","Guest");
            String loggedInProfilePicUrl = pref.getString("loggedInProfilePicUrl",null);
                    //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/1485628183722.jpg";//pref.getString("loggedInProfilePicUrl",null);
            Log.d(TAG,"loggedInUserName="+loggedInUserName+"\n loggedInProfilePicUrl="+loggedInProfilePicUrl);
            ((TextView)rootView.findViewById(R.id.welcomeTxt)).setText(loggedInUserName);

            if(null != loggedInProfilePicUrl){
                Bitmap bitmapImage = null;
                File f = new File(loggedInProfilePicUrl);
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), Uri.fromFile(f));
                }catch (Exception e){
                    e.printStackTrace();;
                }
                if(null != bitmapImage)
                    ((ImageView) rootView.findViewById(R.id.userprofile)).setImageBitmap(bitmapImage);
            }

            ((ImageButton) rootView.findViewById(R.id.plusbtn)).setOnClickListener(this);
            ((Button) rootView.findViewById(R.id.editprofilebtn)).setOnClickListener(this);
            ((Button) rootView.findViewById(R.id.logoutbtn)).setOnClickListener(this);

            qstnsRecyclerView = (RecyclerView) rootView.findViewById(R.id.mydashboard_recycler_qstns);
            qstnsRecyclerView.setHasFixedSize(true);
            getQstnsDataSet();

        }else {
            rootView = inflater.inflate(R.layout.fragment_mydashboard, container, false);
            ((Button) rootView.findViewById(R.id.googlelogin)).setOnClickListener(this);
            ((Button) rootView.findViewById(R.id.fblogin)).setOnClickListener(this);
            ((Button) rootView.findViewById(R.id.applogin)).setOnClickListener(this);
            ((Button) rootView.findViewById(R.id.register)).setOnClickListener(this);
        }

        return rootView;
    }

    private boolean mayRequestReadAccess() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (getActivity().checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
            Snackbar.make(rootView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, 0);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, 0);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG,"inside onClick.."+v.getId());
        Intent gotoIntent = null;
        switch (v.getId()){
            case R.id.googlelogin:
                gotoIntent = new Intent(getActivity(),LoginActivity.class);
                break;
            case R.id.fblogin:
                gotoIntent = new Intent(getActivity(),LoginActivity.class);
                break;
            case R.id.applogin:
                gotoIntent = new Intent(getActivity(),LoginActivity.class);
                break;
            case R.id.register:
                gotoIntent = new Intent(getActivity(),RegistrationActivity.class);
                break;
            case R.id.plusbtn:
                gotoIntent = new Intent(getActivity(),AskQuestionActivity.class);
                break;
            case R.id.editprofilebtn:
                gotoIntent = new Intent(getActivity(),EditProfileActivity.class);
                break;
            case R.id.logoutbtn:
                gotoIntent = new Intent(getActivity(),LogoutActivity.class);
                break;
            default:
                Log.d(TAG,"Unknown button clicked..");
                break;
        }
        startActivity(gotoIntent);
    }

    private void getQstnsDataSet() {

        Log.d(TAG, "isMock?" + isMock);
        isMock=true;
        if (isMock) {
            ArrayList<Qstns> qstns = populateMockQstnsSet();
            Log.d(TAG,"Populated qstns are "+qstns);
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

            qstnsRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new QuestionsViewAdapter(qstns);

            ((QuestionsViewAdapter) mAdapter).setOnItemClickListener(
                    new QuestionsViewAdapter.MyClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            Log.d(TAG, "Section adapter, Clicked item at position : " + position);
                            String qstnId = ((TextView) v.findViewById(R.id.qstnId)).getText().toString();
                            String qstnTxt = ((TextView) v.findViewById(R.id.qstntxt)).getText().toString();
                            String qstnDate = ((TextView) v.findViewById(R.id.datetxt)).getText().toString();
                            Qstns selectedQstn = new Qstns(qstnId,qstnTxt,qstnDate);
                            VideoApplication.selectedQstn = selectedQstn;
                            Log.d(TAG, "set with Qstn..");
                            Intent answerIntent = new Intent(getActivity(),ShowAnswerActivity.class);
                            startActivity(answerIntent);
                        }
                    }
            );
            
            qstnsRecyclerView.setAdapter(mAdapter);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
            qstnsRecyclerView.addItemDecoration(itemDecoration);
            Log.d(TAG,"Populated qstnsRecyclerView");
        } else {
            Log.d(TAG, "In else block");
            final ArrayList<Qstns> results = new ArrayList<Qstns>();
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        List<Qstns> qstns = gson.fromJson(response, List.class);
                        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

                        qstnsRecyclerView.setLayoutManager(mLayoutManager);
                        mAdapter = new QuestionsViewAdapter(qstns);

                        ((QuestionsViewAdapter) mAdapter).setOnItemClickListener(
                                new QuestionsViewAdapter.MyClickListener() {
                                    @Override
                                    public void onItemClick(int position, View v) {
                                        Log.d(TAG, "Section adapter, Clicked item at position : " + position);
                                        String qstnId = ((TextView) v.findViewById(R.id.qstnId)).getText().toString();
                                        String qstnTxt = ((TextView) v.findViewById(R.id.qstntxt)).getText().toString();
                                        String qstnDate = ((TextView) v.findViewById(R.id.datetxt)).getText().toString();
                                        Qstns selectedQstn = new Qstns(qstnId,qstnTxt,qstnDate);
                                        VideoApplication.selectedQstn = selectedQstn;
                                        Log.d(TAG, "set with Qstn..");
                                        Intent answerIntent = new Intent(getActivity(),ShowAnswerActivity.class);
                                        startActivity(answerIntent);
                                    }
                                }
                        );

                        qstnsRecyclerView.setAdapter(mAdapter);
                        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
                        qstnsRecyclerView.addItemDecoration(itemDecoration);
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        Log.d(TAG, "Status is " + statusCode + " and " + content);
                        if (statusCode == 404) {
                            Log.d(TAG, "Requested resource not found");
                        } else if (statusCode == 500) {
                            Log.d(TAG, "Something went wrong at server end");
                        } else {
                            Log.d(TAG, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]");
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                ;
            }
        }

    }

    public ArrayList<Qstns> populateMockQstnsSet() {
        ArrayList<Qstns> mockResults = new ArrayList<Qstns>();
        Qstns vo = null;
        vo = new Qstns("qstn1","Can you please help me with solving this question in Quant?","23/01/2017");
        mockResults.add(vo);
        vo = new Qstns("qstn2","Are the forms for CAT released? If so, how can I apply?","23/01/2017");
        mockResults.add(vo);
        return mockResults;
    }
}
