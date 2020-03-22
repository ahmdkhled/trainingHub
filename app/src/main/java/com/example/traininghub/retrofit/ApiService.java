package com.example.traininghub.retrofit;

import com.example.traininghub.models.CategoriesResponse;
import com.example.traininghub.models.CourseEnrollRes;
import com.example.traininghub.models.CoursesResponse;
import com.example.traininghub.models.Group;
import com.example.traininghub.models.GroupsRes;
import com.example.traininghub.models.Instructor;
import com.example.traininghub.models.LoginResponse;
import com.example.traininghub.models.ReviewsResponse;
import com.example.traininghub.models.StudentCourseRes;
import com.example.traininghub.models.User;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {


    @POST("auth/login")
    Single<LoginResponse> login(@Body User user);

    @POST("register")
    Single<LoginResponse> register(@Body User user);

    @GET("/api/instructors")
    Call<ArrayList<Instructor>> getInstructors(@Query("page")String page,@Query("limit")String limit);

    @GET("/api/courses")
    Single<Response<CoursesResponse>> getCourses(@Query("page")String page,@Query("limit")String limit
            ,@Query("category")String category);

    @GET("/api/categories")
    Single<Response<CategoriesResponse>> getCategories(@Query("courses")String courses, @Query("limit")String limit);

    @GET("/api/reviews")
    Single<Response<ReviewsResponse>> getReviews (@Query("course")String course, @Query("page")String page, @Query("limit")String limit);

    @GET("course_groups")
    Single<Response<GroupsRes>> getGroups (@Query("course_id")String course, @Query("page")String page, @Query("limit")String limit);

    @GET("course_enrollments")
    Single<Response<StudentCourseRes>> getStudentCourses (@Query("student")String student, @Query("page")String page, @Query("limit")String limit);


    @FormUrlEncoded
    @POST("course_enrollments")
    Single<Response<CourseEnrollRes>> enrollToCourse(@Field("student_id") String student_id, @Field("group_id") String group_id);


}
