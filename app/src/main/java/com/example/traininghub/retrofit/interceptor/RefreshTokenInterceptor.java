package com.example.traininghub.retrofit.interceptor;


import android.content.Context;
import android.util.Log;

import com.example.traininghub.model.LoginResponse;
import com.example.traininghub.model.User;
import com.example.traininghub.retrofit.RetrofitClient;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class RefreshTokenInterceptor implements Interceptor {
    private static final String TAG = "MainActivityTags";

    private static RefreshTokenInterceptor instance;
    private Context context;

    public RefreshTokenInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request mainRequest = chain.request();
        Response mainResponse = chain.proceed(mainRequest);

//        if(responseCount(mainResponse) >= 2){
//            Log.d(TAG, "RefreshTokenInterceptor: response count : "+responseCount(mainResponse));
//            return null;
//        }

        if (mainResponse.code() == 403) {
            Log.d(TAG, "RefreshTokenInterceptor: response code "+mainResponse.code());
//            retrofit2.Response<LoginResponse> newTokenResponse = new RetrofitClient().getApiService(context).login(new User("ibrahim@gmail.com", "123654789")).execute();
//
//            if (newTokenResponse.isSuccessful()) {
//                mainResponse.close();
//                // login request succeed, new token generated
//                Log.d(TAG, "RefreshTokenInterceptor: successful login response");
//                LoginResponse loginResponse = newTokenResponse.body();
//                Log.d(TAG, "RefreshTokenInterceptor: new access token " + loginResponse.getAccessToken());
//                tokenManager.saveToken("Bearer "+authorization.getAccessToken());
//                Request.Builder builder = mainRequest.newBuilder().header("Authorization", "Bearer " + loginResponse.getAccessToken()).
//                        method(mainRequest.method(), mainRequest.body());
//                mainResponse = chain.proceed(builder.build());
//            } else {
//                Log.d(TAG, "RefreshTokenInterceptor: error " + newTokenResponse.code());
//            }

        }

        return mainResponse;
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }



}
