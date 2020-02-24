package com.example.traininghub.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.traininghub.R;
import com.example.traininghub.databinding.ActivityLoginBinding;
import com.example.traininghub.login.LoginViewModel;
import com.example.traininghub.model.APIError;
import com.example.traininghub.model.LoginResponse;
import com.example.traininghub.model.User;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivityTags";

    private LoginViewModel mLoginViewModel;
    private ActivityLoginBinding activityLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLogin = DataBindingUtil.setContentView(this, R.layout.activity_login);


        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mLoginViewModel.getLoginResponse().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                Toast.makeText(LoginActivity.this, "successful login", Toast.LENGTH_SHORT).show();
            }
        });


        mLoginViewModel.getLoginError().observe(this, new Observer<APIError>() {
            @Override
            public void onChanged(APIError apiError) {
                Toast.makeText(LoginActivity.this, apiError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        activityLogin.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmailValid() && isPasswordValid()){
                    User user = new User(activityLogin.loginEmail.getText().toString(),
                            activityLogin.loginPass.getText().toString());

                    mLoginViewModel.login(user);
                }
            }
        });

        activityLogin.createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private boolean isEmailValid(){
        String email = activityLogin.loginEmail.getText().toString();
        if(TextUtils.isEmpty(email)){
            activityLogin.loginEmailIL.setError("Email can't be empty");
            return false;
        }

        else{
            activityLogin.loginEmailIL.setError(null);
            return true;
        }
    }

    private boolean isPasswordValid(){
        String password = activityLogin.loginPass.getText().toString();
        if(TextUtils.isEmpty(password)){
            activityLogin.loginPassIL.setError("Password can't be empty");
            return false;
        }

        else{
            activityLogin.loginPassIL.setError(null);
            return true;
        }
    }


}
