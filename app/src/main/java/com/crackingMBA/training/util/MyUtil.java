package com.crackingMBA.training.util;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.File;

import com.crackingMBA.training.CrackingConstant;
import com.crackingMBA.training.FullscreenActivity;
import com.crackingMBA.training.VideoApplication;
import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;
import com.crackingMBA.training.validator.LocalVideoCheck;

/**
 * Created by MSK on 05-02-2017.
 */
public class MyUtil {

public static boolean checkConnectivity(Context context) {
    ConnectivityManager cm =
            (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null &&
            activeNetwork.isConnectedOrConnecting();
    Log.d("first","internet connectivtiy status "+isConnected);
    return isConnected;
}
}
