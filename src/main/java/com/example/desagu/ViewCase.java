package com.example.desagu;

public class ViewCase {

    private String contact,title,description;


    public ViewCase(String contact, String title, String description) {
        this.contact = contact;
        this.title = title;
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
