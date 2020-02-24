package com.example.traininghub.retrofit;

import com.example.traininghub.model.Authorization;
import com.example.traininghub.model.LoginResponse;
import com.example.traininghub.model.Student;
import com.example.traininghub.model.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {


    @POST("auth/login")

   Single<LoginResponse> login(@Body User user);

//    @POST("auth/login")
//    Call<LoginResponse> login(@Body User user);

    @POST("auth/refresh")
    Call<Authorization> refreshToken();


    @GET("students")
    Call<List<Student>> getStudents();


}
