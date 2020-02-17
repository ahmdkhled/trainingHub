package com.example.traininghub.models;

import androidx.annotation.NonNull;

public class Content {

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @NonNull
    @Override
    public String toString() {
        return name+ "  >> "+description;
    }
}
