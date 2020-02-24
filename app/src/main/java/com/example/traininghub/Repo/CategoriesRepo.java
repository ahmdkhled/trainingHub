package com.example.traininghub.Repo;

import com.example.traininghub.models.CategoriesResponse;
import com.example.traininghub.retrofit.RetrofitClient;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.Response;

@Singleton
public class CategoriesRepo {

    private static CategoriesRepo categoriesRepo;
    private RetrofitClient retrofitClient;

    @Inject
    public CategoriesRepo(com.example.traininghub.retrofit.RetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
    }

    public Single<Response<CategoriesResponse>> getCategories(String page, String limit){
        return retrofitClient.getApiService().getCategories(page, limit);
    }
}
