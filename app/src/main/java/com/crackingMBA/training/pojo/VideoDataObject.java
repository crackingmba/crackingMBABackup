package com.crackingMBA.training.pojo;

/**
 * Created by Harish on 1/31/2017.
 */
public class VideoDataObject {

    private String id;
    private String videoTitle;
    private String thumbnailURL;
    private String videoURL;
    private String videoType;
    private String dateOfUploaded;
    private String duration;
    private String videoDescription;

    public VideoDataObject(){

    }

    public VideoDataObject(String id, String videoTitle, String thumbnailURL, String videoURL, String videoType, String dateOdUploaded, String duration, String videoDescription) {
        this.id = id;
        this.videoTitle = videoTitle;
        this.thumbnailURL = thumbnailURL;
        this.videoURL = videoURL;
        this.videoType = videoType;
        this.dateOfUploaded = dateOdUploaded;
        this.duration = duration;
        this.videoDescription = videoDescription;
    }

    @Override
    public String toString() {
        return "[id="+id+",videoTitle="+videoTitle+",thumbnailURL="+thumbnailURL+",videoURL="+videoURL+",videoType="+videoType+",dateOfUploaded="+dateOfUploaded+",duration="+duration+",videoDescription="+videoDescription+"]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getDateOdUploaded() {
        return dateOfUploaded;
    }

    public void setDateOdUploaded(String dateOdUploaded) {
        this.dateOfUploaded = dateOdUploaded;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }
}
