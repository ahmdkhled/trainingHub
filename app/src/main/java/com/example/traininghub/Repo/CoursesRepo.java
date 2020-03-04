package com.example.traininghub.Repo;

import com.example.traininghub.models.Course;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.retrofit.RetrofitClient;

import java.util.ArrayList;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.Response;

@Singleton
public class CoursesRepo {

    private RetrofitClient retrofitClient;
    private static CoursesRepo coursesRepo;

    @Inject
    public CoursesRepo(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
    }


    public static CoursesRepo getInstance() {
        return coursesRepo == null ?coursesRepo=new CoursesRepo():coursesRepo;
    }


    private CoursesRepo() {

    }


    public Single<Response<CoursesResponse>> getCourses(String page,String limit,String category){
        return retrofitClient
                .getApiService()
                .getCourses(page,limit,category);


    }
}
