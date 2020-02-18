package com.example.traininghub.Repo;

import com.example.traininghub.models.Course;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.network.RetrofitClient;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.Response;

public class CoursesRepo {

    private static CoursesRepo coursesRepo;

    public static CoursesRepo getInstance() {
        return coursesRepo == null ? coursesRepo = new CoursesRepo() : coursesRepo;
    }

    private CoursesRepo() {

    }


    public Single<Response<CoursesResponse>> getCourses(String page,String limit){
        return RetrofitClient
                .getInstance()
                .getApi()
                .getCourses(page,limit);

    }
}
