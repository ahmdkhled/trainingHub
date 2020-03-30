package com.example.traininghub.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.traininghub.models.Student;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TokenManager {


    private static final String TPKEN_SHARED_PREF = "token_shared_pref";
    private static final String TOKEN_value = "token_value";
    private static final String STUDENT_VALUE = "student_value";
    private SharedPreferences sharedPreferences;
    private Context context;

    @Inject
    public TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(TPKEN_SHARED_PREF, Context.MODE_PRIVATE);
        this.context=context;
    }

    public void saveToken(String token){
        Log.d("MainActivityTags", "saveToken: "+token);
        sharedPreferences.edit().putString(TOKEN_value, "Bearer "+token).apply();
    }

    public String getToken(){
        Log.d("MainActivityTags", "getToken: "+sharedPreferences.getString(TOKEN_value, "null"));
        return sharedPreferences.getString(TOKEN_value, "null");

    }



    public boolean isLogin() {
        return ! sharedPreferences.getString(TOKEN_value,"null").equals("null")
                ||(AccessToken.getCurrentAccessToken()!=null&&!AccessToken.getCurrentAccessToken().isExpired())
                || (GoogleSignIn.getLastSignedInAccount(context)!=null);

    }


    public Student getStudent(){
        return new Gson().fromJson(sharedPreferences.getString(STUDENT_VALUE,null ),Student.class);
    }

    public void saveStudent(Student student){
        sharedPreferences.edit()
                .putString(STUDENT_VALUE,new Gson().toJson(student)).apply();
    }
    public void signOut(){
        sharedPreferences.edit()
                .putString(STUDENT_VALUE,"").apply();
        sharedPreferences.edit()
                .putString(TOKEN_value,"null").apply();
    }
}
