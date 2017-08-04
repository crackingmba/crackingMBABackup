package com.crackingMBA.training;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;

public class MyDashboardFragment extends Fragment implements View.OnClickListener {

    private static String TAG = "MyDashboardFragment";
    boolean isMock;
    View rootView;

    SharedPreferences pref;
    private static final int RC_SIGN_IN = 007;

    private SignInButton btnSignIn;
    private Button btnSignOut, btnRevokeAccess,btnSignOut1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView of MyDashboardFragment");
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        isMock = pref.getBoolean("isMock", false);
        boolean isLoggedIn = pref.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            rootView = inflater.inflate(R.layout.fragment_mydashboard_loggedin, container, false);

            String loggedInUserName = pref.getString("loggedInUserName", "Guest");
            String loggedInProfilePicUrl = pref.getString("loggedInProfilePicUrl", null);
            String loggedInUserEmail = pref.getString("loggedInUserEmail",null);
            String loggedInUserPassword = pref.getString("loggedInUserPassword",null);
            Log.d(TAG, "loggedInUserName=" + loggedInUserName + "\n loggedInProfilePicUrl=" + loggedInProfilePicUrl);
            ((TextView) rootView.findViewById(R.id.welcomeTxt)).setText(loggedInUserName);
//            txtName=(TextView)rootView.findViewById(R.id.welcomeTxt);
  //          txtEmail = (TextView) rootView.findViewById(R.id.txtEmail1);
            ((TextView) rootView.findViewById(R.id.txtEmail1)).setText(loggedInUserEmail);
    //        txtUser = (TextView) rootView.findViewById(R.id.welcomeTxt);
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
            // ((Button) rootView.findViewById(R.id.editprofilebtn)).setOnClickListener(this);
            ((Button) rootView.findViewById(R.id.logoutbtn)).setOnClickListener(this);


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
            //llProfileLayout = (LinearLayout) rootView.findViewById(R.id.llProfile);
            //imgProfilePic = (ImageView) rootView.findViewById(R.id.imgProfilePic);
            //txtName = (TextView) rootView.findViewById(R.id.txtName);
            //txtEmail = (TextView) rootView.findViewById(R.id.txtEmail);
            //txtUser = (TextView) rootView.findViewById(R.id.welcome_User);
            //txtNoLoggedinMsg=(TextView)rootView.findViewById(R.id.no_login_msg);
            btnSignIn.setOnClickListener(this);
            btnSignOut.setOnClickListener(this);
            btnRevokeAccess.setOnClickListener(this);



        }

        return rootView;
    }

    private void    fblogginFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, "inside onClick.." + v.getId());
        Intent gotoIntent = null;
        switch (v.getId()) {

            default:
                Log.d(TAG, "Unknown button clicked..");
                break;
        }

    }


}
