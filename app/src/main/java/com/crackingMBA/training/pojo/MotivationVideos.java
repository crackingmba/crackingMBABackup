package com.crackingMBA.training.pojo;

/**
 * Created by vijayp on 7/20/17.
 */

public class MotivationVideos {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VocabGames{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
