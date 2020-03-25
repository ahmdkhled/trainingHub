package com.example.traininghub.Repo;

import com.example.traininghub.models.Student;
import com.example.traininghub.retrofit.RetrofitClient;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

@Singleton
public class StudentsRepo {

    RetrofitClient retrofitClient;

    @Inject
    public StudentsRepo(RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
    }

    public Single<Response<Student>> registerStudent(RequestBody nameAr,
                                                     RequestBody nameEn,
                                                     RequestBody email,
                                                     RequestBody phoneNumber,
                                                     RequestBody phoneNumberSec,
                                                     RequestBody state,
                                                     RequestBody city,
                                                     RequestBody address,
                                                     RequestBody faculty,
                                                     RequestBody degree,
                                                     RequestBody idNumber,
                                                     RequestBody passportNumber,
                                                     RequestBody skillCardNumber,

                                                     MultipartBody.Part idImage,
                                                     MultipartBody.Part image){

        return retrofitClient.getApiService()
                .registerStudent(
                          nameAr,
                          nameEn,
                          email,
                          phoneNumber,
                          phoneNumberSec,
                          state,
                          city,
                          address,
                          faculty,
                          degree,
                          idNumber,
                          passportNumber,
                          skillCardNumber
                        
                        ,idImage,image );
    }

    public RetrofitClient getRetrofitClient() {
        return retrofitClient;
    }
}
