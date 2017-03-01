package com.crackingMBA.training.pojo;

/**
 * Created by MSK on 06-02-2017.
 */

public class VideoList {

    private String videoDescription;

    private String videoID;

    private String Duration;

    private String videoSubCategory;

    private String videoTitle;

    private String videoCategory;

    private String uploadDate;

    private String videoURL;

    private String thumbnailURL;

    private String categoryFullName;

    private String subCategoryFullName;

    public String getVideoDownloadURL() {
        return videoDownloadURL;
    }

    public void setVideoDownloadURL(String videoDownloadURL) {
        this.videoDownloadURL = videoDownloadURL;
    }

    public String getVideoYouTubeURL() {
        return videoYouTubeURL;
    }

    public void setVideoYouTubeURL(String videoYouTubeURL) {
        this.videoYouTubeURL = videoYouTubeURL;
    }

    private String videoDownloadURL;

    private String videoYouTubeURL;


    public String getVideoDescription ()
    {
        return videoDescription;
    }

    public void setVideoDescription (String videoDescription)
    {
        this.videoDescription = videoDescription;
    }

    public String getVideoID ()
    {
        return videoID;
    }

    public void setVideoID (String videoID)
    {
        this.videoID = videoID;
    }

    public String getDuration ()
    {
        return Duration;
    }

    public void setDuration (String Duration)
    {
        this.Duration = Duration;
    }

    public String getVideoSubCategory ()
    {
        return videoSubCategory;
    }

    public void setVideoSubCategory (String videoSubCategory)
    {
        this.videoSubCategory = videoSubCategory;
    }

    public String getVideoTitle ()
    {
        return videoTitle;
    }

    public void setVideoTitle (String videoTitle)
    {
        this.videoTitle = videoTitle;
    }

    public String getVideoCategory ()
    {
        return videoCategory;
    }

    public void setVideoCategory (String videoCategory)
    {
        this.videoCategory = videoCategory;
    }

    public String getUploadDate ()
    {
        return uploadDate;
    }

    public void setUploadDate (String uploadDate)
    {
        this.uploadDate = uploadDate;
    }

    public String getVideoURL ()
    {
        return videoURL;
    }

    public void setVideoURL (String videoURL)
    {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL ()
    {
        return thumbnailURL;
    }

    public void setThumbnailURL (String thumbnailURL)
    {
        this.thumbnailURL = thumbnailURL;
    }

    public String getCategoryFullName() {
        return categoryFullName;
    }

    public void setCategoryFullName(String categoryFullName) {
        this.categoryFullName = categoryFullName;
    }

    public String getSubCategoryFullName() {
        return subCategoryFullName;
    }

    public void setSubCategoryFullName(String subCategoryFullName) {
        this.subCategoryFullName = subCategoryFullName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [videoDescription = "+videoDescription+", videoID = "+videoID+", Duration = "+Duration+", videoSubCategory = "+videoSubCategory+", videoTitle = "+videoTitle+", videoCategory = "+videoCategory+", uploadDate = "+uploadDate+", videoURL = "+videoURL+", thumbnailURL = "+thumbnailURL+"]";
    }
    public VideoList(String vidoID, String videoTitle, String thumbnailURL, String videoURL, String videoCategory,String videoSubCategory, String dateOdUploaded, String duration, String videoDescription,String categoryFullName,String subCategoryFullName,String videoYouTubeURL,String videoDownloadURL) {
        this.videoID = vidoID;
        this.videoTitle = videoTitle;
        this.thumbnailURL = thumbnailURL;
        this.videoURL = videoURL;
        this.videoCategory= videoCategory;
        this.videoSubCategory= videoSubCategory;
        this.uploadDate = dateOdUploaded;
        this.Duration = duration;
        this.videoDescription = videoDescription;
        this.categoryFullName=categoryFullName;
        this.subCategoryFullName=subCategoryFullName;
        this.videoYouTubeURL=videoYouTubeURL;
        this.videoDownloadURL=videoDownloadURL;
    }
    public VideoList(){

    }


}
