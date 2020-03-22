package com.example.traininghub.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GroupsRes {

    @SerializedName("data")
    private ArrayList<Group> groups;


    public ArrayList<Group> getGroups() {
        return groups;
    }
}
