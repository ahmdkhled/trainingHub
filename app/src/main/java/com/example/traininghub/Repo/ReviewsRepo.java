package com.example.traininghub.Repo;

import com.example.traininghub.models.ReviewsResponse;
import com.example.traininghub.retrofit.RetrofitClient;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.Response;

@Singleton
public class ReviewsRepo {


    private RetrofitClient retrofitClient;

    @Inject
    public ReviewsRepo(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
    }

    public Single<Response<ReviewsResponse>> getReviews (String course, String page, String limit){
        return retrofitClient.getApiService().getReviews(course, page, limit);
    }



}
