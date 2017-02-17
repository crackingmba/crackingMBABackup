package com.crackingMBA.training.validator;

import android.util.Log;

import java.io.File;
import java.io.FileFilter;

import com.crackingMBA.training.CrackingConstant;

/**
 * Created by MSK on 29-01-2017.
 */
public class LocalVideoCheck {

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
}
