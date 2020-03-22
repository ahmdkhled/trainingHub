package com.example.traininghub.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.traininghub.App;
import com.example.traininghub.R;
import com.example.traininghub.databinding.FragmentAccountBinding;
import com.example.traininghub.helpers.TokenManager;
import com.example.traininghub.view.activities.RegistrationActivity;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    TokenManager tokenManager;
    public static final int LOGIN_REQUEST_CODE=112;
    public static final String LOGIN_REQUEST_CODE_KEY="login_key";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_account,container,false);

        tokenManager=((App)getActivity().getApplication()).getTokenManager();
        setTokenManager();


        binding.signOut.setOnClickListener(view -> {
            if (tokenManager.isLogin()){
                tokenManager.deleteToken();
                setTokenManager();
            }else {
                Intent intent=new Intent(getContext(), RegistrationActivity.class);
                intent.putExtra(LOGIN_REQUEST_CODE_KEY,LOGIN_REQUEST_CODE);
                startActivityForResult(intent,LOGIN_REQUEST_CODE);
            }

        });

        return binding.getRoot();
    }

    private void setTokenManager(){
        binding.setTokenManager(tokenManager);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==LOGIN_REQUEST_CODE&&resultCode== Activity.RESULT_OK)
            setTokenManager();
    }
}
