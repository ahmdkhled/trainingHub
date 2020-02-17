package com.example.traininghub.helpers;

import com.example.traininghub.models.Content;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ContentParser {

    public static ArrayList<Content> getCourseContent(String content){
        Gson gson=new Gson();
        return gson.fromJson(content,new TypeToken<ArrayList<Content>>(){}.getType());
    }
}
