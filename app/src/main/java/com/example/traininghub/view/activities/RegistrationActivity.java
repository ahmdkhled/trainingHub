package com.example.traininghub.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.traininghub.R;
import com.example.traininghub.databinding.ActivityRegisterationActivtyBinding;
import com.example.traininghub.view.fragments.LoginFragment;
import com.example.traininghub.view.fragments.RegisterFragment;
import com.example.traininghub.viewModel.RegistrationViewModel;

public class RegistrationActivity extends AppCompatActivity {


    public static final String TAG = "RegistrationTags";

    public ActivityRegisterationActivtyBinding mBinding;
    FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_registeration_activty);

        // init view model
        RegistrationViewModel mRegistrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        mFragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null) {
            LoginFragment loginFragment = new LoginFragment();
            RegisterFragment registerFragment = new RegisterFragment();
            mFragmentManager.beginTransaction()
                    .add(mBinding.container.getId(), loginFragment)
                    .commit();

            loginFragment.setOnRegisterListener(new LoginFragment.RegisterListener() {
                @Override
                public void onRegisterButtonClicked() {
                    replaceFragment(registerFragment);
                }

                @Override
                public void onSuccessfulLogin() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });


            registerFragment.setOnLoginListener(new RegisterFragment.LoginListener() {
                @Override
                public void onLoginButtonClicked() {
                    replaceFragment(loginFragment);
                }
            });
        }
    }

    private void replaceFragment(Fragment fragment) {
        mFragmentManager.beginTransaction()
                .replace(mBinding.container.getId(), fragment)
                .addToBackStack(null)
                .commit();
    }
}
