package com.crackingMBA.training;

import android.app.Application;

import com.crackingMBA.training.pojo.MockTestQuestion;
import com.crackingMBA.training.pojo.MockTestTest;
import com.crackingMBA.training.pojo.MockTestTopic;
import com.crackingMBA.training.pojo.Qstns;
import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harish on 2/3/2017.
 */
public class VideoApplication extends Application {

    public static VideoDataObject videoSelected = new VideoDataObject();
    public static VideoList videoList = new VideoList();
    public static String sectionClicked;
    public static Qstns selectedQstn;
    public static String selectedMockTestTopicTitle;
    public static MockTestTopic selectedMockTestTopic;
    public static MockTestTest selectedMockTestTest;
    public static List<MockTestQuestion> allMockQstns = new ArrayList<>();
    public static MockTestQuestion selectedMockTestQuestion;
    public static VideoApplication currentInstance;

    public static String subcategorySelected;
    public static String videoURLSelected;
    @Override
    public void onCreate() {
        super.onCreate();
        this.currentInstance = this;
    }

    public  static  VideoApplication getInstance(){
        return currentInstance;
    }

}
