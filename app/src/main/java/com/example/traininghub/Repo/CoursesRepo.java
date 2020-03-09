package com.example.traininghub.Repo;

import com.example.traininghub.models.Course;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.retrofit.RetrofitClient;

import java.util.ArrayList;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;

@Singleton
public class CoursesRepo {

    private RetrofitClient retrofitClient;

    @Inject
    public CoursesRepo(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
    }


    public Single<Response<CoursesResponse>> getCourses(String page,String limit,String category){
        return retrofitClient
                .getApiService()
                .getCourses(page,limit,category);


    }

    public Single<Response<ResponseBody>> enrollToCourse(String student_id,String group_id){
        return retrofitClient
                .getApiService()
                .enrollToCourse(student_id,group_id);
    }

}
