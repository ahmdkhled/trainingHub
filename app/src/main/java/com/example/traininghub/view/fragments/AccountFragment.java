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
import androidx.navigation.Navigation;

import com.example.traininghub.App;
import com.example.traininghub.R;
import com.example.traininghub.databinding.FragmentAccountBinding;
import com.example.traininghub.helpers.TokenManager;
import com.example.traininghub.models.Student;
import com.example.traininghub.view.activities.MainActivity;
import com.example.traininghub.view.activities.ProfileActivity;
import com.example.traininghub.view.activities.RegistrationActivity;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    TokenManager tokenManager;
    public static final int LOGIN_REQUEST_CODE=112;
    public static final String LOGIN_REQUEST_CODE_KEY="login_key";
    SigninMethodsFrag signinMethodsFrag;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_account,container,false);

        tokenManager=((App)getActivity().getApplication()).getTokenManager();
        setTokenManager();
        signinMethodsFrag=new SigninMethodsFrag();

        binding.signOut.setOnClickListener(view -> {
            if (tokenManager.isLogin()){
                Toast.makeText(getContext(), "is logged in", Toast.LENGTH_SHORT).show();
                tokenManager.signOut();
                AccessToken accessToken=AccessToken.getCurrentAccessToken();

                if (accessToken!=null&&!accessToken.isExpired()){
                    LoginManager.getInstance().logOut();
                    ((App)getActivity().getApplication()).getTokenManager().signOut();
                }

                if (GoogleSignIn.getLastSignedInAccount(getContext())!=null){
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    GoogleSignIn.getClient(getContext(), gso)
                    .signOut();
                }

                setTokenManager();
            }else {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment)
                        .navigate(R.id.signIn_methods_frag);
            }

        });

        binding.profile.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);

        });

        signinMethodsFrag
                .setOnCompleteLoginListener(() ->  {
                    getParentFragmentManager().popBackStackImmediate();
                    setTokenManager();
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
