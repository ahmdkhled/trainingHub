package com.example.traininghub.Repo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.traininghub.R;
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
    private MutableLiveData<LoginResponse> loginResponse=new MutableLiveData<>();
    private MutableLiveData<APIResponse> loginError =  new MutableLiveData<>();
    private MutableLiveData<LoginResponse> registerResponse =  new MutableLiveData<>();
    private MutableLiveData<APIResponse> registerError =  new MutableLiveData<>();
    private RetrofitClient retrofitClient;
    private TokenManager tokenManager;
    private Context context;


    @Inject
    public LoginRepository(Context context, RetrofitClient retrofitClient, TokenManager tokenManager)
    {
        this.context = context;
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
                    if(error instanceof HttpException) {
                        APIResponse apiResponse = APIErrorUtil.parseError(retrofitClient, ((HttpException) error).response());
                        Log.d(TAG, "onResponse: response is not successful "
                                + apiResponse.getMessage());
                        loginError.setValue(apiResponse);
                    }else{
                        loginError.setValue(new APIResponse(context.getString(R.string.error_message)));
                    }


                });


        return loginResponse;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<LoginResponse> register(User user){

        retrofitClient.getApiService().register(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response-> {
                    Log.d(TAG, "register: "+response.getAccessToken());
                    tokenManager.saveToken(response.getAccessToken());
                    registerResponse.setValue(response);
                }, error->{
                    if(error instanceof HttpException) {
                        APIResponse apiResponse = APIErrorUtil.parseError(retrofitClient, ((HttpException) error).response());
                        registerError.setValue(apiResponse);
                        Log.d(TAG, "register: error: " + apiResponse.getMessage());
                    }else{
                        registerError.setValue(new APIResponse(context.getString(R.string.error_message)));
                    }

                });


        return registerResponse;
    }

    public MutableLiveData<LoginResponse> getRegisterResponse() {
        return registerResponse;
    }
    public MutableLiveData<LoginResponse> getLoginResponse(){
        return loginResponse;
    }

    public MutableLiveData<APIResponse> getLoginError(){
        return loginError;
    }



    public MutableLiveData<APIResponse> getRegisterError() {
        return registerError;
    }
}
