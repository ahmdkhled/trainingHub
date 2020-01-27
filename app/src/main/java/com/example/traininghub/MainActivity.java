package com.example.traininghub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.traininghub.view.fragments.AccountFragment;
import com.example.traininghub.view.fragments.MainFragment;
import com.example.traininghub.view.fragments.MyCoursesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainBottomNavigation)
    BottomNavigationView mainBottomNavigation;
    Fragment currentFrag;
    MainFragment mainFragment=new MainFragment();
    MyCoursesFragment myCoursesFragment=new MyCoursesFragment();
    AccountFragment accountFragment=new AccountFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);




        showFragment(mainFragment);


        mainBottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId()==R.id.home){
                showFragment(mainFragment);
                currentFrag=mainFragment;
            } else if (menuItem.getItemId()==R.id.myCourses){
                showFragment(myCoursesFragment);

            } else if (menuItem.getItemId()==R.id.account){
                showFragment(accountFragment);
            }
            return true;
        });


    }

    private void showFragment(Fragment fragment){
        if (fragment.isAdded()){
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(fragment)
                    .commit();
        }else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainFragContainer,fragment)
                    .addToBackStack(null)
                    .commit();
        }

        for(Fragment frag : getSupportFragmentManager().getFragments()){
            if(frag != fragment && frag.isAdded()){
                getSupportFragmentManager().beginTransaction().hide(frag).commit();
            }
        }
    }


}
