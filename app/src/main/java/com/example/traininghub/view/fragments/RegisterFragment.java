package com.example.traininghub.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.traininghub.R;
import com.example.traininghub.databinding.FragmentRegisterBinding;
import com.example.traininghub.models.APIResponse;
import com.example.traininghub.models.LoginResponse;
import com.example.traininghub.models.User;
import com.example.traininghub.view.activities.RegistrationActivity;
import com.example.traininghub.viewModel.RegistrationViewModel;
import com.example.traininghub.helpers.PasswordValidation;

public class RegisterFragment extends Fragment {


    private static final String TAG = RegistrationActivity.TAG;
    private FragmentRegisterBinding mBinding;
    private RegistrationViewModel mRegistrationViewModel;
    private LoginListener listener;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);

        View view = mBinding.getRoot();
        mRegistrationViewModel = new ViewModelProvider(getActivity()).get(RegistrationViewModel.class);
        mRegistrationViewModel.getRegisterResponse().observe(getViewLifecycleOwner(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse response) {
                Log.d(TAG, "onChanged: register response: "+response.getAccessToken());
                mBinding.ProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), getString(R.string.successful_registration), Toast.LENGTH_SHORT).show();
                if (getActivity().getIntent().hasExtra(AccountFragment.LOGIN_REQUEST_CODE_KEY)
                        &&getActivity().getIntent().getIntExtra(AccountFragment.LOGIN_REQUEST_CODE_KEY,0)==AccountFragment.LOGIN_REQUEST_CODE){
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
            }
        });

        mRegistrationViewModel.getRegisterError().observe(getViewLifecycleOwner(), new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                mBinding.ProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        mBinding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUserNameValid() && isEmailValid() && isPasswordValid()){
                    String username = mBinding.registerName.getText().toString();
                    String email = mBinding.registerEmail.getText().toString();
                    String password = mBinding.registerPassword.getText().toString();
                    mRegistrationViewModel.register(new User(username, email, password));
                    mBinding.ProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        mBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onLoginButtonClicked();
                }
            }
        });
        return view;
    }

    private boolean isEmailValid(){
        String email = mBinding.registerEmail.getText().toString();
        if(TextUtils.isEmpty(email)){
            mBinding.registerEmailIl.setError("Email can't be empty");
            return false;
        }

        else if(! Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mBinding.registerEmailIl.setError("Please enter valid email");
            return false;
        }
        else{
            mBinding.registerEmailIl.setError(null);
            return true;
        }
    }

    private boolean isPasswordValid(){
        String password = mBinding.registerPassword.getText().toString();
        if(TextUtils.isEmpty(password)){
            mBinding.registerPasswordIl.setError("Password can't be empty");
            return false;
        }

        else if (! PasswordValidation.PASSWORD_PATTERN.matcher(password).matches()){
            mBinding.registerPasswordIl.setError("Too weak password");
            return false;
        }
        else{
            mBinding.registerPasswordIl.setError(null);
            return true;
        }
    }

    private boolean isUserNameValid(){
        String userName = mBinding.registerName.getText().toString();
        if(TextUtils.isEmpty(userName)){
            mBinding.registerNameIl.setError("Username can't be empty");
            return false;
        }

        else{
            mBinding.registerNameIl.setError(null);
            return true;
        }
    }

    public void setOnLoginListener(LoginListener listener){
        this.listener = listener;
    }

    public interface LoginListener{
        void onLoginButtonClicked();
    }
}
