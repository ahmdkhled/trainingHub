package com.example.traininghub.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
        Log.d("MainActivityTags", "saveToken: "+token);
        sharedPreferences.edit().putString(TOKEN_value, "Bearer "+token).apply();
    }

    public String getToken(){
        Log.d("MainActivityTags", "getToken: "+sharedPreferences.getString(TOKEN_value, "null"));
        return sharedPreferences.getString(TOKEN_value, "null");

    }


    public void deleteToken(){
        sharedPreferences.edit().putString(TOKEN_value,"null").apply();
    }

    public boolean isLogin() {
        return ! sharedPreferences.getString(TOKEN_value,"null").equals("null");
    }
}
