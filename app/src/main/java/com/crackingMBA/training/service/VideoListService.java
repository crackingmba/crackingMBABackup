package com.crackingMBA.training.service;

import java.util.List;

/**
 * Created by MSK on 29-01-2017.
 */
public class VideoListService {
    List<String> videolist;
    public List<String> getVideolist() {
        return videolist;
    }

    public void setVideolist(List<String> videolist) {

        videolist.add("video.3gp");
        videolist.add("video.mp4");
        this.videolist = videolist;
    }



}
