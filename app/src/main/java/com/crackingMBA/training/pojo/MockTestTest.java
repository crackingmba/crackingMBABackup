package com.crackingMBA.training.pojo;

/**
 * Created by MSK on 06-02-2017.
 */
public class MockTestTest {

    private String testId;
    private String mtSubCatId;
    private String testThumbnailUrl;
    private String testTitle;

    public MockTestTest(){

    }

    public MockTestTest(String testId, String mtSubCatId, String testThumbnailUrl, String testTitle) {
        this.testId = testId;
        this.mtSubCatId = mtSubCatId;
        this.testThumbnailUrl = testThumbnailUrl;
        this.testTitle = testTitle;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getMtSubCatId() {
        return mtSubCatId;
    }

    public void setMtSubCatId(String mtSubCatId) {
        this.mtSubCatId = mtSubCatId;
    }

    public String getTestThumbnailUrl() {
        return testThumbnailUrl;
    }

    public void setTestThumbnailUrl(String testThumbnailUrl) {
        this.testThumbnailUrl = testThumbnailUrl;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    @Override
    public String toString() {
        return "MockTestTest{" +
                "testId='" + testId + '\'' +
                ", mtSubCatId='" + mtSubCatId + '\'' +
                ", testThumbnailUrl='" + testThumbnailUrl + '\'' +
                ", testTitle='" + testTitle + '\'' +
                '}';
    }
}
