package com.example.traininghub.retrofit;

import android.util.Log;

import com.example.traininghub.retrofit.interceptor.RetrofitInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.traininghub.helpers.Constants.BASE_URL;

@Singleton
public class RetrofitClient {

    private static final String TAG = RetrofitClient.class.getSimpleName();
    private static Retrofit retrofit;
    private static ApiService apiService;
    private RetrofitInterceptor retrofitInterceptor;

    @Inject
    public RetrofitClient(RetrofitInterceptor retrofitInterceptor) {
        this.retrofitInterceptor = retrofitInterceptor;
    }

    public  ApiService getApiService(){
        Log.d(TAG, "retrofit client object: "+this);
        if(apiService == null)
            apiService = getRetrofitInstance().create(ApiService.class);
        return apiService;

    }

    public Retrofit getRetrofitInstance() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }

    private OkHttpClient getOkHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(retrofitInterceptor)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

    }

    private OkHttpClient getClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }


}
