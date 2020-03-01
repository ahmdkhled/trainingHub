package com.example.traininghub.models;

public class Group {
    private String title;
    private String start;
    private String end;

    public Group(String title, String start) {
        this.title = title;
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
