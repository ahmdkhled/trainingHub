package com.example.traininghub.dagger;


import com.example.traininghub.helpers.TokenManager;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class TokenManagerModule {

    @Binds
    abstract TokenManager bindTokenManager(TokenManager tokenManager);
}
