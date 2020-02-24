package com.example.traininghub.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.traininghub.R;
import com.example.traininghub.databinding.ActivityRegisterBinding;
import com.example.traininghub.model.APIResponse;
import com.example.traininghub.model.User;
import com.example.traininghub.remote.LoginViewModel;
import com.example.traininghub.utils.PasswordValidation;

public class RegisterFragment extends Fragment {


    private static final String TAG = RegistrationActivity.TAG;
    private ActivityRegisterBinding mBinding;
    private LoginViewModel mViewModel;
    private LoginListener listener;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_register, container, false);

        View view = mBinding.getRoot();
        mViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        mViewModel.getRegisterResponse().observe(getViewLifecycleOwner(), new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                Log.d(TAG, "onChanged: register response: "+apiResponse.getMessage());
                Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getRegisterError().observe(getViewLifecycleOwner(), new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
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
                    mViewModel.register(new User(username, email, password));
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
