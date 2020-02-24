package com.example.traininghub.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.traininghub.R;
import com.example.traininghub.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {


    private ActivityRegisterBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
    }
}
