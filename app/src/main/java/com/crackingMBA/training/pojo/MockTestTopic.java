package com.crackingMBA.training.pojo;

/**
 * Created by MSK on 06-02-2017.
 */
public class MockTestTopic {

    private String id;

    private String category_name;

    private String name;

    private String thumbnail;

    public MockTestTopic(){

    }

    public MockTestTopic(String id, String category_name, String name, String thumbnail) {
        this.id = id;
        this.category_name = category_name;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "MockTestTopic{" +
                "id='" + id + '\'' +
                ", category_name='" + category_name + '\'' +
                ", name='" + name + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
