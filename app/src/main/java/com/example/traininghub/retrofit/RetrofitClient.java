package com.example.traininghub.retrofit;

import com.example.traininghub.retrofit.interceptor.RetrofitInterceptor;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traininghub.utils.Constants.BASE_URL;


public class RetrofitClient {

    private static Retrofit instance;
    private static ApiService iRemoteInterface;
    private RetrofitInterceptor retrofitInterceptor;

    @Inject
    public RetrofitClient(RetrofitInterceptor retrofitInterceptor) {
        this.retrofitInterceptor = retrofitInterceptor;
    }

    public  ApiService getApiService(){
        if(iRemoteInterface == null)
            iRemoteInterface = getRetrofitInstance().create(ApiService.class);
        return iRemoteInterface;

    }

    public Retrofit getRetrofitInstance() {
        if(instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return instance;
    }

    private OkHttpClient getOkHttpClient(){
        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(retrofitInterceptor)
//                .addInterceptor(new RefreshTokenInterceptor(tokenManager))
                .build();

    }


}
