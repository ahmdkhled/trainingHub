package com.example.traininghub.network;

import com.example.traininghub.models.CategoriesResponse;
import com.example.traininghub.models.Category;
import com.example.traininghub.models.Course;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.models.Instructor;
import com.example.traininghub.models.ReviewsResponse;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("/api/instructors")
    Call<ArrayList<Instructor>> getInstructors(@Query("page")String page,@Query("limit")String limit);

    @GET("/api/courses")
    Single<Response<CoursesResponse>> getCourses(@Query("page")String page,@Query("limit")String limit);

    @GET("/api/categories")
    Single<Response<CategoriesResponse>> getCategories(@Query("courses")String courses, @Query("limit")String limit);

    @GET("/api/reviews")
    Single<Response<ReviewsResponse>> getReviews (@Query("course")String course,@Query("page")String page,@Query("limit")String limit);

}
