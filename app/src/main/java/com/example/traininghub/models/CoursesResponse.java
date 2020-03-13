package com.example.traininghub.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CoursesResponse {

    @SerializedName("data")
    private ArrayList<Course> courses;

    public ArrayList<Course> getCourses() {
        return courses;
    }
}
