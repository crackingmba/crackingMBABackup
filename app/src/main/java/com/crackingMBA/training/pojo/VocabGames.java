package com.crackingMBA.training.pojo;

/**
 * Created by vijayp on 7/7/17.
 */

public class VocabGames {

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

