package com.example.traininghub.dagger;


import android.content.Context;

import com.example.traininghub.Repo.LoginRepository;
import com.example.traininghub.helpers.TokenManager;

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
