package com.crackingMBA.training;

import android.app.Application;

import java.util.Map;

import com.crackingMBA.training.pojo.MockTestTest;
import com.crackingMBA.training.pojo.MockTestTopic;
import com.crackingMBA.training.pojo.Qstns;
import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;

/**
 * Created by Harish on 2/3/2017.
 */
public class VideoApplication extends Application {

    public static VideoDataObject videoSelected = new VideoDataObject();
    public static VideoList videoList = new VideoList();
    public static String sectionClicked;
    public static Qstns selectedQstn;
    public static MockTestTopic selectedMockTestTopic;
    public static MockTestTest selectedMockTestTest;
    public static VideoApplication currentInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.currentInstance = this;
    }

    public  static  VideoApplication getInstance(){
        return currentInstance;
    }

}
