package com.example.traininghub.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.traininghub.R;
import com.example.traininghub.view.fragments.AccountFragment;
import com.example.traininghub.view.fragments.MainFragment;
import com.example.traininghub.view.fragments.MyCoursesFragment;
import com.example.traininghub.viewModel.MainActivityVM;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainBottomNavigation)
    BottomNavigationView mainBottomNavigation;
    Fragment currentFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MainActivityVM mainActivityVM= ViewModelProviders.of(this).get(MainActivityVM.class);



        if (savedInstanceState==null)
        showFragment(mainActivityVM.getMainFragment(),false);


        mainBottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            Log.d("FRAGG", "OnNavigationItemSelected: "+menuItem.getItemId());
            if (menuItem.getItemId()==R.id.home){
                showFragment(mainActivityVM.getMainFragment(),false);
                currentFrag=mainActivityVM.getMainFragment();
            } else if (menuItem.getItemId()==R.id.myCourses){
                showFragment(mainActivityVM.getMyCoursesFragment(),false);

            } else if (menuItem.getItemId()==R.id.account){
                showFragment(mainActivityVM.getAccountFragment(),false);
            }
            return true;
        });


    }

    private void showFragment(Fragment fragment,boolean addToBackStack){
        Log.d("FRAGG", "added: "+fragment.isAdded());
        if (fragment.isAdded()){
            FragmentTransaction fragmentTransaction=getSupportFragmentManager()
                    .beginTransaction()
                    .show(fragment);
                    if (addToBackStack)fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();




        }else {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainFragContainer,fragment);
                    if (addToBackStack)fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

        }

        for(Fragment frag : getSupportFragmentManager().getFragments()){
            if(frag != fragment && frag.isAdded()){
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(frag)
                        .commit();
            }
        }

    }


}
