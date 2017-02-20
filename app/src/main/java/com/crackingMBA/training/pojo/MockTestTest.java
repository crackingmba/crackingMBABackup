package com.crackingMBA.training.pojo;

/**
 * Created by MSK on 06-02-2017.
 */
public class MockTestTest {

    private String mocktestTestId;
    private String mocktestTestThumbnailURL;
    private String mocktestTestTitle;
    private String mockTestTopicTxt;
    private String mockTestNoOfQstns;
    private String mockTestDuration;

    public MockTestTest(){

    }

    public MockTestTest(String mocktestTestId, String mocktestTestThumbnailURL, String mocktestTestTitle, String mockTestTopicTxt, String mockTestNoOfQstns, String mockTestDuration) {
        this.mocktestTestId = mocktestTestId;
        this.mocktestTestThumbnailURL = mocktestTestThumbnailURL;
        this.mocktestTestTitle = mocktestTestTitle;
        this.mockTestTopicTxt = mockTestTopicTxt;
        this.mockTestNoOfQstns = mockTestNoOfQstns;
        this.mockTestDuration = mockTestDuration;
    }

    public String getMocktestTestId() {
        return mocktestTestId;
    }

    public void setMocktestTestId(String mocktestTestId) {
        this.mocktestTestId = mocktestTestId;
    }

    public String getMocktestTestThumbnailURL() {
        return mocktestTestThumbnailURL;
    }

    public void setMocktestTestThumbnailURL(String mocktestTestThumbnailURL) {
        this.mocktestTestThumbnailURL = mocktestTestThumbnailURL;
    }

    public String getMocktestTestTitle() {
        return mocktestTestTitle;
    }

    public void setMocktestTestTitle(String mocktestTestTitle) {
        this.mocktestTestTitle = mocktestTestTitle;
    }

    public String getMockTestTopicTxt() {
        return mockTestTopicTxt;
    }

    public void setMockTestTopicTxt(String mockTestTopicTxt) {
        this.mockTestTopicTxt = mockTestTopicTxt;
    }

    public String getMockTestNoOfQstns() {
        return mockTestNoOfQstns;
    }

    public void setMockTestNoOfQstns(String mockTestNoOfQstns) {
        this.mockTestNoOfQstns = mockTestNoOfQstns;
    }

    public String getMockTestDuration() {
        return mockTestDuration;
    }

    public void setMockTestDuration(String mockTestDuration) {
        this.mockTestDuration = mockTestDuration;
    }
}
