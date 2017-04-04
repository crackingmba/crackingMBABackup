package com.crackingMBA.training;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.crackingMBA.training.pojo.MockTestQuestion;
import com.crackingMBA.training.pojo.MockTestTest;
import com.crackingMBA.training.pojo.MockTestTopic;
import com.crackingMBA.training.pojo.Qstns;
import com.crackingMBA.training.pojo.Question;
import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Harish on 2/3/2017.
 */
public class VideoApplication extends Application {

    public static VideoDataObject videoSelected = new VideoDataObject();
    public static VideoList videoList = new VideoList();
    public static String sectionClicked;
    public static Question selectedQstn;
    public static String selectedMockTestTopicTitle;
    public static MockTestTopic selectedMockTestTopic;
    public static MockTestTest selectedMockTestTest;
    public static List<MockTestQuestion> allMockQstns = new ArrayList<>();
    public static MockTestQuestion selectedMockTestQuestion;

    public static List<VideoList> allQuantVideos;
    public static List<VideoList> allDilrVideos;
    public static List<VideoList> allVerbalVideos;
    public static List<VideoList> allDownloadedVideos;
    public static Set<String> downloadingVideoIds = new HashSet<>();

    public static List<Question> loggedInUserQstns;

    public static VideoApplication currentInstance;

    public static String subcategorySelected;
    public static String subcategoryID;
    public static String videoURLSelected;
    public static String registeringUserName;
    public static String registeringUserEmail;
    public static String registeringUserPwd;

    @Override
    public void onCreate() {
        super.onCreate();
        this.currentInstance = this;

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity,
                                          Bundle savedInstanceState) {

                // Force activity's orientation to portrait
                activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }
        });
    }

    public  static  VideoApplication getInstance(){
        return currentInstance;
    }

}
