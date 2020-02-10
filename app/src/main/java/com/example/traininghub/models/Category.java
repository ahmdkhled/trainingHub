package com.example.traininghub.models;

import java.util.ArrayList;

public class Category {

    private int id;
    private int parent_id;
    private String name;
    private String image;
    private String description;
    ArrayList<Category> subCategories;

    public int getId() {
        return id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }
}
