package com.crackingMBA.training.pojo;

import java.util.ArrayList;

/**
 * Created by Harish on 2/18/2017.
 */
public class MockTestTestsModel {
    private ArrayList<MockTestTest> mockTestList;

    public ArrayList<MockTestTest> getMockTestList() {
        return mockTestList;
    }

    public void setMockTestList(ArrayList<MockTestTest> mockTestList) {
        this.mockTestList = mockTestList;
    }

    @Override
    public String toString() {
        return "MockTestTestsModel{" +
                "mockTestList=" + mockTestList +
                '}';
    }
}
