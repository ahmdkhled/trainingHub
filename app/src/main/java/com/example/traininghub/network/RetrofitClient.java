package com.example.traininghub.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    
    private static  RetrofitClient retrofitClient;
    private Retrofit retrofit;
    private Api api;

    public static RetrofitClient getInstance() {
        return retrofitClient == null ? retrofitClient=new RetrofitClient() : retrofitClient;
    }

    private Retrofit getRetrofit()
    {
        return retrofit==null?new Retrofit.Builder()
                .baseUrl("http://traininghub.tk")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build():retrofit;

    }

    public Api getApi(){
        return api==null?api=getRetrofit().create(Api.class):api;
    }
}