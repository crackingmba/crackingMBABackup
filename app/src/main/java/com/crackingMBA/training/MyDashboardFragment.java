package com.crackingMBA.training;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.crackingMBA.training.pojo.LoginResponseObject;
import com.crackingMBA.training.pojo.Question;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
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

import org.json.JSONObject;

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

    SharedPreferences pref;
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private SignInButton btnSignIn;
    private Button btnSignOut, btnRevokeAccess,btnSignOut1;
    private LinearLayout llProfileLayout;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail, txtUser,txtNoLoggedinMsg;
    //this is a sample comment

    //fb variables
private LoginButton fbloginButton;
    CallbackManager callbackManager;



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
          //  ((Button) rootView.findViewById(R.id.fblogin)).setOnClickListener(this);
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



            //fb login

            fbloginButton = (LoginButton) rootView.findViewById(R.id.login_button);
            fbloginButton.setReadPermissions("email");
            // If using in a fragment
            fbloginButton.setFragment(this);
            // Other app specific specialization
            callbackManager = CallbackManager.Factory.create();
            // Callback registration
            fbloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.d(TAG,"onSuccess RESPONSE:");

                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(
                                        JSONObject object,
                                        GraphResponse response) {
                                   Log.d(TAG,"FB RESPONSE:"+response);
                                    try {
                                        //txtNoLoggedinMsg.setVisibility(View.GONE);
                                        txtName.setText(response.getJSONObject().getString("name"));
                                        txtEmail.setText(response.getJSONObject().getString("email"));

                                        txtUser.setText(response.getJSONObject().getString("name"));
                                    }
                                    catch (Exception e){

                                    }
                                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                                    SharedPreferences.Editor editor = pref.edit();
                                    //    editor.remove("loggedInUserName");
                                    editor.putBoolean("isLoggedIn",true);
                                    editor.commit();
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,link,email");
                    request.setParameters(parameters);
                    request.executeAsync();

                }

                @Override
                public void onCancel() {
                    Log.d(TAG,"onSuccess onCancel:");
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    Log.d(TAG,"onSuccess onError:");
                    // App code
                }
            });



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

        /*    case R.id.fblogin:
                gotoIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(gotoIntent);
                break;*/
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
        if (isMock) {
            final ArrayList<Question> qstns = populateMockQstnsSet();
            VideoApplication.loggedInUserQstns = qstns;
            Log.d(TAG, "Populated qstns are " + qstns);
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

            qstnsRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new QuestionsViewAdapter(qstns);

            ((QuestionsViewAdapter) mAdapter).setOnItemClickListener(
                    new QuestionsViewAdapter.MyClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            Log.d(TAG, "Section adapter, Clicked item at position : " + position);
                            VideoApplication.selectedQstn = VideoApplication.loggedInUserQstns.get(position);
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

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String email = pref.getString("loggedInUserEmail",null);
            String pwd = pref.getString("loggedInUserPassword",null);
            RequestParams params = new RequestParams();
            params.put("email", email);
            params.put("password", pwd);
            Log.d(TAG, "loginServiceCall for populating questions..");
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(CrackingConstant.LOGIN_SERVICE_URL, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, " Login Response is : " + response);
                        Gson gson = new Gson();
                        LoginResponseObject loginResponseObject = gson.fromJson(response, LoginResponseObject.class);
                        Log.d(TAG,"Parsed response is "+loginResponseObject);
                        if (loginResponseObject != null && loginResponseObject.getUserQuestions() != null) {
                            (( TextView) rootView.findViewById(R.id.qstns_not_available)).setVisibility(View.GONE);
                            List<Question> questions = loginResponseObject.getUserQuestions();

                            VideoApplication.loggedInUserQstns = questions;
                            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            Log.d(TAG,"Questions populated are" + questions);
                            qstnsRecyclerView.setLayoutManager(mLayoutManager);
                            mAdapter = new QuestionsViewAdapter(questions);

                            ((QuestionsViewAdapter) mAdapter).setOnItemClickListener(
                                    new QuestionsViewAdapter.MyClickListener() {
                                        @Override
                                        public void onItemClick(int position, View v) {
                                            Log.d(TAG, "Section adapter, Clicked item at position : " + position);
                                            VideoApplication.selectedQstn = VideoApplication.loggedInUserQstns.get(position);
                                            Log.d(TAG, "set with Qstn..");
                                            Intent answerIntent = new Intent(getActivity(), ShowAnswerActivity.class);
                                            startActivity(answerIntent);
                                        }
                                    }
                            );

                            qstnsRecyclerView.setAdapter(mAdapter);
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
                            qstnsRecyclerView.addItemDecoration(itemDecoration);
                        }else{
                            (( TextView) rootView.findViewById(R.id.qstns_not_available)).setVisibility(View.VISIBLE);
                            Log.d(TAG,"There is no subcategories for the category selected");
                        }
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

    public ArrayList<Question> populateMockQstnsSet() {
        ArrayList<Question> mockResults = new ArrayList<Question>();
        Question vo = null;
        vo = new Question("qstn1", "Can you please help me with solving this question in Quant?", "23/01/2017","This is Answer","25/01/2017");
        mockResults.add(vo);
        vo = new Question("qstn2", "Are the forms for CAT released? If so, how can I apply?", "26/01/2017","This is Another Answer","28/01/2017");
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
      else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
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
        }catch(Exception e){
            e.printStackTrace();
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

/* fb profile inforamtion**//*

    public void getProfileInformation() {
        mAsyncRunner.request("me", new RequestListener() {
            @Override
            public void onComplete(String response, Object state) {
                Log.d("Profile", response);
                String json = response;
                try {
                    JSONObject profile = new JSONObject(json);
                    // getting name of the user
                    String name = profile.getString("name");
                    // getting email of the user
                    String email = profile.getString("email");

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
                        }

                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onIOException(IOException e, Object state) {
            }

            @Override
            public void onFileNotFoundException(FileNotFoundException e,
                                                Object state) {
            }

            @Override
            public void onMalformedURLException(MalformedURLException e,
                                                Object state) {
            }

            @Override
            public void onFacebookError(FacebookRequestError e, Object state) {
            }
        });
    }*/