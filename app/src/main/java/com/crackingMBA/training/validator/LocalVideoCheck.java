package com.crackingMBA.training.validator;

import android.app.Activity;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.List;

import com.crackingMBA.training.CrackingConstant;
import com.crackingMBA.training.db.DBHelper;
import com.crackingMBA.training.pojo.VideoList;

/**
 * Created by MSK on 29-01-2017.
 */
public class LocalVideoCheck {
    public static DBHelper dbHelper;
    public static String TAG="LocalVideoCheck";

    public static boolean verifyLocalStorage(final String name){

        String directory;

        Log.d("first","check fir the file"+ name);
        File dir = new File(CrackingConstant.localstoragepath+CrackingConstant.myFolder+CrackingConstant.noMedia);

        Log.d("first","check in the dir for  the file"+ CrackingConstant.localstoragepath+CrackingConstant.myFolder+CrackingConstant.noMedia);
        File[] list = dir.listFiles();
        if(list!=null)
            for (File fil : list)
            {

                Log.d("first","file name"+fil.getName());
                if (fil.getName().equals(name))
                {
                    Log.d("first","return the value true"+ name);
                    return true;
                }

            }
        Log.d("first","return false"+ name);
        return false;

    }

    public static boolean verifyLocalStorageByVideoID(final String videoID, Activity activity){

        String directory;
        dbHelper = DBHelper.getInstance(activity);
        List<VideoList> videoLists=dbHelper.getAllDownloadedVideos();
        Log.d(TAG,"videoLists"+ videoLists);
        for(VideoList videoList:videoLists){
            if(videoList.getVideoID().equals(videoID)){
                Log.d(TAG,"return true videoid"+ videoID);
                return true;
            }
        }

        Log.d(TAG,"return false videoid"+ videoID);
        return false;

    }
}
