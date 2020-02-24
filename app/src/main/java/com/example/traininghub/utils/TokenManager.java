package com.example.traininghub.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TokenManager {


    private static final String TPKEN_SHARED_PREF = "token_shared_pref";
    private static final String TOKEN_value = "token_value";
    private SharedPreferences sharedPreferences;

    @Inject
    public TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(TPKEN_SHARED_PREF, Context.MODE_PRIVATE);
    }

    public void saveToken(String token){
        sharedPreferences.edit().putString(TOKEN_value, token).apply();
    }

    public String getToken(){
        return sharedPreferences.getString(TOKEN_value, "null");

    }


}
