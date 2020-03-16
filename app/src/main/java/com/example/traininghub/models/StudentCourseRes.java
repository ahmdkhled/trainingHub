package com.example.traininghub.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StudentCourseRes {

    @SerializedName("data")
    private ArrayList<StudentCourse> studentCourses;

    public ArrayList<StudentCourse> getStudentCourses() {
        return studentCourses;
    }
}
