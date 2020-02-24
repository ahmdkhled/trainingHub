package com.example.traininghub;

import android.app.Application;

import com.example.traininghub.dagger.AppComponent;
import com.example.traininghub.Repo.LoginRepository;
import com.example.traininghub.dagger.DaggerAppComponent;
import com.example.traininghub.helpers.TokenManager;

public class App extends Application {

    private TokenManager tokenManager;
    private LoginRepository loginRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = DaggerAppComponent.builder().context(getApplicationContext())
                                    .build();

        tokenManager = appComponent.getTokenManager();
        loginRepository = appComponent.getLoginRepository();
    }

    public TokenManager getTokenManager(){
        return tokenManager;
    }
    public LoginRepository getLoginRepository(){
        return loginRepository;
    }
}
