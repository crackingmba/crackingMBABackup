package com.crackingMBA.training.pojo;

public class Exam {

    private String id;
    private String name;
    private String description;
    private String dates;
    private String notifications;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dates='" + dates + '\'' +
                ", notifications='" + notifications + '\'' +
                '}';
    }
}
