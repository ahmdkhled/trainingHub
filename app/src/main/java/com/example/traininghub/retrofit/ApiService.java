package com.example.traininghub.retrofit;

import com.example.traininghub.models.APIResponse;
import com.example.traininghub.models.LoginResponse;
import com.example.traininghub.models.User;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {


    @POST("auth/login")
    Single<LoginResponse> login(@Body User user);

    @POST("register")
    Single<APIResponse> register(@Body User user);





}
