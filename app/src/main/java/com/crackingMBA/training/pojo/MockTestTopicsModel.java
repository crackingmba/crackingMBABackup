package com.crackingMBA.training.pojo;

import java.util.ArrayList;

/**
 * Created by Harish on 2/18/2017.
 */
public class MockTestTopicsModel {
    private String mTSubCatTitle;
    private ArrayList<MockTestTopic> mTSubCatList;

    public String getmTSubCatTitle() {
        return mTSubCatTitle;
    }

    public void setmTSubCatTitle(String mTSubCatTitle) {
        this.mTSubCatTitle = mTSubCatTitle;
    }

    public ArrayList<MockTestTopic> getmTSubCatList() {
        return mTSubCatList;
    }

    public void setmTSubCatList(ArrayList<MockTestTopic> mTSubCatList) {
        this.mTSubCatList = mTSubCatList;
    }

    @Override
    public String toString() {
        return "MockTestTopicsModel{" +
                "mTSubCatTitle='" + mTSubCatTitle + '\'' +
                ", mTSubCatList=" + mTSubCatList +
                '}';
    }
}
