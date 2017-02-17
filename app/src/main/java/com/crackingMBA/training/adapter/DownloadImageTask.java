package com.crackingMBA.training.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by MSK on 02-08-2016.
 */
public  class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    public static Boolean isException=false;
    public static Context context;
    private String DEBUG_TAG="Cartoon";

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }



    public Bitmap doInBackground(String... urls)  {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            isException=false;
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
        Log.e(DEBUG_TAG, e.getMessage());
        e.printStackTrace();
            isException=true;
            Log.d(DEBUG_TAG, String.valueOf(isException));
    }

        return mIcon11;
    }

    public void onPostExecute(Bitmap result) {

        Context context = null;
        if (result != null) {
     /*       TranslateAnimation animation = new TranslateAnimation(0.0f, 100.0f,0.0f, 0.0f);
          animation.setDuration(10000);
           animation.setRepeatCount(0);
            animation.setRepeatMode(1);
//      animation.setFillAfter(true);  
            bmImage.startAnimation(animation);*/
            bmImage.setImageBitmap(result);

        }

    }
}