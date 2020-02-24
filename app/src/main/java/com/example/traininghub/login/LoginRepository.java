package com.example.traininghub.login;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.traininghub.model.APIError;
import com.example.traininghub.model.LoginResponse;
import com.example.traininghub.model.User;
import com.example.traininghub.retrofit.RetrofitClient;
import com.example.traininghub.utils.APIErrorUtil;
import com.example.traininghub.utils.TokenManager;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

@Singleton
public class LoginRepository {

    private static final String TAG = "LoginRepositoryTags";
    private MutableLiveData<LoginResponse> loginResponse =  new MutableLiveData<>();
    private MutableLiveData<APIError> loginError =  new MutableLiveData<>();
    private RetrofitClient retrofitClient;
    private TokenManager tokenManager;


    @Inject
    public LoginRepository(RetrofitClient retrofitClient, TokenManager tokenManager)
    {
        this.retrofitClient = retrofitClient;
        this.tokenManager = tokenManager;
    }


    @SuppressLint("CheckResult")
    public MutableLiveData<LoginResponse> login(User user){

        retrofitClient.getApiService().login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response-> {
                    Log.d(TAG, "onResponseSuccessful: token : "+response.getAccessToken());
                    tokenManager.saveToken(response.getAccessToken());
                    loginResponse.setValue(response);
                }, error->{

                    APIError apiError = APIErrorUtil.parseError(retrofitClient, ((HttpException) error).response());
                    Log.d(TAG, "onResponse: response is not successful "
                            +apiError.getMessage());
                    loginError.setValue(apiError);


                });


        return loginResponse;
    }

    public MutableLiveData<LoginResponse> getLoginResponse(){
        return loginResponse;
    }

    public MutableLiveData<APIError> getLoginError(){
        return loginError;
    }
}
