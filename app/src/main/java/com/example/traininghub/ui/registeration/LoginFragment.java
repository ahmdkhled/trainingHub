package com.example.traininghub.ui.registeration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.traininghub.R;
import com.example.traininghub.databinding.FragmentLoginBinding;
import com.example.traininghub.model.APIResponse;
import com.example.traininghub.model.LoginResponse;
import com.example.traininghub.model.User;
import com.example.traininghub.remote.LoginViewModel;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginActivityTags";

    private LoginViewModel mLoginViewModel;
    private FragmentLoginBinding mBinding;
    private RegisterListener listener;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        View view = mBinding.getRoot();

        mLoginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        mLoginViewModel.getLoginResponse().observe(getViewLifecycleOwner(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                Toast.makeText(getContext(), "successful login", Toast.LENGTH_SHORT).show();
            }
        });


        mLoginViewModel.getLoginError().observe(getViewLifecycleOwner(), new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmailValid() && isPasswordValid()){
                    User user = new User(mBinding.loginEmail.getText().toString(),
                            mBinding.loginPass.getText().toString());

                    mLoginViewModel.login(user);
                }
            }
        });

        mBinding.createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onRegisterButtonClicked();
                }
            }
        });

        return view;
    }



    private boolean isEmailValid(){
        String email = mBinding.loginEmail.getText().toString();
        if(TextUtils.isEmpty(email)){
            mBinding.loginEmailIL.setError("Email can't be empty");
            return false;
        }

        else{
            mBinding.loginEmailIL.setError(null);
            return true;
        }
    }

    private boolean isPasswordValid(){
        String password = mBinding.loginPass.getText().toString();
        if(TextUtils.isEmpty(password)){
            mBinding.loginPassIL.setError("Password can't be empty");
            return false;
        }

        else{
            mBinding.loginPassIL.setError(null);
            return true;
        }
    }

    public void setOnRegisterListener(RegisterListener listener){
        this.listener = listener;
    }



    public interface RegisterListener {
        void onRegisterButtonClicked();
    }

}
