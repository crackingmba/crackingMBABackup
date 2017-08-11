package com.crackingMBA.training;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by vijayp on 3/21/17.
 */
public class MyConfig {

    private MyConfig() {
    }

    public static final String YOUTUBE_API_KEY = "AIzaSyDm5HEV0z1Wh06-mpnExOFY3GGnCN98eRQ";



    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}