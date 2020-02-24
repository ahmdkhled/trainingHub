package com.example.traininghub.Repo;

import com.example.traininghub.models.CategoriesResponse;
import com.example.traininghub.network.RetrofitClient;

import io.reactivex.Single;
import retrofit2.Response;

public class CategoriesRepo {

    private static CategoriesRepo categoriesRepo;

    public static CategoriesRepo getInstance() {
        return categoriesRepo==null?categoriesRepo=new CategoriesRepo():categoriesRepo;
    }

    public Single<Response<CategoriesResponse>> getCategories(String page, String limit){
        return RetrofitClient
                .getInstance()
                .getApi()
                .getCategories(page,limit);
    }
}
