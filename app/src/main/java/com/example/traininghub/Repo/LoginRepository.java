package com.example.traininghub.Repo;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.traininghub.models.APIResponse;
import com.example.traininghub.models.LoginResponse;
import com.example.traininghub.models.User;
import com.example.traininghub.retrofit.RetrofitClient;
import com.example.traininghub.helpers.APIErrorUtil;
import com.example.traininghub.helpers.TokenManager;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

@Singleton
public class LoginRepository {

    private static final String TAG = "LoginRepositoryTags";
    private MutableLiveData<LoginResponse> loginResponse =  new MutableLiveData<>();
    private MutableLiveData<APIResponse> loginError =  new MutableLiveData<>();
    private MutableLiveData<APIResponse> registerResponse =  new MutableLiveData<>();
    private MutableLiveData<APIResponse> registerError =  new MutableLiveData<>();
    private MutableLiveData<Boolean> loggingIn =  new MutableLiveData<>();
    private MutableLiveData<Boolean> registering =  new MutableLiveData<>();
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

        loggingIn.setValue(true);
        retrofitClient.getApiService().login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response-> {
                    loggingIn.setValue(false);
                    Log.d(TAG, "onResponseSuccessful: token : "+response.getAccessToken());
                    tokenManager.saveToken(response.getAccessToken());
                    loginResponse.setValue(response);
                }, error->{
                    loggingIn.setValue(false);
                    APIResponse apiResponse = APIErrorUtil.parseError(retrofitClient, ((HttpException) error).response());
                    Log.d(TAG, "onResponse: response is not successful "
                            + apiResponse.getMessage());
                    loginError.setValue(apiResponse);


                });


        return loginResponse;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<APIResponse> register(User user){

        registering.setValue(true);
        retrofitClient.getApiService().register(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response-> {
                    Log.d(TAG, "register: "+response.getMessage());
                    registerResponse.setValue(response);
                    registering.setValue(false);
                }, error->{
                   registering.setValue(false);
                    APIResponse apiResponse = APIErrorUtil.parseError(retrofitClient, ((HttpException) error).response());
                    registerError.setValue(apiResponse);
                    Log.d(TAG, "register: error: "+apiResponse.getMessage());

                });


        return registerResponse;
    }

    public MutableLiveData<APIResponse> getRegisterResponse() {
        return registerResponse;
    }
    public MutableLiveData<LoginResponse> getLoginResponse(){
        return loginResponse;
    }

    public MutableLiveData<APIResponse> getLoginError(){
        return loginError;
    }

    public MutableLiveData<Boolean> getLoggingIn() {
        return loggingIn;
    }

    public MutableLiveData<Boolean> getRegistering() {
        return registering;
    }

    public MutableLiveData<APIResponse> getRegisterError() {
        return registerError;
    }
}
