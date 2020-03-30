package com.example.traininghub.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.traininghub.databinding.SigninMethodsFragBinding;
import com.example.traininghub.helpers.TokenManager;
import com.example.traininghub.models.Student;
import com.example.traininghub.view.activities.MainActivity;
import com.example.traininghub.view.activities.RegistrationActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SigninMethodsFrag extends Fragment {

    private SigninMethodsFragBinding binding;
    private CallbackManager callbackManager;
    private static final String TAG = "SigninMethodsFrag";
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN=115;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.signin_methods_frag,container,false);

        callbackManager = CallbackManager.Factory.create();

        binding.emailLogin
                .setOnClickListener(view->{
                    showFrag();
                });

        binding.fbLoginButton.setPermissions(Arrays.asList("email","public_profile"));



        binding.fbLoginButton
                .setOnClickListener(view->{
                    AccessToken accessToken=AccessToken.getCurrentAccessToken();
                    if (accessToken!=null&&!accessToken.isExpired()){
                        LoginManager.getInstance().logOut();
                        ((App)getActivity().getApplication()).getTokenManager().signOut();
                        return;
                    }


                        FacebookCallback<LoginResult> facebookCallback=new FacebookCallback<LoginResult>() {
                                @Override
                                public void onSuccess(LoginResult loginResult) {
                                    Log.d(TAG, "onSuccess: ");
                                    AccessToken accessToken=AccessToken.getCurrentAccessToken();
                                        getUserData(accessToken);
                                    Intent intent=new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancel() {
                                    Log.d(TAG, "onCancel: ");
                                }

                                @Override
                                public void onError(FacebookException error) {
                                    Log.d(TAG, "onError: "+error.getMessage());
                                    Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
                                }
                            };

                    LoginManager.getInstance().registerCallback(callbackManager,facebookCallback);
                    binding.fbLoginButton.registerCallback(callbackManager,facebookCallback);
                    LoginManager.getInstance()
                            .logInWithReadPermissions(this,Arrays.asList("email","public_profile"));


                });

        binding.gmailLogin
                .setOnClickListener(view->{
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);

                });

        return binding.getRoot();
    }

    private void showFrag(){
        LoginFragment loginFragment=new LoginFragment();
        getParentFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(((RegistrationActivity)getActivity()).mBinding.container.getId(),loginFragment,loginFragment.getClass().getSimpleName())
        .commit();
    }

    public void getUserData(AccessToken accessToken){
        GraphRequest graphRequest=GraphRequest.newMeRequest(
                accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String email = object.getString("email");
                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                    ((App)getActivity().getApplication()).getTokenManager()
                            .saveStudent(new Student(email,image));
                    Log.d(TAG, "onCompleted: "+name+"  >>  "+email+"  >>  "+image);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onCompleted: "+e.getMessage());
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()){
                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    ((App)getActivity().getApplication()).getTokenManager()
                            .saveStudent(new Student(account.getEmail(),account.getPhotoUrl().toString()));


                } catch (ApiException e) {
                    Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    Toast.makeText(getContext(), "failed to login ", Toast.LENGTH_SHORT).show();

                }


            }else {
                Toast.makeText(getContext(), "error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

    }
}
