package com.example.traininghub.Repo;

import com.example.traininghub.models.ReviewsResponse;
import com.example.traininghub.network.RetrofitClient;

import io.reactivex.Single;
import retrofit2.Response;

public class ReviewsRepo {

    private static ReviewsRepo reviewsRepo;

    public static ReviewsRepo getInstance() {
        return reviewsRepo == null ? reviewsRepo = new ReviewsRepo() : reviewsRepo;
    }


    public Single<Response<ReviewsResponse>> getReviews (String course,String page, String limit){
        return RetrofitClient
                .getInstance()
                .getApi()
                .getReviews(course,page,limit);
    }



}
