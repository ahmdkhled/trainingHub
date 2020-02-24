package com.example.traininghub;


import android.content.Context;

import com.example.traininghub.login.LoginRepository;
import com.example.traininghub.utils.TokenManager;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component
public interface AppComponent {
    TokenManager getTokenManager();
    LoginRepository getLoginRepository();

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder context(Context context);

        AppComponent build();
    }
}
