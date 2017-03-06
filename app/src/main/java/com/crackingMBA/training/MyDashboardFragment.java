package com.crackingMBA.training;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.QuestionsViewAdapter;
import com.crackingMBA.training.pojo.Qstns;
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

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

/**
 * Created by MSK on 24-01-2017.
 */
public class MyDashboardFragment extends Fragment implements View.OnClickListener

        , GoogleApiClient.OnConnectionFailedListener {

    private static String TAG = "MyDashboardFragment";
    boolean isMock;
    View rootView;

    RecyclerView qstnsRecyclerView;
    RecyclerView.Adapter mAdapter;
    LinearLayoutManager mLayoutManager;
    String url = "";

    SharedPreferences pref;
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private SignInButton btnSignIn;
    private Button btnSignOut, btnRevokeAccess,btnSignOut1;
    private LinearLayout llProfileLayout;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail, txtUser,txtNoLoggedinMsg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView of MyDashboardFragment");
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        isMock = pref.getBoolean("isMock", false);
        boolean isLoggedIn = pref.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            rootView = inflater.inflate(R.layout.fragment_mydashboard_loggedin, container, false);

            mayRequestReadAccess();

            String loggedInUserName = pref.getString("loggedInUserName", "Guest");
            String loggedInProfilePicUrl = pref.getString("loggedInProfilePicUrl", null);
            //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/1485628183722.jpg";//pref.getString("loggedInProfilePicUrl",null);
            Log.d(TAG, "loggedInUserName=" + loggedInUserName + "\n loggedInProfilePicUrl=" + loggedInProfilePicUrl);
            ((TextView) rootView.findViewById(R.id.welcomeTxt)).setText(loggedInUserName);
                txtName=(TextView)rootView.findViewById(R.id.welcomeTxt);
            txtEmail = (TextView) rootView.findViewById(R.id.txtEmail1);
            txtUser = (TextView) rootView.findViewById(R.id.welcomeTxt);
            btnSignOut1 = (Button) rootView.findViewById(R.id.logoutbtn);
            btnSignOut1.setOnClickListener(this);
         /*   if (null != loggedInProfilePicUrl) {
                Bitmap bitmapImage = null;
                File f = new File(loggedInProfilePicUrl);
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), Uri.fromFile(f));
                } catch (Exception e) {
                    e.printStackTrace();
                    ;
                }
                if (null != bitmapImage)
                    ((ImageView) rootView.findViewById(R.id.userprofile)).setImageBitmap(bitmapImage);
            }
*/

            ((ImageButton) rootView.findViewById(R.id.plusbtn)).setOnClickListener(this);
            ((Button) rootView.findViewById(R.id.editprofilebtn)).setOnClickListener(this);
            ((Button) rootView.findViewById(R.id.logoutbtn)).setOnClickListener(this);

            qstnsRecyclerView = (RecyclerView) rootView.findViewById(R.id.mydashboard_recycler_qstns);
            qstnsRecyclerView.setHasFixedSize(true);
            getQstnsDataSet();

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            try {
                mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                        .enableAutoManage(getActivity(), this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            rootView = inflater.inflate(R.layout.fragment_mydashboard, container, false);
            // ((Button) rootView.findViewById(R.id.googlelogin)).setOnClickListener(this);
            ((Button) rootView.findViewById(R.id.fblogin)).setOnClickListener(this);
            ((Button) rootView.findViewById(R.id.applogin)).setOnClickListener(this);
            ((Button) rootView.findViewById(R.id.register)).setOnClickListener(this);


            //google login


            btnSignIn = (SignInButton) rootView.findViewById(R.id.btn_sign_in);
            btnSignOut = (Button) rootView.findViewById(R.id.btn_sign_out);
            btnRevokeAccess = (Button) rootView.findViewById(R.id.btn_revoke_access);
            llProfileLayout = (LinearLayout) rootView.findViewById(R.id.llProfile);
            imgProfilePic = (ImageView) rootView.findViewById(R.id.imgProfilePic);
            txtName = (TextView) rootView.findViewById(R.id.txtName);
            txtEmail = (TextView) rootView.findViewById(R.id.txtEmail);
            txtUser = (TextView) rootView.findViewById(R.id.welcome_User);
            txtNoLoggedinMsg=(TextView)rootView.findViewById(R.id.no_login_msg);
            btnSignIn.setOnClickListener(this);
            btnSignOut.setOnClickListener(this);
            btnRevokeAccess.setOnClickListener(this);

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            try {
                mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                        .enableAutoManage(getActivity(), this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Customizing G+ button
            btnSignIn.setSize(SignInButton.SIZE_STANDARD);
            btnSignIn.setScopes(gso.getScopeArray());


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
        Log.d(TAG, "inside onClick.." + v.getId());
        Intent gotoIntent = null;
        switch (v.getId()) {

            case R.id.fblogin:
                gotoIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(gotoIntent);
                break;
            case R.id.applogin:
                gotoIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(gotoIntent);
                break;
            case R.id.register:
                gotoIntent = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(gotoIntent);
                break;
            case R.id.plusbtn:
                gotoIntent = new Intent(getActivity(), AskQuestionActivity.class);
                startActivity(gotoIntent);
                break;
            case R.id.editprofilebtn:
                gotoIntent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(gotoIntent);
                break;
            case R.id.logoutbtn:
                /*gotoIntent = new Intent(getActivity(), LogoutActivity.class);
                startActivity(gotoIntent);*/
                signOut();
                break;

            case R.id.btn_sign_in:
                signIn();
                break;

            case R.id.btn_sign_out:
                signOut();
                break;

            case R.id.btn_revoke_access:
                revokeAccess();
                break;

            default:
                Log.d(TAG, "Unknown button clicked..");
                break;
        }

    }

    private void getQstnsDataSet() {

        Log.d(TAG, "isMock?" + isMock);
        isMock = true;
        if (isMock) {
            ArrayList<Qstns> qstns = populateMockQstnsSet();
            Log.d(TAG, "Populated qstns are " + qstns);
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
                            Qstns selectedQstn = new Qstns(qstnId, qstnTxt, qstnDate);
                            VideoApplication.selectedQstn = selectedQstn;
                            Log.d(TAG, "set with Qstn..");
                            Intent answerIntent = new Intent(getActivity(), ShowAnswerActivity.class);
                            startActivity(answerIntent);
                        }
                    }
            );

            qstnsRecyclerView.setAdapter(mAdapter);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
            qstnsRecyclerView.addItemDecoration(itemDecoration);
            Log.d(TAG, "Populated qstnsRecyclerView");
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
                                        Qstns selectedQstn = new Qstns(qstnId, qstnTxt, qstnDate);
                                        VideoApplication.selectedQstn = selectedQstn;
                                        Log.d(TAG, "set with Qstn..");
                                        Intent answerIntent = new Intent(getActivity(), ShowAnswerActivity.class);
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
        vo = new Qstns("qstn1", "Can you please help me with solving this question in Quant?", "23/01/2017");
        mockResults.add(vo);
        vo = new Qstns("qstn2", "Are the forms for CAT released? If so, how can I apply?", "23/01/2017");
        mockResults.add(vo);
        return mockResults;
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
       // txtNoLoggedinMsg.setVisibility(View.VISIBLE);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("loggedInUserName");
        editor.putBoolean("isLoggedIn",false);
        editor.commit();
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {


            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            //    String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();


            txtName.setText(personName);
            txtEmail.setText(email);

            txtUser.setText(personName);

            //txtNoLoggedinMsg.setVisibility(View.GONE);
            updateUI(true);

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            SharedPreferences.Editor editor = pref.edit();
        //    editor.remove("loggedInUserName");
            editor.putBoolean("isLoggedIn",true);
            editor.commit();



        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean isSignedIn) {
   if (isSignedIn) {


          /*  btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            // btnRevokeAccess.setVisibility(View.VISIBLE);
            llProfileLayout.setVisibility(View.VISIBLE);*/
        } else {
         /*   btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            // btnRevokeAccess.setVisibility(View.GONE);
            llProfileLayout.setVisibility(View.GONE);*/
        }
    }

}
