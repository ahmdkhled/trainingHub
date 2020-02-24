package com.example.traininghub;


import android.content.Context;

import com.example.traininghub.utils.TokenManager;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class TokenManagerModule {

    @Binds
    abstract TokenManager bindTokenManager(TokenManager tokenManager);
}
