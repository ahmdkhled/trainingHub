package com.example.traininghub.retrofit.interceptor;

import android.util.Log;

import com.example.traininghub.helpers.TokenManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;


import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetrofitInterceptor implements Interceptor {

    private TokenManager tokenManager;
    private static final String TAG = "MainActivityTags";

    @Inject
    public RetrofitInterceptor(TokenManager tokenManager){
        Log.d(TAG, "RetrofitInterceptor: cons");
        this.tokenManager = tokenManager;
    }


    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(TAG, "intercept: token save in pref : "+tokenManager.getToken());
        Request request = chain.request();
        Request.Builder builder = request.newBuilder()
                .header("Authorization", tokenManager.getToken());
        Log.d(TAG, "intercept: "+chain.request().toString());
        return chain.proceed(builder.build());
    }




}
