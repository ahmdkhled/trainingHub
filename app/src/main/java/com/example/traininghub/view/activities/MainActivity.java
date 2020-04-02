package com.example.traininghub.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.traininghub.R;
import com.example.traininghub.databinding.ActivityMainBinding;
import com.example.traininghub.view.fragments.AccountFragment;
import com.example.traininghub.view.fragments.MainFragment;
import com.example.traininghub.view.fragments.MyCoursesFragment;
import com.example.traininghub.viewModel.MainActivityVM;
import com.facebook.BuildConfig;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    Fragment currentFrag;
    public ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        MainActivityVM mainActivityVM= ViewModelProviders.of(this).get(MainActivityVM.class);


        binding.mainBottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            Log.d("FRAGG", "OnNavigationItemSelected: "+menuItem.getItemId());
            if (menuItem.getItemId()==R.id.home){
                Navigation.findNavController(this,R.id.main_nav_host_fragment).navigate(R.id.main_frag);
            } else if (menuItem.getItemId()==R.id.myCourses){
                Navigation.findNavController(this,R.id.main_nav_host_fragment).navigate(R.id.mycourses_frag);


            } else if (menuItem.getItemId()==R.id.account){
                Navigation.findNavController(this,R.id.main_nav_host_fragment)
                        .navigate(R.id.account_frag);

            }
            return true;
        });


    }



}
