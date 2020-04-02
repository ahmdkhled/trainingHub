package com.example.traininghub.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.traininghub.R;
import com.example.traininghub.databinding.FragmentLoginBinding;
import com.example.traininghub.models.APIResponse;
import com.example.traininghub.models.LoginResponse;
import com.example.traininghub.models.User;
import com.example.traininghub.viewModel.RegistrationViewModel;
import com.example.traininghub.viewModel.SigninFragVM;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginActivityTags";

    private RegistrationViewModel mRegistrationViewModel;
    private FragmentLoginBinding mBinding;
    private RegisterListener listener;
    Fragment fragment;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        View view = mBinding.getRoot();
        fragment=this;

        mRegistrationViewModel = new ViewModelProvider(getActivity()).get(RegistrationViewModel.class);
        SigninFragVM signinFragVM=new ViewModelProvider(this).get(SigninFragVM.class);


        mRegistrationViewModel.getLoginResponse().observe(getViewLifecycleOwner(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                //Toast.makeText(getContext(), "successful login", Toast.LENGTH_SHORT).show();
                mBinding.loginProgressBar.setVisibility(View.INVISIBLE);

                if (getActivity().getIntent().hasExtra(AccountFragment.LOGIN_REQUEST_CODE_KEY)
                &&getActivity().getIntent().getIntExtra(AccountFragment.LOGIN_REQUEST_CODE_KEY,0)==AccountFragment.LOGIN_REQUEST_CODE){
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
                else{
                    Navigation.findNavController(getView()).navigate(R.id.signIn_methods_frag);
                }

            }
        });


        mRegistrationViewModel.getLoginError().observe(getViewLifecycleOwner(), new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                mBinding.loginProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmailValid() && isPasswordValid()){
                    User user = new User(Objects.requireNonNull(mBinding.loginEmail.getText()).toString(),
                            Objects.requireNonNull(mBinding.loginPass.getText()).toString());

                    mRegistrationViewModel.login(user);
                    mBinding.loginProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        mBinding.createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.register_frag);
            }
        });

        return view;
    }



    private boolean isEmailValid(){
        String email = Objects.requireNonNull(mBinding.loginEmail.getText()).toString();
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
        String password = Objects.requireNonNull(mBinding.loginPass.getText()).toString();
        if(TextUtils.isEmpty(password)){
            mBinding.loginPassIL.setError("Password can't be empty");
            return false;
        }

        else{
            mBinding.loginPassIL.setError(null);
            return true;
        }
    }

    public RegistrationViewModel getRegistrationViewModel() {
        return mRegistrationViewModel;
    }

    public void setOnRegisterListener(RegisterListener listener){
        this.listener = listener;
    }



    public interface RegisterListener {
        void onRegisterButtonClicked();
        void onSuccessfulLogin();
    }



}
