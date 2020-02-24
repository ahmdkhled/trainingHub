package com.example.traininghub.remote;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.traininghub.App;
import com.example.traininghub.model.APIResponse;
import com.example.traininghub.model.LoginResponse;
import com.example.traininghub.model.User;

public class LoginViewModel extends AndroidViewModel {


    private LoginRepository loginRepository;
    private MutableLiveData<LoginResponse> loginResponse;
    private MutableLiveData<APIResponse> registerResponse;
    private MutableLiveData<APIResponse> loginError;
    private MutableLiveData<APIResponse> registerError;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = ((App)getApplication()).getLoginRepository();
        loginResponse = loginRepository.getLoginResponse();
        loginError = loginRepository.getLoginError();
        registerResponse = loginRepository.getRegisterResponse();
        registerError = loginRepository.getRegisterError();
    }


    public void login(User user){
        loginResponse = loginRepository.login(user);

    }

    public void register(User user){
        registerResponse = loginRepository.register(user);

    }
    public MutableLiveData<LoginResponse> getLoginResponse() {
        return loginResponse;
    }

    public MutableLiveData<APIResponse> getLoginError() {
        return loginError;
    }

    public MutableLiveData<APIResponse> getRegisterResponse() {
        return registerResponse;
    }

    public MutableLiveData<APIResponse> getRegisterError() {
        return registerError;
    }
}
