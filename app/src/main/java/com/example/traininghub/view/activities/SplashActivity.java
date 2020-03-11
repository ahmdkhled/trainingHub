package com.example.traininghub.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.traininghub.App;
import com.example.traininghub.R;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(((App) getApplication()).getTokenManager().isLogin()){
                    intent=new Intent(SplashActivity.this,MainActivity.class);
                }else{
                    intent=new Intent(SplashActivity.this,RegistrationActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
