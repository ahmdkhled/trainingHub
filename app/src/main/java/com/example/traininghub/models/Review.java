package com.example.traininghub.models;

public class Review {

    private int id;
    private float rating;
    private String review_body;
    private int course_id;
    private String created_at;
    private String updated_at;
    private Student student;



    public int getId() {
        return id;
    }

    public float getRating() {
        return rating;
    }

    public String getReview_body() {
        return review_body;
    }

    public int getCourse_id() {
        return course_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Student getStudent() {
        return student;
    }
}
