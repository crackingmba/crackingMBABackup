package com.crackingMBA.training.pojo;

import android.widget.TextView;

/**
 * Created by MSK on 06-02-2017.
 */
public class MockTestTopic {

    private String mocktestTopicId;

    private String mocktestTopicThumbnailURL;

    private String mocktestTopicTxt;

    public MockTestTopic(){

    }

    public MockTestTopic(String mocktestTopicId, String mocktestTopicThumbnailURL, String mocktestTopicTxt) {
        this.mocktestTopicId = mocktestTopicId;
        this.mocktestTopicThumbnailURL = mocktestTopicThumbnailURL;
        this.mocktestTopicTxt = mocktestTopicTxt;
    }

    public String getMocktestTopicId() {
        return mocktestTopicId;
    }

    public void setMocktestTopicId(String mocktestTopicId) {
        this.mocktestTopicId = mocktestTopicId;
    }

    public String getMocktestTopicThumbnailURL() {
        return mocktestTopicThumbnailURL;
    }

    public void setMocktestTopicThumbnailURL(String mocktestTopicThumbnailURL) {
        this.mocktestTopicThumbnailURL = mocktestTopicThumbnailURL;
    }

    public String getMocktestTopicTxt() {
        return mocktestTopicTxt;
    }

    public void setMocktestTopicTxt(String mocktestTopicTxt) {
        this.mocktestTopicTxt = mocktestTopicTxt;
    }

    @Override
    public String toString()
    {
        return "MockTestTopic [mocktestTopicId = "+mocktestTopicId+", mocktestTopicThumbnailURL = "+mocktestTopicThumbnailURL+", mocktestTopicTxt = "+mocktestTopicTxt+"]";
    }
}
