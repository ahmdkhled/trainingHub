package com.example.traininghub.utils;

import com.example.traininghub.model.APIResponse;
import com.example.traininghub.retrofit.RetrofitClient;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class APIErrorUtil {


    public static APIResponse parseError(RetrofitClient retrofitClient, Response<?> response) {
        Converter<ResponseBody, APIResponse> converter =
                retrofitClient.getRetrofitInstance()
                        .responseBodyConverter(APIResponse.class, new Annotation[0]);

        APIResponse error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIResponse();
        }

        return error;
    }
}
