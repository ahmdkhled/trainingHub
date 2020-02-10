package com.example.traininghub.models;

import java.util.ArrayList;

public class CategoriesResponse extends Response {

    private ArrayList<Category> categories;

    public CategoriesResponse(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }
}
