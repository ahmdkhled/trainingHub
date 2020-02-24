package com.example.traininghub.retrofit.interceptor;

import android.content.Context;
import android.util.Log;

import com.example.traininghub.retrofit.RetrofitClient;
import com.example.traininghub.utils.TokenManager;

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
        this.tokenManager = tokenManager;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(TAG, "intercept: token save in pref : "+tokenManager.getToken());
        Request request = chain.request();
        Request.Builder builder = request.newBuilder()
                .header("Authorization", tokenManager.getToken());

        return chain.proceed(builder.build());
    }
}
