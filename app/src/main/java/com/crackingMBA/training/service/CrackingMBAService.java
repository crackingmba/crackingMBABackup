package com.crackingMBA.training.service;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.crackingMBA.training.pojo.SubCategories;

/**
 * Created by MSK on 07-02-2017.
 */
public class CrackingMBAService {/*

    private final String TAG = this.getClass().getName();

    public static SubCategories getSectionDataSet(String sectionName) {

        final ArrayList<SubCategories> subCategoriesList = new ArrayList<SubCategories>();
        String url = "http://crackingmba.com/getSubCategories.php";
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, null, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(String response) {
                    Log.d("", "Response is : " + response);
                    Gson gson = new Gson();
                    SubCategories subCategories = gson.fromJson(response, SubCategories.class);
                    subCategoriesList.add(subCategories);
                    Log.d(TAG, "subCategories : " + subCategories);
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
        if (subCategoriesList.size() > 0)
            return subCategoriesList.get(0);
        else
            return null;
    }*/
}
