package com.example.desagu;

public class Notification {

    private String title,description,date,by;

    public Notification(String title, String description, String date, String by) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.by = by;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getBy() {
        return by;
    }
}
