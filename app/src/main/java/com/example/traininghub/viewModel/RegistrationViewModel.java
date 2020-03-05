package com.example.traininghub.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.traininghub.App;
import com.example.traininghub.Repo.LoginRepository;
import com.example.traininghub.models.APIResponse;
import com.example.traininghub.models.LoginResponse;
import com.example.traininghub.models.User;

public class RegistrationViewModel extends AndroidViewModel {


    private LoginRepository loginRepository;
    private MutableLiveData<LoginResponse> loginResponse;
    private MutableLiveData<LoginResponse> registerResponse;
    private MutableLiveData<APIResponse> loginError;
    private MutableLiveData<APIResponse> registerError;

    public RegistrationViewModel(@NonNull Application application) {
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

    public MutableLiveData<LoginResponse> getRegisterResponse() {
        return registerResponse;
    }

    public MutableLiveData<APIResponse> getRegisterError() {
        return registerError;
    }
}
