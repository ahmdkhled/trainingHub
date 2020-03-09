package com.example.traininghub.models;

import androidx.annotation.NonNull;

public class CourseEnrollRes {

    String message;

    public CourseEnrollRes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @NonNull
    @Override
    public String toString() {
        return message;
    }
}
