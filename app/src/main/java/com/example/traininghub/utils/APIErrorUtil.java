package com.example.traininghub.utils;

import com.example.traininghub.model.APIError;
import com.example.traininghub.retrofit.RetrofitClient;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class APIErrorUtil {


    public static APIError parseError(RetrofitClient retrofitClient, Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                retrofitClient.getRetrofitInstance()
                        .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}
