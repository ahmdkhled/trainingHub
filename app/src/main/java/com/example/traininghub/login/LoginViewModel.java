package com.example.traininghub.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.traininghub.App;
import com.example.traininghub.model.APIError;
import com.example.traininghub.model.LoginResponse;
import com.example.traininghub.model.User;

public class LoginViewModel extends AndroidViewModel {


    private LoginRepository loginRepository;
    private MutableLiveData<LoginResponse> loginResponse;
    private MutableLiveData<APIError> loginError;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = ((App)getApplication()).getLoginRepository();
        loginResponse = loginRepository.getLoginResponse();
        loginError = loginRepository.getLoginError();
    }


    public void login(User user){
        loginResponse = loginRepository.login(user);

    }

    public MutableLiveData<LoginResponse> getLoginResponse() {
        return loginResponse;
    }

    public MutableLiveData<APIError> getLoginError() {
        return loginError;
    }
}
