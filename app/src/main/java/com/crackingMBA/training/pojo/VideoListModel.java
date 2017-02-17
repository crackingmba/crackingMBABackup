package com.crackingMBA.training.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSK on 06-02-2017.
 */
public class VideoListModel {

    private ArrayList<VideoList> videoList;

    public ArrayList<VideoList> getVideoList ()
    {
        return videoList;
    }

    public void setVideoList (ArrayList<VideoList> videoList)
    {
        this.videoList = videoList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [videoList = "+videoList+"]";
    }
}
